package alotra.milktea.service;

import alotra.milktea.entity.Ingredients;

import java.util.List;
import java.util.Optional;

public interface IIngredientService {
    List<Ingredients> findAll();
    List<Ingredients> findAllByStatusNot(short status);
    Optional<Ingredients> findOne(int id);
    void saveIngredients(Ingredients ingredients);
    void deleteIngredients(int id);
    List<Ingredients> finIngredientByName(String name);
}
