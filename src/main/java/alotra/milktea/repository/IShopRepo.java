package alotra.milktea.repository;

import alotra.milktea.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IShopRepo extends JpaRepository<Shop, Integer> {
}
