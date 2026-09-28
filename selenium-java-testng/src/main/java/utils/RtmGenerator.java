package utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;

public class RtmGenerator {

    private static final String[][] ROWS = {
            {"REQ-01", "User can register a new account", "TC-01", "RegisterTests.testRegisterUser", "High", "Functional - Positive", "AutomationExercise Test Case Repository", "Automated"},
            {"REQ-02", "User can log in with valid credentials", "TC-02", "LoginTests.testValidLogin", "Critical", "Functional - Positive", "AutomationExercise Test Case Repository", "Automated"},
            {"REQ-03", "Invalid login is rejected with an error message", "TC-03", "LoginTests.testInvalidLogin", "High", "Functional - Negative", "AutomationExercise Test Case Repository", "Automated"},
            {"REQ-04", "User can submit the Contact Us form with an attachment", "TC-06", "ContactUsTests.testContactUsForm", "Medium", "Functional - Positive", "AutomationExercise Test Case Repository", "Automated"},
            {"REQ-05", "User can search for products and see results", "TC-09", "SearchProductTests.testSearchProduct", "Medium", "Functional - Positive", "AutomationExercise Test Case Repository", "Automated"},
            {"REQ-06", "User can subscribe to the newsletter on the home page", "TC-10", "SubscriptionTests.testSubscriptionHomePage", "Low", "Functional - Positive", "AutomationExercise Test Case Repository", "Automated"},
            {"REQ-07", "User can add a product to the cart", "TC-12", "CartTests.testAddProductToCart", "Critical", "Functional - Positive", "AutomationExercise Test Case Repository", "Automated"},
            {"REQ-08", "Logged-in user can proceed to checkout and reach order review", "TC-16", "CheckoutTests.testPlaceOrderLoginBeforeCheckout", "Critical", "Functional - Positive - E2E", "AutomationExercise Test Case Repository", "Automated"}
    };

    public static final int COL_REQ_ID = 0;
    public static final int COL_DESCRIPTION = 1;
    public static final int COL_TC_ID = 2;
    public static final int COL_SCRIPT = 3;
    public static final int COL_PRIORITY = 4;
    public static final int COL_TEST_TYPE = 5;
    public static final int COL_REQ_SOURCE = 6;
    public static final int COL_AUTOMATION_STATUS = 7;
    public static final int COL_DEFECTS_FOUND = 8;
    public static final int COL_STATUS = 9;
    public static final int COL_LAST_RUN = 10;

    public static void main(String[] args) throws Exception {
        new File("docs").mkdirs();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("RTM");

        CellStyle headerStyle = createHeaderStyle(workbook);
        CellStyle bodyStyle = createBodyStyle(workbook);
        CellStyle priorityCriticalStyle = createPriorityStyle(workbook, IndexedColors.RED.getIndex());
        CellStyle priorityHighStyle = createPriorityStyle(workbook, IndexedColors.ORANGE.getIndex());
        CellStyle priorityMediumStyle = createPriorityStyle(workbook, IndexedColors.GOLD.getIndex());
        CellStyle priorityLowStyle = createPriorityStyle(workbook, IndexedColors.GREY_40_PERCENT.getIndex());

        String[] headers = {
                "Requirement ID", "Description", "Test Case ID", "Script",
                "Priority", "Test Type", "Requirement Source", "Automation Status",
                "Defects Found", "Status", "Last Run"
        };

        Row headerRow = sheet.createRow(0);
        headerRow.setHeightInPoints(22);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }
        sheet.createFreezePane(0, 1);

        int rowNum = 1;
        for (String[] data : ROWS) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(COL_REQ_ID).setCellValue(data[0]);
            row.createCell(COL_DESCRIPTION).setCellValue(data[1]);
            row.createCell(COL_TC_ID).setCellValue(data[2]);
            row.createCell(COL_SCRIPT).setCellValue(data[3]);

            Cell priorityCell = row.createCell(COL_PRIORITY);
            priorityCell.setCellValue(data[4]);
            switch (data[4]) {
                case "Critical": priorityCell.setCellStyle(priorityCriticalStyle); break;
                case "High": priorityCell.setCellStyle(priorityHighStyle); break;
                case "Medium": priorityCell.setCellStyle(priorityMediumStyle); break;
                default: priorityCell.setCellStyle(priorityLowStyle);
            }

