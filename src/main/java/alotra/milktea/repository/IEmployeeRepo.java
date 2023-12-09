package alotra.milktea.repository;

import alotra.milktea.entity.Employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface IEmployeeRepo extends JpaRepository<Employee, Integer > {
    Employee findEmployeeByEmployeeID(int id);
}
