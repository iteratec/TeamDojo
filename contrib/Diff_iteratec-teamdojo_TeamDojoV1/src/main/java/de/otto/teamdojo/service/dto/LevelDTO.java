32,35d31
<     private Long dimensionId;
< 
<     private String dimensionName;
< 
44c40,42
<     private String imageHash;
---
>     private Long dimensionId;
> 
>     private String dimensionName;
94,109d91
<     public Long getDimensionId() {
<         return dimensionId;
<     }
< 
<     public void setDimensionId(Long dimensionId) {
<         this.dimensionId = dimensionId;
<     }
< 
<     public String getDimensionName() {
<         return dimensionName;
<     }
< 
<     public void setDimensionName(String dimensionName) {
<         this.dimensionName = dimensionName;
<     }
< 
142,143c124,125
<     public String getImageHash() {
<         return imageHash;
---
>     public Long getDimensionId() {
>         return dimensionId;
146,147c128,137
<     public void setImageHash(String imageHash) {
<         this.imageHash = imageHash;
---
>     public void setDimensionId(Long dimensionId) {
>         this.dimensionId = dimensionId;
>     }
> 
>     public String getDimensionName() {
>         return dimensionName;
>     }
> 
>     public void setDimensionName(String dimensionName) {
>         this.dimensionName = dimensionName;
180,181d169
<             ", dimension=" + getDimensionId() +
<             ", dimension='" + getDimensionName() + "'" +
185,186c173,175
<             ", imageName='" + getImageName() + "'" +
<             ", imageHash='" + getImageHash() + "'" +
---
>             ", image='" + getImageName() + "'" +
>             ", dimension=" + getDimensionId() +
>             ", dimension='" + getDimensionName() + "'" +
