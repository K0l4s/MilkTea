package alotra.milktea.service;

import alotra.milktea.entity.Bill_Products;
import alotra.milktea.repository.IBill_ProductsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class Bill_ProductsServiceImpl implements IBill_ProductsService{
    @Autowired
    IBill_ProductsRepo billProductsRepo;


    @Override
    public List<Bill_Products> findAll() {
        return billProductsRepo.findAll();
    }

    @Override
    public Optional<Bill_Products> findOne(int id) {
        return billProductsRepo.findById(id);
    }

    @Override
    public void saveBill_Products(Bill_Products bp) {
        billProductsRepo.save(bp);
    }

    @Override
    public void deleteBill_Products(int id) {
        billProductsRepo.deleteById(id);
    }
}
