11c11
< @Mapper(componentModel = "spring", uses = {DimensionMapper.class, ImageMapper.class})
---
> @Mapper(componentModel = "spring", uses = {ImageMapper.class, DimensionMapper.class})
14,15d13
<     @Mapping(source = "dimension.id", target = "dimensionId")
<     @Mapping(source = "dimension.name", target = "dimensionName")
20c18,19
<     @Mapping(source = "image.hash", target = "imageHash")
---
>     @Mapping(source = "dimension.id", target = "dimensionId")
>     @Mapping(source = "dimension.name", target = "dimensionName")
23d21
<     @Mapping(source = "dimensionId", target = "dimension")
26a25
>     @Mapping(source = "dimensionId", target = "dimension")
