package com.library.reporting.web;

import com.library.reporting.dto.BookDto;
import com.library.reporting.service.ReportGenerator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
public class ReportingController {

    private final ReportGenerator reportGenerator;

    public ReportingController(ReportGenerator reportGenerator) {
        this.reportGenerator = reportGenerator;
    }

    @GetMapping("/top-books")
    public List<BookDto> topBooks(@RequestParam(defaultValue = "5") int topN) {
        return reportGenerator.topBooksLastMonth(topN);
    }

    @GetMapping("/overview")
    public Map<String, Object> overview() {
        return reportGenerator.borrowingOverview();
    }
}
