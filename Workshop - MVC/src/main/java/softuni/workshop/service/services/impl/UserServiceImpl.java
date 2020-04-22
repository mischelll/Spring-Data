package softuni.workshop.service.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import softuni.workshop.data.entities.Role;
import softuni.workshop.data.entities.User;
import softuni.workshop.data.repositories.UserRepository;
import softuni.workshop.service.models.UserServiceModel;
import softuni.workshop.service.services.RoleService;
import softuni.workshop.service.services.UserService;
import softuni.workshop.web.models.UserRegisterModel;

import java.util.LinkedHashSet;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final RoleService roleService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, RoleService roleService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.roleService = roleService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserServiceModel registerUser(UserRegisterModel userRegisterModel) {
//        User user = this.userRepository.findByUsername(userRegisterModel.getUsername());
        User user2 = this.modelMapper.map(userRegisterModel, User.class);
        if (userRepository.count() == 0) {
            this.roleService.seedRolesInDb();
            user2.setAuthorities(this.roleService.findAllRoles()
                    .stream()
                    .map(roleServiceModel -> this.modelMapper.map(roleServiceModel, Role.class))
                    .collect(Collectors.toSet()));
        } else {
            user2.setAuthorities(new LinkedHashSet<>());
            user2.getAuthorities().add(this.modelMapper.map(this.roleService.findByAuthority("USER"), Role.class));
        }

//        if (user == null) {
//
//        } else {
//
//        }

        user2.setPassword(bCryptPasswordEncoder.encode(userRegisterModel.getPassword()));

        return this.modelMapper.map(this.userRepository.save(user2), UserServiceModel.class);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return this.userRepository.findByUsername(s);
    }
}
