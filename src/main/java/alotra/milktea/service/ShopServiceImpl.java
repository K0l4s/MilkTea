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
    public List<Shop> findAllBySatusNot(short status) {
        return shopRepo.findAllByStatusNot((short) 0);
    }

    @Override
    public Optional<Shop> findOne(int id) {
        return shopRepo.findById(id);
    }

    @Override
    public void saveShop(Shop shop) {
        shop.setStatus((short) 1);
        shopRepo.save(shop);
    }

    @Override
    public void deleteShop(int id) {
        Optional<Shop> optional = shopRepo.findById(id);
        if (optional.isPresent()){
            Shop shop = optional.get();
            shop.setStatus((short) 0);
            shopRepo.save(shop);
        }
        else {
            System.out.println("Doesnt exists!");
        }
    }

    @Override
    public List<Shop> findShopByName(String name) {
        return shopRepo.findShopByKeyWord(name);
    }

    @Override
    public boolean isShopIdUnique(String id) {
        List<Shop> shops = findAll();
        for(Shop item: shops)
            if(item.getShopID().equals(id)){
                return false;
            }
        return true;
    }

    @Override
    public Shop findShopByShopID(String id) {
        return shopRepo.findByShopID(id);
    }

}
