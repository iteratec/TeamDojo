3a4
> import com.fasterxml.jackson.annotation.JsonIgnore;
9,11c10,11
< import javax.validation.constraints.NotNull;
< import javax.validation.constraints.Pattern;
< import javax.validation.constraints.Size;
---
> import javax.validation.constraints.*;
> 
15d14
< import java.util.Objects;
16a16
> import java.util.Objects;
27c27
< 
---
>     
61,68c61
<     @ManyToMany(fetch = FetchType.EAGER)
<     @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
<     @JoinTable(name = "team_participations",
<         joinColumns = @JoinColumn(name = "team_id", referencedColumnName = "id"),
<         inverseJoinColumns = @JoinColumn(name = "participations_id", referencedColumnName = "id"))
<     private Set<Dimension> participations = new HashSet<>();
< 
<     @OneToMany(mappedBy = "team" , cascade = CascadeType.REMOVE)
---
>     @OneToMany(mappedBy = "team")
74a68,74
>     @ManyToMany
>     @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
>     @JoinTable(name = "team_participations",
>                joinColumns = @JoinColumn(name = "team_id", referencedColumnName = "id"),
>                inverseJoinColumns = @JoinColumn(name = "participations_id", referencedColumnName = "id"))
>     private Set<Dimension> participations = new HashSet<>();
> 
175,199d174
<     public Set<Dimension> getParticipations() {
<         return participations;
<     }
< 
<     public Team participations(Set<Dimension> dimensions) {
<         this.participations = dimensions;
<         return this;
<     }
< 
<     public Team addParticipations(Dimension dimension) {
<         this.participations.add(dimension);
<         dimension.getParticipants().add(this);
<         return this;
<     }
< 
<     public Team removeParticipations(Dimension dimension) {
<         this.participations.remove(dimension);
<         dimension.getParticipants().remove(this);
<         return this;
<     }
< 
<     public void setParticipations(Set<Dimension> dimensions) {
<         this.participations = dimensions;
<     }
< 
235a211,235
>     }
> 
>     public Set<Dimension> getParticipations() {
>         return participations;
>     }
> 
>     public Team participations(Set<Dimension> dimensions) {
>         this.participations = dimensions;
>         return this;
>     }
> 
>     public Team addParticipations(Dimension dimension) {
>         this.participations.add(dimension);
>         dimension.getParticipants().add(this);
>         return this;
>     }
> 
>     public Team removeParticipations(Dimension dimension) {
>         this.participations.remove(dimension);
>         dimension.getParticipants().remove(this);
>         return this;
>     }
> 
>     public void setParticipations(Set<Dimension> dimensions) {
>         this.participations = dimensions;
