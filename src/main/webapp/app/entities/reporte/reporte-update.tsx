import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ILlamado } from 'app/shared/model/llamado.model';
import { getEntities as getLlamados } from 'app/entities/llamado/llamado.reducer';
import { IReporte } from 'app/shared/model/reporte.model';
import { getEntity, updateEntity, createEntity, reset } from './reporte.reducer';

export const ReporteUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const llamados = useAppSelector(state => state.llamado.entities);
  const reporteEntity = useAppSelector(state => state.reporte.entity);
  const loading = useAppSelector(state => state.reporte.loading);
  const updating = useAppSelector(state => state.reporte.updating);
  const updateSuccess = useAppSelector(state => state.reporte.updateSuccess);

  const handleClose = () => {
    navigate('/reporte');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getLlamados({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.hora = convertDateTimeToServer(values.hora);

    const entity = {
      ...reporteEntity,
      ...values,
      llamado: llamados.find(it => it.id.toString() === values.llamado.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          hora: displayDefaultDateTime(),
        }
      : {
          ...reporteEntity,
          hora: convertDateTimeFromServer(reporteEntity.hora),
          llamado: reporteEntity?.llamado?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="emerzulApp.reporte.home.createOrEditLabel" data-cy="ReporteCreateUpdateHeading">
            Crear o editar Reporte
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="reporte-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Area"
                id="reporte-area"
                name="area"
                data-cy="area"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                }}
              />
              <ValidatedField
                label="Origen"
                id="reporte-origen"
                name="origen"
                data-cy="origen"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                }}
              />
              <ValidatedField
                label="Hora"
                id="reporte-hora"
                name="hora"
                data-cy="hora"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                }}
              />
              <ValidatedField id="reporte-llamado" name="llamado" data-cy="llamado" label="Llamado" type="select">
                <option value="" key="0" />
                {llamados
                  ? llamados.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/reporte" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Volver</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Guardar
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default ReporteUpdate;
