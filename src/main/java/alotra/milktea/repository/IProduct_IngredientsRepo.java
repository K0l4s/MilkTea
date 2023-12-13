package alotra.milktea.repository;

import alotra.milktea.entity.Product;
import alotra.milktea.entity.Product_Ingredients;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IProduct_IngredientsRepo extends JpaRepository<Product_Ingredients, Integer> {
    List<Product_Ingredients> findByProductProductID(int id);

    Product_Ingredients findProduct_IngredientsById(int id);
}
