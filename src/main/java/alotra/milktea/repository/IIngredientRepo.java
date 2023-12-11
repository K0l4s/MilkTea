package alotra.milktea.repository;

import alotra.milktea.entity.Bill;
import alotra.milktea.entity.Ingredients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IIngredientRepo extends JpaRepository<Ingredients, Integer> {
    @Query("SELECT i FROM Ingredients i WHERE i.name LIKE :keyword ")
    List<Ingredients> findIngredientsByKeyWord(@Param("keyword") String keyword);

    List<Ingredients> findAllByStatusNot(short status);
}
