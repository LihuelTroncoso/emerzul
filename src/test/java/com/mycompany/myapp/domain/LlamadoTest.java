package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LlamadoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Llamado.class);
        Llamado llamado1 = new Llamado();
        llamado1.setId(1L);
        Llamado llamado2 = new Llamado();
        llamado2.setId(llamado1.getId());
        assertThat(llamado1).isEqualTo(llamado2);
        llamado2.setId(2L);
        assertThat(llamado1).isNotEqualTo(llamado2);
        llamado1.setId(null);
        assertThat(llamado1).isNotEqualTo(llamado2);
    }
}
