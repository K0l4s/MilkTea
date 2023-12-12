package alotra.milktea.repository;

import alotra.milktea.entity.Cart;
import alotra.milktea.entity.CartProducts;
import alotra.milktea.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ICartProductsRepo extends JpaRepository<CartProducts,Integer> {
    List<CartProducts> findCartProductsByCartId(int id);

    CartProducts findCartProductsById (int id);
    Optional<CartProducts> findByCartAndProduct(Cart cart, Product product);
}
