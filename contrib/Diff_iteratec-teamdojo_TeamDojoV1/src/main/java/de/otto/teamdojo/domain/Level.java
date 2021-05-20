55,59d54
<     @ManyToOne(optional = false)
<     @NotNull
<     @JsonIgnoreProperties()
<     private Dimension dimension;
< 
70a66,70
>     @ManyToOne(optional = false)
>     @NotNull
>     @JsonIgnoreProperties("levels")
>     private Dimension dimension;
> 
145,157d144
<     public Dimension getDimension() {
<         return dimension;
<     }
< 
<     public Level dimension(Dimension dimension) {
<         this.dimension = dimension;
<         return this;
<     }
< 
<     public void setDimension(Dimension dimension) {
<         this.dimension = dimension;
<     }
< 
206a194,206
>     }
> 
>     public Dimension getDimension() {
>         return dimension;
>     }
> 
>     public Level dimension(Dimension dimension) {
>         this.dimension = dimension;
>         return this;
>     }
> 
>     public void setDimension(Dimension dimension) {
>         this.dimension = dimension;
