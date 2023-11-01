import dayjs from 'dayjs/esm';
import { ReportType } from 'app/entities/enumerations/report-type.model';

export interface IReport {
  id: number;
  title?: string | null;
  description?: string | null;
  type?: keyof typeof ReportType | null;
  createdAt?: dayjs.Dayjs | null;
  updatedAt?: dayjs.Dayjs | null;
}

export type NewReport = Omit<IReport, 'id'> & { id: null };
