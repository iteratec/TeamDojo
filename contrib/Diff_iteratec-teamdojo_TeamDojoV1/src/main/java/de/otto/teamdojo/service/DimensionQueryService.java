47d46
<      *
60d58
<      *
62c60
<      * @param page     The page, which should be returned.
---
>      * @param page The page, which should be returned.
100,103d97
<             if (criteria.getParticipantsId() != null) {
<                 specification = specification.and(buildSpecification(criteria.getParticipantsId(),
<                     root -> root.join(Dimension_.participants, JoinType.LEFT).get(Team_.id)));
<             }
110a105,108
>             }
>             if (criteria.getParticipantsId() != null) {
>                 specification = specification.and(buildSpecification(criteria.getParticipantsId(),
>                     root -> root.join(Dimension_.participants, JoinType.LEFT).get(Team_.id)));
