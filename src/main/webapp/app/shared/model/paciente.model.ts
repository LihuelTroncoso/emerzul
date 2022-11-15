import { IUsuario } from 'app/shared/model/usuario.model';

export interface IPaciente {
  id?: number;
  intervenciones?: string | null;
  antecedentesFamiliares?: string | null;
  estadoGeneral?: string;
  enfermedades?: string | null;
  discapacidades?: string | null;
  tipoSangre?: string | null;
  usuario?: IUsuario | null;
}

export const defaultValue: Readonly<IPaciente> = {};
