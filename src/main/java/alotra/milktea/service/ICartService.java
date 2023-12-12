package alotra.milktea.service;

import alotra.milktea.entity.Cart;
import alotra.milktea.entity.Customer;

import java.util.List;
import java.util.Optional;

public interface ICartService {
    List<Cart> findAll();
    Optional<Cart> findOne(int id);
    void saveCart(Cart cart);
    void deleteCart(int id);
    List<Cart> findCartByName(String name);

    Cart findCartByCustomer(Customer customer);

    Cart findByID(int id);
}
