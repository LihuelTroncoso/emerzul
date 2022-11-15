import paciente from 'app/entities/paciente/paciente.reducer';
import reporte from 'app/entities/reporte/reporte.reducer';
import habitacion from 'app/entities/habitacion/habitacion.reducer';
import usuario from 'app/entities/usuario/usuario.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  paciente,
  reporte,
  habitacion,
  usuario,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
