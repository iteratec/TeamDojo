package com.iteratec.teamdojo.test.util;

import com.iteratec.teamdojo.service.mapper.*;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Helper to create mapper implementations with injected dependencies
 * <p>
 * Some oft he generated MapStruct mappers have dependencies to other mappers. Usually they are autowired via dependency
 * injection (DI) at runtime. We could spin up tests with soring boot test annotation for autowireing, but this means full
 * application stack is running for each test. This is way to slow for unit tests.
 * </p>
 * <p>
 * This is necessary due to the architecture of JHipster: They autowire the generated sub mappers via private fields –
 * which is highly discouraged by the way – and so we must use reflection magic which will break on field renames, or
 * we must use an extremely heavy
 * <p>
 */
public final class MapperFactory {

    private MapperFactory() {
        super();
    }

    public static CommentMapper newCommentMapper() {
        final CommentMapper mapper = new CommentMapperImpl();
        wantSkillAndTeamMapper(mapper);
        return mapper;
    }

    public static TeamMapper newTeamMapper() {
        final var mapper = new TeamMapperImpl();
        wantsImageAndDimensionMapper(mapper);
        return mapper;
    }

    public static TeamSkillMapper newTeamSkillMapper() {
        final var mapper = new TeamSkillMapperImpl();
        wantSkillAndTeamMapper(mapper);
        return mapper;
    }

    public static BadgeMapper newBadgeMapper() {
        final var mapper = new BadgeMapperImpl();
        wantsImageAndDimensionMapper(mapper);
        return mapper;
    }

    public static LevelMapper newLevelMapper() {
        final var mapper = new LevelMapperImpl();
        wantsImageAndDimensionMapper(mapper);
        return mapper;
    }

    public static TrainingMapper newTrainingMapper() {
        final var mapper = new TrainingMapperImpl();
        wantsSkillMapper(mapper);
        return mapper;
    }

    private static <D, E> void wantsImageAndDimensionMapper(final EntityMapper<D, E> mapper) {
        wantsImageMapper(mapper);
        wantsDimensionMapper(mapper);
    }

    private static <D, E> void wantSkillAndTeamMapper(final EntityMapper<D, E> mapper) {
        wantsTeamMapper(mapper);
        wantsSkillMapper(mapper);
    }

    private static <D, E> void wantsImageMapper(final EntityMapper<D, E> mapper) {
        ReflectionTestUtils.setField(mapper, "imageMapper", new ImageMapperImpl());
    }

    private static <D, E> void wantsDimensionMapper(final EntityMapper<D, E> mapper) {
        ReflectionTestUtils.setField(mapper, "dimensionMapper", new DimensionMapperImpl());
    }

    private static <D, E> void wantsTeamMapper(final EntityMapper<D, E> mapper) {
        ReflectionTestUtils.setField(mapper, "teamMapper", newTeamMapper());
    }

    private static <D, E> void wantsSkillMapper(final EntityMapper<D, E> mapper) {
        ReflectionTestUtils.setField(mapper, "skillMapper", new SkillMapperImpl());
    }
}
