package alotra.milktea.service;

import alotra.milktea.entity.Role;
import alotra.milktea.repository.IRoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class RoleServiceImpl implements IRoleService{

    @Autowired
    IRoleRepo roleRepo;
    @Override
    public List<Role> findAll() {
        return roleRepo.findAll();
    }

    @Override
    public List<Role> findAllByStatusNot(short status) {
        return roleRepo.findAllByStatusNot(status);
    }

    @Override
    public Optional<Role> findOne(int id) {
        return roleRepo.findById(id);
    }

    @Override
    public void saveRole(Role role) {
        role.setStatus((short) 1);
        roleRepo.save(role);
    }

    @Override
    public void deleteRole(int id) {
        Optional<Role> optional = roleRepo.findById(id);
        if(optional.isPresent()){
            Role role = optional.get();
            role.setStatus((short) 0);
            roleRepo.save(role);
        }
        else {
            System.out.println("Doesnt exists!");
        }
    }

    @Override
    public List<Role> findRoleByName(String name) {
        return roleRepo.findRoleByKeyWord(name);
    }
}
