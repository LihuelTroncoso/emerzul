package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.CsvService;
import java.io.IOException;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/export")
public class CsvResource {

    CsvService csvService;

    public CsvResource(CsvService csvService) {
        this.csvService = csvService;
    }

    @GetMapping("/reportes")
    public ResponseEntity<InputStreamResource> exportPacientes() throws IOException {
        String filename = "ejemplo.csv";
        InputStreamResource file = new InputStreamResource(csvService.loadReportes());

        return ResponseEntity
            .ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
            .contentType(MediaType.parseMediaType("application/csv"))
            .body(file);
    }
}
