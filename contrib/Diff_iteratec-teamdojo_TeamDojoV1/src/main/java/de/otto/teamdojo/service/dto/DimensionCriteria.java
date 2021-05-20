4a5,6
> import io.github.jhipster.service.filter.BooleanFilter;
> import io.github.jhipster.service.filter.DoubleFilter;
5a8,9
> import io.github.jhipster.service.filter.FloatFilter;
> import io.github.jhipster.service.filter.IntegerFilter;
9,13d12
< 
< 
< 
< 
< 
32,33d30
<     private LongFilter participantsId;
< 
37a35,36
>     private LongFilter participantsId;
> 
62,69d60
<     public LongFilter getParticipantsId() {
<         return participantsId;
<     }
< 
<     public void setParticipantsId(LongFilter participantsId) {
<         this.participantsId = participantsId;
<     }
< 
85a77,84
>     public LongFilter getParticipantsId() {
>         return participantsId;
>     }
> 
>     public void setParticipantsId(LongFilter participantsId) {
>         this.participantsId = participantsId;
>     }
> 
100d98
<             Objects.equals(participantsId, that.participantsId) &&
102c100,101
<             Objects.equals(badgesId, that.badgesId);
---
>             Objects.equals(badgesId, that.badgesId) &&
>             Objects.equals(participantsId, that.participantsId);
111d109
<         participantsId,
113c111,112
<         badgesId
---
>         badgesId,
>         participantsId
120,125c119,124
<             (id != null ? "id=" + id + ", " : "") +
<             (name != null ? "name=" + name + ", " : "") +
<             (description != null ? "description=" + description + ", " : "") +
<             (participantsId != null ? "participantsId=" + participantsId + ", " : "") +
<             (levelsId != null ? "levelsId=" + levelsId + ", " : "") +
<             (badgesId != null ? "badgesId=" + badgesId + ", " : "") +
---
>                 (id != null ? "id=" + id + ", " : "") +
>                 (name != null ? "name=" + name + ", " : "") +
>                 (description != null ? "description=" + description + ", " : "") +
>                 (levelsId != null ? "levelsId=" + levelsId + ", " : "") +
>                 (badgesId != null ? "badgesId=" + badgesId + ", " : "") +
>                 (participantsId != null ? "participantsId=" + participantsId + ", " : "") +
