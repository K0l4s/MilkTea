package alotra.milktea.service;

import alotra.milktea.entity.Product;
import alotra.milktea.entity.Product_Ingredients;

import java.util.List;
import java.util.Optional;

public interface IProduct_IngredientsService {
    List<Product_Ingredients> findAll();
    Optional<Product_Ingredients> findOne(int id);
    void savePro_Ingredients(Product_Ingredients pi);
    void deletePro_Ingredients(int id);

    List<Product_Ingredients> findByProductID(int id);

    Product_Ingredients findProduct_IngredientsByID(int id);
}
