import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ILlamado } from 'app/shared/model/llamado.model';
import { getEntities } from './llamado.reducer';

export const Llamado = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const llamadoList = useAppSelector(state => state.llamado.entities);
  const loading = useAppSelector(state => state.llamado.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="llamado-heading" data-cy="LlamadoHeading">
        Llamados
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refrescar lista
          </Button>
          <Link to="/llamado/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Crear nuevo Llamado
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {llamadoList && llamadoList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Hora Inicio</th>
                <th>Hora Final</th>
                <th>Tipo</th>
                <th>Atendido</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {llamadoList.map((llamado, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/llamado/${llamado.id}`} color="link" size="sm">
                      {llamado.id}
                    </Button>
                  </td>
                  <td>
                    {llamado.horaInicio ? <TextFormat type="date" value={llamado.horaInicio} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>{llamado.horaFinal ? <TextFormat type="date" value={llamado.horaFinal} format={APP_LOCAL_DATE_FORMAT} /> : null}</td>
                  <td>{llamado.tipo}</td>
                  <td>{llamado.atendido ? 'true' : 'false'}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/llamado/${llamado.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">Vista</span>
                      </Button>
                      <Button tag={Link} to={`/llamado/${llamado.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editar</span>
                      </Button>
                      <Button tag={Link} to={`/llamado/${llamado.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Eliminar</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">Ningún Llamados encontrado</div>
        )}
      </div>
    </div>
  );
};

export default Llamado;
