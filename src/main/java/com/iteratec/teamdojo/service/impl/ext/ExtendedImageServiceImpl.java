package com.iteratec.teamdojo.service.impl.ext;

import com.iteratec.teamdojo.repository.ext.ExtendedImageRepository;
import com.iteratec.teamdojo.service.dto.ImageDTO;
import com.iteratec.teamdojo.service.ext.ExtendedImageService;
import com.iteratec.teamdojo.service.impl.ImageServiceImpl;
import com.iteratec.teamdojo.service.mapper.ImageMapper;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import javax.xml.bind.DatatypeConverter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Slf4j
@Primary
@Service
public class ExtendedImageServiceImpl extends ImageServiceImpl implements ExtendedImageService {

    private final ExtendedImageRepository repo;
    private final ImageMapper mapper;
    private final ImageResizer resizer = new ImageResizer();

    public ExtendedImageServiceImpl(final ExtendedImageRepository repo, final ImageMapper mapper) {
        super(repo, mapper);
        this.repo = repo;
        this.mapper = mapper;
    }

    @Override
    public Optional<ImageDTO> findByTitle(final String name) {
        log.debug("Request to get Image : {}", name);
        return repo.findByTitle(name).map(mapper::toDto);
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
