package alotra.milktea.service;

import alotra.milktea.entity.Employee;
import alotra.milktea.entity.Product;

import java.util.List;
import java.util.Optional;

public interface IEmployeeService {
    List<Employee> findAll();
    List<Employee> findAllByStatusNot(short status);
    Optional<Employee> findOne(int id);

//    void saveEmployee(Employee employee);
    <S extends Employee> S saveEmployee(S entity);
    void deleteEmployee(int id);

    List<Employee> findEmployeeByName(String name);
}
