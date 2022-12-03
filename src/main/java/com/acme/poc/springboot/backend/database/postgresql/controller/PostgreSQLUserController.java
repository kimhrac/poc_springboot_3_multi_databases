package com.acme.poc.springboot.backend.database.postgresql.controller;

import com.acme.poc.springboot.backend.database.postgresql.dto.UserDto;
import com.acme.poc.springboot.backend.database.postgresql.entity.User;
import com.acme.poc.springboot.backend.database.postgresql.mapper.PostgreSQLUserMapper;
import com.acme.poc.springboot.backend.database.postgresql.service.PostgreSQLUserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@RestController("PoC_PostgreSQL_UserController")
@RequestMapping(path = "/api/v1/postgresql/users")
public class PostgreSQLUserController {

    private final PostgreSQLUserService userService;
    private final PostgreSQLUserMapper userMapper;


    PostgreSQLUserController(PostgreSQLUserService userService, PostgreSQLUserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }


    @GetMapping(path = "")
    public List<UserDto> getAllUsers(Pageable pageable, @RequestParam(defaultValue = "username,asc", required = false) String[] sort) {        // TODO Check if sort could just be: Sort sort (just lige Pageable pageable)
        Page<User> users = userService.getAllUsers(pageable, sort);
        return users.stream()
                .map(userMapper::userToUserDto)
                .collect(Collectors.toList());
    }

    @PostMapping(path = "")
    public UserDto createUser(@RequestBody @NonNull @Valid UserDto userDto) {
        User user = userMapper.userDtoToUser(userDto);
        return userMapper.userToUserDto(userService.createUser(user));
    }

    @GetMapping(path = "{uuid}")
    public UserDto getUserById(@PathVariable/*(name = "uuid", required = true)*/ UUID uuid) {
        return userMapper.userToUserDto(userService.getUserById(uuid));
    }

//    @PutMapping(path = "")
//    public UserDto updateUser(@RequestBody @NonNull @Valid UserDto userDto) {
//        if (null == userDto.uuid()) throw new IllegalArgumentException("User UUID is missing!");
//        User user = userRepository
//                .findById(userDto.uuid())
//                .orElseThrow(EntityNotFoundException::new);
//        userMapper.updateUserFromUserDto(userDto, user);
//        return userMapper.userToUserDto(
//                userRepository.save(user)
//        );
//    }

}
