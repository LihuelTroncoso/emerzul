import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './enfermero.reducer';

export const EnfermeroDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const enfermeroEntity = useAppSelector(state => state.enfermero.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="enfermeroDetailsHeading">Enfermero</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{enfermeroEntity.id}</dd>
          <dt>
            <span id="nombre">Nombre</span>
          </dt>
          <dd>{enfermeroEntity.nombre}</dd>
          <dt>
            <span id="matricula">Matricula</span>
          </dt>
          <dd>{enfermeroEntity.matricula}</dd>
          <dt>Reporte</dt>
          <dd>{enfermeroEntity.reporte ? enfermeroEntity.reporte.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/enfermero" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Volver</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/enfermero/${enfermeroEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editar</span>
        </Button>
      </Col>
    </Row>
  );
};

export default EnfermeroDetail;
