package com.iteratec.teamdojo.service.impl.custom;

import com.iteratec.teamdojo.repository.custom.ExtendedImageRepository;
import com.iteratec.teamdojo.service.custom.ExtendedImageService;
import com.iteratec.teamdojo.service.dto.ImageDTO;
import com.iteratec.teamdojo.service.impl.ImageServiceImpl;
import com.iteratec.teamdojo.service.mapper.ImageMapper;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import javax.xml.bind.DatatypeConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Slf4j
@Primary
@Service
public class ExtendedImageServiceImpl extends ImageServiceImpl implements ExtendedImageService {

    /**
     * MIME content type of image byte arrays.
     */
    private static final String CONTENT_TYPE = "image/" + ImageResizer.IMAGE_FORMAT;

    private final ExtendedImageRepository repo;
    private final ImageMapper mapper;
    private final ImageResizer resizer = new ImageResizer();
    private final MessageDigest md5 = MessageDigest.getInstance("MD5");

    public ExtendedImageServiceImpl(final ExtendedImageRepository repo, final ImageMapper mapper) throws NoSuchAlgorithmException {
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
    public ImageDTO save(final ImageDTO image) {
        log.debug("Request to save Image : {}", image);

        // TODO: #42 Her set createdAt/updatedAt

        if (shouldResetImages(image)) {
            image.setLarge(null);
            image.setLargeContentType(null);
            image.setMedium(null);
            image.setMediumContentType(null);
            image.setSmall(null);
            image.setSmallContentType(null);
            image.setHash(null);
        }

        if (shouldResizeImage(image)) {
            // FIXME: Validate the input (https://github.com/iteratec/TeamDojo/issues/11)
            final var large = image.getLarge();
            image.setLarge(resizer.resize(large, ImageResizer.MaxSize.LARGE));
            image.setLargeContentType(CONTENT_TYPE);
            image.setMedium(resizer.resize(large, ImageResizer.MaxSize.MEDIUM));
            image.setMediumContentType(CONTENT_TYPE);
            image.setSmall(resizer.resize(large, ImageResizer.MaxSize.SMALL));
            image.setSmallContentType(CONTENT_TYPE);
            image.setHash(digest(image.getLarge()));
        }

        return super.save(image);
    }

    String digest(final byte[] input) {
        final var digest = md5.digest(input);
        return DatatypeConverter.printHexBinary(digest).toUpperCase();
    }

    boolean shouldResetImages(final ImageDTO image) {
        return image.getLarge() == null;
    }

    boolean shouldResizeImage(final ImageDTO image) {
        if (image.getLarge() == null) {
            return false;
        }

        if (image.getMedium() == null || image.getSmall() == null) {
            return true;
        }

        return false;
    }
}
