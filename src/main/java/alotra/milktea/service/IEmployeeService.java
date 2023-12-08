package alotra.milktea.service;

import alotra.milktea.entity.Employee;

import java.util.List;
import java.util.Optional;

public interface IEmployeeService {
    List<Employee> findAll();

    Employee findOne(int id);

    void saveEmployee(Employee employee);

    void deleteEmployee(int id);
}
