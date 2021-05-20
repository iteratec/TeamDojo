3a4
> 
7d7
< import java.security.NoSuchAlgorithmException;
21c21
<     ImageDTO save(ImageDTO imageDTO) throws NoSuchAlgorithmException;
---
>     ImageDTO save(ImageDTO imageDTO);
39,46d38
< 
<     /**
<      * Get the "name" image.
<      *
<      * @param name the name of the entity
<      * @return the entity
<      */
<     Optional<ImageDTO> findByName(String name);
