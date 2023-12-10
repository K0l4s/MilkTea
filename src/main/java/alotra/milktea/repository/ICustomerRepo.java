package alotra.milktea.repository;

import alotra.milktea.entity.Category;
import alotra.milktea.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ICustomerRepo extends JpaRepository<Customer, Integer> {
    @Query("SELECT c FROM Customer c WHERE c.customerName LIKE :keyword OR c.user.username LIKE :keyword OR c.user.email LIKE :keyword " )
    List<Customer> findCategoryByKeyWord(@Param("keyword") String keyword);
}
