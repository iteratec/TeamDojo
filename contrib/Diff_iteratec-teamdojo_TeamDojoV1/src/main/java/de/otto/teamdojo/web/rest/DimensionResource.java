98c98
<         return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
---
>         return ResponseEntity.ok().headers(headers).body(page.getContent());
