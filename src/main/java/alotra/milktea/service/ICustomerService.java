package alotra.milktea.service;

import alotra.milktea.entity.Customer;

import java.util.List;

public interface ICustomerService {
    List<Customer> findAll();
}
