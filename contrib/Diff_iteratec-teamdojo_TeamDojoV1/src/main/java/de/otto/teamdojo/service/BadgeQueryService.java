117,120d116
<             if (criteria.getDimensionsId() != null) {
<                 specification = specification.and(buildSpecification(criteria.getDimensionsId(),
<                     root -> root.join(Badge_.dimensions, JoinType.LEFT).get(Dimension_.id)));
<             }
123a120,123
>             }
>             if (criteria.getDimensionsId() != null) {
>                 specification = specification.and(buildSpecification(criteria.getDimensionsId(),
>                     root -> root.join(Badge_.dimensions, JoinType.LEFT).get(Dimension_.id)));
