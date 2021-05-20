2a3
> import de.otto.teamdojo.service.ImageService;
5d5
< import de.otto.teamdojo.service.ImageService;
9a10
> 
15,23d15
< import javax.imageio.ImageIO;
< import javax.xml.bind.DatatypeConverter;
< import java.awt.*;
< import java.awt.image.BufferedImage;
< import java.io.ByteArrayInputStream;
< import java.io.ByteArrayOutputStream;
< import java.io.IOException;
< import java.security.MessageDigest;
< import java.security.NoSuchAlgorithmException;
33,37d24
<     public static final int MAX_SIZE_LARGE = 512;
<     public static final int MAX_SIZE_MEDIUM = 224;
<     public static final int MAX_SIZE_SMALL = 72;
<     public static final String IMAGE_FORMAT = "png";
< 
56c43
<     public ImageDTO save(ImageDTO imageDTO) throws NoSuchAlgorithmException {
---
>     public ImageDTO save(ImageDTO imageDTO) {
58,87d44
< 
<         byte[] imgByteArray = imageDTO.getLarge();
<         if (imgByteArray != null) {
<             String contentType = "image/" + IMAGE_FORMAT;
<             BufferedImage img = createImageFromBytes(imgByteArray);
<             BufferedImage large = resize(img, MAX_SIZE_LARGE);
<             BufferedImage medium = resize(img, MAX_SIZE_MEDIUM);
<             BufferedImage small = resize(img, MAX_SIZE_SMALL);
<             imageDTO.setLarge(getByteArrayFromBufferedImage(large));
<             imageDTO.setLargeContentType(contentType);
<             imageDTO.setMedium(getByteArrayFromBufferedImage(medium));
<             imageDTO.setMediumContentType(contentType);
<             imageDTO.setSmall(getByteArrayFromBufferedImage(small));
<             imageDTO.setSmallContentType(contentType);
< 
<             MessageDigest md = MessageDigest.getInstance("MD5");
<             byte[] imageDigest = md.digest(imageDTO.getLarge());
<             String hash = DatatypeConverter
<                 .printHexBinary(imageDigest).toUpperCase();
<             imageDTO.setHash(hash);
<         } else {
<             imageDTO.setLarge(null);
<             imageDTO.setLargeContentType(null);
<             imageDTO.setMedium(null);
<             imageDTO.setMediumContentType(null);
<             imageDTO.setSmall(null);
<             imageDTO.setSmallContentType(null);
<             imageDTO.setHash(null);
<         }
< 
93,137d49
<     private byte[] getByteArrayFromBufferedImage(BufferedImage img) {
<         try {
<             ByteArrayOutputStream baos = new ByteArrayOutputStream();
<             ImageIO.write(img, IMAGE_FORMAT, baos);
<             baos.flush();
<             byte[] imageInByte = baos.toByteArray();
<             baos.close();
<             return imageInByte;
<         } catch (IOException e) {
<             System.out.println(e.getMessage());
<             return null;
<         }
<     }
< 
<     private BufferedImage resize(BufferedImage img, int max) {
<         // no scaling if img width and height are smaller than max
<         if (img.getWidth() <= max && img.getHeight() <= max) {
<             return img;
<         }
< 
<         int width = max;
<         int height = max;
<         if (img.getWidth() < img.getHeight()) {
<             width = (int) (img.getWidth() * (1.0 * height / img.getHeight()));
<         } else {
<             height = (int) (img.getHeight() * (1.0 * width / img.getWidth()));
<         }
< 
<         java.awt.Image tmp = img.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
<         BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
<         Graphics2D g2d = resized.createGraphics();
<         g2d.drawImage(tmp, 0, 0, null);
<         g2d.dispose();
<         return resized;
<     }
< 
<     private BufferedImage createImageFromBytes(byte[] imageData) {
<         ByteArrayInputStream bais = new ByteArrayInputStream(imageData);
<         try {
<             return ImageIO.read(bais);
<         } catch (IOException e) {
<             throw new RuntimeException(e);
<         }
<     }
< 
168,181d79
<      * Get one image by name.
<      *
<      * @param name the name of the entity
<      * @return the entity
<      */
<     @Override
<     @Transactional(readOnly = true)
<     public Optional<ImageDTO> findByName(String name) {
<         log.debug("Request to get Image : {}", name);
<         return imageRepository.findByName(name)
<             .map(imageMapper::toDto);
<     }
< 
<     /**
188,189c86
<         log.debug("Request to delete Image : {}", id);
<         imageRepository.deleteById(id);
---
>         log.debug("Request to delete Image : {}", id);        imageRepository.deleteById(id);
