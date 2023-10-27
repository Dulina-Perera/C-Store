package com.cstore.domain.report;

import com.cstore.dao.report.ReportDao;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportDao reportDao;

    @Scheduled(cron = "00 00 00 1,4,7,10 *")
    public void sendReport() {

    }
}
