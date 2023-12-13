package alotra.milktea.repository;

import alotra.milktea.entity.Role;
import alotra.milktea.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IShopRepo extends JpaRepository<Shop, Integer> {
    @Query("SELECT s FROM Shop s WHERE s.name LIKE :keyword ")
    List<Shop> findShopByKeyWord(@Param("keyword") String keyword);

    List<Shop> findAllByStatusNot(short status);

    Shop findByShopID(String id);
}
