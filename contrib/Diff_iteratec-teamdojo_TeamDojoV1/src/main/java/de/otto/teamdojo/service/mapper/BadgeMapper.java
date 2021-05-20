11c11
< @Mapper(componentModel = "spring", uses = {DimensionMapper.class, ImageMapper.class})
---
> @Mapper(componentModel = "spring", uses = {ImageMapper.class, DimensionMapper.class})
16d15
<     @Mapping(source = "image.hash", target = "imageHash")
