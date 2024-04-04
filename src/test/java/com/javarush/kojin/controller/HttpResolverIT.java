package com.javarush.kojin.controller;

import com.javarush.kojin.BaseIT;
import com.javarush.kojin.cmd.Command;
import com.javarush.kojin.cmd.Home;
import com.javarush.kojin.config.Components;
import com.javarush.kojin.util.Go;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;

class HttpResolverIT extends BaseIT {
    private final HttpResolver httpResolver = Components.getComponent(HttpResolver.class);

    @Test
    void whenResolveCommand_thenReturnCommand(){
        Command home = httpResolver.resolve(Go.HOME);
        assertInstanceOf(Home.class, home);
    }

    @Test
    void whenResolveIncorrectCommand_thenReturnNull(){
        Command command = httpResolver.resolve("NoCommand");
        assertNull(command);
    }
}