import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './paciente.reducer';

export const PacienteDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const pacienteEntity = useAppSelector(state => state.paciente.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="pacienteDetailsHeading">Paciente</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{pacienteEntity.id}</dd>
          <dt>
            <span id="intervenciones">Intervenciones</span>
          </dt>
          <dd>{pacienteEntity.intervenciones}</dd>
          <dt>
            <span id="antecedentesFamiliares">Antecedentes Familiares</span>
          </dt>
          <dd>{pacienteEntity.antecedentesFamiliares}</dd>
          <dt>
            <span id="estadoGeneral">Estado General</span>
          </dt>
          <dd>{pacienteEntity.estadoGeneral}</dd>
          <dt>
            <span id="enfermedades">Enfermedades</span>
          </dt>
          <dd>{pacienteEntity.enfermedades}</dd>
          <dt>
            <span id="discapacidades">Discapacidades</span>
          </dt>
          <dd>{pacienteEntity.discapacidades}</dd>
          <dt>
            <span id="tipoSangre">Tipo Sangre</span>
          </dt>
          <dd>{pacienteEntity.tipoSangre}</dd>
          <dt>Usuario</dt>
          <dd>{pacienteEntity.usuario ? pacienteEntity.usuario.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/paciente" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Volver</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/paciente/${pacienteEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editar</span>
        </Button>
      </Col>
    </Row>
  );
};

export default PacienteDetail;
