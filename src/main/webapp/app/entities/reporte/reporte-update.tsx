import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IReporte } from 'app/shared/model/reporte.model';
import { getEntity, updateEntity, createEntity, reset } from './reporte.reducer';

export const ReporteUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

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
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.horaInicio = convertDateTimeToServer(values.horaInicio);
    values.horaFinal = convertDateTimeToServer(values.horaFinal);

    const entity = {
      ...reporteEntity,
      ...values,
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
          horaInicio: displayDefaultDateTime(),
          horaFinal: displayDefaultDateTime(),
        }
      : {
          ...reporteEntity,
          horaInicio: convertDateTimeFromServer(reporteEntity.horaInicio),
          horaFinal: convertDateTimeFromServer(reporteEntity.horaFinal),
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
                label="Hora Inicio"
                id="reporte-horaInicio"
                name="horaInicio"
                data-cy="horaInicio"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                }}
              />
              <ValidatedField
                label="Hora Final"
                id="reporte-horaFinal"
                name="horaFinal"
                data-cy="horaFinal"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                }}
              />
              <ValidatedField
                label="Tipo"
                id="reporte-tipo"
                name="tipo"
                data-cy="tipo"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                }}
              />
              <ValidatedField label="Alerta" id="reporte-alerta" name="alerta" data-cy="alerta" check type="checkbox" />
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
