package edu.uep.cos.attendance.utility;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class CsvZipUtil {

    // SINGLE CSV export (OneBillion_Rising format)
    public static byte[] createCsv(List<Map<String, Object>> data) throws IOException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BufferedWriter bw = new BufferedWriter(
                new OutputStreamWriter(baos, StandardCharsets.UTF_8)
        );

        // Header
        bw.write("STUDENT NO,NAME,YEAR,AM STATUS,PM STATUS,AM TIME,PM TIME");
        bw.newLine();

        for (Map<String, Object> row : data) {
            bw.write(
                    safe(row.get("student_no")) + "," +
                            safe(row.get("full_name")) + "," +
                            safe(row.get("year_level")) + "," +
                            safe(row.get("status_am")) + "," +
                            safe(row.get("status_pm")) + "," +
                            safe(row.get("time_in_am")) + "," +
                            safe(row.get("time_in_pm"))
            );
            bw.newLine();
        }

        bw.flush();
        return baos.toByteArray();
    }

    private static String safe(Object v) {
        if (v == null) return "";
        String s = v.toString();
        if (s.contains(",") || s.contains("\"") || s.contains("\n")) {
            s = s.replace("\"", "\"\"");
            s = "\"" + s + "\"";
        }
        return s;
    }
}


