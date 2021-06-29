package com.iteratec.teamdojo.service.impl.ext;

import com.iteratec.teamdojo.repository.ext.ExtendedImageRepository;
import com.iteratec.teamdojo.service.dto.ImageDTO;
import com.iteratec.teamdojo.service.ext.ExtendedImageService;
import com.iteratec.teamdojo.service.impl.ImageServiceImpl;
import com.iteratec.teamdojo.service.mapper.ImageMapper;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Slf4j
@Primary
@Service
public class ExtendedImageServiceImpl extends ImageServiceImpl implements ExtendedImageService {

    private final ExtendedImageRepository imageRepository;
    private final ImageMapper imageMapper;
    private final ImageResizer resizer = new ImageResizer();

    public ExtendedImageServiceImpl(final ExtendedImageRepository imageRepository, final ImageMapper imageMapper) {
        super(imageRepository, imageMapper);
        this.imageRepository = imageRepository;
        this.imageMapper = imageMapper;
    }

    @Override
    public Optional<ImageDTO> findByTitle(final String name) {
        log.debug("Request to get Image : {}", name);
        return imageRepository.findByTitle(name).map(imageMapper::toDto);
    }

    @Override
    @SneakyThrows(NoSuchAlgorithmException.class)
    public ImageDTO save(final ImageDTO imageDTO) {
        log.debug("Request to save Image : {}", imageDTO);

        final var image = imageDTO.getLarge();

        if (image == null) {
            imageDTO.setLarge(null);
            imageDTO.setLargeContentType(null);
            imageDTO.setMedium(null);
            imageDTO.setMediumContentType(null);
            imageDTO.setSmall(null);
            imageDTO.setSmallContentType(null);
            imageDTO.setHash(null);
        } else {
            final var contentType = "image/" + ImageResizer.IMAGE_FORMAT;
            imageDTO.setLarge(resizer.resize(image, ImageResizer.MaxSize.LARGE));
            imageDTO.setLargeContentType(contentType);
            imageDTO.setMedium(resizer.resize(image, ImageResizer.MaxSize.MEDIUM));
            imageDTO.setMediumContentType(contentType);
            imageDTO.setSmall(resizer.resize(image, ImageResizer.MaxSize.SMALL));
            imageDTO.setSmallContentType(contentType);

            final var md = MessageDigest.getInstance("MD5");
            final var imageDigest = md.digest(imageDTO.getLarge());
            final var hash = DatatypeConverter.printHexBinary(imageDigest).toUpperCase();
            imageDTO.setHash(hash);
        }

        return super.save(imageDTO);
    }
}
