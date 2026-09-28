package utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class RtmUpdater {

    public static void main(String[] args) throws Exception {
        String[] candidatePaths = {
                "target/surefire-reports/testng-results.xml",
                "test-output/testng-results.xml"
        };

        String resultsPath = null;
        for (String p : candidatePaths) {
            if (new File(p).exists()) {
                resultsPath = p;
                break;
            }
        }

        if (resultsPath == null) {
            System.out.println("No testng-results.xml found — skipping RTM update.");
            return;
        }

        Map<String, String> results = parseTestResults(resultsPath);
        updateRtm("docs/RTM.xlsx", results);
    }

    private static Map<String, String> parseTestResults(String path) throws Exception {
        Map<String, String> statusMap = new HashMap<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new File(path));

        NodeList classNodes = doc.getElementsByTagName("class");
        for (int i = 0; i < classNodes.getLength(); i++) {
            Element classEl = (Element) classNodes.item(i);
            String fullClassName = classEl.getAttribute("name");
            String simpleClassName = fullClassName.substring(fullClassName.lastIndexOf('.') + 1);

            NodeList methodNodes = classEl.getElementsByTagName("test-method");
            for (int j = 0; j < methodNodes.getLength(); j++) {
                Element methodEl = (Element) methodNodes.item(j);
                if (methodEl.hasAttribute("is-config")) continue;

                String methodName = methodEl.getAttribute("name");
                String status = methodEl.getAttribute("status");
                statusMap.put(simpleClassName + "." + methodName, status);
            }
        }
        return statusMap;
    }

    private static void updateRtm(String rtmPath, Map<String, String> results) throws Exception {
        File rtmFile = new File(rtmPath);
        if (!rtmFile.exists()) {
            System.out.println("RTM.xlsx not found — run RtmGenerator first.");
            return;
        }

        Workbook workbook;
        try (FileInputStream fis = new FileInputStream(rtmFile)) {
            workbook = new XSSFWorkbook(fis);
        }

        Sheet sheet = workbook.getSheetAt(0);
        String now = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());

        int passedCount = 0;
        int failedCount = 0;
        int totalCount = 0;
        int summaryStartRow = -1;

        for (Row row : sheet) {
            if (row.getRowNum() == 0) continue;

            Cell reqIdCell = row.getCell(RtmGenerator.COL_REQ_ID);
            if (reqIdCell != null && getCellString(reqIdCell).equals("SUMMARY")) {
                summaryStartRow = row.getRowNum();
                break;
            }

            Cell scriptCell = row.getCell(RtmGenerator.COL_SCRIPT);
            if (scriptCell == null || getCellString(scriptCell).isEmpty()) continue;

            String script = getCellString(scriptCell).trim();
            String status = results.get(script);

            if (status != null) {
                totalCount++;
                Cell statusCell = row.getCell(RtmGenerator.COL_STATUS, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                Cell lastRunCell = row.getCell(RtmGenerator.COL_LAST_RUN, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);

                boolean passed = status.equals("PASS");
                statusCell.setCellValue(passed ? "Passed" : "Failed");
                statusCell.setCellStyle(RtmGenerator.createStatusStyle(workbook, passed));
                lastRunCell.setCellValue(now);

                if (passed) passedCount++; else failedCount++;
            }
        }

        if (summaryStartRow != -1) {
            RtmGenerator.writeSummaryBlock(sheet, workbook, summaryStartRow, totalCount, passedCount, failedCount);
        }

        for (int i = 0; i <= RtmGenerator.COL_LAST_RUN; i++) {
            sheet.autoSizeColumn(i);
        }

        try (FileOutputStream fos = new FileOutputStream(rtmFile)) {
            workbook.write(fos);
        }
        workbook.close();
        System.out.println("RTM.xlsx updated: " + passedCount + " passed, " + failedCount + " failed, out of " + totalCount + ".");
    }

    private static String getCellString(Cell cell) {
        if (cell == null) return "";
        if (cell.getCellType() == CellType.STRING) return cell.getStringCellValue();
        return cell.toString();
    }
}