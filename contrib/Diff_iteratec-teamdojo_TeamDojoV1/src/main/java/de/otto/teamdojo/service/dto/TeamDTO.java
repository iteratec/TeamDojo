9,11d8
< import static java.time.temporal.ChronoUnit.DAYS;
< import static java.time.temporal.ChronoUnit.SECONDS;
< 
41,42d37
<     private Set<DimensionDTO> participations = new HashSet<>();
< 
46a42,43
>     private Set<DimensionDTO> participations = new HashSet<>();
> 
111,118d107
<     public Set<DimensionDTO> getParticipations() {
<         return participations;
<     }
< 
<     public void setParticipations(Set<DimensionDTO> dimensions) {
<         this.participations = dimensions;
<     }
< 
135,136c124,125
<     public Long getDaysUntilExpiration() {
<         return this.validUntil != null ? Instant.now().until(this.validUntil, DAYS) : Long.MAX_VALUE;
---
>     public Set<DimensionDTO> getParticipations() {
>         return participations;
139,140c128,129
<     public Boolean getExpired() {
<         return this.validUntil != null ? Instant.now().until(this.validUntil, SECONDS) < 0 : false;
---
>     public void setParticipations(Set<DimensionDTO> dimensions) {
>         this.participations = dimensions;
