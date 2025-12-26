package edu.uep.cos.attendance.utility;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class CsvZipUtil {

    public static byte[] createZip(
            List<Map<String,Object>> students,
            List<Map<String,Object>> attendance
    ) throws Exception {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(baos);

        zip.putNextEntry(new ZipEntry("students.csv"));
        writeStudentsCsv(zip, students);
        zip.closeEntry();

        zip.putNextEntry(new ZipEntry("attendance.csv"));
        writeAttendanceCsv(zip, attendance);
        zip.closeEntry();

        zip.close();
        return baos.toByteArray();
    }

    private static void writeStudentsCsv(OutputStream os, List<Map<String,Object>> list) throws Exception {
        os.write("Student Number,Full Name,Course,Year Level\n".getBytes());

        for (var r : list) {
            os.write((
                    quote(r.get("student_number")) + "," +
                            quote(r.get("fullname")) + "," +
                            quote(r.get("course_name")) + "," +
                            quote(r.get("year_level")) + "\n"
            ).getBytes(StandardCharsets.UTF_8));
        }
    }

    private static void writeAttendanceCsv(OutputStream os, List<Map<String,Object>> list) throws Exception {
        os.write("Student Number,Name,AM Status,PM Status\n".getBytes());

        for (var r : list) {
            os.write((
                    quote(r.get("student_number")) + "," +
                            quote(r.get("fullname")) + "," +
                            quote(r.get("status_am")) + "," +
                            quote(r.get("status_pm")) + "\n"
            ).getBytes(StandardCharsets.UTF_8));
        }
    }

    private static String quote(Object v) {
        return "\"" + (v == null ? "" : v.toString()) + "\"";
    }
}

