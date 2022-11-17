import { IReporte } from 'app/shared/model/reporte.model';

export interface IEnfermero {
  id?: number;
  nombre?: string;
  matricula?: number;
  reporte?: IReporte | null;
}

export const defaultValue: Readonly<IEnfermero> = {};
