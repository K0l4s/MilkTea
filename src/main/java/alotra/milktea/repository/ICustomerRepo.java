package alotra.milktea.repository;

import alotra.milktea.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICustomerRepo extends JpaRepository<Customer, Integer> {
}
