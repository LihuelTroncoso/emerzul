import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPaciente } from 'app/shared/model/paciente.model';
import { getEntities } from './paciente.reducer';

export const Paciente = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const pacienteList = useAppSelector(state => state.paciente.entities);
  const loading = useAppSelector(state => state.paciente.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="paciente-heading" data-cy="PacienteHeading">
        Pacientes
        <div className="d-flex justify-content-end">
          <Link
            to="/api/export/pacientes"
            className="btn btn-primary jh-create-entity2"
            id="jh-create-entity2"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp; aaaaaa
          </Link>
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refrescar lista
          </Button>
          <Link to="/paciente/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Crear nuevo Paciente
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {pacienteList && pacienteList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Nombre</th>
                <th>Dni</th>
                <th>Sexo</th>
                <th>Edad</th>
                <th>Intervenciones</th>
                <th>Antecedentes Familiares</th>
                <th>Estado</th>
                <th>Enfermedades</th>
                <th>Discapacidades</th>
                <th>Tipo Sangre</th>
                <th>Habitacion</th>
                <th>Llamado</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {pacienteList.map((paciente, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/paciente/${paciente.id}`} color="link" size="sm">
                      {paciente.id}
                    </Button>
                  </td>
                  <td>{paciente.nombre}</td>
                  <td>{paciente.dni}</td>
                  <td>{paciente.sexo}</td>
                  <td>{paciente.edad}</td>
                  <td>{paciente.intervenciones}</td>
                  <td>{paciente.antecedentesFamiliares}</td>
                  <td>{paciente.estado}</td>
                  <td>{paciente.enfermedades}</td>
                  <td>{paciente.discapacidades}</td>
                  <td>{paciente.tipoSangre}</td>
                  <td>{paciente.habitacion ? <Link to={`/habitacion/${paciente.habitacion.id}`}>{paciente.habitacion.id}</Link> : ''}</td>
                  <td>{paciente.llamado ? <Link to={`/llamado/${paciente.llamado.id}`}>{paciente.llamado.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/paciente/${paciente.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">Vista</span>
                      </Button>
                      <Button tag={Link} to={`/paciente/${paciente.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editar</span>
                      </Button>
                      <Button tag={Link} to={`/paciente/${paciente.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Eliminar</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">Ningún Pacientes encontrado</div>
        )}
      </div>
    </div>
  );
};

export default Paciente;
