import { ActivityType } from '@/shared/model/enumerations/activity-type.model';
export interface IActivity {
  id?: number;
  type?: ActivityType | null;
  data?: string | null;
  createdAt?: Date;
  updatedAt?: Date;
}

export class Activity implements IActivity {
  constructor(
    public id?: number,
    public type?: ActivityType | null,
    public data?: string | null,
    public createdAt?: Date,
    public updatedAt?: Date
  ) {}
}
