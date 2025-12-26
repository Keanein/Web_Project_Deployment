package edu.uep.cos.attendance.utility;

import edu.uep.cos.attendance.repository.ExportEventRepository;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

// Utility class for generating CSV files and ZIP archives for event exports
public class CsvZipUtil {

    // Builds a ZIP file containing CSV files for multiple events
    public static byte[] buildZip(List<Map<String, Object>> events,
                                  ExportEventRepository repo) throws Exception {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(baos);

        for (var ev : events) {
            byte[] csv = buildCsv(ev, repo);
            zip.putNextEntry(new ZipEntry(fileName(ev)));
            zip.write(csv);
            zip.closeEntry();
        }

        zip.finish();
        zip.close();
        return baos.toByteArray();
    }

    // Builds a CSV file for ONE event (courses are fetched per event)
    public static byte[] buildCsv(Map<String, Object> ev,
                                  ExportEventRepository repo) throws Exception {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintWriter w = new PrintWriter(
                new OutputStreamWriter(out, StandardCharsets.UTF_8)
        );

        // Event title
        w.println("Event: " + ev.get("event_name"));
        w.println();

        // ✅ fetch courses PER EVENT
        List<Map<String, Object>> courses =
                repo.findCoursesByEvent((int) ev.get("event_id"));

        for (var c : courses) {

            List<Map<String, Object>> rows =
                    repo.findAttendance(
                            (int) ev.get("event_id"),
                            (int) c.get("course_id")
                    );

            if (rows.isEmpty()) continue;

            w.println("Course: " + c.get("course_name"));
            w.println("Student Number,Name,AM,PM");

            for (var r : rows) {
                w.printf("%s,\"%s\",%s,%s%n",
                        r.get("student_number"),
                        r.get("fullname"),
                        format(r.get("status_am"), r.get("time_in_am")),
                        format(r.get("status_pm"), r.get("time_in_pm"))
                );
            }
            w.println();
        }

        w.flush();
        w.close();
        return out.toByteArray();
    }

    // Formats attendance output based on status and time
    private static String format(Object status, Object time) {
        if ("Present".equals(status) && time != null) {
            return time.toString();
        }
        return "Absent";
    }

    // Generates a ZIP-safe CSV filename
    public static String fileName(Map<String, Object> ev) {
        String name = ev.get("event_name").toString()
                .replaceAll("[^A-Za-z0-9_-]", "_");

        String date = ev.get("event_date").toString().replaceAll(":", "-");

        return name + "_" + date + ".csv";
    }
}


