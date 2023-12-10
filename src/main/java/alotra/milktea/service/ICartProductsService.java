package alotra.milktea.service;

import alotra.milktea.entity.CartProducts;

import java.util.List;
import java.util.Optional;

public interface ICartProductsService {
    List<CartProducts> findAll();
    Optional<CartProducts> findOne(int id);
    void saveCartPro(CartProducts cp);

    void deleteCartPro(int id);
}
