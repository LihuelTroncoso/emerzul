import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Paciente from './paciente';
import Reporte from './reporte';
import Habitacion from './habitacion';
import Usuario from './usuario';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="paciente/*" element={<Paciente />} />
        <Route path="reporte/*" element={<Reporte />} />
        <Route path="habitacion/*" element={<Habitacion />} />
        <Route path="usuario/*" element={<Usuario />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
