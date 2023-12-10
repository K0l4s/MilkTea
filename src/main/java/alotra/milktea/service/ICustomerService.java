package alotra.milktea.service;

import alotra.milktea.entity.Customer;

import java.util.List;
import java.util.Optional;

public interface ICustomerService {
    List<Customer> findAll();
    Optional<Customer> findOne(int id);
    void saveCustomer(Customer customer);
    void deleteCustomer(int id);
    List<Customer> findCustomerByName(String name);
}
