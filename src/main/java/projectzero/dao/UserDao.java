package projectzero.dao;

import projectzero.exceptions.AlreadyExistsException;
import projectzero.exceptions.NoSuchElementException;
import projectzero.model.User;
import projectzero.utils.JSONUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserDao implements IDao<String, User> {

    private String pathToJson;

    public UserDao(String pathToJson) {
        this.pathToJson = pathToJson;
    }

    /**
     * @return list of all users
     */
    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        try {
            users.addAll(Arrays.asList(JSONUtils.readAllFromFile(pathToJson, User[].class)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    /**
     * @param id - id used in search through the list of users
     * @return user with current id
     * @throws NoSuchElementException if user not found in list
     */
    @Override
    public User getById(String id) throws NoSuchElementException {
        return this.getAll().stream()
                .filter(item -> item.getEmail().equals(id))
                .findFirst()
                .orElseThrow(NoSuchElementException::new);
    }

    /**
     * @param newEntity - user which will add to the list of users
     * @throws AlreadyExistsException if list already contains current user
     */
    @Override
    public void add(User newEntity) throws AlreadyExistsException {
        List<User> userList = this.getAll();
        for (User user : userList)
            if (user.equals(newEntity))
                throw new AlreadyExistsException("User already exists");
        userList.add(newEntity);
        JSONUtils.writeListIntoFile(pathToJson, userList);
    }

    /**
     * @param user - user which will removed from list of users
     * @return true if user was removed
     *          false if user wasn't found
     */
    @Override
    public boolean remove(User user) {
        List<User> userList = this.getAll();

        int index = userList.indexOf(user);

        if (index < 0)
            return false;
        userList.remove(index);
        JSONUtils.writeListIntoFile(pathToJson, userList);
        return true;
    }

    /**
     * @param user - user which will updated
     * @return updated use
     * @throws NoSuchElementException if list doesn't contains current user
     */
    @Override
    public User update(User user) throws NoSuchElementException {
        List<User> userList = this.getAll();

        int index = userList.indexOf(user);

        if (index < 0)
            throw  new NoSuchElementException();
        User result = userList.set(index, user);
        JSONUtils.writeListIntoFile(pathToJson, userList);
        return result;
    }
}
