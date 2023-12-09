package alotra.milktea.repository;

import alotra.milktea.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IBillRepo extends JpaRepository<Bill,Integer> {
    List<Bill> findBillByCustomerCustomerID(int id);
}
