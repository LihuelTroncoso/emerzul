import React from 'react';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/paciente">
        Paciente
      </MenuItem>
      <MenuItem icon="asterisk" to="/reporte">
        Reporte
      </MenuItem>
      <MenuItem icon="asterisk" to="/habitacion">
        Habitacion
      </MenuItem>
      <MenuItem icon="asterisk" to="/usuario">
        Usuario
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
