package alotra.milktea.repository;

import alotra.milktea.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBillRepo extends JpaRepository<Bill,Integer> {
    Bill findBillById (int id);
}
