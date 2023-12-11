package alotra.milktea.repository;

import alotra.milktea.entity.Ingredients;
import alotra.milktea.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IRoleRepo extends JpaRepository<Role,Integer> {
    @Query("SELECT r FROM Role r WHERE r.name LIKE :keyword ")
    List<Role> findRoleByKeyWord(@Param("keyword") String keyword);

    List<Role> findAllByStatusNot(short status);
}
