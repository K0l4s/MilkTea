package alotra.milktea.service;

import alotra.milktea.entity.Product;
import alotra.milktea.repository.IProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements IProductService{
    @Autowired
    IProductRepo productRepo;
    @Override
    public List<Product> findAll() {
        return productRepo.findAll();
    }

    @Override
    public Optional<Product> findOne(int id) {
        return productRepo.findById(id);
    }

    @Override
    public void saveProduct(Product product) {
        productRepo.save(product);
    }

    @Override
    public void DeleteProduct(int id) {
        productRepo.deleteById(id);
    }

    @Override
    public List<Product> findProductByCategoryName(String name) {
        return productRepo.findProductByCategoryCategoryName(name);
    }
}
