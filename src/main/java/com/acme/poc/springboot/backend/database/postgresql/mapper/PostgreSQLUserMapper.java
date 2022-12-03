package com.acme.poc.springboot.backend.database.postgresql.mapper;

import com.acme.poc.springboot.backend.database.postgresql.dto.UserDto;
import com.acme.poc.springboot.backend.database.postgresql.entity.User;
import org.mapstruct.*;


/**
 * User mapper
 */
//@Component("PoC_PostgreSQL_UserMapper")
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface PostgreSQLUserMapper {


    UserDto userToUserDto(User user);

    User userDtoToUser(UserDto userDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserFromUserDto(UserDto userDto, @MappingTarget User user);

//    @AfterMapping
//    default void linkTasks(@MappingTarget User user) {
//        user.getTasks().forEach(task -> task.setUser(user));
//    }

}
