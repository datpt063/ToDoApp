package com.demo.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.demo.web.rest.TestUtil;

public class TasksTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tasks.class);
        Tasks tasks1 = new Tasks();
        tasks1.setId(1L);
        Tasks tasks2 = new Tasks();
        tasks2.setId(tasks1.getId());
        assertThat(tasks1).isEqualTo(tasks2);
        tasks2.setId(2L);
        assertThat(tasks1).isNotEqualTo(tasks2);
        tasks1.setId(null);
        assertThat(tasks1).isNotEqualTo(tasks2);
    }
}
