import { ReportType } from '@/shared/model/enumerations/report-type.model';
export interface IReport {
  id?: number;
  title?: string;
  description?: string;
  type?: ReportType;
  createdAt?: Date;
  updatedAt?: Date;
}

export class Report implements IReport {
  constructor(
    public id?: number,
    public title?: string,
    public description?: string,
    public type?: ReportType,
    public createdAt?: Date,
    public updatedAt?: Date
  ) {}
}
