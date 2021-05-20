3a4
> 
18c19
<  * <p>
---
>  *
45,46c46,47
<     @Pointcut("within(de.otto.teamdojo.repository..*)" +
<         " || within(de.otto.teamdojo.service..*)" +
---
>     @Pointcut("within(de.otto.teamdojo.repository..*)"+
>         " || within(de.otto.teamdojo.service..*)"+
56c57
<      * @param e         exception
---
>      * @param e exception
62c63
<                 joinPoint.getSignature().getName(), e.getCause() != null ? e.getCause() : "NULL", e.getMessage(), e);
---
>                 joinPoint.getSignature().getName(), e.getCause() != null? e.getCause() : "NULL", e.getMessage(), e);
66c67
<                 joinPoint.getSignature().getName(), e.getCause() != null ? e.getCause() : "NULL");
---
>                 joinPoint.getSignature().getName(), e.getCause() != null? e.getCause() : "NULL");
