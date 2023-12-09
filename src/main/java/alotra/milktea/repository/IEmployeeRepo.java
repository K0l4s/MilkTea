package alotra.milktea.repository;

import alotra.milktea.entity.Customer;
import alotra.milktea.entity.Employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface IEmployeeRepo extends JpaRepository<Employee, Integer > {
    Employee findEmployeeByEmployeeID(int id);

    @Query("SELECT e FROM Employee e WHERE e.name LIKE :keyword OR e.username.username LIKE :keyword OR e.roleID.name LIKE :keyword " )
    List<Employee> findEmployeeByKeyWord(@Param("keyword") String keyword);
}
