import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Llamado from './llamado';
import LlamadoDetail from './llamado-detail';
import LlamadoUpdate from './llamado-update';
import LlamadoDeleteDialog from './llamado-delete-dialog';

const LlamadoRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Llamado />} />
    <Route path="new" element={<LlamadoUpdate />} />
    <Route path=":id">
      <Route index element={<LlamadoDetail />} />
      <Route path="edit" element={<LlamadoUpdate />} />
      <Route path="delete" element={<LlamadoDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default LlamadoRoutes;
