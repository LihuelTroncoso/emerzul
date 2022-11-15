import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Reporte from './reporte';
import ReporteDetail from './reporte-detail';
import ReporteUpdate from './reporte-update';
import ReporteDeleteDialog from './reporte-delete-dialog';

const ReporteRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Reporte />} />
    <Route path="new" element={<ReporteUpdate />} />
    <Route path=":id">
      <Route index element={<ReporteDetail />} />
      <Route path="edit" element={<ReporteUpdate />} />
      <Route path="delete" element={<ReporteDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ReporteRoutes;
