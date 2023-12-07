package alotra.milktea.service;

import alotra.milktea.entity.Customer;
import alotra.milktea.repository.ICustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements ICustomerService{
    @Autowired
    ICustomerRepo customerRepo;

    @Override
    public List<Customer> findAll() {
        return customerRepo.findAll();
    }
}
