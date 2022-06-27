/*
 SPDX-FileCopyrightText: the TeamDojo authors
 SPDX-License-Identifier: Apache-2.0
 */
import { Moment } from 'moment';

export interface IServerInfo {
  time: Moment;
}

export class ServerInfo implements IServerInfo {
  constructor(public time: Moment) {}
}
