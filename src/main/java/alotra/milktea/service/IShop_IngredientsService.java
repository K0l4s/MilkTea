package alotra.milktea.service;

import alotra.milktea.entity.Shop_Ingredients;

import java.util.List;
import java.util.Optional;

public interface IShop_IngredientsService {
    List<Shop_Ingredients> findAll();
    Optional<Shop_Ingredients> findOne(int id);
    void saveShop_Ingredients(Shop_Ingredients si);
    void deleteShop_Ingredietns(int id);
}
