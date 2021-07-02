import * as dayjs from 'dayjs';
import { ReportType } from 'app/entities/enumerations/report-type.model';

export interface IReport {
  id?: number;
  title?: string;
  description?: string;
  type?: ReportType;
  createdAt?: dayjs.Dayjs;
  updatedAt?: dayjs.Dayjs;
}

export class Report implements IReport {
  constructor(
    public id?: number,
    public title?: string,
    public description?: string,
    public type?: ReportType,
    public createdAt?: dayjs.Dayjs,
    public updatedAt?: dayjs.Dayjs
  ) {}
}

export function getReportIdentifier(report: IReport): number | undefined {
  return report.id;
}
