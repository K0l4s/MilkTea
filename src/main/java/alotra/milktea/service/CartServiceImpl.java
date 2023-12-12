package alotra.milktea.service;

import alotra.milktea.entity.Cart;
import alotra.milktea.entity.Customer;
import alotra.milktea.repository.ICartRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class CartServiceImpl implements ICartService{
    @Autowired
    ICartRepo cartRepo;
    @Override
    public List<Cart> findAll() {
        return cartRepo.findAll();
    }

    @Override
    public Optional<Cart> findOne(int id) {
        return cartRepo.findById(id);
    }

    @Override
    public void saveCart(Cart cart) {
        cartRepo.save(cart);
    }

    @Override
    public void deleteCart(int id) {
        cartRepo.deleteById(id);
    }

    @Override
    public List<Cart> findCartByName(String name) {
        return cartRepo.findCartByKeyWord(name);
    }

    @Override
    public Cart findCartByCustomer(Customer customer) {
        return cartRepo.findCartByCustomer(customer);
    }

    @Override
    public Cart findByID(int id) {
        return cartRepo.findCartById(id);
    }
}
