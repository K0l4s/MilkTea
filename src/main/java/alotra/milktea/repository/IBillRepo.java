package alotra.milktea.repository;

import alotra.milktea.entity.Bill;
import alotra.milktea.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IBillRepo extends JpaRepository<Bill,Integer> {
    List<Bill> findBillByCustomerCustomerID(int id);

    @Query("SELECT b FROM Bill b WHERE b.customer.customerName LIKE :keyword OR b.employee.name LIKE :keyword")
    List<Bill> findBillByKeyWord(@Param("keyword") String keyword);
}
