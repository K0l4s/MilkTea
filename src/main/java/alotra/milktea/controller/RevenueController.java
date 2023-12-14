package alotra.milktea.controller;

import alotra.milktea.model.RevenueModel;
import alotra.milktea.service.IRevenueService;
import alotra.milktea.service.RevenueServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.ResponseEntity;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.List;
@Controller
@RequestMapping("/admin/revenue")
public class RevenueController {

    @Autowired
    private IRevenueService revenueService;

//    @GetMapping("/view")
//    protected String viewRevenuePage() {
//        return "admin/revenue/list";
//    }

    @GetMapping("/data")
    public ResponseEntity<RevenueModel> viewRevenueData(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime startTime,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime endTime) {

        if (startTime != null && endTime != null) {
            // Specific date range revenue
            RevenueModel revenueModel = revenueService.getRevenue(startTime, endTime);
            return ResponseEntity.ok(revenueModel);
        }

        // Handle the case when startTime or endTime is not provided
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/yearly")
    public ResponseEntity<List<RevenueModel>> viewYearlyRevenue() {
        LocalDateTime today = LocalDateTime.now();
        List<RevenueModel> yearlyRevenueList = revenueService.getThisYearRevenue(today);
        return ResponseEntity.ok(yearlyRevenueList);
    }
}