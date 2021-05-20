44,45d43
<     private LongFilter dimensionsId;
< 
47a46,47
>     private LongFilter dimensionsId;
> 
120,127d119
<     public LongFilter getDimensionsId() {
<         return dimensionsId;
<     }
< 
<     public void setDimensionsId(LongFilter dimensionsId) {
<         this.dimensionsId = dimensionsId;
<     }
< 
135a128,135
>     public LongFilter getDimensionsId() {
>         return dimensionsId;
>     }
> 
>     public void setDimensionsId(LongFilter dimensionsId) {
>         this.dimensionsId = dimensionsId;
>     }
> 
156,157c156,157
<             Objects.equals(dimensionsId, that.dimensionsId) &&
<             Objects.equals(imageId, that.imageId);
---
>             Objects.equals(imageId, that.imageId) &&
>             Objects.equals(dimensionsId, that.dimensionsId);
172,173c172,173
<         dimensionsId,
<         imageId
---
>         imageId,
>         dimensionsId
189d188
<                 (dimensionsId != null ? "dimensionsId=" + dimensionsId + ", " : "") +
190a190
>                 (dimensionsId != null ? "dimensionsId=" + dimensionsId + ", " : "") +
