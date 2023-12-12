package alotra.milktea.service;

import alotra.milktea.entity.Cart;
import alotra.milktea.entity.CartProducts;
import alotra.milktea.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ICartProductsService {
    List<CartProducts> findAll();
    Optional<CartProducts> findOne(int id);
    void saveCartPro(CartProducts cp);
    void deleteCartPro(int id);
    List<CartProducts> findProByCartID(int id);

    CartProducts findCartProductsById(int id);
    Optional<CartProducts> findByCartAndProduct(Cart cart, Product product);
    int calculateTotalAmount(Cart cart);
}
