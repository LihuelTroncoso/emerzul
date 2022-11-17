import { IHabitacion } from 'app/shared/model/habitacion.model';
import { ILlamado } from 'app/shared/model/llamado.model';

export interface IPaciente {
  id?: number;
  nombre?: string;
  dni?: number;
  sexo?: string;
  edad?: number;
  intervenciones?: string | null;
  antecedentesFamiliares?: string | null;
  estado?: string;
  enfermedades?: string | null;
  discapacidades?: string | null;
  tipoSangre?: string;
  habitacion?: IHabitacion | null;
  llamado?: ILlamado | null;
}

export const defaultValue: Readonly<IPaciente> = {};
