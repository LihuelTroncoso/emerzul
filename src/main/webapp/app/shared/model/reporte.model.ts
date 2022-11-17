import dayjs from 'dayjs';
import { ILlamado } from 'app/shared/model/llamado.model';
import { IEnfermero } from 'app/shared/model/enfermero.model';

export interface IReporte {
  id?: number;
  area?: string;
  origen?: string;
  hora?: string;
  llamado?: ILlamado | null;
  enfermeros?: IEnfermero[] | null;
}

export const defaultValue: Readonly<IReporte> = {};
