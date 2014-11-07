package red.dragon.controller.interfaces;


import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

import red.dragon.Exceptions.DuplicateItemException;
import red.dragon.Exceptions.ItemNotFoundException;
import red.dragon.ViewModels.UserAccountVM;
import red.dragon.pojos.*;





public interface IUsers
{
	public boolean validate(Object user);
	
		
	public int addUser(User u) throws DuplicateItemException, NoSuchAlgorithmException, InvalidKeySpecException;
	
	public boolean deleteUser(User u)throws ItemNotFoundException;
	
	public boolean update(User u)throws DuplicateItemException;
	
	public List<User> getAllUsers();
	
	public User getUser(String name);
	
	
	
	public int addCustomer(Customer cust) throws DuplicateItemException, NoSuchAlgorithmException, InvalidKeySpecException;
	
	public boolean deleteCustomer(Customer cust)throws ItemNotFoundException;
	
	public boolean update(Customer cust)throws DuplicateItemException;
	
	public List<Customer> getAllCustomers();
	
	public Customer getCustomer(String name);
	
	
		
	public int addUserGroup(UserGroup group) throws DuplicateItemException;
	
	public boolean removeUserGroup(UserGroup group)throws ItemNotFoundException;
	
	public boolean updateUserGroup(UserGroup group)throws DuplicateItemException;
	
	public List<UserGroup> getAllUserGroups();
	
	public UserGroup getUserGroup(String name);
	
	
	
	public boolean removeFromGroup(User u, UserGroup group)throws ItemNotFoundException;
	
	public boolean addToGroup(User u, UserGroup group)throws DuplicateItemException;
	
	
	//Gets all user groups that this user is a part of
	public List<UserGroup> getUserGroups(User u)throws ItemNotFoundException;
	
	//Gets all users in the user group
	public List<User> getUsers(UserGroup group)throws ItemNotFoundException;
	
	
	public Permission getPermissions(UserGroup group)throws ItemNotFoundException;
		
	public int addPermissions(Permission perms)throws DuplicateItemException;
	
	public Permission getPermissions(String name);
	
	public List<Permission> getAllPermissions();
	
	
	
	public UserAccountVM getUserVM(String name)throws ItemNotFoundException;
	
	public List<UserAccountVM> getAllUserVMs();
	
	public boolean modifyUserVM(UserAccountVM usr)throws ItemNotFoundException;
	
	public boolean deleteUserVM(UserAccountVM usr)throws ItemNotFoundException;
	
	public boolean addUserVM(UserAccountVM usr)throws DuplicateItemException;
	
	public boolean setUserPassword(String password, String userName)throws ItemNotFoundException, DuplicateItemException;
}
