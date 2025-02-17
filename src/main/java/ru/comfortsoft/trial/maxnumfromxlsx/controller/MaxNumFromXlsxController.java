package ru.comfortsoft.trial.maxnumfromxlsx.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.comfortsoft.trial.maxnumfromxlsx.service.MaxNumFromXlsxService;

@RestController
@RequestMapping("/api")
public class MaxNumFromXlsxController {
    private final MaxNumFromXlsxService maxNumFromXlsxService;

    public MaxNumFromXlsxController(MaxNumFromXlsxService maxNumFromXlsxService) {
        this.maxNumFromXlsxService = maxNumFromXlsxService;
    }

    @Operation(summary = "Получить N-ое максимальное число из XLSX")
    @PostMapping(path="findMaxNumFromXLSX", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> findMaxNumFromXLSX(
            @Parameter(description = "XLSX с целыми числами столбиком")
            @RequestPart("file")MultipartFile file,
            @Parameter(description = "N-ое максимальное число, которое необходимо найти")
            @RequestParam("n") int n
            ) {
    return maxNumFromXlsxService.maxNumFromXlsx(file,n);
    }
}
