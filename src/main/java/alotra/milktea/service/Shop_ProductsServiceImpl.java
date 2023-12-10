package alotra.milktea.service;

import alotra.milktea.entity.Shop_Products;
import alotra.milktea.repository.IShop_ProductsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class Shop_ProductsServiceImpl implements IShop_ProductsService{
    @Autowired
    IShop_ProductsRepo shopProductsRepo;

    @Override
    public List<Shop_Products> findAll() {
        return shopProductsRepo.findAll();
    }

    @Override
    public Optional<Shop_Products> findOne(int id) {
        return shopProductsRepo.findById(id);
    }

    @Override
    public void saveShop_Pro(Shop_Products sp) {
        shopProductsRepo.save(sp);
    }

    @Override
    public void deleteShop_Pro(int id) {
        shopProductsRepo.deleteById(id);
    }
}
