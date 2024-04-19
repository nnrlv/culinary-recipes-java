package daos;

import exceptions.EmailAlreadyTakenException;

import java.util.List;

public interface Dao<K, E> {
    E create(E entity) throws EmailAlreadyTakenException;

    E getById(K id);

    List<E> getAll();

    boolean update(E entity);

    boolean delete(K id);
}