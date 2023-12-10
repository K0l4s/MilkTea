package alotra.milktea.service;

import alotra.milktea.entity.Bill;

import java.util.List;
import java.util.Optional;

public interface IBillService {
    List<Bill> findAll();

    Optional<Bill> findOne(int id);

    void saveBill(Bill bill);

    void deleteBill(int id);

    List<Bill> findBillsByCustomerID(int id);

    List<Bill> findBillByName(String name);
}
