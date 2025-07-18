package com.flightlive.service;

import com.flightlive.repository.VoloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Service
public class ReportService {
    @Autowired
    private VoloRepository voloRepository;

    public List<Object[]> getReportVoliSettimanali() {
        LocalDate startOfWeek = LocalDate.now().with(DayOfWeek.MONDAY);
        return voloRepository.findTopVoliSettimanali(startOfWeek);
    }
}