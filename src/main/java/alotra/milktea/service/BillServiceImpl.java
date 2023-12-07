package alotra.milktea.service;

import alotra.milktea.entity.Bill;
import alotra.milktea.repository.IBillRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillServiceImpl implements IBillService{
    @Autowired
    IBillRepo billRepo;

    @Override
    public List<Bill> findAll() {
        return billRepo.findAll();
    }

    @Override
    public Bill findOne(Bill bill) {
        return null;
    }
}
