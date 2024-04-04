package com.javarush.kojin.config;

import com.javarush.kojin.BaseIT;
import com.javarush.kojin.service.UserService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;

class ComponentsIT extends BaseIT {
    @Test
    void whenComponentNotExist_thenReturnNull(){
        String someString = Components.getComponent(String.class);
        assertNull(someString);
    }

    @Test
    void whenComponentExist_thenReturnComponent(){
        UserService userService = Components.getComponent(UserService.class);
        assertInstanceOf(UserService.class, userService);
    }
}