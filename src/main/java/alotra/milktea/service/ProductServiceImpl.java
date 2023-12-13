package alotra.milktea.service;

import alotra.milktea.entity.Category;
import alotra.milktea.entity.Product;
import alotra.milktea.repository.IProductRepo;
import io.micrometer.common.util.StringUtils;
import org.codehaus.groovy.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
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
    public Page<Product> findAllByStatusNot(short status, Pageable pageable) {
        return productRepo.findAllByStatusNot(status, pageable);
    }

    @Override
    public Optional<Product> findOne(int id) {
        return productRepo.findById(id);
    }

    @Override
    public <S extends Product> S saveProduct(S entity) {
        entity.setStatus((short) 1);
        if (entity.getProductID() == 0) {
            return productRepo.save(entity);
        }
        else {
            Optional<Product> optImages = findOne(entity.getProductID());
            if (optImages.isPresent()) {
                if (StringUtils.isEmpty(entity.getImageURL())) {
                    entity.setImageURL(optImages.get().getImageURL());
                }
                else {
                    entity.setImageURL(entity.getImageURL());
                }
            }
            return productRepo.save(entity);
        }
    }

//    @Override
//    public void saveProduct(Product product) {
//        product.setStatus((short) 1);
//        productRepo.save(product);
//    }

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

    @Override
    public List<Product> findProductByName(String name) {
        return productRepo.findProductByKeyWord(name);
    }

    @Override
    public List<Product> getProducts(int offset, int limit) {
        Pageable pageable = PageRequest.of(offset / limit, limit);
        Page<Product> page = productRepo.findAllByStatusNot((short) 0, pageable);
        return page.getContent();
    }

    @Override
    public Page<Product> searchProducts(String searchTerm, short status, Pageable pageable) {
        return productRepo.findProductsByNameContainingAndStatusNot(searchTerm, status, pageable);
    }

    @Override
    public Page<Product> searchProductsByCategory(String searchTerm, Category category, short status, Pageable pageable) {
        return productRepo.searchProductsByCategory(searchTerm, category, status, pageable);
    }

    @Override
    public Page<Product> searchProductsByCategoryAndName(String searchTerm, Category category, short status, Pageable pageable) {
        return productRepo.searchProductsByCategoryAndName(searchTerm, category, status, pageable);
    }
}
