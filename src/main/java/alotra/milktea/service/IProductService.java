package alotra.milktea.service;

import alotra.milktea.entity.Category;
import alotra.milktea.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IProductService {
    List<Product> findAll();
    List<Product> findAllByStatusNot(short status);
    Page<Product> findAllByStatusNot(short status, Pageable pageable);
    Optional<Product> findOne(int id);

//    void saveProduct(Product product);
    <S extends Product> S saveProduct(S entity);
    void DeleteProduct(int id);

    List<Product> findProductByCategoryName(String name);

    List<Product> findProductByName(String name);
    List<Product> getProducts(int offset, int limit);

    Page<Product> searchProducts(String searchTerm, short status, Pageable pageable);
    Page<Product> searchProductsByCategory(String searchTerm, Category category, short status, Pageable pageable);
    Page<Product> searchProductsByCategoryAndName(String searchTerm, Category category, short status, Pageable pageable);

    Product findProductByProductID(int id);
}
