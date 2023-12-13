package alotra.milktea.repository;

import alotra.milktea.entity.Cart;
import alotra.milktea.entity.Category;
import alotra.milktea.entity.Customer;
import alotra.milktea.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ICartRepo extends JpaRepository<Cart,Integer> {
    @Query("SELECT c FROM Cart c WHERE c.customer.customerName LIKE :keyword ")
    List<Cart> findCartByKeyWord(@Param("keyword") String keyword);

    Cart findCartByCustomer(Customer customer);


    Cart findCartById(int id);

}
