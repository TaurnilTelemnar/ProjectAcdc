package com.javarush.kojin.controller;

import com.javarush.kojin.cmd.Command;
import com.javarush.kojin.config.Components;

public class HttpResolver {
    public Command resolve(String name){
        return Components.getCommand(name);
    }
}
