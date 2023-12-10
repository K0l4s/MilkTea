package alotra.milktea.service;

import alotra.milktea.entity.Shop_Ingredients;
import alotra.milktea.repository.IShop_IngredientsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class Shop_IngredientsServiceImpl implements  IShop_IngredientsService {
    @Autowired
    IShop_IngredientsRepo shopIngredientsRepo;

    @Override
    public List<Shop_Ingredients> findAll() {
        return shopIngredientsRepo.findAll();
    }

    @Override
    public Optional<Shop_Ingredients> findOne(int id) {
        return shopIngredientsRepo.findById(id);
    }

    @Override
    public void saveShop_Ingredients(Shop_Ingredients si) {
        shopIngredientsRepo.save(si);
    }

    @Override
    public void deleteShop_Ingredietns(int id) {
        shopIngredientsRepo.deleteById(id);
    }
}
