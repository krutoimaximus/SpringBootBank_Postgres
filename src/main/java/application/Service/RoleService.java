package application.Service;


import application.Entity.Role;
import application.Exception.RoleNotFoundException;
import application.Repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role findByRole(String role){
        Optional<Role> foundedRole = Optional.ofNullable(roleRepository.findByRole(role));
        return foundedRole.orElseThrow(RoleNotFoundException::new);
    }


}
