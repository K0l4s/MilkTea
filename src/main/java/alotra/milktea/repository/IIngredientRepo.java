package alotra.milktea.repository;

import alotra.milktea.entity.Ingredients;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IIngredientRepo extends JpaRepository<Ingredients, Integer> {
}
