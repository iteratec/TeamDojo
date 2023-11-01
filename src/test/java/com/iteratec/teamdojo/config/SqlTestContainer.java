package com.iteratec.teamdojo.config;

import com.iteratec.teamdojo.GeneratedByJHipster;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.testcontainers.containers.JdbcDatabaseContainer;

@GeneratedByJHipster
public interface SqlTestContainer extends InitializingBean, DisposableBean {
    JdbcDatabaseContainer<?> getTestContainer();
}
