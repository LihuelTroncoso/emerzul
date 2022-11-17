import dayjs from 'dayjs';
import { IPaciente } from 'app/shared/model/paciente.model';

export interface ILlamado {
  id?: number;
  horaInicio?: string;
  horaFinal?: string;
  tipo?: number;
  atendido?: boolean;
  pacientes?: IPaciente[] | null;
}

export const defaultValue: Readonly<ILlamado> = {
  atendido: false,
};
