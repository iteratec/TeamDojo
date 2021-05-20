package com.iteratec.teamdojo.config.ext;

import com.iteratec.teamdojo.config.ApplicationProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ExtendedApplicationProperties extends ApplicationProperties {

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
