package com.iteratec.teamdojo.config;

import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.*;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.config.cache.PrefixedKeyGenerator;

@Configuration
@EnableCaching
public class CacheExtendedConfiguration {

    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheExtendedConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration =
            Eh107Configuration.fromEhcacheCacheConfiguration(
                CacheConfigurationBuilder
                    .newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                    .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                    .build()
            );
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(com.iteratec.teamdojo.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(com.iteratec.teamdojo.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(com.iteratec.teamdojo.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.iteratec.teamdojo.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.iteratec.teamdojo.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.iteratec.teamdojo.domain.PersistentToken.class.getName(), jcacheConfiguration);
            cm.createCache(com.iteratec.teamdojo.domain.User.class.getName() + ".persistentTokens", jcacheConfiguration);
            cm.createCache(com.iteratec.teamdojo.domain.Dimension.class.getName(), jcacheConfiguration);
            cm.createCache(com.iteratec.teamdojo.domain.Dimension.class.getName() + ".participants", jcacheConfiguration);
            cm.createCache(com.iteratec.teamdojo.domain.Dimension.class.getName() + ".levels", jcacheConfiguration);
            cm.createCache(com.iteratec.teamdojo.domain.Dimension.class.getName() + ".badges", jcacheConfiguration);
            cm.createCache(com.iteratec.teamdojo.domain.Skill.class.getName(), jcacheConfiguration);
            cm.createCache(com.iteratec.teamdojo.domain.Skill.class.getName() + ".teams", jcacheConfiguration);
            cm.createCache(com.iteratec.teamdojo.domain.Skill.class.getName() + ".badges", jcacheConfiguration);
            cm.createCache(com.iteratec.teamdojo.domain.Skill.class.getName() + ".levels", jcacheConfiguration);
            cm.createCache(com.iteratec.teamdojo.domain.Team.class.getName(), jcacheConfiguration);
            cm.createCache(com.iteratec.teamdojo.domain.Team.class.getName() + ".participations", jcacheConfiguration);
            cm.createCache(com.iteratec.teamdojo.domain.Team.class.getName() + ".skills", jcacheConfiguration);
            cm.createCache(com.iteratec.teamdojo.domain.TeamSkill.class.getName(), jcacheConfiguration);
            cm.createCache(com.iteratec.teamdojo.domain.Level.class.getName(), jcacheConfiguration);
            cm.createCache(com.iteratec.teamdojo.domain.Level.class.getName() + ".skills", jcacheConfiguration);
            cm.createCache(com.iteratec.teamdojo.domain.Badge.class.getName(), jcacheConfiguration);
            cm.createCache(com.iteratec.teamdojo.domain.Badge.class.getName() + ".skills", jcacheConfiguration);
            cm.createCache(com.iteratec.teamdojo.domain.Badge.class.getName() + ".dimensions", jcacheConfiguration);
            cm.createCache(com.iteratec.teamdojo.domain.BadgeSkill.class.getName(), jcacheConfiguration);
            cm.createCache(com.iteratec.teamdojo.domain.LevelSkill.class.getName(), jcacheConfiguration);
            cm.createCache(com.iteratec.teamdojo.domain.Organisation.class.getName(), jcacheConfiguration);
            cm.createCache(com.iteratec.teamdojo.domain.Report.class.getName(), jcacheConfiguration);
            cm.createCache(com.iteratec.teamdojo.domain.Comment.class.getName(), jcacheConfiguration);
            cm.createCache(com.iteratec.teamdojo.domain.Activity.class.getName(), jcacheConfiguration);
            cm.createCache(com.iteratec.teamdojo.domain.Image.class.getName(), jcacheConfiguration);
            cm.createCache(com.iteratec.teamdojo.domain.Training.class.getName(), jcacheConfiguration);
            cm.createCache(com.iteratec.teamdojo.domain.Training.class.getName() + ".skills", jcacheConfiguration);
            cm.createCache(com.iteratec.teamdojo.domain.Skill.class.getName() + ".trainings", jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }

    /** TODO remove  */
    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
