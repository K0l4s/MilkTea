package alotra.milktea.service;

import alotra.milktea.entity.Shop;

import java.util.List;
import java.util.Optional;

public interface IShopService {
    List<Shop> findAll();
    Optional<Shop>  findOne(int id);

    void saveShop(Shop shop);

    void deleteShop(int id);

    List<Shop> findShopByName(String name);
    boolean isShopIdUnique(String id);
}
