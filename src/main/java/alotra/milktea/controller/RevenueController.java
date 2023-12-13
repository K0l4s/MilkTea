package alotra.milktea.controller;

import alotra.milktea.model.RevenueModel;
import alotra.milktea.service.IRevenueService;
import alotra.milktea.service.RevenueServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

@Controller
@RequestMapping("/admin/revenue")
public class RevenueController {
    @Autowired
    IRevenueService revenueService = new RevenueServiceImpl();

    @GetMapping("home")
    protected String getHome(Model model){
        // Lấy thời điểm hiện tại
        LocalDateTime today = LocalDateTime.now();

//        // Lấy ngày đầu tiên của tháng
//        LocalDateTime firstDayOfMonth = today.withDayOfMonth(1);
//
//        // Lấy ngày cuối cùng của tháng
//        LocalDateTime lastDayOfMonth = today.with(TemporalAdjusters.lastDayOfMonth());

        RevenueModel revenueModel = revenueService.getMonthRevenue(today);
        model.addAttribute("revenue",revenueModel);
        return "revenue/list";
    }

    @GetMapping("view")
    protected  String getView(){
        return null;
    }
}
