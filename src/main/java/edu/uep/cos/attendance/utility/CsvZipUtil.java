package edu.uep.cos.attendance.utility;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class CsvZipUtil {

    public static byte[] createZip(
            List<Map<String, Object>> students,
            List<Map<String, Object>> attendance
    ) throws IOException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try (ZipOutputStream zip = new ZipOutputStream(baos, StandardCharsets.UTF_8)) {

            zip.putNextEntry(new ZipEntry("students.csv"));
            writeStudents(zip, students);
            zip.closeEntry();

            zip.putNextEntry(new ZipEntry("attendance.csv"));
            writeAttendance(zip, attendance);
            zip.closeEntry();
        }

        return baos.toByteArray();
    }

    private static void writeStudents(OutputStream os, List<Map<String, Object>> data)
            throws IOException {

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, StandardCharsets.UTF_8));

        bw.write("student_id,student_no,full_name,course,year_level");
        bw.newLine();

        for (Map<String, Object> row : data) {
            bw.write(
                    safe(row.get("student_id")) + "," +
                            safe(row.get("student_no")) + "," +
                            safe(row.get("full_name")) + "," +
                            safe(row.get("course")) + "," +
                            safe(row.get("year_level"))
            );
            bw.newLine();
        }

        bw.flush();
    }

    private static void writeAttendance(OutputStream os, List<Map<String, Object>> data)
            throws IOException {

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, StandardCharsets.UTF_8));

        bw.write("student_no,full_name,status,time_in");
        bw.newLine();

        for (Map<String, Object> row : data) {
            bw.write(
                    safe(row.get("student_no")) + "," +
                            safe(row.get("full_name")) + "," +
                            safe(row.get("status")) + "," +
                            safe(row.get("time_in"))
            );
            bw.newLine();
        }

        bw.flush();
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

