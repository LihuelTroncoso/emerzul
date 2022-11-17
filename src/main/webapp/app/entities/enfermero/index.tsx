import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Enfermero from './enfermero';
import EnfermeroDetail from './enfermero-detail';
import EnfermeroUpdate from './enfermero-update';
import EnfermeroDeleteDialog from './enfermero-delete-dialog';

const EnfermeroRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Enfermero />} />
    <Route path="new" element={<EnfermeroUpdate />} />
    <Route path=":id">
      <Route index element={<EnfermeroDetail />} />
      <Route path="edit" element={<EnfermeroUpdate />} />
      <Route path="delete" element={<EnfermeroDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default EnfermeroRoutes;
