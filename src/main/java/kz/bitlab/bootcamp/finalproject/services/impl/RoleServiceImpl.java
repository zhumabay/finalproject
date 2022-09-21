package kz.bitlab.bootcamp.finalproject.services.impl;

import kz.bitlab.bootcamp.finalproject.models.Role;
import kz.bitlab.bootcamp.finalproject.repositories.RoleRepository;
import kz.bitlab.bootcamp.finalproject.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role getUserRole() {
        return roleRepository.findByRole("ROLE_USER");
    }
}
