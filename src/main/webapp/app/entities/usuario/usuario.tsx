import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IUsuario } from 'app/shared/model/usuario.model';
import { getEntities } from './usuario.reducer';

export const Usuario = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const usuarioList = useAppSelector(state => state.usuario.entities);
  const loading = useAppSelector(state => state.usuario.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="usuario-heading" data-cy="UsuarioHeading">
        Usuarios
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refrescar lista
          </Button>
          <Link to="/usuario/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Crear nuevo Usuario
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {usuarioList && usuarioList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Nombre</th>
                <th>Contrasena</th>
                <th>Rol</th>
                <th>Nombre Usuario</th>
                <th>Sexo</th>
                <th>Edad</th>
                <th>Apellido</th>
                <th>Zona</th>
                <th>Reporte</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {usuarioList.map((usuario, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/usuario/${usuario.id}`} color="link" size="sm">
                      {usuario.id}
                    </Button>
                  </td>
                  <td>{usuario.nombre}</td>
                  <td>{usuario.contrasena}</td>
                  <td>{usuario.rol}</td>
                  <td>{usuario.nombreUsuario}</td>
                  <td>{usuario.sexo}</td>
                  <td>{usuario.edad}</td>
                  <td>{usuario.apellido}</td>
                  <td>{usuario.zona ? <Link to={`/habitacion/${usuario.zona.id}`}>{usuario.zona.id}</Link> : ''}</td>
                  <td>{usuario.reporte ? <Link to={`/reporte/${usuario.reporte.id}`}>{usuario.reporte.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/usuario/${usuario.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">Vista</span>
                      </Button>
                      <Button tag={Link} to={`/usuario/${usuario.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editar</span>
                      </Button>
                      <Button tag={Link} to={`/usuario/${usuario.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Eliminar</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">Ningún Usuarios encontrado</div>
        )}
      </div>
    </div>
  );
};

export default Usuario;
