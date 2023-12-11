package alotra.milktea.service;

import alotra.milktea.entity.Employee;

import java.util.List;
import java.util.Optional;

public interface IEmployeeService {
    List<Employee> findAll();
    List<Employee> findAllByStatusNot(short status);
    Optional<Employee> findOne(int id);

    void saveEmployee(Employee employee);

    void deleteEmployee(int id);

    List<Employee> findEmployeeByName(String name);
}
