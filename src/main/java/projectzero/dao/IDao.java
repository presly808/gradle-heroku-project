package projectzero.dao;

import projectzero.exceptions.AlreadyExistsException;
import projectzero.exceptions.NoSuchElementException;

import java.util.List;

public interface IDao<Key, Entity> {

    List<Entity> getAll();

    Entity getById(Key id) throws NoSuchElementException;

    void add(Entity newEntity) throws AlreadyExistsException;

    boolean remove(Entity entity);

    Entity update(Entity entity) throws NoSuchElementException;
}
