package apap.tutorial.bacabaca.service;

import java.util.List;

import apap.tutorial.bacabaca.model.Role;

public interface RoleService {
    
    List<Role> getAllList();

    Role getRoleByRoleName(String name);
}
