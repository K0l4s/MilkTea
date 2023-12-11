package alotra.milktea.controller;

import alotra.milktea.entity.Category;
import alotra.milktea.entity.Product;
import alotra.milktea.model.ProductModel;
import alotra.milktea.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class ProductController {
    @Autowired
    IProductService productService = new ProductServiceImpl();
    @Autowired
    ICategoryService categoryService = new CategoryServiceImpl();
    @Autowired
    IStorageService storageService;
    @GetMapping("/admin/product")
    public String findAll(Model model){
        model.addAttribute("products",productService.findAllByStatusNot((short) 0));
        return "admin/product/list";
    }
    @GetMapping("/admin/product/edit/{id}")
    public String findOne(@PathVariable("id") int id, Model model){
        Optional<Product> product = productService.findOne(id);
        if(product.isPresent()){
            model.addAttribute("categories", categoryService.findAllByStatusNot((short) 0));
            model.addAttribute("product", product.get());
            return "admin/product/edit";
        }
        return"error";
    }
    @GetMapping("/admin/product/add")
    public String addProduct(Model model){
        Product pro = new Product();
        model.addAttribute("categories", categoryService.findAllByStatusNot((short) 0));
        model.addAttribute("product",pro);
        return "/admin/product/add";
    }
    @PostMapping("/product/save")
    public String saveProduct(@ModelAttribute("product") ProductModel proModel){
        Product entity = new Product();
        //Copy model sang entity
        BeanUtils.copyProperties(proModel, entity);

        Category cateEntity = new Category();
        cateEntity.setCategoryID(proModel.getCategory().getCategoryID());
        entity.setCategory(cateEntity);

        //Kiểm tra file
        if (!proModel.getImageFile().isEmpty()) {
            UUID uuid = UUID.randomUUID();
            String uuString = uuid.toString();
            //Lưu file vào ImageURL
            entity.setImageURL(storageService.getStorageFilename(proModel.getImageFile(), uuString));
            storageService.store(proModel.getImageFile(), entity.getImageURL());
        }
        productService.saveProduct(entity);
        return "redirect:/admin/product";
    }
    @GetMapping("/admin/product/delete/{id}")
    public String deleteProduct(@PathVariable("id") int id){
        productService.DeleteProduct(id);
        return "redirect:/admin/product";
    }
    @GetMapping("admin/product/images/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serverFile(@PathVariable String filename) {
        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment;filename=\"" + file.getFilename() + "\"").body(file);
    }
    @GetMapping("/admin/product/search")
    public String searchProByName(@RequestParam("name") String name,Model model){
        if (name != "") {
            model.addAttribute("name", name);
            model.addAttribute("products",productService.findProductByName(name));
            return "/product/list";
        }
        else {
            model.addAttribute("products",productService.findAll());
            return "redirect:/admin/product";
        }
    }
    @GetMapping("/product")
    public String findAllWeb(Model model){
        model.addAttribute("products",productService.getProducts(0, 4));
        return "web/product/list";
    }
    @GetMapping("/loadMoreProducts")
    @ResponseBody
    public List<Product> loadMoreProducts(@RequestParam int offset, @RequestParam int limit) {
        // Sử dụng offset và limit để truy vấn sản phẩm từ db
        return productService.getProducts(offset, limit);
    }
}
