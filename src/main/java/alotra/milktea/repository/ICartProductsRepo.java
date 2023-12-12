package alotra.milktea.repository;

import alotra.milktea.entity.CartProducts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ICartProductsRepo extends JpaRepository<CartProducts,Integer> {
    List<CartProducts> findCartProductsByCartId(int id);

    CartProducts findCartProductsById (int id);
}
