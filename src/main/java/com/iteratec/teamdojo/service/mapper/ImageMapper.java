package com.iteratec.teamdojo.service.mapper;

import com.iteratec.teamdojo.domain.Image;
import com.iteratec.teamdojo.service.dto.ImageDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Image} and its DTO {@link ImageDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ImageMapper extends EntityMapper<ImageDTO, Image> {
    @Named("title")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "title", source = "title")
    ImageDTO toDtoTitle(Image image);
}
