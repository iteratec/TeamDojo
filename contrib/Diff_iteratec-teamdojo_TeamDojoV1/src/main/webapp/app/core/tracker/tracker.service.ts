44,54c44,57
<         this.stompClient.connect(headers, () => {
<             this.connectedPromise('success');
<             this.connectedPromise = null;
<             this.sendActivity();
<             if (!this.alreadyConnectedOnce) {
<                 this.subscription = this.router.events.subscribe(event => {
<                     if (event instanceof NavigationEnd) {
<                         this.sendActivity();
<                     }
<                 });
<                 this.alreadyConnectedOnce = true;
---
>         this.stompClient.connect(
>             headers,
>             () => {
>                 this.connectedPromise('success');
>                 this.connectedPromise = null;
>                 this.sendActivity();
>                 if (!this.alreadyConnectedOnce) {
>                     this.subscription = this.router.events.subscribe(event => {
>                         if (event instanceof NavigationEnd) {
>                             this.sendActivity();
>                         }
>                     });
>                     this.alreadyConnectedOnce = true;
>                 }
56c59
<         });
---
>         );
