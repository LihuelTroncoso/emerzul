import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IEnfermero } from 'app/shared/model/enfermero.model';
import { getEntities } from './enfermero.reducer';

export const Enfermero = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const enfermeroList = useAppSelector(state => state.enfermero.entities);
  const loading = useAppSelector(state => state.enfermero.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="enfermero-heading" data-cy="EnfermeroHeading">
        Enfermeros
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refrescar lista
          </Button>
          <Link to="/enfermero/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Crear nuevo Enfermero
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {enfermeroList && enfermeroList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Nombre</th>
                <th>Matricula</th>
                <th>Reporte</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {enfermeroList.map((enfermero, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/enfermero/${enfermero.id}`} color="link" size="sm">
                      {enfermero.id}
                    </Button>
                  </td>
                  <td>{enfermero.nombre}</td>
                  <td>{enfermero.matricula}</td>
                  <td>{enfermero.reporte ? <Link to={`/reporte/${enfermero.reporte.id}`}>{enfermero.reporte.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/enfermero/${enfermero.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">Vista</span>
                      </Button>
                      <Button tag={Link} to={`/enfermero/${enfermero.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editar</span>
                      </Button>
                      <Button tag={Link} to={`/enfermero/${enfermero.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Eliminar</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">Ningún Enfermeros encontrado</div>
        )}
      </div>
    </div>
  );
};

export default Enfermero;
