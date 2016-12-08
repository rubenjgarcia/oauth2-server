package es.rubenjgarcia.oauth2.server.mongo.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.GroupManager;
import org.springframework.security.provisioning.UserDetailsManager;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;
import java.util.Optional;

public class MongoUserDetailsManager implements UserDetailsManager, GroupManager {

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
    public List<String> findAllGroups() {
        throw new NotImplementedException(); //TODO
    }

    @Override
    public List<String> findUsersInGroup(String groupName) {
        throw new NotImplementedException(); //TODO
    }

    @Override
    public void createGroup(String groupName, List<GrantedAuthority> authorities) {
        throw new NotImplementedException(); //TODO
    }

    @Override
    public void deleteGroup(String groupName) {
        throw new NotImplementedException(); //TODO
    }

    @Override
    public void renameGroup(String oldName, String newName) {
        throw new NotImplementedException(); //TODO
    }

    @Override
    public void addUserToGroup(String username, String group) {
        throw new NotImplementedException(); //TODO
    }

    @Override
    public void removeUserFromGroup(String username, String groupName) {
        throw new NotImplementedException(); //TODO
    }

    @Override
    public List<GrantedAuthority> findGroupAuthorities(String groupName) {
        throw new NotImplementedException(); //TODO
    }

    @Override
    public void addGroupAuthority(String groupName, GrantedAuthority authority) {
        throw new NotImplementedException(); //TODO
    }

    @Override
    public void removeGroupAuthority(String groupName, GrantedAuthority authority) {
        throw new NotImplementedException(); //TODO
    }

    @Override
    public void createUser(UserDetails user) {
        throw new NotImplementedException(); //TODO
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
