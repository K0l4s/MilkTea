package alotra.milktea.repository;

import alotra.milktea.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IProductRepo extends JpaRepository<Product, Integer> {
    List<Product> findProductByCategoryCategoryName(String name);
    List<Product> findAllByStatusNot(short status);
}
