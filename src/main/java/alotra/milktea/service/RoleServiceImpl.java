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
    public Optional<Role> findOne(int id) {
        return roleRepo.findById(id);
    }

    @Override
    public void saveRole(Role role) {
        roleRepo.save(role);
    }

    @Override
    public void deleteRole(int id) {
        roleRepo.deleteById(id);
    }
}
