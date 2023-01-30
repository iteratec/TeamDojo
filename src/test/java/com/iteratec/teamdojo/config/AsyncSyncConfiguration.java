package com.iteratec.teamdojo.config;

import com.iteratec.teamdojo.GeneratedByJHipster;
import java.util.concurrent.Executor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.task.SyncTaskExecutor;

@Configuration
@GeneratedByJHipster
public class AsyncSyncConfiguration {

    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {
        return new SyncTaskExecutor();
    }
}
