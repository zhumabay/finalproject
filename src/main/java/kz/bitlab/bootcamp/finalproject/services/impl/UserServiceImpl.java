package kz.bitlab.bootcamp.finalproject.services.impl;

import kz.bitlab.bootcamp.finalproject.models.Role;
import kz.bitlab.bootcamp.finalproject.models.User;
import kz.bitlab.bootcamp.finalproject.repositories.UserRepository;
import kz.bitlab.bootcamp.finalproject.services.RoleService;
import kz.bitlab.bootcamp.finalproject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleService roleService;

    @Override
    public List<User> getUsers() {
        return userRepository.findAllByOrderByFullNameAsc();
    }

    @Override
    public List<User> getUsersByKeyword(String keyword) {
        return userRepository.findAllByFullNameContainingIgnoreCaseOrderByFullNameAsc(keyword);
    }

    @Override
    public User getUser(String email){
        return userRepository.findByEmail(email);
    }

    @Override
    public User getUser(Long userId) {
        return userRepository.findById(userId).orElseThrow();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User foundUser = getUser(username);
        if (foundUser!=null){
            return foundUser;
        } throw new UsernameNotFoundException("User Name not found");
    }

    @Override
    public User addUser(User user, String rePassword){
        if(user.getPassword().equals(rePassword)) {
            User foundUser = getUser(user.getEmail());
            if (foundUser == null) {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                List<Role> roles = new ArrayList<>();
                Role role = roleService.getUserRole();
                roles.add(role);
                user.setRoles(roles);
                return userRepository.save(user);
            }
        }
        return null;
    }

    @Override
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){ //не анонимный
            return (User) authentication.getPrincipal();
        }
        return null;
    }

    @Override
    public boolean updateUserPassword(String oldPassword, String newPassword, String reNewPassword) {
        if (newPassword.equals(reNewPassword)){
            User currentUser = getCurrentUser();
            if (currentUser!=null && passwordEncoder.matches(oldPassword, currentUser.getPassword())){
                currentUser.setPassword(passwordEncoder.encode(newPassword));
                userRepository.save(currentUser);
                return true;
            }
        }
        return false;
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }
}
