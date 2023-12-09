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
    public Optional<Ingredients> findOne(int id) {
        return ingredientRepo.findById(id);
    }

    @Override
    public void saveIngredients(Ingredients ingredients) {
        ingredientRepo.save(ingredients);
    }

    @Override
    public void deleteIngredients(int id) {
        ingredientRepo.deleteById(id);
    }
}
