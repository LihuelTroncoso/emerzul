export interface IHabitacion {
  id?: number;
  nombre?: string;
  urgencia?: number;
  zona?: string;
}

export const defaultValue: Readonly<IHabitacion> = {};
