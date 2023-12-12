package alotra.milktea.service;

import alotra.milktea.model.RevenueModel;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface IRevenueService {

    RevenueModel getRevenue(LocalDateTime start, LocalDateTime end);

    RevenueModel getMonthRevenue(LocalDateTime today);

    List<RevenueModel> getThisYearRevenue(LocalDateTime today);
}
