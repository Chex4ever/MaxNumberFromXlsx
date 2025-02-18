package ru.comfortsoft.trial.maxnumfromxlsx.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import ru.comfortsoft.trial.maxnumfromxlsx.service.MaxNumFromXlsxService;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MaxNumFromXlsxController.class)
public class MaxNumFromXlsxControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MaxNumFromXlsxService maxNumFromXlsxService;

    @Test
    void FindMaxNumFromXlsxTest_Positive() throws Exception {
        int expectedResult = 20;
        MultiValueMap<String, String> paramsMap = new LinkedMultiValueMap<>();
        paramsMap.add("filePath", "test.xlsx");
        paramsMap.add("n", "20");

        when(maxNumFromXlsxService.maxNumFromXlsx(anyString(), anyInt()))
                .thenReturn(expectedResult);

        mockMvc.perform(get("/api/findMaxNumFromXLSX")
                        .params(paramsMap))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(expectedResult)));
    }

    @Test
    void FindMaxNumFromXlsxTest_Negative_InvalidN() throws Exception {
        String filePath = "test.xlsx";
        int n = 0;
        String errorMessage = "n должно быть больше 0";

        when(maxNumFromXlsxService.maxNumFromXlsx(anyString(), anyInt()))
                .thenThrow(new IllegalArgumentException(errorMessage));

        mockMvc.perform(get("/api/findMaxNumFromXLSX")
                        .param("filePath", filePath)
                        .param("n", String.valueOf(n)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(errorMessage));
    }

    @Test
    void FindMaxNumFromXlsxTest_Negative_FileReadError() throws Exception {
        String filePath = "invalid.xlsx";
        int n = 2;
        String errorMessage = "Ошибка при обработке файла. File not found";

        when(maxNumFromXlsxService.maxNumFromXlsx(anyString(), anyInt()))
                .thenThrow(new IllegalArgumentException(errorMessage));

        mockMvc.perform(get("/api/findMaxNumFromXLSX")
                        .param("filePath", filePath)
                        .param("n", String.valueOf(n)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(errorMessage));
    }
}