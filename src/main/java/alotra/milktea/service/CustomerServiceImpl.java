package alotra.milktea.service;

import alotra.milktea.entity.Cart;
import alotra.milktea.entity.Customer;
import alotra.milktea.repository.ICartRepo;
import alotra.milktea.repository.ICustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements ICustomerService{
    @Autowired
    ICustomerRepo customerRepo;
    @Autowired
    ICartRepo cartRepo;
    @Override
    public List<Customer> findAll() {
        return customerRepo.findAll();
    }

    @Override
    public Optional<Customer> findOne(int id) {
        return customerRepo.findById(id);
    }

    @Override
    public void saveCustomer(Customer customer) {
        customerRepo.save(customer);
    }

    @Override
    public void deleteCustomer(int id) {
        customerRepo.deleteById(id);
    }

    @Override
    public List<Customer> findCustomerByName(String name) {
        return customerRepo.findCategoryByKeyWord(name);
    }

    @Override
    public Cart getOrCreateCartForCustomer(Customer customer) {
        return cartRepo.findCartByCustomer(customer);
    }
}
