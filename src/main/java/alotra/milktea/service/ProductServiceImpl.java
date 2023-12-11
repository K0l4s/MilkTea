package alotra.milktea.service;

import alotra.milktea.entity.Product;
import alotra.milktea.repository.IProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements IProductService{
    @Autowired
    IProductRepo productRepo;
    @Override
    public List<Product> findAll() {
        return productRepo.findAll();
    }

    @Override
    public List<Product> findAllByStatusNot(short status) {
        return productRepo.findAllByStatusNot(status);
    }

    @Override
    public Optional<Product> findOne(int id) {
        return productRepo.findById(id);
    }

    @Override
    public void saveProduct(Product product) {
        product.setStatus((short) 1);
        productRepo.save(product);
    }

    @Override
    public void DeleteProduct(int id) {
        Optional<Product> optionalProduct = productRepo.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setStatus((short) 0);
            productRepo.save(product);
        }
        else {
            System.out.println("Doesnt exists!");
        }
    }

    @Override
    public List<Product> findProductByCategoryName(String name) {
        return productRepo.findProductByCategoryCategoryName(name);
    }
}
