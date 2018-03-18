package projectzero.dao;

import projectzero.exceptions.AlreadyExistsException;
import projectzero.exceptions.NoSuchElementException;
import projectzero.model.Order;
import projectzero.model.User;
import projectzero.utils.JSONUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class OrderDao implements IDao<Integer, Order> {

    private String pathToJson;

    public OrderDao(String pathToJson) {
        this.pathToJson = pathToJson;
    }

    /**
     * @return list of all orders
     */
    @Override
    public List<Order> getAll() {
        List<Order> orders = new ArrayList<>();
        try {
            orders.addAll(Arrays.asList(JSONUtils.readAllFromFile(pathToJson, Order[].class)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return orders;
    }

    /**
     * @param user - user used in search through the list of orders
     * @return list of orders which user have
     */
    public List<Order> getAllByUser(User user) {
        return this.getAll().stream()
                .filter(order -> order.getOwner().equals(user))
                .collect(Collectors.toList());
    }

    /**
     * @param id - id used in search through the list of orders
     * @return order with current id
     * @throws NoSuchElementException if order not found in list
     */
    @Override
    public Order getById(Integer id) throws NoSuchElementException {
        return this.getAll().stream().filter(order -> order.getId() == id).findFirst().orElseThrow(NoSuchElementException::new);
    }

    /**
     * @param newOrder - order which will add to the list of orders
     * @throws AlreadyExistsException if list already contains current order
     */
    @Override
    public void add(Order newOrder) throws AlreadyExistsException {
        List<Order> orders = this.getAll();

        if (orders.contains(newOrder))
            throw new AlreadyExistsException("Order already exist");

        orders.add(newOrder);

        JSONUtils.writeListIntoFile(pathToJson, orders);
    }

    /**
     * @param order - order which will removed from list of orders
     * @return true if order was removed
     *          false if order wasn't found
     */
    @Override
    public boolean remove(Order order) {
        List<Order> orders = this.getAll();

        int index = orders.indexOf(order);

        if (index < 0)
            return false;

        orders.remove(index);

        JSONUtils.writeListIntoFile(pathToJson, orders);

        return true;
    }

    /**
     * @param order - order which will updated
     * @return updated order
     * @throws NoSuchElementException if list doesn't contains current order
     */
    @Override
    public Order update(Order order) throws NoSuchElementException {
        List<Order> orders = this.getAll();

        int index = orders.indexOf(order);

        if (index < 0)
            throw  new NoSuchElementException();

        Order result = orders.set(index, order);

        JSONUtils.writeListIntoFile(pathToJson, orders);

        return result;
    }
}