            row.createCell(COL_TEST_TYPE).setCellValue(data[5]);
            row.createCell(COL_REQ_SOURCE).setCellValue(data[6]);
            row.createCell(COL_AUTOMATION_STATUS).setCellValue(data[7]);
            row.createCell(COL_DEFECTS_FOUND).setCellValue("None");
            row.createCell(COL_STATUS).setCellValue("Not Run");
            row.createCell(COL_LAST_RUN).setCellValue("");

            for (int c = 0; c <= COL_LAST_RUN; c++) {
                if (c != COL_PRIORITY && row.getCell(c) != null) {
                    row.getCell(c).setCellStyle(bodyStyle);
                }
            }
        }

        int summaryStartRow = rowNum + 1;
        writeSummaryBlock(sheet, workbook, summaryStartRow, ROWS.length, 0, 0);

        int[] widths = {14, 45, 12, 42, 10, 22, 34, 16, 14, 10, 18};
        for (int i = 0; i < widths.length; i++) {
            sheet.setColumnWidth(i, widths[i] * 256);
        }

        try (FileOutputStream fos = new FileOutputStream("docs/RTM.xlsx")) {
            workbook.write(fos);
        }
        workbook.close();
        System.out.println("RTM.xlsx generated at docs/RTM.xlsx");
    }

    static CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setColor(IndexedColors.WHITE.getIndex());
        font.setFontHeightInPoints((short) 11);
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderBottom(BorderStyle.THIN);
        style.setWrapText(true);
        return style;
    }

    static CellStyle createBodyStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setWrapText(true);
        style.setVerticalAlignment(VerticalAlignment.TOP);
        return style;
    }

    static CellStyle createPriorityStyle(Workbook workbook, short color) {
        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(color);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        return style;
    }

    static CellStyle createStatusStyle(Workbook workbook, boolean passed) {
        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(passed ? IndexedColors.LIGHT_GREEN.getIndex() : IndexedColors.RED.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        Font font = workbook.createFont();
        font.setBold(true);
        if (!passed) font.setColor(IndexedColors.WHITE.getIndex());
        style.setFont(font);
        return style;
    }

    static void writeSummaryBlock(Sheet sheet, Workbook workbook, int startRow, int total, int passed, int failed) {
        CellStyle titleStyle = workbook.createCellStyle();
        Font titleFont = workbook.createFont();
        titleFont.setBold(true);
        titleFont.setFontHeightInPoints((short) 13);
        titleStyle.setFont(titleFont);
        titleStyle.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        CellStyle labelStyle = workbook.createCellStyle();
        Font labelFont = workbook.createFont();
        labelFont.setBold(true);
        labelStyle.setFont(labelFont);

        CellStyle rateStyle = workbook.createCellStyle();
        Font rateFont = workbook.createFont();
        rateFont.setBold(true);
        rateFont.setFontHeightInPoints((short) 13);
        double rate = total == 0 ? 0 : (100.0 * passed / total);
        rateFont.setColor(rate == 100.0 ? IndexedColors.GREEN.getIndex() : IndexedColors.RED.getIndex());
        rateStyle.setFont(rateFont);

        Row titleRow = sheet.createRow(startRow);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("SUMMARY");
        titleCell.setCellStyle(titleStyle);
        titleRow.createCell(1).setCellStyle(titleStyle);

        Row totalRow = sheet.createRow(startRow + 1);
        totalRow.createCell(0).setCellStyle(labelStyle);
        totalRow.getCell(0).setCellValue("Total Requirements Covered:");
        totalRow.createCell(1).setCellValue(total);

        Row passedRow = sheet.createRow(startRow + 2);
        passedRow.createCell(0).setCellStyle(labelStyle);
        passedRow.getCell(0).setCellValue("Passed:");
        passedRow.createCell(1).setCellValue(passed);

        Row failedRow = sheet.createRow(startRow + 3);
        failedRow.createCell(0).setCellStyle(labelStyle);
        failedRow.getCell(0).setCellValue("Failed:");
        failedRow.createCell(1).setCellValue(failed);

        Row rateRow = sheet.createRow(startRow + 4);
        rateRow.createCell(0).setCellStyle(labelStyle);
        rateRow.getCell(0).setCellValue("Pass Rate:");
        Cell rateCell = rateRow.createCell(1);
        rateCell.setCellValue(String.format("%.1f%%", rate));
        rateCell.setCellStyle(rateStyle);
    }
}