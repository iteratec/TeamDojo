import dayjs from 'dayjs/esm';
import { ActivityType } from 'app/entities/enumerations/activity-type.model';

export interface IActivity {
  id?: number;
  type?: ActivityType | null;
  data?: string | null;
  createdAt?: dayjs.Dayjs;
  updatedAt?: dayjs.Dayjs;
}

export class Activity implements IActivity {
  constructor(
    public id?: number,
    public type?: ActivityType | null,
    public data?: string | null,
    public createdAt?: dayjs.Dayjs,
    public updatedAt?: dayjs.Dayjs
  ) {}
}

export function getActivityIdentifier(activity: IActivity): number | undefined {
  return activity.id;
}
