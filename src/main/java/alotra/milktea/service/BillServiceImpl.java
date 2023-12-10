package alotra.milktea.service;

import alotra.milktea.entity.Bill;
import alotra.milktea.repository.IBillRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class BillServiceImpl implements IBillService{
    @Autowired
    IBillRepo billRepo;

    @Override
    public List<Bill> findAll() {
        return billRepo.findAll();
    }

    @Override
    public Optional<Bill> findOne(int id) {
        return billRepo.findById(id);
    }

    @Override
    public void saveBill(Bill bill) {
        billRepo.save(bill);
    }

    @Override
    public void deleteBill(int id) {
        billRepo.deleteById(id);
    }

    @Override
    public List<Bill> findBillsByCustomerID(int id) {
        return billRepo.findBillByCustomerCustomerID(id);
    }

    @Override
    public List<Bill> findBillByName(String name) {
        return billRepo.findBillByKeyWord(name);
    }
}
