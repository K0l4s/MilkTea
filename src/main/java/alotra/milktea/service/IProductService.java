package alotra.milktea.service;

import alotra.milktea.entity.Product;

import java.util.List;
import java.util.Optional;

public interface IProductService {
    List<Product> findAll();
    Optional<Product> findOne(int id);

    void saveProduct(Product product);

    void DeleteProduct(int id);

    List<Product> findProductByCategoryName(String name);

    List<Product> findProductByName(String name);
}
