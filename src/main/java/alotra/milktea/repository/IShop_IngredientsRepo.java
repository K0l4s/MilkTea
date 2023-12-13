package alotra.milktea.repository;

import alotra.milktea.entity.Shop_Ingredients;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IShop_IngredientsRepo extends JpaRepository<Shop_Ingredients, Integer> {
    List<Shop_Ingredients> findByShopShopID(String id);

    Shop_Ingredients findShop_IngredientsByShopShopID(String id);

    Shop_Ingredients findShop_IngredientsById(int id);
}
