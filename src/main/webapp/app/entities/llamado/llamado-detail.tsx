import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './llamado.reducer';

export const LlamadoDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const llamadoEntity = useAppSelector(state => state.llamado.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="llamadoDetailsHeading">Llamado</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{llamadoEntity.id}</dd>
          <dt>
            <span id="horaInicio">Hora Inicio</span>
          </dt>
          <dd>
            {llamadoEntity.horaInicio ? <TextFormat value={llamadoEntity.horaInicio} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="horaFinal">Hora Final</span>
          </dt>
          <dd>
            {llamadoEntity.horaFinal ? <TextFormat value={llamadoEntity.horaFinal} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="tipo">Tipo</span>
          </dt>
          <dd>{llamadoEntity.tipo}</dd>
          <dt>
            <span id="atendido">Atendido</span>
          </dt>
          <dd>{llamadoEntity.atendido ? 'true' : 'false'}</dd>
        </dl>
        <Button tag={Link} to="/llamado" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Volver</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/llamado/${llamadoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editar</span>
        </Button>
      </Col>
    </Row>
  );
};

export default LlamadoDetail;
