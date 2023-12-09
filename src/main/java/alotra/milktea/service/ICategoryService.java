package alotra.milktea.service;

import alotra.milktea.entity.Category;

import java.util.List;
import java.util.Optional;

public interface ICategoryService {
    List<Category> findAll();

    Optional<Category> findOne(int id);

    void saveCategory(Category category);
    void deleteCategory(int id);
}
