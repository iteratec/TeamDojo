/*
 SPDX-FileCopyrightText: the TeamDojo authors
 SPDX-License-Identifier: Apache-2.0
 */
import { Params } from '@angular/router';

export interface IBreadcrumb {
  text?: string;
  path?: string;
  active?: boolean;
  queryParams?: Params;
}

export class Breadcrumb implements IBreadcrumb {
  constructor(public text?: string, public path?: string, public active?: boolean, public queryParams?: Params) {}
}
