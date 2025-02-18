package ru.comfortsoft.trial.maxnumfromxlsx.service;

import org.springframework.http.ResponseEntity;

public interface MaxNumFromXlsxService {
    ResponseEntity<?> maxNumFromXlsx(String filePath, int n);
}
