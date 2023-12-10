package alotra.milktea.service;

import alotra.milktea.entity.Product_Ingredients;
import alotra.milktea.repository.IProduct_IngredientsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class Product_IngredientsServiceImpl implements  IProduct_IngredientsService{
    @Autowired
    IProduct_IngredientsRepo productIngredientsRepo;
    @Override
    public List<Product_Ingredients> findAll() {
        return productIngredientsRepo.findAll();
    }

    @Override
    public Optional<Product_Ingredients> findOne(int id) {
        return productIngredientsRepo.findById(id);
    }

    @Override
    public void savePro_Ingredients(Product_Ingredients pi) {
        productIngredientsRepo.save(pi);
    }

    @Override
    public void deletePro_Ingredients(int id) {
        productIngredientsRepo.deleteById(id);
    }
}
