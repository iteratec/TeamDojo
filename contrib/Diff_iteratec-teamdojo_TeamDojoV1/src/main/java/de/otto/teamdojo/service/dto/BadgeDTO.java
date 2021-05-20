40,41d39
<     private Set<DimensionDTO> dimensions = new HashSet<>();
< 
44,45d41
<     private String imageHash;
< 
47a44,45
>     private Set<DimensionDTO> dimensions = new HashSet<>();
> 
112,119d109
<     public Set<DimensionDTO> getDimensions() {
<         return dimensions;
<     }
< 
<     public void setDimensions(Set<DimensionDTO> dimensions) {
<         this.dimensions = dimensions;
<     }
< 
128,135d117
<     public String getImageHash() {
<         return imageHash;
<     }
< 
<     public void setImageHash(String imageHash) {
<         this.imageHash = imageHash;
<     }
< 
143a126,133
>     public Set<DimensionDTO> getDimensions() {
>         return dimensions;
>     }
> 
>     public void setDimensions(Set<DimensionDTO> dimensions) {
>         this.dimensions = dimensions;
>     }
> 
177,178c167
<             ", imageName='" + getImageName() + "'" +
<             ", imageHash='" + getImageHash() + "'" +
---
>             ", image='" + getImageName() + "'" +
