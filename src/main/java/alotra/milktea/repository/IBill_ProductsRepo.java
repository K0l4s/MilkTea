package alotra.milktea.repository;

import alotra.milktea.entity.Bill_Products;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IBill_ProductsRepo extends JpaRepository<Bill_Products,Integer> {
    List<Bill_Products> findBill_ProductByBillId(int id);
}
