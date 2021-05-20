7,11c7
<     template: `
<         <div class="ribbon" *ngIf="ribbonEnv">
<             <a href="" dojoTranslate="teamdojoApp.global.ribbon.{{ ribbonEnv }}">{{ ribbonEnv }}</a>
<         </div>
<     `,
---
>     template: `<div class="ribbon" *ngIf="ribbonEnv"><a href="" jhiTranslate="global.ribbon.{{ribbonEnv}}">{{ribbonEnv}}</a></div>`,
