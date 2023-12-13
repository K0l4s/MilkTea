package alotra.milktea.repository;

import alotra.milktea.entity.Shop_Products;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IShop_ProductsRepo extends JpaRepository<Shop_Products,Integer> {
    List<Shop_Products> findByShopShopID(String id);

    Shop_Products findShop_ProductsById(int id);

    List<Shop_Products> findByProductsProductID(int id);

}
