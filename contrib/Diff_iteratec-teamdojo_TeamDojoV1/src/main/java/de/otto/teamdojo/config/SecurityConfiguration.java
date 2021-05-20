3c3,4
< import de.otto.teamdojo.security.AuthoritiesConstants;
---
> import de.otto.teamdojo.security.*;
> 
5,7c6,7
< import io.github.jhipster.security.AjaxAuthenticationFailureHandler;
< import io.github.jhipster.security.AjaxAuthenticationSuccessHandler;
< import io.github.jhipster.security.AjaxLogoutSuccessHandler;
---
> import io.github.jhipster.security.*;
> 
49c49
<     public SecurityConfiguration(AuthenticationManagerBuilder authenticationManagerBuilder, UserDetailsService userDetailsService,
---
>     public SecurityConfiguration(AuthenticationManagerBuilder authenticationManagerBuilder, UserDetailsService userDetailsService, 
139,161d138
<             .antMatchers(HttpMethod.GET, "/api/teams/**").permitAll()
<             .antMatchers(HttpMethod.POST, "/api/teams/**").permitAll()
<             .antMatchers(HttpMethod.PUT, "/api/teams/**").permitAll()
<             .antMatchers(HttpMethod.PUT, "/api/teams/*/achievable-skills/**").permitAll()
<             .antMatchers(HttpMethod.GET, "/api/badges/**").permitAll()
<             .antMatchers(HttpMethod.GET, "/api/dimensions/**").permitAll()
<             .antMatchers(HttpMethod.GET, "/api/levels/**").permitAll()
<             .antMatchers(HttpMethod.GET, "/api/skills/**").permitAll()
<             .antMatchers(HttpMethod.POST, "/api/skills/*/vote/**").permitAll()
<             .antMatchers(HttpMethod.GET, "/api/team-skills/**").permitAll()
<             .antMatchers(HttpMethod.PUT, "/api/team-skills/**").permitAll()
<             .antMatchers(HttpMethod.GET, "/api/level-skills/**").permitAll()
<             .antMatchers(HttpMethod.GET, "/api/badge-skills/**").permitAll()
<             .antMatchers(HttpMethod.POST, "/api/reports").permitAll()
<             .antMatchers(HttpMethod.GET, "/api/comments/**").permitAll()
<             .antMatchers(HttpMethod.POST, "/api/comments").permitAll()
<             .antMatchers(HttpMethod.GET, "/api/organizations/**").permitAll()
<             .antMatchers(HttpMethod.GET, "/api/activities/**").permitAll()
<             .antMatchers(HttpMethod.GET, "/api/images/**").permitAll()
<             .antMatchers(HttpMethod.PUT, "/api/images").permitAll()
<             .antMatchers(HttpMethod.POST, "/api/images").permitAll()
<             .antMatchers(HttpMethod.GET, "/api/trainings/**").permitAll()
<             .antMatchers(HttpMethod.POST, "/api/trainings").permitAll()
163d139
<             .antMatchers("/api/server/info").permitAll()
173,176c149
<             .antMatchers("/management/**").hasAuthority(AuthoritiesConstants.ADMIN)
<         .antMatchers("/v2/api-docs/**").permitAll()
<             .antMatchers("/swagger-resources/configuration/ui").permitAll()
<             .antMatchers("/swagger-ui/index.html").hasAuthority(AuthoritiesConstants.ADMIN);
---
>             .antMatchers("/management/**").hasAuthority(AuthoritiesConstants.ADMIN);
