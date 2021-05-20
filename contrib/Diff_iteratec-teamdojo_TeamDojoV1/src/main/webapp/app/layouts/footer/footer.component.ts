2d1
< import { VERSION, BUILD_TIMESTAMP } from 'app/app.constants';
6,7c5
<     templateUrl: './footer.component.html',
<     styleUrls: ['footer.scss']
---
>     templateUrl: './footer.component.html'
9,15c7
< export class FooterComponent {
<     version: string;
< 
<     constructor() {
<         this.version = (VERSION ? `v${VERSION}` : '') + `.${BUILD_TIMESTAMP}`;
<     }
< }
---
> export class FooterComponent {}
