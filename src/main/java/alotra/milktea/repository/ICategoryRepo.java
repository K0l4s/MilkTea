package alotra.milktea.repository;

import alotra.milktea.entity.Category;
import alotra.milktea.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ICategoryRepo extends JpaRepository<Category, Integer> {
    @Query("SELECT c FROM Category c WHERE c.categoryName LIKE :keyword ")
    List<Category> findCategoryByKeyWord(@Param("keyword") String keyword);

    List<Category> findAllByStatusNot(short status);
}
