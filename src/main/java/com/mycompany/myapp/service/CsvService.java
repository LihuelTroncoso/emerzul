package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Reporte;
import com.mycompany.myapp.repository.ReporteRepository;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CsvService {

    @Autowired
    ReporteRepository reporteRepository;

    public ByteArrayInputStream loadReportes() throws IOException {
        List<Reporte> reportes = reporteRepository.findAll();

        ByteArrayInputStream in = CsvHelper.reportesToCsv(reportes);
        return in;
    }
}
