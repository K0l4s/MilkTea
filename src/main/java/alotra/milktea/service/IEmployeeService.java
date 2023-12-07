package alotra.milktea.service;

import alotra.milktea.entity.Employee;

import java.util.List;

public interface IEmployeeService {
    List<Employee> findAll();

    Employee findOne(int id);

    void addEmployee(Employee employee);
}
