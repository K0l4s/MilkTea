package alotra.milktea.service;

import alotra.milktea.entity.Employee;
import alotra.milktea.repository.IEmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class EmployeeServiceImpl implements  IEmployeeService{
    @Autowired
    IEmployeeRepo employeeRepo;
    @Override
    public List<Employee> findAll() {
        return employeeRepo.findAll();
    }

    @Override
    public Employee findOne(int id) {
        return employeeRepo.findEmployeeByEmployeeID(id);
    }

    @Override
    public void addEmployee(Employee employee) {
        employeeRepo.save(employee);
    }


}
