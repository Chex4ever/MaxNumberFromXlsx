package ru.comfortsoft.trial.maxnumfromxlsx.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface MaxNumFromXlsxService {
    ResponseEntity<?> maxNumFromXlsx(MultipartFile file, int n);
}
