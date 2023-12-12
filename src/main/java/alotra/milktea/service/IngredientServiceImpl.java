package alotra.milktea.service;

import alotra.milktea.entity.Ingredients;
import alotra.milktea.repository.IIngredientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientServiceImpl implements IIngredientService{
    @Autowired
    IIngredientRepo ingredientRepo;
    @Override
    public List<Ingredients> findAll() {
        return ingredientRepo.findAll();
    }

    @Override
    public List<Ingredients> findAllByStatusNot(short status) {
        return ingredientRepo.findAllByStatusNot((short) 0);
    }

    @Override
    public Optional<Ingredients> findOne(int id) {
        return ingredientRepo.findById(id);
    }

    @Override
    public void saveIngredients(Ingredients ingredients) {
        ingredients.setStatus((short) 1);
        ingredientRepo.save(ingredients);
    }

    @Override
    public void deleteIngredients(int id) {
        Optional<Ingredients> optional = ingredientRepo.findById(id);
        if (optional.isPresent()){
            Ingredients ingredients = optional.get();
            ingredients.setStatus((short) 0);
            ingredientRepo.save(ingredients);
        }
        else {
            System.out.println("Doesnt exists!");
        }
    }

    @Override
    public List<Ingredients> finIngredientByName(String name) {
        return ingredientRepo.findIngredientsByKeyWord(name);
    }
}
