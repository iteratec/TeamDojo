2c2
< import { getTestBed, TestBed } from '@angular/core/testing';
---
> import { TestBed, getTestBed } from '@angular/core/testing';
4c4,6
< import { map, take } from 'rxjs/operators';
---
> import { HttpClient, HttpResponse } from '@angular/common/http';
> import { of } from 'rxjs';
> import { take, map } from 'rxjs/operators';
9d10
< import { SkillStatus } from 'app/shared/model/skill-status';
27c28
<             elemDefault = new TeamSkill(0, currentDate, currentDate, false, SkillStatus.OPEN, 'AAAAAAA', 0, 'AAAAAAA');
---
>             elemDefault = new TeamSkill(0, currentDate, currentDate, false, 'AAAAAAA', 0, 'AAAAAAA');
