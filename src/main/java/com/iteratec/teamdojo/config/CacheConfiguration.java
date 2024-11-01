package com.iteratec.teamdojo.config;

import com.iteratec.teamdojo.GeneratedByJHipster;
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
@GeneratedByJHipster
public class CacheConfiguration {

    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
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
            createCache(cm, com.iteratec.teamdojo.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.iteratec.teamdojo.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.iteratec.teamdojo.domain.User.class.getName());
            createCache(cm, com.iteratec.teamdojo.domain.Authority.class.getName());
            createCache(cm, com.iteratec.teamdojo.domain.User.class.getName() + ".authorities");
            createCache(cm, com.iteratec.teamdojo.domain.Activity.class.getName());
            createCache(cm, com.iteratec.teamdojo.domain.Badge.class.getName());
            createCache(cm, com.iteratec.teamdojo.domain.Badge.class.getName() + ".skills");
            createCache(cm, com.iteratec.teamdojo.domain.Badge.class.getName() + ".dimensions");
            createCache(cm, com.iteratec.teamdojo.domain.BadgeSkill.class.getName());
            createCache(cm, com.iteratec.teamdojo.domain.Comment.class.getName());
            createCache(cm, com.iteratec.teamdojo.domain.Dimension.class.getName());
            createCache(cm, com.iteratec.teamdojo.domain.Dimension.class.getName() + ".levels");
            createCache(cm, com.iteratec.teamdojo.domain.Dimension.class.getName() + ".badges");
            createCache(cm, com.iteratec.teamdojo.domain.Dimension.class.getName() + ".participants");
            createCache(cm, com.iteratec.teamdojo.domain.Image.class.getName());
            createCache(cm, com.iteratec.teamdojo.domain.Level.class.getName());
            createCache(cm, com.iteratec.teamdojo.domain.Level.class.getName() + ".skills");
            createCache(cm, com.iteratec.teamdojo.domain.LevelSkill.class.getName());
            createCache(cm, com.iteratec.teamdojo.domain.Report.class.getName());
            createCache(cm, com.iteratec.teamdojo.domain.Skill.class.getName());
            createCache(cm, com.iteratec.teamdojo.domain.Skill.class.getName() + ".badges");
            createCache(cm, com.iteratec.teamdojo.domain.Skill.class.getName() + ".levels");
            createCache(cm, com.iteratec.teamdojo.domain.Skill.class.getName() + ".teams");
            createCache(cm, com.iteratec.teamdojo.domain.Skill.class.getName() + ".trainings");
            createCache(cm, com.iteratec.teamdojo.domain.Team.class.getName());
            createCache(cm, com.iteratec.teamdojo.domain.Team.class.getName() + ".skills");
            createCache(cm, com.iteratec.teamdojo.domain.Team.class.getName() + ".participations");
            createCache(cm, com.iteratec.teamdojo.domain.TeamSkill.class.getName());
            createCache(cm, com.iteratec.teamdojo.domain.Training.class.getName());
            createCache(cm, com.iteratec.teamdojo.domain.Training.class.getName() + ".skills");
            createCache(cm, com.iteratec.teamdojo.domain.PersistentAuditEvent.class.getName());
            createCache(cm, com.iteratec.teamdojo.domain.PersistentAuditEvent.class.getName() + ".data");
            createCache(cm, com.iteratec.teamdojo.domain.PersistentAuditEventData.class.getName());
            createCache(cm, com.iteratec.teamdojo.domain.TeamGroup.class.getName());
            createCache(cm, com.iteratec.teamdojo.domain.TeamGroup.class.getName() + ".teams");
            // jhipster-needle-ehcache-add-entry
        };
    }

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
