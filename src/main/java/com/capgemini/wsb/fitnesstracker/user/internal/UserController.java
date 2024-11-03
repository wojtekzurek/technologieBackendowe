package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
class UserController {

    private final UserServiceImpl userService;

    private final UserMapper userMapper;

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.findAllUsers()
                          .stream()
                          .map(userMapper::toDto)
                          .toList();
    }

    @GetMapping("/simple")
    public List<UserSimpleDto> getAllBasicInformationAboutUsers(){
        return userService.findAllUsers()
                .stream()
                .map(userMapper::toSimpleDto)
                .toList();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping
    public UserDto createUser(@RequestBody UserDto userDto){
        return userMapper.toDto(userService.createUser(userMapper.toEntity(userDto)));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
    }

    @GetMapping("/email")
    public List<UserSimpleByEmail> getUserByEmail(@RequestParam String email){
        UserSimpleByEmail user = userService.getUserByEmail(email)
                .map(userMapper::toSimpleByEmail)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return Collections.singletonList(user);
    }

    @GetMapping("/older/{time}")
    public List<User> getUsersOlderThan(@PathVariable LocalDate time){
        int expectedYear = time.getYear();
        int expectedDayOfYear = time.getDayOfYear();

        List<User> users = userService.findAllUsers()
                .stream()
                .filter(user -> user.getBirthdate().getYear() < expectedYear ||
                        user.getBirthdate().getYear() - expectedYear == 0 &&
                        user.getBirthdate().getDayOfYear() < expectedDayOfYear)
                .collect(Collectors.toList());

        return users;
    }

    @GetMapping("/{userId}")
    public void updateUser(@PathVariable Long userId){

    }

    @PostMapping
    public User addUser(@RequestBody UserDto userDto) throws InterruptedException {

        // Demonstracja how to use @RequestBody
        System.out.println("User with e-mail: " + userDto.email() + "passed to the request");

        // TODO: saveUser with Service and return User
        return null;
    }

}