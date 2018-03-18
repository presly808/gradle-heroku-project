package projectzero.controller;

import projectzero.exceptions.AlreadyExistsException;
import projectzero.exceptions.JoinException;
import projectzero.exceptions.LoginException;
import projectzero.exceptions.NoSuchElementException;
import projectzero.model.Order;
import projectzero.model.User;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public interface IUserController {

    String login(User user) throws LoginException;

    void join (String email, String pass) throws JoinException;

    List<Order> getAll(User user);

    List<Order> getAllSortedBy(User user, Comparator<Order> comparator);

    List<Order> getFilteredBy(User user, Predicate<Order> predicate);

}
