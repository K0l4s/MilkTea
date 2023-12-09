package alotra.milktea.repository;

import alotra.milktea.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICategoryRepo extends JpaRepository<Category, Integer> {
}
