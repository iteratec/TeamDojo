9,10c9,10
< import javax.validation.constraints.NotNull;
< import javax.validation.constraints.Size;
---
> import javax.validation.constraints.*;
> 
13d12
< import java.util.Objects;
14a14
> import java.util.Objects;
40,44d39
<     @ManyToMany(mappedBy = "participations")
<     @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
<     @JsonIgnore
<     private Set<Team> participants = new HashSet<>();
< 
52a48,52
>     @ManyToMany(mappedBy = "participations")
>     @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
>     @JsonIgnore
>     private Set<Team> participants = new HashSet<>();
> 
88,112d87
<     public Set<Team> getParticipants() {
<         return participants;
<     }
< 
<     public Dimension participants(Set<Team> teams) {
<         this.participants = teams;
<         return this;
<     }
< 
<     public Dimension addParticipants(Team team) {
<         this.participants.add(team);
<         team.getParticipations().add(this);
<         return this;
<     }
< 
<     public Dimension removeParticipants(Team team) {
<         this.participants.remove(team);
<         team.getParticipations().remove(this);
<         return this;
<     }
< 
<     public void setParticipants(Set<Team> teams) {
<         this.participants = teams;
<     }
< 
160a136,160
>     }
> 
>     public Set<Team> getParticipants() {
>         return participants;
>     }
> 
>     public Dimension participants(Set<Team> teams) {
>         this.participants = teams;
>         return this;
>     }
> 
>     public Dimension addParticipants(Team team) {
>         this.participants.add(team);
>         team.getParticipations().add(this);
>         return this;
>     }
> 
>     public Dimension removeParticipants(Team team) {
>         this.participants.remove(team);
>         team.getParticipations().remove(this);
>         return this;
>     }
> 
>     public void setParticipants(Set<Team> teams) {
>         this.participants = teams;
