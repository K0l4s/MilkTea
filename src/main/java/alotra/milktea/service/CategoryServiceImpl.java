package alotra.milktea.service;

import alotra.milktea.entity.Category;
import alotra.milktea.repository.ICategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements ICategoryService{
    @Autowired
    ICategoryRepo categoryRepo;
    @Override
    public List<Category> findAll() {
        return categoryRepo.findAll();
    }

    @Override
    public List<Category> findAllByStatusNot(short status) {
        return categoryRepo.findAllByStatusNot((short) 0);
    }

    @Override
    public Optional<Category> findOne(int id) {
        return categoryRepo.findById(id);
    }

    @Override
    public void saveCategory(Category category) {
        category.setStatus((short) 1);
        categoryRepo.save(category);
    }

    @Override
    public void deleteCategory(int id) {
        Optional<Category> optional = categoryRepo.findById(id);
        if (optional.isPresent()){
            Category category = optional.get();
            category.setStatus((short) 0);
            categoryRepo.save(category);
        }
        else {
            System.out.println("Doesnt exists!");
        }
    }

    @Override
    public List<Category> findProductByName(String name) {
        return categoryRepo.findCategoryByKeyWord(name);
    }
}
