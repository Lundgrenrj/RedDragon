package red.dragon.daoInterfaces;

import java.util.List;

import red.dragon.Exceptions.DuplicateItemException;
import red.dragon.Exceptions.ItemNotFoundException;

public interface ICommonInterface<T>{
	
	//Returns all Objects of Type T
	//Example: UserDao userDao = new UserDao();
	//List<User> userList = userDao.getAll(User.class);
	public List<T> getAll(Class<T> classObject);

	//Returns a single object of T, throws item not found exception
	//Example: UserDao userDao = new UserDao();
	//Example: User user1 = userDao.getByID(10);
	public T getByID(int id) throws ItemNotFoundException;
	
	//Adds this object of type T to the T table
	//Example: UserDao userDao = new UserDao();
	//User user1 = new User();
	//user1.setName("Bob");
	//userDao.add(user1);
	public void add(T object) throws DuplicateItemException;


	//Updates this object of type T to the T table. Returns true if successful.
	//Example: UserDao userDao = new UserDao();
	//User user1 = userDao.getByID(10);
	//user1.setName("Bob");
	//userDao.update(user1);
	public boolean update(T object) throws DuplicateItemException;


	//Removes this object of type T from the T table. Returns true if successful.
	//Example: UserDao userDao = new UserDao();
	//User user1 = userDao.getByID(10);
	//userDao.delete(user1);
	public boolean delete(T object);

	//Removes object with this id from table. Returns true if successful.
	//Example: UserDao userDao = new UserDao();
	//userDao.deleteByID(10);
	public boolean deleteByID(int id)throws ItemNotFoundException;;
}
