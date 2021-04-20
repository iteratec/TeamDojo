package com.iteratec.teamdojo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Team Dojo.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 * See {@link tech.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationPropertiesExtended extends ApplicationProperties {

    private String mattermost;
    private String frontend;

    public void setMattermost(String mattermost) {
        this.mattermost = mattermost;
    }

    public void setFrontend(String frontend) {
        this.frontend = frontend;
    }

    public String getMattermost() {
        return mattermost;
    }

    public String getFrontend() {
        return frontend;
    }
}
