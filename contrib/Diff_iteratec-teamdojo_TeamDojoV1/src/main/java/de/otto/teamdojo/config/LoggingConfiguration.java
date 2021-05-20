2a3,7
> import java.net.InetSocketAddress;
> import java.util.Iterator;
> 
> import io.github.jhipster.config.JHipsterProperties;
> 
13d17
< import io.github.jhipster.config.JHipsterProperties;
22,24d25
< import java.net.InetSocketAddress;
< import java.util.Iterator;
< 
43c44
<                                 JHipsterProperties jHipsterProperties) {
---
>          JHipsterProperties jHipsterProperties) {
108c109
<             for (Iterator<Appender<ILoggingEvent>> it = logger.iteratorForAppenders(); it.hasNext(); ) {
---
>             for (Iterator<Appender<ILoggingEvent>> it = logger.iteratorForAppenders(); it.hasNext();) {
