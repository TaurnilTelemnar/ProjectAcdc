package com.javarush.kojin.config;

import com.javarush.kojin.cmd.Error;
import com.javarush.kojin.cmd.*;
import com.javarush.kojin.controller.HttpResolver;
import com.javarush.kojin.repository.UserRepository;
import com.javarush.kojin.service.UserService;
import com.javarush.kojin.util.Go;
import lombok.experimental.UtilityClass;

import java.util.HashMap;
import java.util.Map;

@UtilityClass
public class Components {
    private static final Map<Class<?>, Object> components = new HashMap<>();
    private static final Map<String, Command> commands = new HashMap<>();
    static {
        init();
    }
    private static void init(){
        initComponents();
        initCommands();
    }


    @SuppressWarnings("unchecked")
    public static<T> T getComponent(Class<T> aClass){
        Object component = components.get(aClass);
        if(component == null){
            return null;
        }
        return (T) component;
    }

    public static Command getCommand(String name){
        return commands.get(name);
    }

    private static void initComponents(){
        HttpResolver httpResolver = new HttpResolver();
        UserRepository userRepository = new UserRepository();
        UserService userService = new UserService(userRepository);
        Config config = new Config(userService);

        components.put(HttpResolver.class, httpResolver);
        components.put(UserRepository.class, userRepository);
        components.put(UserService.class, userService);
        components.put(Config.class, config);
    }

    private static void initCommands() {

        Command home = new Home();
        Command login = new Login(getComponent(UserService.class));
        Command profile = new Profile();
        Command logout = new Logout();
        Command editUser = new EditUser(getComponent(UserService.class));
        Command error = new Error();
        Command listUser = new ListUser(getComponent(UserService.class));
        Command deleteUser = new DeleteUser(getComponent(UserService.class));
        Command signup = new Signup(getComponent(UserService.class));
        Command createUser = new CreateUser(getComponent(UserService.class));


        commands.put(Go.HOME, home);
        commands.put(Go.LOGIN, login);
        commands.put(Go.PROFILE, profile);
        commands.put(Go.LOGOUT, logout);
        commands.put(Go.EDIT_USER, editUser);
        commands.put(Go.ERROR, error);
        commands.put(Go.LIST_USER, listUser);
        commands.put(Go.DELETE_USER, deleteUser);
        commands.put(Go.SIGNUP, signup);
        commands.put(Go.CREATE_USER, createUser);

    }
}