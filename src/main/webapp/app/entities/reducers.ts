import paciente from 'app/entities/paciente/paciente.reducer';
import reporte from 'app/entities/reporte/reporte.reducer';
import habitacion from 'app/entities/habitacion/habitacion.reducer';
import usuario from 'app/entities/usuario/usuario.reducer';
import enfermero from 'app/entities/enfermero/enfermero.reducer';
import llamado from 'app/entities/llamado/llamado.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  paciente,
  reporte,
  habitacion,
  usuario,
  enfermero,
  llamado,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
