37,38d36
<     private LongFilter dimensionId;
< 
44a43,44
>     private LongFilter dimensionId;
> 
93,100d92
<     public LongFilter getDimensionId() {
<         return dimensionId;
<     }
< 
<     public void setDimensionId(LongFilter dimensionId) {
<         this.dimensionId = dimensionId;
<     }
< 
124a117,124
>     public LongFilter getDimensionId() {
>         return dimensionId;
>     }
> 
>     public void setDimensionId(LongFilter dimensionId) {
>         this.dimensionId = dimensionId;
>     }
> 
142d141
<             Objects.equals(dimensionId, that.dimensionId) &&
145c144,145
<             Objects.equals(imageId, that.imageId);
---
>             Objects.equals(imageId, that.imageId) &&
>             Objects.equals(dimensionId, that.dimensionId);
157d156
<         dimensionId,
160c159,160
<         imageId
---
>         imageId,
>         dimensionId
173d172
<                 (dimensionId != null ? "dimensionId=" + dimensionId + ", " : "") +
176a176
>                 (dimensionId != null ? "dimensionId=" + dimensionId + ", " : "") +
