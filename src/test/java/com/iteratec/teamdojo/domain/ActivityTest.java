package com.iteratec.teamdojo.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.iteratec.teamdojo.GeneratedByJHipster;
import com.iteratec.teamdojo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

@GeneratedByJHipster
class ActivityTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Activity.class);
        Activity activity1 = new Activity();
        activity1.setId(1L);
        Activity activity2 = new Activity();
        activity2.setId(activity1.getId());
        assertThat(activity1).isEqualTo(activity2);
        activity2.setId(2L);
        assertThat(activity1).isNotEqualTo(activity2);
        activity1.setId(null);
        assertThat(activity1).isNotEqualTo(activity2);
    }
}
