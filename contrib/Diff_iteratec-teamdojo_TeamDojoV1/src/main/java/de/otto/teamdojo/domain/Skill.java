71,74d70
<     private Set<TeamSkill> teams = new HashSet<>();
< 
<     @OneToMany(mappedBy = "skill")
<     @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
76d71
< 
80c75,77
< 
---
>     @OneToMany(mappedBy = "skill")
>     @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
>     private Set<TeamSkill> teams = new HashSet<>();
82d78
<     @JsonIgnore
83a80
>     @JsonIgnore
212,236d208
<     public Set<TeamSkill> getTeams() {
<         return teams;
<     }
< 
<     public Skill teams(Set<TeamSkill> teamSkills) {
<         this.teams = teamSkills;
<         return this;
<     }
< 
<     public Skill addTeams(TeamSkill teamSkill) {
<         this.teams.add(teamSkill);
<         teamSkill.setSkill(this);
<         return this;
<     }
< 
<     public Skill removeTeams(TeamSkill teamSkill) {
<         this.teams.remove(teamSkill);
<         teamSkill.setSkill(null);
<         return this;
<     }
< 
<     public void setTeams(Set<TeamSkill> teamSkills) {
<         this.teams = teamSkills;
<     }
< 
286a259,283
>     public Set<TeamSkill> getTeams() {
>         return teams;
>     }
> 
>     public Skill teams(Set<TeamSkill> teamSkills) {
>         this.teams = teamSkills;
>         return this;
>     }
> 
>     public Skill addTeams(TeamSkill teamSkill) {
>         this.teams.add(teamSkill);
>         teamSkill.setSkill(this);
>         return this;
>     }
> 
>     public Skill removeTeams(TeamSkill teamSkill) {
>         this.teams.remove(teamSkill);
>         teamSkill.setSkill(null);
>         return this;
>     }
> 
>     public void setTeams(Set<TeamSkill> teamSkills) {
>         this.teams = teamSkills;
>     }
> 
341c338
<             ", expiryPeriod='" + getExpiryPeriod() + "'" +
---
>             ", expiryPeriod=" + getExpiryPeriod() +
