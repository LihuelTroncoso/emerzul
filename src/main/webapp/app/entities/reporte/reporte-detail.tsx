import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './reporte.reducer';

export const ReporteDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const reporteEntity = useAppSelector(state => state.reporte.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="reporteDetailsHeading">Reporte</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{reporteEntity.id}</dd>
          <dt>
            <span id="area">Area</span>
          </dt>
          <dd>{reporteEntity.area}</dd>
          <dt>
            <span id="origen">Origen</span>
          </dt>
          <dd>{reporteEntity.origen}</dd>
          <dt>
            <span id="hora">Hora</span>
          </dt>
          <dd>{reporteEntity.hora ? <TextFormat value={reporteEntity.hora} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>Llamado</dt>
          <dd>{reporteEntity.llamado ? reporteEntity.llamado.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/reporte" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Volver</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/reporte/${reporteEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editar</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ReporteDetail;
