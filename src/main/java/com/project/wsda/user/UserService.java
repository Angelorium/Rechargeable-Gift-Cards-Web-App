package com.project.wsda.user;

public interface UserService {
    void saveUser(UserDto userDto);

    User findByUsername(String username);
}
