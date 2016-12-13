package es.rubenjgarcia.oauth2.server.mongo.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MongoUserDetailsManager implements UserDetailsManager {

    private UserRepository userRepository;

    public MongoUserDetailsManager(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findOne(username);
        return Optional.ofNullable(user).orElseThrow(() -> new UsernameNotFoundException(username + " not exists"));
    }

    @Override
    public void createUser(UserDetails userDetails) {
        User user = new User();
        user.setUsername(userDetails.getUsername());
        user.setPassword(userDetails.getPassword());
        user.setEnabled(true);
        List<String> authorities = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        user.setAuthorities(authorities);
        this.userRepository.save(user);
    }

    @Override
    public void updateUser(UserDetails user) {
        throw new NotImplementedException(); //TODO
    }

    @Override
    public void deleteUser(String username) {
        this.userRepository.delete(username);
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        throw new NotImplementedException(); //TODO
    }

    @Override
    public boolean userExists(String username) {
        return loadUserByUsername(username) != null;
    }
}
