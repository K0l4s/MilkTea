package alotra.milktea.service;

import alotra.milktea.entity.Cart;
import alotra.milktea.entity.CartProducts;
import alotra.milktea.entity.Product;
import alotra.milktea.repository.ICartProductsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartProductsServiceImpl implements ICartProductsService{
    @Autowired
    ICartProductsRepo cartProductsRepo;

    @Override
    public List<CartProducts> findAll() {
        return cartProductsRepo.findAll();
    }

    @Override
    public Optional<CartProducts> findOne(int id) {
        return cartProductsRepo.findById(id);
    }

    @Override
    public void saveCartPro(CartProducts cp) {
        cartProductsRepo.save(cp);
    }

    @Override
    public void deleteCartPro(int id) {
        cartProductsRepo.deleteById(id);
    }

    @Override
    public List<CartProducts> findProByCartID(int id) {
        return cartProductsRepo.findCartProductsByCartId(id);
    }

    @Override
    public CartProducts findCartProductsById(int id) {
        return cartProductsRepo.findCartProductsById(id);
    }

    @Override
    public Optional<CartProducts> findByCartAndProduct(Cart cart, Product product) {
        return cartProductsRepo.findByCartAndProduct(cart, product);
    }

    @Override
    public int calculateTotalAmount(Cart cart) {
        List<CartProducts> cartProducts = findProByCartID(cart.getId());
        return cartProducts.stream().mapToInt(CartProducts::getAmount).sum();
    }

    @Override
    public void deleteAll() {
        cartProductsRepo.deleteAll();
    }

}
