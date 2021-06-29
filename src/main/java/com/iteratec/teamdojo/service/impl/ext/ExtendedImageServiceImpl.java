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

    public static final int MAX_SIZE_LARGE = 512;
    public static final int MAX_SIZE_MEDIUM = 224;
    public static final int MAX_SIZE_SMALL = 72;
    public static final String IMAGE_FORMAT = "png";

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

    @Override
    @SneakyThrows(NoSuchAlgorithmException.class)
    public ImageDTO save(ImageDTO imageDTO) {
        log.debug("Request to save Image : {}", imageDTO);

        byte[] imgByteArray = imageDTO.getLarge();

        if (imgByteArray == null) {
            imageDTO.setLarge(null);
            imageDTO.setLargeContentType(null);
            imageDTO.setMedium(null);
            imageDTO.setMediumContentType(null);
            imageDTO.setSmall(null);
            imageDTO.setSmallContentType(null);
            imageDTO.setHash(null);
        } else {
            String contentType = "image/" + IMAGE_FORMAT;
            // FIXME: Here the method returns null which causes NPE in subsequent calls.
            BufferedImage img = createImageFromBytes(imgByteArray);
            log.warn("Given image byte array resulted in null!");
            BufferedImage large = resize(img, MAX_SIZE_LARGE);
            BufferedImage medium = resize(img, MAX_SIZE_MEDIUM);
            BufferedImage small = resize(img, MAX_SIZE_SMALL);
            imageDTO.setLarge(getByteArrayFromBufferedImage(large));
            imageDTO.setLargeContentType(contentType);
            imageDTO.setMedium(getByteArrayFromBufferedImage(medium));
            imageDTO.setMediumContentType(contentType);
            imageDTO.setSmall(getByteArrayFromBufferedImage(small));
            imageDTO.setSmallContentType(contentType);

            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] imageDigest = md.digest(imageDTO.getLarge());
            String hash = DatatypeConverter.printHexBinary(imageDigest).toUpperCase();
            imageDTO.setHash(hash);
        }

        return super.save(imageDTO);
    }

    private byte[] getByteArrayFromBufferedImage(BufferedImage img) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(img, IMAGE_FORMAT, baos);
            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            baos.close();
            return imageInByte;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    BufferedImage resize(BufferedImage img, int max) {
        if (img == null) {
            // It is possible that null is passed in.
            // Also it is possible to save null as images in the DTO.
            // So we make this method null save by just returning null.
            return null;
        }

        // no scaling if img width and height are smaller than max
        if (img.getWidth() <= max && img.getHeight() <= max) {
            return img;
        }

        int width = max;
        int height = max;
        if (img.getWidth() < img.getHeight()) {
            width = (int) (img.getWidth() * (1.0 * height / img.getHeight()));
        } else {
            height = (int) (img.getHeight() * (1.0 * width / img.getWidth()));
        }

        java.awt.Image tmp = img.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }

    private BufferedImage createImageFromBytes(byte[] imageData) {
        try {
            return ImageIO.read(new ByteArrayInputStream(imageData));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
