package ait.cohort63.online_shop.service;

import ait.cohort63.online_shop.model.entity.Role;
import ait.cohort63.online_shop.repository.RoleRepository;
import ait.cohort63.online_shop.service.interfaces.RoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role getRoleUser() {
        // Получаем роль USER из БД
        return roleRepository.findByTitle("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("DB doesn't contain role USER"));
    }
}
