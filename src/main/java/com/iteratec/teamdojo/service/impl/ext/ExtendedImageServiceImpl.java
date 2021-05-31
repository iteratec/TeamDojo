package com.iteratec.teamdojo.service.impl.ext;

import com.iteratec.teamdojo.repository.ImageRepository;
import com.iteratec.teamdojo.repository.ext.ExtendedImageRepository;
import com.iteratec.teamdojo.service.dto.ImageDTO;
import com.iteratec.teamdojo.service.ext.ExtendedImageService;
import com.iteratec.teamdojo.service.impl.ImageServiceImpl;
import com.iteratec.teamdojo.service.mapper.ImageMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Primary
@Service
public class ExtendedImageServiceImpl extends ImageServiceImpl implements ExtendedImageService {

    private final Logger log = LoggerFactory.getLogger(ExtendedImageService.class);
    private final ExtendedImageRepository imageRepository;
    private final ImageMapper imageMapper;

    public ExtendedImageServiceImpl(ExtendedImageRepository imageRepository, ImageMapper imageMapper) {
        super(imageRepository, imageMapper);
        this.imageRepository = imageRepository;
        this.imageMapper = imageMapper;
    }

    @Override
    public Optional<ImageDTO> findByTitle(String name) {
        log.debug("Request to get Image : {}", name);
        return imageRepository.findByTitle(name).map(imageMapper::toDto);
    }
}
