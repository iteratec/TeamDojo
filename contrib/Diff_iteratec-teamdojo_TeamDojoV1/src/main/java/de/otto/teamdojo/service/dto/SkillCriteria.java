4a5
> import io.github.jhipster.service.filter.BooleanFilter;
6a8
> import io.github.jhipster.service.filter.FloatFilter;
43,44d44
<     private LongFilter teamsId;
< 
49c49
<     private LongFilter trainingsId;
---
>     private LongFilter teamsId;
51,52c51
<     public SkillCriteria() {
<     }
---
>     private LongFilter trainingsId;
134,141d132
<     public LongFilter getTeamsId() {
<         return teamsId;
<     }
< 
<     public void setTeamsId(LongFilter teamsId) {
<         this.teamsId = teamsId;
<     }
< 
157a149,156
>     public LongFilter getTeamsId() {
>         return teamsId;
>     }
> 
>     public void setTeamsId(LongFilter teamsId) {
>         this.teamsId = teamsId;
>     }
> 
187d185
<             Objects.equals(teamsId, that.teamsId) &&
189a188
>             Objects.equals(teamsId, that.teamsId) &&
206d204
<         teamsId,
208a207
>         teamsId,
216,229c215,228
<             (id != null ? "id=" + id + ", " : "") +
<             (title != null ? "title=" + title + ", " : "") +
<             (description != null ? "description=" + description + ", " : "") +
<             (implementation != null ? "implementation=" + implementation + ", " : "") +
<             (validation != null ? "validation=" + validation + ", " : "") +
<             (expiryPeriod != null ? "expiryPeriod=" + expiryPeriod + ", " : "") +
<             (contact != null ? "contact=" + contact + ", " : "") +
<             (score != null ? "score=" + score + ", " : "") +
<             (rateScore != null ? "rateScore=" + rateScore + ", " : "") +
<             (rateCount != null ? "rateCount=" + rateCount + ", " : "") +
<             (teamsId != null ? "teamsId=" + teamsId + ", " : "") +
<             (badgesId != null ? "badgesId=" + badgesId + ", " : "") +
<             (levelsId != null ? "levelsId=" + levelsId + ", " : "") +
<             (trainingsId != null ? "trainingsId=" + trainingsId + ", " : "") +
---
>                 (id != null ? "id=" + id + ", " : "") +
>                 (title != null ? "title=" + title + ", " : "") +
>                 (description != null ? "description=" + description + ", " : "") +
>                 (implementation != null ? "implementation=" + implementation + ", " : "") +
>                 (validation != null ? "validation=" + validation + ", " : "") +
>                 (expiryPeriod != null ? "expiryPeriod=" + expiryPeriod + ", " : "") +
>                 (contact != null ? "contact=" + contact + ", " : "") +
>                 (score != null ? "score=" + score + ", " : "") +
>                 (rateScore != null ? "rateScore=" + rateScore + ", " : "") +
>                 (rateCount != null ? "rateCount=" + rateCount + ", " : "") +
>                 (badgesId != null ? "badgesId=" + badgesId + ", " : "") +
>                 (levelsId != null ? "levelsId=" + levelsId + ", " : "") +
>                 (teamsId != null ? "teamsId=" + teamsId + ", " : "") +
>                 (trainingsId != null ? "trainingsId=" + trainingsId + ", " : "") +
