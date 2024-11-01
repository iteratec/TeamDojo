import dayjs from 'dayjs/esm';
import { ISkill } from 'app/entities/skill/skill.model';

export interface ITraining {
  id?: number;
  titleEN?: string;
  titleDE?: string | null;
  descriptionEN?: string | null;
  descriptionDE?: string | null;
  contact?: string | null;
  link?: string | null;
  validUntil?: dayjs.Dayjs | null;
  isOfficial?: boolean;
  suggestedBy?: string | null;
  createdAt?: dayjs.Dayjs;
  updatedAt?: dayjs.Dayjs;
  skills?: ISkill[] | null;
}

export class Training implements ITraining {
  constructor(
    public id?: number,
    public titleEN?: string,
    public titleDE?: string | null,
    public descriptionEN?: string | null,
    public descriptionDE?: string | null,
    public contact?: string | null,
    public link?: string | null,
    public validUntil?: dayjs.Dayjs | null,
    public isOfficial?: boolean,
    public suggestedBy?: string | null,
    public createdAt?: dayjs.Dayjs,
    public updatedAt?: dayjs.Dayjs,
    public skills?: ISkill[] | null
  ) {
    this.isOfficial = this.isOfficial ?? false;
  }
}

export function getTrainingIdentifier(training: ITraining): number | undefined {
  return training.id;
}
