package ru.comfortsoft.trial.maxnumfromxlsx.service.impl;

import org.apache.poi.ss.usermodel.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.comfortsoft.trial.maxnumfromxlsx.service.MaxNumFromXlsxService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

@Service
public class MaxNumFromXlsxServiceImpl implements MaxNumFromXlsxService {
    @Override
    public ResponseEntity<?> maxNumFromXlsx(MultipartFile file, int n) {
        if (n<=0){
            return ResponseEntity.badRequest().body("n должно быть больше 0");
        }
        try {
            List<Integer> numbers = readXlsx(file);
            int result = nMaxNum(numbers, n);
            return ResponseEntity.ok(result);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Ошибка при обработке файла. " + e.getMessage());
        }
    }

    /**
     * Находим n-ое максимально число из несортированного списка чисел.
     * Стандартный способ для такой задачи, даже IDEA подставила большинство
     * кода, мне надо было только начать PriorityQ... и нажимать TAB :)
     * ну почти...
     * @param numbers список целых чисел
     * @param n которое по счёту максимальное число будем искать
     * @return n-ое максимально число
     */
    private int nMaxNum(List<Integer> numbers, int n) {
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        for (int num: numbers) {
            queue.offer(num);
            if (queue.size() > n){
                queue.poll();
            }
        }
        return queue.peek();
    }

    /**
     * Извлекаем целые числа из первого столбца XLSX-файла
     * @param file XLSX-файл
     * @return список целых чисел
     * @throws IOException если не сможет прочитать файл, то что с этим делать
     * должен решать вызывающий метод
     */
    private List<Integer> readXlsx(MultipartFile file) throws IOException {
        List<Integer> numbers = new ArrayList<>();
        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);
        for (Row row : sheet) {
            Cell cell = row.getCell(0);
            if (cell != null && cell.getCellType() == CellType.NUMERIC) {
                numbers.add((int) cell.getNumericCellValue());
            }
        }
    workbook.close();
        return numbers;
    }

}
