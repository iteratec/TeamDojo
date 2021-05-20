2a3,4
> import static de.otto.teamdojo.config.WebsocketConfiguration.IP_ADDRESS;
> 
3a6,9
> 
> import java.security.Principal;
> import java.time.Instant;
> 
7,9c13
< import org.springframework.messaging.handler.annotation.MessageMapping;
< import org.springframework.messaging.handler.annotation.Payload;
< import org.springframework.messaging.handler.annotation.SendTo;
---
> import org.springframework.messaging.handler.annotation.*;
14,18d17
< 
< import java.security.Principal;
< import java.time.Instant;
< 
< import static de.otto.teamdojo.config.WebsocketConfiguration.IP_ADDRESS;
