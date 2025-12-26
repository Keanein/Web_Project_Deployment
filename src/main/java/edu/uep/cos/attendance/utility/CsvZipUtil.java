package edu.uep.cos.attendance.utility;

import edu.uep.cos.attendance.repository.ExportEventRepository;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

// Utility class for generating CSV files and ZIP archives for event exports
public class CsvZipUtil {

    public static byte[] createZip(
            List<Map<String,Object>> students,
            List<Map<String,Object>> attendance
    ) throws Exception {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(baos);

        zip.putNextEntry(new ZipEntry("students.csv"));
        writeCsv(zip, students);
        zip.closeEntry();

        zip.putNextEntry(new ZipEntry("attendance.csv"));
        writeCsv(zip, attendance);
        zip.closeEntry();

        zip.close();
        return baos.toByteArray();
    }

    private static void writeCsv(OutputStream os, List<Map<String,Object>> data) throws Exception {
        if (data.isEmpty()) return;

        var headers = data.get(0).keySet();
        os.write(String.join(",", headers).getBytes());
        os.write("\n".getBytes());

        for (var row : data) {
            os.write(
                    headers.stream()
                            .map(h -> String.valueOf(row.get(h)))
                            .collect(Collectors.joining(","))
                            .getBytes()
            );
            os.write("\n".getBytes());
        }
    }
}
