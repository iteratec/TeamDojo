package com.iteratec.teamdojo;

import com.iteratec.teamdojo.GeneratedByJHipster;
import com.iteratec.teamdojo.TeamDojoApp;
import com.iteratec.teamdojo.config.TestSecurityConfiguration;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Base composite annotation for integration tests.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(classes = { TeamDojoApp.class, TestSecurityConfiguration.class })
public @interface IntegrationTest {
}
