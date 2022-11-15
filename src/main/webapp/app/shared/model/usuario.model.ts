import { IHabitacion } from 'app/shared/model/habitacion.model';
import { IReporte } from 'app/shared/model/reporte.model';

export interface IUsuario {
  id?: number;
  nombre?: string;
  contrasena?: string;
  rol?: string;
  nombreUsuario?: string;
  sexo?: string;
  edad?: string;
  apellido?: string;
  zona?: IHabitacion | null;
  reporte?: IReporte | null;
}

export const defaultValue: Readonly<IUsuario> = {};
