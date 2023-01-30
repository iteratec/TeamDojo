import dayjs from 'dayjs/esm';
import { ActivityType } from 'app/entities/enumerations/activity-type.model';

export interface IActivity {
  id: number;
  type?: ActivityType | null;
  data?: string | null;
  createdAt?: dayjs.Dayjs | null;
  updatedAt?: dayjs.Dayjs | null;
}

export type NewActivity = Omit<IActivity, 'id'> & { id: null };
