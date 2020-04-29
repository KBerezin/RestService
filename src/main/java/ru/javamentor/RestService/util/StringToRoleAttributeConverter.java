package ru.javamentor.RestService.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.javamentor.RestService.entity.Role;
import ru.javamentor.RestService.service.RoleService;


@Component
public class StringToRoleAttributeConverter implements Converter<String, Role> {
    private RoleService roleService;

    public StringToRoleAttributeConverter() {
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    public Role convert(String role){
        return roleService.findByRole(role);
    }
}