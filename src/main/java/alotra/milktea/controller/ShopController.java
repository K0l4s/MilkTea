package alotra.milktea.controller;

import alotra.milktea.entity.Product_Ingredients;
import alotra.milktea.entity.Shop;
import alotra.milktea.entity.Shop_Ingredients;
import alotra.milktea.entity.Shop_Products;
import alotra.milktea.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class ShopController {
    @Autowired
    IShopService shopService = new ShopServiceImpl();
    @Autowired
    IShop_ProductsService shopProductsService = new Shop_ProductsServiceImpl();
    @Autowired
    IShop_IngredientsService shopIngredientsService = new Shop_IngredientsServiceImpl();
    @Autowired
    IProduct_IngredientsService productIngredientsService = new Product_IngredientsServiceImpl();
    @Autowired
    IProductService productService = new ProductServiceImpl();
    @GetMapping("/admin/shop")
    public String findAll(Model model){
        model.addAttribute("shops", shopService.findAllBySatusNot((short) 0));
        return "admin/shop/list";
    }
    @GetMapping("/admin/shop/edit/{id}")
    public String findOne(@PathVariable("id") int id, Model model){
        Optional<Shop> shop = shopService.findOne(id);
        if(shop.isPresent()){
            model.addAttribute("shop",shop.get());
            return "admin/shop/edit";
        }
        return "error";
    }
    @GetMapping("/admin/shop/add")
    public String addShop(Model model){
        Shop shop = new Shop();
        model.addAttribute("shop",shop);
        return "admin/shop/add";
    }
    @PostMapping("/shop/update")
    public String updateShop(@ModelAttribute("shop") Shop shop){
        shopService.saveShop(shop);
        return "redirect:/admin/shop";
    }
    @PostMapping("/shop/save")
    public String saveShop(@ModelAttribute("shop") Shop shop){
        if (!shopService.isShopIdUnique(shop.getShopID())) {
            return "error";
        } else {
            shopService.saveShop(shop);
            return "redirect:/admin/shop";
        }
    }
    @GetMapping("/admin/shop/delete/{id}")
    public String deleteShop(@PathVariable("id") int id){
        shopService.deleteShop(id);
        return "redirect:/admin/shop";
    }
//    Shop_Products
    @GetMapping("/admin/shop/products/view")
    public String viewdetailProducts(@RequestParam("shopID")String id, Model model){
        model.addAttribute("list",shopProductsService.findProByShopID(id));
        model.addAttribute("shopID",id);

        return "/admin/shop_products/list";
    }
    @GetMapping("/admin/shop/products/view/shop_products/add")
    public String addShopProducts_(@RequestParam("shopID") String id, Model model){
        Shop_Products si = new Shop_Products();
        si.setShop(shopService.findShopByShopID(id));
        model.addAttribute("list",si);
        return "/admin/shop_products/add";
    }
    @PostMapping("view/shop_products/save")
    public String saveShopProducts(@ModelAttribute("list") Shop_Products sp){
        shopProductsService.saveShop_Pro(sp);
        return "redirect:/admin/shop/products/view?shopID=" + sp.getShop().getShopID();
    }
    @GetMapping("/admin/shop/products/view/shop_products/delete/{id}")
    public  String deleteProducts(@PathVariable("id") int id){
        Shop_Products si = shopProductsService.findByID(id);
        String shopID = si.getShop().getShopID();
        shopProductsService.deleteShop_Pro(id);
        return "redirect:/admin/shop/products/view?shopID=" + shopID;
    }
    @GetMapping("/admin/shop/products/view/shop_products/edit/{id}")
    public String editShop_Products(@PathVariable("id") int id, Model model){
        Optional<Shop_Products> optional = shopProductsService.findOne(id);
        if(optional.isPresent()){
            Shop_Products si = optional.get();
            model.addAttribute("list",si);
            return "/admin/shop_products/edit";
        }
        return "error";
    }


//    Shop_Ingredients
    @GetMapping("/admin/shop/ingredients/view")
    public String viewdetailIngredients(@RequestParam("shopID")String id, Model model){
        model.addAttribute("list",shopIngredientsService.findIngredientsByShopID(id));
        model.addAttribute("shopID",id);

        return "/admin/shop_ingredients/list";
    }

    @GetMapping("/admin/shop/ingredients/view/shop_ingredients/add")
    public String addShopIngredients_(@RequestParam("shopID") String id, Model model){
        Shop_Ingredients si = new Shop_Ingredients();
        si.setShop(shopService.findShopByShopID(id));
        model.addAttribute("list",si);
        return "/admin/shop_ingredients/add";
    }
    @PostMapping("view/shop_ingredients/save")
    public String saveShopIngredients(@ModelAttribute("list") Shop_Ingredients si){
        shopIngredientsService.saveShop_Ingredients(si);
        return "redirect:/admin/shop/ingredients/view?shopID=" + si.getShop().getShopID();
    }
    @GetMapping("/admin/shop/ingredients/view/shop_ingredients/delete/{id}")
    public  String deleteIngredients(@PathVariable("id") int id){
        Shop_Ingredients si = shopIngredientsService.findByID(id);
        String shopID = si.getShop().getShopID();
        shopIngredientsService.deleteShop_Ingredients(id);
        return "redirect:/admin/shop/ingredients/view?shopID=" + shopID;
    }
    @GetMapping("/admin/shop/ingredients/view/shop_ingredients/edit/{id}")
    public String editShop_Ingredients(@PathVariable("id") int id, Model model){
        Optional<Shop_Ingredients> optional = shopIngredientsService.findOne(id);
        if(optional.isPresent()){
            Shop_Ingredients si = optional.get();
            model.addAttribute("list",si);
            return "/admin/shop_ingredients/edit";
        }
        return "error";
    }

//    Product_Ingredients
    @GetMapping("/admin/shop/products/view/shop_products/details")
    public  String viewDetail(@RequestParam("productID") int id, Model model){
        model.addAttribute("list",productIngredientsService.findByProductID(id));
        model.addAttribute("productID",id);
        return "/admin/product_ingredients/list";
    }
    @GetMapping("/admin/shop/products/view/shop_products/details/product_ingredients/add")
    public  String addIngredientsForPro(@RequestParam("productID") int id, Model model){

        Product_Ingredients pi = new Product_Ingredients();
        pi.setProduct(productService.findProductByProductID(id));

        model.addAttribute("list",pi);
        return "/admin/product_ingredients/add";
    }
    @PostMapping("details/product_ingredients/save")
    public String saveProduct_Ingredients(@ModelAttribute("list") Product_Ingredients pi){
        productIngredientsService.savePro_Ingredients(pi);
        return "redirect:/admin/shop/products/view/shop_products/details?productID="+pi.getProduct().getProductID();
    }
    @GetMapping("/admin/shop/products/view/shop_products/details/product_ingredients/delete/{id}")
    public  String deleteProduct_Ingredients(@PathVariable("id")int id){
        Product_Ingredients pi = productIngredientsService.findProduct_IngredientsByID(id);
        int productID = pi.getProduct().getProductID();
        productIngredientsService.deletePro_Ingredients(id);
        return "redirect:/admin/shop/products/view/shop_products/details?productID=" + productID;
    }
    @GetMapping("/admin/shop/products/view/shop_products/details/product_ingredients/edit/{id}")
    public String editProduct_Ingredients(@PathVariable("id")int id, Model model){
        Optional<Product_Ingredients> optional = productIngredientsService.findOne(id);
        if(optional.isPresent()){
            Product_Ingredients pi = optional.get();
            model.addAttribute("list",pi);
            return "/admin/product_ingredients/edit";
        }
        return "error";
    }
}
