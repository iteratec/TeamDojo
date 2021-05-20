107,110d106
<             if (criteria.getDimensionId() != null) {
<                 specification = specification.and(buildSpecification(criteria.getDimensionId(),
<                     root -> root.join(Level_.dimension, JoinType.LEFT).get(Dimension_.id)));
<             }
121a118,121
>             }
>             if (criteria.getDimensionId() != null) {
>                 specification = specification.and(buildSpecification(criteria.getDimensionId(),
>                     root -> root.join(Level_.dimension, JoinType.LEFT).get(Dimension_.id)));
