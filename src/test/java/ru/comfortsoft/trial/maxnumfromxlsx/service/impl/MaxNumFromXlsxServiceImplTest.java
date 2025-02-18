package ru.comfortsoft.trial.maxnumfromxlsx.service.impl;

import org.apache.poi.ss.usermodel.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MaxNumFromXlsxServiceImplTest {
    @InjectMocks
    private MaxNumFromXlsxServiceImpl maxNumFromXlsxService;

    @Mock
    private Workbook workbook;

    @Mock
    private Sheet sheet;

    @Mock
    private Row row;

    @Mock
    private Cell cell;

    @Test
    void testMaxNumFromXlsx_Positive() throws IOException {
        File tempFile = File.createTempFile("test", ".xlsx");
        tempFile.deleteOnExit();
        String filePath = tempFile.getPath();
        int n = 3;

        when(workbook.getSheetAt(0)).thenReturn(sheet);
        when(sheet.iterator()).thenReturn(List.of(row, row, row, row, row, row).iterator());
        when(row.getCell(0)).thenReturn(cell);
        when(cell.getCellType()).thenReturn(CellType.NUMERIC);
        when(cell.getNumericCellValue()).thenReturn(10.0, 20.0, 30.0, 40.0, 50.0, 60.0);

        try (MockedStatic<WorkbookFactory> workbookFactoryMock = mockStatic(WorkbookFactory.class)) {
            workbookFactoryMock.when(() -> WorkbookFactory.create(any(FileInputStream.class))).thenReturn(workbook);
            int response = maxNumFromXlsxService.maxNumFromXlsx(filePath, n);
            assertEquals(40, response);
        }
    }

    @Test
    void testMaxNumFromXlsx_Negative_NotValidFile() throws IOException {
        String expected = "В файле не найдены числа";
        File tempFile = File.createTempFile("invalid", ".xlsx");
        tempFile.deleteOnExit();
        String filePath = tempFile.getPath();
        int n = 2;

        when(workbook.getSheetAt(0)).thenReturn(sheet);
        when(sheet.iterator()).thenReturn(List.of(row, row, row).iterator());
        when(row.getCell(0)).thenReturn(cell, cell, null);
        when(cell.getCellType()).thenReturn(CellType.STRING, null, CellType.STRING);

        try (MockedStatic<WorkbookFactory> workbookFactoryMock = mockStatic(WorkbookFactory.class)) {
            workbookFactoryMock.when(() -> WorkbookFactory.create(any(FileInputStream.class))).thenReturn(workbook);
            IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                    () -> maxNumFromXlsxService.maxNumFromXlsx(filePath, n));
            assertEquals(expected, e.getMessage());
        }

    }

    @Test
    void testMaxNumFromXlsx_Negative_InvalidN() {
        String expected = "n должно быть больше 0";
        String filePath = "test.xlsx";
        int n = 0;
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> maxNumFromXlsxService.maxNumFromXlsx(filePath, n));
        assertEquals(expected, e.getMessage());
    }

    @Test
    void testMaxNumFromXlsx_Negative_FileReadError() {
        String expected = "Ошибка при обработке файла. invalid.xlsx (Не удается найти указанный файл)";
        String filePath = "invalid.xlsx";
        int n = 2;
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> maxNumFromXlsxService.maxNumFromXlsx(filePath, n));
        assertEquals(expected, e.getMessage());
    }
}