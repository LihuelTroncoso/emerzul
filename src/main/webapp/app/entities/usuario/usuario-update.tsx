import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IHabitacion } from 'app/shared/model/habitacion.model';
import { getEntities as getHabitacions } from 'app/entities/habitacion/habitacion.reducer';
import { IReporte } from 'app/shared/model/reporte.model';
import { getEntities as getReportes } from 'app/entities/reporte/reporte.reducer';
import { IUsuario } from 'app/shared/model/usuario.model';
import { getEntity, updateEntity, createEntity, reset } from './usuario.reducer';

export const UsuarioUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const habitacions = useAppSelector(state => state.habitacion.entities);
  const reportes = useAppSelector(state => state.reporte.entities);
  const usuarioEntity = useAppSelector(state => state.usuario.entity);
  const loading = useAppSelector(state => state.usuario.loading);
  const updating = useAppSelector(state => state.usuario.updating);
  const updateSuccess = useAppSelector(state => state.usuario.updateSuccess);

  const handleClose = () => {
    navigate('/usuario');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getHabitacions({}));
    dispatch(getReportes({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...usuarioEntity,
      ...values,
      zona: habitacions.find(it => it.id.toString() === values.zona.toString()),
      reporte: reportes.find(it => it.id.toString() === values.reporte.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...usuarioEntity,
          zona: usuarioEntity?.zona?.id,
          reporte: usuarioEntity?.reporte?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="emerzulApp.usuario.home.createOrEditLabel" data-cy="UsuarioCreateUpdateHeading">
            Crear o editar Usuario
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="usuario-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Nombre"
                id="usuario-nombre"
                name="nombre"
                data-cy="nombre"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                }}
              />
              <ValidatedField
                label="Contrasena"
                id="usuario-contrasena"
                name="contrasena"
                data-cy="contrasena"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                }}
              />
              <ValidatedField
                label="Rol"
                id="usuario-rol"
                name="rol"
                data-cy="rol"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                }}
              />
              <ValidatedField
                label="Nombre Usuario"
                id="usuario-nombreUsuario"
                name="nombreUsuario"
                data-cy="nombreUsuario"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                }}
              />
              <ValidatedField
                label="Sexo"
                id="usuario-sexo"
                name="sexo"
                data-cy="sexo"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                }}
              />
              <ValidatedField
                label="Edad"
                id="usuario-edad"
                name="edad"
                data-cy="edad"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                }}
              />
              <ValidatedField
                label="Apellido"
                id="usuario-apellido"
                name="apellido"
                data-cy="apellido"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                }}
              />
              <ValidatedField id="usuario-zona" name="zona" data-cy="zona" label="Zona" type="select">
                <option value="" key="0" />
                {habitacions
                  ? habitacions.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="usuario-reporte" name="reporte" data-cy="reporte" label="Reporte" type="select">
                <option value="" key="0" />
                {reportes
                  ? reportes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/usuario" replace color="info">
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

export default UsuarioUpdate;
