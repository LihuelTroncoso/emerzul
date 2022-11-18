package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Paciente;
import com.mycompany.myapp.domain.Reporte;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

public class CsvHelper {

    public static ByteArrayInputStream reportesToCsv(List<Reporte> reportes) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), CSVFormat.DEFAULT);

        for (Reporte reporte : reportes) {
            List<String> data = Arrays.asList(
                String.valueOf(reporte.getId()),
                reporte.getArea(),
                reporte.getOrigen(),
                String.valueOf(reporte.getHora())
            );
            csvPrinter.printRecord(data);
        }
        csvPrinter.flush();
        return new ByteArrayInputStream(out.toByteArray());
    }
}
