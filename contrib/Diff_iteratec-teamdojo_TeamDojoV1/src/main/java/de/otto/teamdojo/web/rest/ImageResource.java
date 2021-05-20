14d13
< import org.springframework.http.CacheControl;
21d19
< import javax.xml.bind.DatatypeConverter;
25,26d22
< import java.security.MessageDigest;
< import java.security.NoSuchAlgorithmException;
29d24
< import java.util.concurrent.TimeUnit;
59c54
<     public ResponseEntity<ImageDTO> createImage(@Valid @RequestBody ImageDTO imageDTO) throws URISyntaxException, NoSuchAlgorithmException {
---
>     public ResponseEntity<ImageDTO> createImage(@Valid @RequestBody ImageDTO imageDTO) throws URISyntaxException {
80c75
<     public ResponseEntity<ImageDTO> updateImage(@Valid @RequestBody ImageDTO imageDTO) throws URISyntaxException, NoSuchAlgorithmException {
---
>     public ResponseEntity<ImageDTO> updateImage(@Valid @RequestBody ImageDTO imageDTO) throws URISyntaxException {
129,187d123
<     }
< 
<     /**
<      * GET  /images/:id : get the "id" image.
<      *
<      * @param id the id of the imageDTO to retrieve
<      * @return the ResponseEntity with status 200 (OK) and with body the imageDTO, or with status 404 (Not Found)
<      */
<     @GetMapping("/images/{id}/content")
<     public ResponseEntity<byte[]> getImageContent(@PathVariable Long id, @RequestParam(value="size", required=false) String size) {
<         log.debug("REST request to get Image : {}", id);
<         Optional<ImageDTO> imageDTO = imageService.findOne(id);
<         if (!imageDTO.isPresent()) {
<             return new ResponseEntity<>(HttpStatus.NOT_FOUND);
<         }
<         ImageDTO image = imageDTO.get();
< 
<         size = size == null ? "LARGE" : size.toUpperCase();
<         byte[] imageBlob;
<         String contentType;
<         if (size.equals("SMALL")) {
<             imageBlob = image.getSmall();
<             contentType = image.getSmallContentType();
<         } else if (size.equals("MEDIUM")) {
<             imageBlob = image.getMedium();
<             contentType = image.getMediumContentType();
<         } else {
<             imageBlob = image.getLarge();
<             contentType = image.getLargeContentType();
<         }
<         return ResponseEntity.ok()
<             .header("Content-Type", contentType)
<             .cacheControl(CacheControl.maxAge(30, TimeUnit.DAYS))
<             .body(imageBlob);
<     }
< 
<     /**
<      * GET  /images/name/:name : get the "name" image.
<      *
<      * @param name the name of the imageDTO to retrieve
<      * @return the ResponseEntity with status 200 (OK) and with body the imageDTO, or with status 404 (Not Found)
<      */
<     @GetMapping("/images/name/{name}")
<     public ResponseEntity<byte[]> getImageContent(@PathVariable String name) {
<         log.debug("REST request to get Image : {}", name);
<         Optional<ImageDTO> imageDTO = imageService.findByName(name);
<         if (!imageDTO.isPresent()) {
<             return new ResponseEntity<>(HttpStatus.NOT_FOUND);
<         }
<         ImageDTO image = imageDTO.get();
< 
<         byte[] imageBlob;
<         String contentType;
<             imageBlob = image.getLarge();
<             contentType = image.getLargeContentType();
<         return ResponseEntity.ok()
<             .header("Content-Type", contentType)
<             .cacheControl(CacheControl.maxAge(30, TimeUnit.DAYS))
<             .body(imageBlob);
