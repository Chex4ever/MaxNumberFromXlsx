package ru.comfortsoft.trial.maxnumfromxlsx;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.comfortsoft.trial.maxnumfromxlsx.controller.MaxNumFromXlsxController;
import ru.comfortsoft.trial.maxnumfromxlsx.service.MaxNumFromXlsxService;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MaxNumFromXlsxApplicationTests {

    @Autowired
    private MaxNumFromXlsxController maxNumFromXlsxController;

    @Autowired
    private MaxNumFromXlsxService maxNumFromXlsxService;

    @Test
    void contextLoads() {
        assertThat(maxNumFromXlsxController).isNotNull();
        assertThat(maxNumFromXlsxService).isNotNull();
    }

}
