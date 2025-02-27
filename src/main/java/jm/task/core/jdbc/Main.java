package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    private final static UserService userService = new UserServiceImpl();
    public static void main(String[] args) {
        userService.createUsersTable();

        userService.saveUser("Ivan", "Ivanov", (byte) 32);
        userService.saveUser("Sergey", "Sergeev", (byte) 58);
        userService.saveUser("Vasya", "Vasyeliev", (byte) 24);
        userService.saveUser("Kirill", "Kirilov", (byte) 18);

        userService.removeUserById(2);

        userService.getAllUsers();

        userService.cleanUsersTable();

        userService.dropUsersTable();


    }
}
