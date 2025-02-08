package iscae.master.sb.role.controllers;

import iscae.master.sb.role.dtos.RoleDto;
import iscae.master.sb.role.services.RoleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/role")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public List<RoleDto> getAll() {
        return roleService.getAll();
    }

    @GetMapping("/{id}")
    public RoleDto getById(@PathVariable("id") Short id) {
        return roleService.getById(id);
    }

    @PostMapping
    public Short add(@RequestBody RoleDto roleDto) {
        return roleService.add(roleDto);
    }

    @PutMapping("/{id}")
    public Short update(@RequestBody RoleDto roleDto, @PathVariable("id") Short id) {
        return roleService.update(roleDto, id);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Short id) {
        roleService.deleteById(id);
    }
}