113,116d112
<             if (criteria.getParticipationsId() != null) {
<                 specification = specification.and(buildSpecification(criteria.getParticipationsId(),
<                     root -> root.join(Team_.participations, JoinType.LEFT).get(Dimension_.id)));
<             }
123a120,123
>             }
>             if (criteria.getParticipationsId() != null) {
>                 specification = specification.and(buildSpecification(criteria.getParticipationsId(),
>                     root -> root.join(Team_.participations, JoinType.LEFT).get(Dimension_.id)));
