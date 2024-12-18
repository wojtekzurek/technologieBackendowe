package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import org.springframework.stereotype.Component;

@Component
class UserMapper {

    UserDto toDto(User user) {
        return new UserDto(user.getId(),
                           user.getFirstName(),
                           user.getLastName(),
                           user.getBirthdate(),
                           user.getEmail());
    }

    UserSimpleDto toSimpleDto(User user){
        return new UserSimpleDto(user.getId(),
                user.getFirstName(),
                user.getLastName());
    }

    UserSimpleByEmail toSimpleByEmail(User user){
        return new UserSimpleByEmail(
                user.getId(),
                user.getEmail());
    }

    UserUpdateDto toUpdateDto(User user){
        return new UserUpdateDto(
                user.getFirstName(),
                user.getLastName(),
                user.getBirthdate(),
                user.getEmail());
    }

    User toEntity(UserDto userDto) {
        return new User(
                        userDto.firstName(),
                        userDto.lastName(),
                        userDto.birthdate(),
                        userDto.email());
    }

    User toUpdateEntity(User user, UserDto userDto){
        user.setFirstName(userDto.firstName());
        user.setLastName(userDto.lastName());
        user.setBirthDate(userDto.birthdate());
        user.setEmail(userDto.email());
        return user;
    }

}
