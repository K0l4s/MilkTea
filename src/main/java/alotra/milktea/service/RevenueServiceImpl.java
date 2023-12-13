package alotra.milktea.service;

import alotra.milktea.entity.Bill;
import alotra.milktea.entity.Bill_Products;
import alotra.milktea.model.RevenueModel;
import alotra.milktea.repository.IBillRepo;
import alotra.milktea.repository.IBill_ProductsRepo;
import alotra.milktea.repository.IProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

@Service
public class RevenueServiceImpl implements IRevenueService{
    @Autowired
    IBillRepo iBillRepo;
    @Autowired
    IBill_ProductsRepo iBillProductsRepo;
    @Autowired
    IProductRepo productRepo;
    @Override
    public RevenueModel getRevenue(LocalDateTime start, LocalDateTime end){
        RevenueModel revenueModel = new RevenueModel();
        double revenue = 0.0F;
        List<Bill> listBill = iBillRepo.findBillsByCreateDayBetween(start,end);
        for (Bill bill: listBill
             ) {
            List<Bill_Products> billProducts = iBillProductsRepo.findBill_ProductByBillId(bill.getId());
            for(Bill_Products bp : billProducts){
                revenue += bp.getAmount()*bp.getProduct().getPrice();
            }
        }
        revenueModel.setRevenue(revenue);
        return revenueModel;
    }

    @Override
    public RevenueModel getMonthRevenue(LocalDateTime today){


        // Lấy ngày đầu tiên của tháng
        LocalDateTime firstDayOfMonth = today.withDayOfMonth(1);

        // Lấy ngày cuối cùng của tháng
        LocalDateTime lastDayOfMonth = today.with(TemporalAdjusters.lastDayOfMonth());
        return getRevenue(firstDayOfMonth, lastDayOfMonth);
    }
    @Override
    public List<RevenueModel> getThisYearRevenue(LocalDateTime today) {
        List<RevenueModel> yearlyRevenueList = new ArrayList<>();
        for (int month = 1; month <= 12; month++) {
            LocalDateTime firstDayOfMonth = LocalDateTime.of(today.getYear(), month, 1, 0, 0);
            LocalDateTime lastDayOfMonth = firstDayOfMonth.with(TemporalAdjusters.lastDayOfMonth());
            RevenueModel monthlyRevenue = getRevenue(firstDayOfMonth, lastDayOfMonth);
            yearlyRevenueList.add(monthlyRevenue);
        }

        return yearlyRevenueList;
    }

}
