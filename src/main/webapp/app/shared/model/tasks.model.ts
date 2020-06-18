import { Moment } from 'moment';

export interface ITasks {
  id?: number;
  userId?: number;
  crearedDate?: Moment;
  dueDate?: Moment;
  impotance?: boolean;
  status?: number;
  noiDung?: string;
}

export class Tasks implements ITasks {
  constructor(
    public id?: number,
    public userId?: number,
    public crearedDate?: Moment,
    public dueDate?: Moment,
    public impotance?: boolean,
    public status?: number,
    public noiDung?: string
  ) {
    this.impotance = this.impotance || false;
  }
}
