package alotra.milktea.service;

import alotra.milktea.entity.Bill_Products;

import java.util.List;
import java.util.Optional;

public interface IBill_ProductsService {
    List<Bill_Products> findAll();

    Optional<Bill_Products> findOne(int id);

    void saveBill_Products(Bill_Products bp);

    void deleteBill_Products(int id);
}
