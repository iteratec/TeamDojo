4c4
< import org.json.JSONException;
---
> 
8d7
< import java.util.List;
22c21
<     BadgeDTO save(BadgeDTO badgeDTO) throws JSONException;
---
>     BadgeDTO save(BadgeDTO badgeDTO);
32,33d30
<     Page<BadgeDTO> findByIdIn(List<Long> badgeIds, Pageable pageable);
< 
40c37
< 
---
>     
