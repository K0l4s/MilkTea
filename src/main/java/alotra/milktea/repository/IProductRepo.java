package alotra.milktea.repository;

import alotra.milktea.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IProductRepo extends JpaRepository<Product, Integer> {
    List<Product> findProductByCategoryCategoryName(String name);

    @Query("SELECT p FROM Product p WHERE p.name LIKE :keyword OR p.category.categoryName LIKE :keyword")
    List<Product> findProductByKeyWord(@Param("keyword") String keyword);
    List<Product> findAllByStatusNot(short status);
    Page<Product> findAllByStatusNot(short status, Pageable pageable);
    @Query("SELECT p FROM Product p WHERE p.name LIKE %:searchTerm% AND p.status <> :status")
    Page<Product> findProductsByNameContainingAndStatusNot(String searchTerm, short status, Pageable pageable);
}
