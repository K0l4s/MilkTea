package alotra.milktea.service;

import alotra.milktea.entity.Employee;
import alotra.milktea.repository.IEmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements  IEmployeeService{

    @Autowired
    IEmployeeRepo employeeRepo;

    @Override
    public List<Employee> findAll() {
        return employeeRepo.findAll();
    }

    @Override
    public List<Employee> findAllByStatusNot(short status) {
        return employeeRepo.findAllByStatusNot((short) 0);
    }

    @Override
    public Optional<Employee> findOne(int id) {
        return employeeRepo.findById(id);
    }

    @Override
    public void saveEmployee(Employee employee) {
        employee.setStatus((short) 1);
        employeeRepo.save(employee);
    }

    @Override
    public void deleteEmployee(int id) {
        Optional<Employee> optional = employeeRepo.findById(id);
        if(optional.isPresent()){
            Employee employee = optional.get();
            employee.setStatus((short) 0);
            employeeRepo.save(employee);
        }
        else {
            System.out.println("Doesnt exists!");
        }
    }

    @Override
    public List<Employee> findEmployeeByName(String name) {
        return employeeRepo.findEmployeeByKeyWord(name);
    }
}
