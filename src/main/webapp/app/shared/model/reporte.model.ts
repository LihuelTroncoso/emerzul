import dayjs from 'dayjs';
import { IUsuario } from 'app/shared/model/usuario.model';

export interface IReporte {
  id?: number;
  horaInicio?: string;
  horaFinal?: string;
  tipo?: string;
  alerta?: boolean;
  usuarios?: IUsuario[] | null;
}

export const defaultValue: Readonly<IReporte> = {
  alerta: false,
};
