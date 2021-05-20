42,43d41
<     private LongFilter participationsId;
< 
47a46,47
>     private LongFilter participationsId;
> 
112,119d111
<     public LongFilter getParticipationsId() {
<         return participationsId;
<     }
< 
<     public void setParticipationsId(LongFilter participationsId) {
<         this.participationsId = participationsId;
<     }
< 
135a128,135
>     public LongFilter getParticipationsId() {
>         return participationsId;
>     }
> 
>     public void setParticipationsId(LongFilter participationsId) {
>         this.participationsId = participationsId;
>     }
> 
155d154
<             Objects.equals(participationsId, that.participationsId) &&
157c156,157
<             Objects.equals(imageId, that.imageId);
---
>             Objects.equals(imageId, that.imageId) &&
>             Objects.equals(participationsId, that.participationsId);
171d170
<         participationsId,
173c172,173
<         imageId
---
>         imageId,
>         participationsId
188d187
<                 (participationsId != null ? "participationsId=" + participationsId + ", " : "") +
190a190
>                 (participationsId != null ? "participationsId=" + participationsId + ", " : "") +
