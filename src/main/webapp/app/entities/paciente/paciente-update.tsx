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
import { ILlamado } from 'app/shared/model/llamado.model';
import { getEntities as getLlamados } from 'app/entities/llamado/llamado.reducer';
import { IPaciente } from 'app/shared/model/paciente.model';
import { getEntity, updateEntity, createEntity, reset } from './paciente.reducer';

export const PacienteUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const habitacions = useAppSelector(state => state.habitacion.entities);
  const llamados = useAppSelector(state => state.llamado.entities);
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

    dispatch(getHabitacions({}));
    dispatch(getLlamados({}));
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
      habitacion: habitacions.find(it => it.id.toString() === values.habitacion.toString()),
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
      ? {}
      : {
          ...pacienteEntity,
          habitacion: pacienteEntity?.habitacion?.id,
          llamado: pacienteEntity?.llamado?.id,
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
                label="Nombre"
                id="paciente-nombre"
                name="nombre"
                data-cy="nombre"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                }}
              />
              <ValidatedField
                label="Dni"
                id="paciente-dni"
                name="dni"
                data-cy="dni"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                  validate: v => isNumber(v) || 'Este campo debe ser un número.',
                }}
              />
              <ValidatedField
                label="Sexo"
                id="paciente-sexo"
                name="sexo"
                data-cy="sexo"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                }}
              />
              <ValidatedField
                label="Edad"
                id="paciente-edad"
                name="edad"
                data-cy="edad"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                  validate: v => isNumber(v) || 'Este campo debe ser un número.',
                }}
              />
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
                label="Estado"
                id="paciente-estado"
                name="estado"
                data-cy="estado"
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
              <ValidatedField
                label="Tipo Sangre"
                id="paciente-tipoSangre"
                name="tipoSangre"
                data-cy="tipoSangre"
                type="text"
                validate={{
                  required: { value: true, message: 'Este campo es obligatorio.' },
                }}
              />
              <ValidatedField id="paciente-habitacion" name="habitacion" data-cy="habitacion" label="Habitacion" type="select">
                <option value="" key="0" />
                {habitacions
                  ? habitacions.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="paciente-llamado" name="llamado" data-cy="llamado" label="Llamado" type="select">
                <option value="" key="0" />
                {llamados
                  ? llamados.map(otherEntity => (
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
