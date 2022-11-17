import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IReporte } from 'app/shared/model/reporte.model';
import { getEntities } from './reporte.reducer';

export const Reporte = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const reporteList = useAppSelector(state => state.reporte.entities);
  const loading = useAppSelector(state => state.reporte.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="reporte-heading" data-cy="ReporteHeading">
        Reportes
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refrescar lista
          </Button>
          <Link to="/reporte/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Crear nuevo Reporte
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {reporteList && reporteList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Area</th>
                <th>Origen</th>
                <th>Hora</th>
                <th>Llamado</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {reporteList.map((reporte, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/reporte/${reporte.id}`} color="link" size="sm">
                      {reporte.id}
                    </Button>
                  </td>
                  <td>{reporte.area}</td>
                  <td>{reporte.origen}</td>
                  <td>{reporte.hora ? <TextFormat type="date" value={reporte.hora} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{reporte.llamado ? <Link to={`/llamado/${reporte.llamado.id}`}>{reporte.llamado.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/reporte/${reporte.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">Vista</span>
                      </Button>
                      <Button tag={Link} to={`/reporte/${reporte.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editar</span>
                      </Button>
                      <Button tag={Link} to={`/reporte/${reporte.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Eliminar</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">Ningún Reportes encontrado</div>
        )}
      </div>
    </div>
  );
};

export default Reporte;
