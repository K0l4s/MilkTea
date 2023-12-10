package alotra.milktea.service;

import alotra.milktea.entity.Shop_Products;

import java.util.List;
import java.util.Optional;

public interface IShop_ProductsService {
    List<Shop_Products> findAll();
    Optional<Shop_Products> findOne(int id);
    void saveShop_Pro(Shop_Products sp);
    void deleteShop_Pro(int id);
}
