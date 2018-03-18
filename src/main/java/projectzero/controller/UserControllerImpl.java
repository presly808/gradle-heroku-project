package projectzero.controller;

import projectzero.dao.OrderDao;
import projectzero.dao.UserDao;
import projectzero.exceptions.*;
import projectzero.model.Order;
import projectzero.model.User;
import projectzero.utils.KeyUtils;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class UserControllerImpl implements IUserController {

    private UserDao userDao;
    private OrderDao orderDao;

    public UserControllerImpl(UserDao userDao, OrderDao orderDao) {
        this.userDao = userDao;
        this.orderDao = orderDao;
    }

    @Override
    public String login(User user) throws LoginException {
        User loginUser;

        try {
            loginUser = userDao.getById(user.getEmail());
            user.setRole(loginUser.getRole());
        } catch (NoSuchElementException e) {
            throw new LoginException("No such user");
        }

        if (!loginUser.getPass().equals(user.getPass()))
            throw new LoginException("Wrong password");

        return KeyUtils.getUniqueKey();
    }

    @Override
    public void join(String email, String pass) throws JoinException {
        try {
            userDao.add(new User(email, pass));
        } catch (AlreadyExistsException e) {
            throw new JoinException(e.getMessage());
        }
    }

    @Override
    public List<Order> getAll(User user) {
        return orderDao.getAllByUser(user);
    }

    @Override
    public List<Order> getAllSortedBy(User user, Comparator<Order> comparator) {
        List<Order> orders = orderDao.getAllByUser(user);
        orders.sort(comparator);
        return orders;
    }

    @Override
    public List<Order> getFilteredBy(User user, Predicate<Order> predicate) {
        return orderDao.getAllByUser(user).stream().filter(predicate).collect(Collectors.toList());
    }
}
