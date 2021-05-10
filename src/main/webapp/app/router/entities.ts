import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore

// prettier-ignore
const Activity = () => import('@/entities/activity/activity.vue');
// prettier-ignore
const ActivityUpdate = () => import('@/entities/activity/activity-update.vue');
// prettier-ignore
const ActivityDetails = () => import('@/entities/activity/activity-details.vue');
// prettier-ignore
const Badge = () => import('@/entities/badge/badge.vue');
// prettier-ignore
const BadgeUpdate = () => import('@/entities/badge/badge-update.vue');
// prettier-ignore
const BadgeDetails = () => import('@/entities/badge/badge-details.vue');
// prettier-ignore
const BadgeSkill = () => import('@/entities/badge-skill/badge-skill.vue');
// prettier-ignore
const BadgeSkillUpdate = () => import('@/entities/badge-skill/badge-skill-update.vue');
// prettier-ignore
const BadgeSkillDetails = () => import('@/entities/badge-skill/badge-skill-details.vue');
// prettier-ignore
const Comment = () => import('@/entities/comment/comment.vue');
// prettier-ignore
const CommentUpdate = () => import('@/entities/comment/comment-update.vue');
// prettier-ignore
const CommentDetails = () => import('@/entities/comment/comment-details.vue');
// prettier-ignore
const Dimension = () => import('@/entities/dimension/dimension.vue');
// prettier-ignore
const DimensionUpdate = () => import('@/entities/dimension/dimension-update.vue');
// prettier-ignore
const DimensionDetails = () => import('@/entities/dimension/dimension-details.vue');
// prettier-ignore
const Image = () => import('@/entities/image/image.vue');
// prettier-ignore
const ImageUpdate = () => import('@/entities/image/image-update.vue');
// prettier-ignore
const ImageDetails = () => import('@/entities/image/image-details.vue');
// prettier-ignore
const Level = () => import('@/entities/level/level.vue');
// prettier-ignore
const LevelUpdate = () => import('@/entities/level/level-update.vue');
// prettier-ignore
const LevelDetails = () => import('@/entities/level/level-details.vue');
// prettier-ignore
const LevelSkill = () => import('@/entities/level-skill/level-skill.vue');
// prettier-ignore
const LevelSkillUpdate = () => import('@/entities/level-skill/level-skill-update.vue');
// prettier-ignore
const LevelSkillDetails = () => import('@/entities/level-skill/level-skill-details.vue');
// prettier-ignore
const Organisation = () => import('@/entities/organisation/organisation.vue');
// prettier-ignore
const OrganisationUpdate = () => import('@/entities/organisation/organisation-update.vue');
// prettier-ignore
const OrganisationDetails = () => import('@/entities/organisation/organisation-details.vue');
// prettier-ignore
const Report = () => import('@/entities/report/report.vue');
// prettier-ignore
const ReportUpdate = () => import('@/entities/report/report-update.vue');
// prettier-ignore
const ReportDetails = () => import('@/entities/report/report-details.vue');
// prettier-ignore
const Skill = () => import('@/entities/skill/skill.vue');
// prettier-ignore
const SkillUpdate = () => import('@/entities/skill/skill-update.vue');
// prettier-ignore
const SkillDetails = () => import('@/entities/skill/skill-details.vue');
// prettier-ignore
const Team = () => import('@/entities/team/team.vue');
// prettier-ignore
const TeamUpdate = () => import('@/entities/team/team-update.vue');
// prettier-ignore
const TeamDetails = () => import('@/entities/team/team-details.vue');
// prettier-ignore
const TeamSkill = () => import('@/entities/team-skill/team-skill.vue');
// prettier-ignore
const TeamSkillUpdate = () => import('@/entities/team-skill/team-skill-update.vue');
// prettier-ignore
const TeamSkillDetails = () => import('@/entities/team-skill/team-skill-details.vue');
// prettier-ignore
const Training = () => import('@/entities/training/training.vue');
// prettier-ignore
const TrainingUpdate = () => import('@/entities/training/training-update.vue');
// prettier-ignore
const TrainingDetails = () => import('@/entities/training/training-details.vue');
// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default [
  {
    path: '/activity',
    name: 'Activity',
    component: Activity,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/activity/new',
    name: 'ActivityCreate',
    component: ActivityUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/activity/:activityId/edit',
    name: 'ActivityEdit',
    component: ActivityUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/activity/:activityId/view',
    name: 'ActivityView',
    component: ActivityDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/badge',
    name: 'Badge',
    component: Badge,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/badge/new',
    name: 'BadgeCreate',
    component: BadgeUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/badge/:badgeId/edit',
    name: 'BadgeEdit',
    component: BadgeUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/badge/:badgeId/view',
    name: 'BadgeView',
    component: BadgeDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/badge-skill',
    name: 'BadgeSkill',
    component: BadgeSkill,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/badge-skill/new',
    name: 'BadgeSkillCreate',
    component: BadgeSkillUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/badge-skill/:badgeSkillId/edit',
    name: 'BadgeSkillEdit',
    component: BadgeSkillUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/badge-skill/:badgeSkillId/view',
    name: 'BadgeSkillView',
    component: BadgeSkillDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/comment',
    name: 'Comment',
    component: Comment,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/comment/new',
    name: 'CommentCreate',
    component: CommentUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/comment/:commentId/edit',
    name: 'CommentEdit',
    component: CommentUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/comment/:commentId/view',
    name: 'CommentView',
    component: CommentDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/dimension',
    name: 'Dimension',
    component: Dimension,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/dimension/new',
    name: 'DimensionCreate',
    component: DimensionUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/dimension/:dimensionId/edit',
    name: 'DimensionEdit',
    component: DimensionUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/dimension/:dimensionId/view',
    name: 'DimensionView',
    component: DimensionDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/image',
    name: 'Image',
    component: Image,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/image/new',
    name: 'ImageCreate',
    component: ImageUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/image/:imageId/edit',
    name: 'ImageEdit',
    component: ImageUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/image/:imageId/view',
    name: 'ImageView',
    component: ImageDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/level',
    name: 'Level',
    component: Level,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/level/new',
    name: 'LevelCreate',
    component: LevelUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/level/:levelId/edit',
    name: 'LevelEdit',
    component: LevelUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/level/:levelId/view',
    name: 'LevelView',
    component: LevelDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/level-skill',
    name: 'LevelSkill',
    component: LevelSkill,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/level-skill/new',
    name: 'LevelSkillCreate',
    component: LevelSkillUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/level-skill/:levelSkillId/edit',
    name: 'LevelSkillEdit',
    component: LevelSkillUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/level-skill/:levelSkillId/view',
    name: 'LevelSkillView',
    component: LevelSkillDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/organisation',
    name: 'Organisation',
    component: Organisation,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/organisation/new',
    name: 'OrganisationCreate',
    component: OrganisationUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/organisation/:organisationId/edit',
    name: 'OrganisationEdit',
    component: OrganisationUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/organisation/:organisationId/view',
    name: 'OrganisationView',
    component: OrganisationDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/report',
    name: 'Report',
    component: Report,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/report/new',
    name: 'ReportCreate',
    component: ReportUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/report/:reportId/edit',
    name: 'ReportEdit',
    component: ReportUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/report/:reportId/view',
    name: 'ReportView',
    component: ReportDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/skill',
    name: 'Skill',
    component: Skill,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/skill/new',
    name: 'SkillCreate',
    component: SkillUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/skill/:skillId/edit',
    name: 'SkillEdit',
    component: SkillUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/skill/:skillId/view',
    name: 'SkillView',
    component: SkillDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/team',
    name: 'Team',
    component: Team,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/team/new',
    name: 'TeamCreate',
    component: TeamUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/team/:teamId/edit',
    name: 'TeamEdit',
    component: TeamUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/team/:teamId/view',
    name: 'TeamView',
    component: TeamDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/team-skill',
    name: 'TeamSkill',
    component: TeamSkill,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/team-skill/new',
    name: 'TeamSkillCreate',
    component: TeamSkillUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/team-skill/:teamSkillId/edit',
    name: 'TeamSkillEdit',
    component: TeamSkillUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/team-skill/:teamSkillId/view',
    name: 'TeamSkillView',
    component: TeamSkillDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/training',
    name: 'Training',
    component: Training,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/training/new',
    name: 'TrainingCreate',
    component: TrainingUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/training/:trainingId/edit',
    name: 'TrainingEdit',
    component: TrainingUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/training/:trainingId/view',
    name: 'TrainingView',
    component: TrainingDetails,
    meta: { authorities: [Authority.USER] },
  },
  // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
];
