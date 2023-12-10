package alotra.milktea.service;

import alotra.milktea.entity.Shop;
import alotra.milktea.repository.IShopRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class ShopServiceImpl implements IShopService{
    @Autowired
    IShopRepo shopRepo;

    @Override
    public List<Shop> findAll() {
        return shopRepo.findAll();
    }

    @Override
    public Optional<Shop> findOne(int id) {
        return shopRepo.findById(id);
    }

    @Override
    public void saveShop(Shop shop) {
        shopRepo.save(shop);
    }

    @Override
    public void deleteShop(int id) {
        shopRepo.deleteById(id);
    }

    @Override
    public List<Shop> findShopByName(String name) {
        return shopRepo.findShopByKeyWord(name);
    }
}
