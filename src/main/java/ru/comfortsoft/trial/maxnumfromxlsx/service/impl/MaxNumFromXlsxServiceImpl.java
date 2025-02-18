package ru.comfortsoft.trial.maxnumfromxlsx.service.impl;

import org.apache.poi.EmptyFileException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import ru.comfortsoft.trial.maxnumfromxlsx.service.MaxNumFromXlsxService;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

@Service
public class MaxNumFromXlsxServiceImpl implements MaxNumFromXlsxService {
    @Override
    public int maxNumFromXlsx(String filePath, int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n должно быть больше 0");
        }
        try (FileInputStream file = new FileInputStream(filePath)) {
            List<Integer> numbers = readXlsx(file);
            if (numbers.isEmpty()) {
                throw new IllegalArgumentException("В файле не найдены числа");
            }
            return nMaxNum(numbers, n);
        } catch (IOException | EmptyFileException e) {
            throw new IllegalArgumentException("Ошибка при обработке файла. " + e.getMessage());
        }
    }

    /**
     * Находим n-ое максимально число из несортированного списка чисел.
     * Обрати внимание, предварительно уже есть проверки на ненулевой n и numbers, поэтому
     * здесь уже нет проверок, зато есть SuppressWarnings ;)
     *
     * @param numbers список целых чисел
     * @param n       которое по счёту максимальное число будем искать
     * @return n-ое максимально число
     */
    @SuppressWarnings("DataFlowIssue")
    private int nMaxNum(List<Integer> numbers, int n) {
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        for (int num : numbers) {
            queue.offer(num);
            if (queue.size() > n) {
                queue.poll();
            }
        }
        return queue.peek();
    }

    /**
     * Читаем XLSX-файла и извлекаем целые числа из первого столбца
     *
     * @param file FileInputStream XLSX-файла
     * @return список целых чисел
     * @throws IOException если не сможет прочитать файл, то что с этим делать
     *                     должен решать вызывающий метод
     */
    private List<Integer> readXlsx(FileInputStream file) throws IOException {
        List<Integer> numbers = new ArrayList<>();
        Workbook workbook = WorkbookFactory.create(file);
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
