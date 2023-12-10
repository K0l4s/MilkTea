package alotra.milktea.service;

import alotra.milktea.entity.Role;

import java.util.List;
import java.util.Optional;

public interface IRoleService {
    List<Role> findAll();

    Optional<Role> findOne(int id);

    void saveRole(Role role);

    void deleteRole(int id);

    List<Role> findRoleByName(String name);
}
