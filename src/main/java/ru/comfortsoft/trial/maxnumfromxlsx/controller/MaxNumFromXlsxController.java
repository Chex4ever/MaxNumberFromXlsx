package ru.comfortsoft.trial.maxnumfromxlsx.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.comfortsoft.trial.maxnumfromxlsx.service.MaxNumFromXlsxService;

@RestController
@RequestMapping("/api")
public class MaxNumFromXlsxController {
    private final MaxNumFromXlsxService maxNumFromXlsxService;

    public MaxNumFromXlsxController(MaxNumFromXlsxService maxNumFromXlsxService) {
        this.maxNumFromXlsxService = maxNumFromXlsxService;
    }

    @Operation(summary = "Получить N-ое максимальное число из XLSX")
    @PostMapping("findMaxNumFromXLSX")
    public ResponseEntity<?> findMaxNumFromXLSX(@Parameter(description = "Локальный путь к XLSX с целыми числами столбиком") @RequestParam("filePath") String filePath, @Parameter(description = "N-ое максимальное число, которое необходимо найти") @RequestParam("n") int n) {
        return maxNumFromXlsxService.maxNumFromXlsx(filePath, n);
    }
}
