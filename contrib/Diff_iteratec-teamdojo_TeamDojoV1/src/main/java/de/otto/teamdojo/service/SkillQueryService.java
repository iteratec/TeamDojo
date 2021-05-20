4a5,6
> import javax.persistence.criteria.JoinType;
> 
19d20
< 
23,24d23
< import javax.persistence.criteria.JoinType;
< 
108,110d106
<             if (criteria.getScore() != null) {
<                 specification = specification.and(buildRangeSpecification(criteria.getScore(), Skill_.score));
<             }
113a110,112
>             if (criteria.getScore() != null) {
>                 specification = specification.and(buildRangeSpecification(criteria.getScore(), Skill_.score));
>             }
120,123d118
<             if (criteria.getTeamsId() != null) {
<                 specification = specification.and(buildSpecification(criteria.getTeamsId(),
<                     root -> root.join(Skill_.teams, JoinType.LEFT).get(TeamSkill_.id)));
<             }
131a127,130
>             if (criteria.getTeamsId() != null) {
>                 specification = specification.and(buildSpecification(criteria.getTeamsId(),
>                     root -> root.join(Skill_.teams, JoinType.LEFT).get(TeamSkill_.id)));
>             }
133c132,133
<                 specification = specification.and(buildReferringEntitySpecification(criteria.getTrainingsId(), Skill_.trainings, Training_.id));
---
>                 specification = specification.and(buildSpecification(criteria.getTrainingsId(),
>                     root -> root.join(Skill_.trainings, JoinType.LEFT).get(Training_.id)));
