package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import javax.persistence.metamodel.StaticMetamodel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserDaoJDBCImpl implements UserDao {
    private static final Connection conn = Util.getInstance().getConnection();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Statement stmts = conn.createStatement()) {
            stmts.executeUpdate("CREATE TABLE IF NOT EXISTS `test_connection`.`users` " + "(id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(255), lastname VARCHAR(255), age INT)");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        try(Statement stmts = conn.createStatement()){
            stmts.executeUpdate("DROP TABLE IF EXISTS `test_connection`.`users`");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try(PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO `test_connection`.`users` (`name`, `lastname`, `age`) VALUES (?, ?, ?)")){
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3,age);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try(PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM `test_connection`.`users` WHERE (`id` = ?)")){
            preparedStatement.setLong(1,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try(ResultSet resultSet = conn.createStatement().executeQuery("SELECT * FROM `test_connection`.`users`")){
            while(resultSet.next()){
                User user = new User(resultSet.getString("name"),resultSet.getString("lastname"),resultSet.getByte("age"));
                user.setId(resultSet.getLong("id"));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public void cleanUsersTable() {
        try (Statement statement = conn.createStatement()) {
            statement.executeUpdate("TRUNCATE TABLE `test_connection`.`users`");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
