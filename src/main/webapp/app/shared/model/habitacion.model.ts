export interface IHabitacion {
  id?: number;
  nombre?: string;
  tipo?: string;
  zona?: string;
}

export const defaultValue: Readonly<IHabitacion> = {};
