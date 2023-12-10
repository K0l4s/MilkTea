package alotra.milktea.repository;

import alotra.milktea.entity.CartProducts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICartProductsRepo extends JpaRepository<CartProducts,Integer> {
}
