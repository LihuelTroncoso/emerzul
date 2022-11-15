import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IUsuario } from 'app/shared/model/usuario.model';
import { getEntities as getUsuarios } from 'app/entities/usuario/usuario.reducer';
import { IPaciente } from 'app/shared/model/paciente.model';
import { getEntity, updateEntity, createEntity, reset } from './paciente.reducer';

export const PacienteUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const usuarios = useAppSelector(state => state.usuario.entities);
  const pacienteEntity = useAppSelector(state => state.paciente.entity);
  const loading = useAppSelector(state => state.paciente.loading);
  const updating = useAppSelector(state => state.paciente.updating);
  const updateSuccess = useAppSelector(state => state.paciente.updateSuccess);

  const handleClose = () => {
    navigate('/paciente');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getUsuarios({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...pacienteEntity,
      ...values,
      usuario: usuarios.find(it => it.id.toString() === values.usuario.toString()),
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
          ...pacienteEntity,
          usuario: pacienteEntity?.usuario?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="emerzulApp.paciente.home.createOrEditLabel" data-cy="PacienteCreateUpdateHeading">
            Crear o editar Paciente
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="paciente-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Intervenciones"
                id="paciente-intervenciones"
                name="intervenciones"
                data-cy="intervenciones"
                type="text"
              />
              <ValidatedField
                label="Antecedentes Familiares"
                id="paciente-antecedentesFamiliares"
                name="antecedentesFamiliares"
                data-cy="antecedentesFamiliares"
                type="text"
              />
              <ValidatedField
                label="Estado General"
                id="paciente-estadoGeneral"
                name="estadoGeneral"
                data-cy="estadoGeneral"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                }}
              />
              <ValidatedField label="Enfermedades" id="paciente-enfermedades" name="enfermedades" data-cy="enfermedades" type="text" />
              <ValidatedField
                label="Discapacidades"
                id="paciente-discapacidades"
                name="discapacidades"
                data-cy="discapacidades"
                type="text"
              />
              <ValidatedField label="Tipo Sangre" id="paciente-tipoSangre" name="tipoSangre" data-cy="tipoSangre" type="text" />
              <ValidatedField id="paciente-usuario" name="usuario" data-cy="usuario" label="Usuario" type="select">
                <option value="" key="0" />
                {usuarios
                  ? usuarios.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/paciente" replace color="info">
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

export default PacienteUpdate;
