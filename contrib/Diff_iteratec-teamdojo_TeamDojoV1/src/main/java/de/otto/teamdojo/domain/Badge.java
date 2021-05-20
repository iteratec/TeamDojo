65a66,69
>     @ManyToOne
>     @JsonIgnoreProperties("badges")
>     private Image image;
> 
73,76d76
<     @ManyToOne
<     @JsonIgnoreProperties("badges")
<     private Image image;
< 
201a202,214
>     public Image getImage() {
>         return image;
>     }
> 
>     public Badge image(Image image) {
>         this.image = image;
>         return this;
>     }
> 
>     public void setImage(Image image) {
>         this.image = image;
>     }
> 
225,237d237
<     }
< 
<     public Image getImage() {
<         return image;
<     }
< 
<     public Badge image(Image image) {
<         this.image = image;
<         return this;
<     }
< 
<     public void setImage(Image image) {
<         this.image = image;
