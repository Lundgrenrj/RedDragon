package red.dragon.controller;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import red.dragon.Exceptions.DuplicateItemException;
import red.dragon.Exceptions.ItemNotFoundException;
import red.dragon.ViewModels.UserAccountVM;
import red.dragon.controller.interfaces.IUsers;
import red.dragon.dao.*;
import red.dragon.pojos.*;

/*
 *
 */
public class UserController implements IUsers
{

	private UserDao			ud;
	private CustomerDao		cd;
	private UserGroupDao	ugd;
	private PermissionsDao	pd;

	public UserController()
	{
		ud = new UserDao();
		cd = new CustomerDao();
		ugd = new UserGroupDao();
		pd = new PermissionsDao();
	}

	public boolean validate(Object user)
	{

		if (user instanceof User)
		{
			if (user == null || ((User) user).getName() == null
					|| ((User) user).getName().isEmpty()) { return false; }
			User u = ud.getByID(((User) user).getId());
			if (u == null)
			{
				System.out.println("User object is null");
				u = ud.getByName(((User) user).getName());
				if (u == null) 
				{
					return false;
				}
			}

			try
			{

				byte[] b = null;

				// generate a hash using the salt from the database and the
				// password of the supplied user object.
				b = ControlUtil.generatePasswordHash(
						new String(((User) user).getPassword()), u.getSalt());
				System.out.println("entering password check method");
				if (Arrays.equals(u.getPassword(), b))
				{
					System.out.println("passwords match");
					if (u.getName().equals(((User) user).getName())) { return true; }
				}
			}
			catch (NoSuchAlgorithmException | InvalidKeySpecException e)
			{
				e.printStackTrace();
				return false;
			}
		}
		if (user instanceof Customer)
		{
			if (user == null || ((Customer) user).getName() == null
					|| ((Customer) user).getName().isEmpty()) { return false; }
			Customer c = cd.getByID(((Customer) user).getId());
			if (c == null)
			{
				c = cd.getByName(((Customer) user).getName());
				if (c == null) { return false; }
			}

			try
			{

				byte[] b = null;

				// generate a hash using the salt from the database and the
				// password of the supplied user object.
				b = ControlUtil.generatePasswordHash(new String(
						((Customer) user).getPassword()), c.getSalt());

				if (Arrays.equals(c.getPassword(), b))
				{
					if (c.getName().equals(((Customer) user).getName())) { return true; }
				}
			}
			catch (NoSuchAlgorithmException | InvalidKeySpecException e)
			{
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}

	@Override
	public int addUser(User u) throws DuplicateItemException,
			NoSuchAlgorithmException, InvalidKeySpecException
	{
		User tmp;
		byte[] b = ControlUtil.generateSalt(30);
		String password = new String(u.getPassword());
		byte[] by = ControlUtil.generatePasswordHash(password, b);

		tmp = u.clone();
		tmp.setSalt(b);
		tmp.setPassword(by);
		tmp.setId(new User().getId());// set uninitialized id
		ud.add(tmp);

		tmp = ud.getByName(u.getName());
		return tmp.getId();// return new user ID
	}

	@Override
	public boolean deleteUser(User u) throws ItemNotFoundException
	{
		if (u.getId() < 0)
		{
			if (u.getName() != null || !u.getName().isEmpty())
			{
				User tmp = ud.getByName(u.getName());
				ud.deleteByID(tmp.getId());
			}
			else
			{
				throw new ItemNotFoundException(
						"User ID and Name are not initialized");
			}
		}
		else
		{
			ud.delete(u);
		}
		if (ud.getByName(u.getName()) == null)
		{
			return true;
		}
		else
		{
			return false;
		}

	}

	@Override
	//This method finds the user by name and updates it. In this way you create a user object with the name and 
	//the fields needing update and it will find and update.
	public boolean update(User u) throws DuplicateItemException
	{
		User tmp = ud.getByName(u.getName());
		tmp = ControlUtil.merge(tmp, u);

		ud.update(tmp);
		if (ud.getByName(u.getName()).equals(tmp))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	@Override
	public List<User> getAllUsers()
	{
		return ud.getAll(User.class);
	}

	@Override
	public int addUserGroup(UserGroup group) throws DuplicateItemException
	{
		ugd.add(group);
		return ugd.getByName(group.getName()).getId();
	}

	@Override
	public boolean removeUserGroup(UserGroup group) throws ItemNotFoundException
	{
		
		if (group.getId()<0)
		{
			ugd.deleteByID(ugd.getByName(group.getName()).getId());
		}
		else
		{
			ugd.deleteByID(group.getId());
		}
		if (ugd.getByName(group.getName())==null)
		{
			return true;
		}
		return false;
	}

	@Override
	public boolean updateUserGroup(UserGroup group)
			throws DuplicateItemException
	{
		UserGroup tmp = ugd.getByName(group.getName());
		tmp = ControlUtil.merge(tmp, group);

		ugd.update(tmp);
		if (ugd.getByName(group.getName()).equals(tmp))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	@Override
	public List<UserGroup> getAllUserGroups()
	{
		return ugd.getAll(UserGroup.class);
	}

	@Override
	public boolean removeFromGroup(User u, UserGroup group)
	{
		group.removeUser(u);
		try
		{
			//need to test and return that it really works.
			ugd.update(group);
		}
		catch (DuplicateItemException e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean addToGroup(User u, UserGroup group)
	{
		group.addUser(u);
		try
		{
			//need to test and return that it really works.
			ugd.update(group);
		}
		catch (DuplicateItemException e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public List<UserGroup> getUserGroups(User u)
	{
		List<UserGroup> l = new ArrayList<UserGroup>();
		l.add(u.getUserGroup());//Currently returns only a single item because a user has only a single group membership option.
		//if that ever changes the interface will be able to accommodate a list of groups this user belongs to.
		return l;
	}

	@Override
	public List<User> getUsers(UserGroup group)
	{
		return group.getUsers();
	}

	@Override
	public Permission getPermissions(UserGroup group)
	{
		return group.getPermission();
	}

	@Override
	public int addPermissions(Permission perms)
			throws DuplicateItemException
	{
		pd.add(perms);
		return pd.getByName(perms.getName()).getId();
	}

	@Override
	public List<Permission> getAllPermissions()
	{
		return pd.getAll(Permission.class);
	}

	@Override
	public int addCustomer(Customer cust) throws DuplicateItemException,
			NoSuchAlgorithmException, InvalidKeySpecException
	{
		//need to test and return that it really works.
		cd.add(cust);
		return 0;
	}

	@Override
	public boolean deleteCustomer(Customer cust) throws ItemNotFoundException
	{
		//need to test and return that it really works.
		cd.delete(cust);
		return true;
	}

	@Override
	public boolean update(Customer cust) throws DuplicateItemException
	{
		//need to test and return that it really works.
		cd.update(cust);
		return true;
	}

	@Override
	public List<Customer> getAllCustomers()
	{
		return cd.getAll(Customer.class);
	}

	@Override
	public User getUser(String name)
	{
		return ud.getByName(name);
	}

	@Override
	public Customer getCustomer(String name)
	{
		return cd.getByName(name);
	}

	@Override
	public UserGroup getUserGroup(String name)
	{
		return ugd.getByName(name);
	}

	@Override
	public Permission getPermissions(String name)
	{
		return pd.getByName(name);
	}

	
	
	
	@Override
	public UserAccountVM getUserVM(String name) throws ItemNotFoundException
	{
		User tmp = ud.getByName(name);
		return ControlUtil.getUserAccountVM(tmp);
	}

	@Override
	public List<UserAccountVM> getAllUserVMs()
	{
		return ControlUtil.getAllUserAccountVMs(ud.getAll(User.class));
	}

	@Override
	public boolean modifyUserVM(UserAccountVM usr) throws ItemNotFoundException
	{
		// TODO Auto-generated method stub
		User tmp = ud.getByName(usr.getUserName());
		
		if (tmp==null)
		{
			throw new ItemNotFoundException("User not found: "+usr.getUserName());
		}
		try
		{
			this.update(ControlUtil.merge(tmp,ControlUtil.convertUserAccountVM(usr)));
			return true;
		}
		catch (DuplicateItemException e)
		{
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean deleteUserVM(UserAccountVM usr) throws ItemNotFoundException
	{
		User tmp = ud.getByName(usr.getUserName());
		this.deleteUser(tmp);
		tmp = ud.getByName(usr.getUserName());
		if (tmp==null)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	@Override
	public boolean addUserVM(UserAccountVM usr) throws DuplicateItemException
	{
		try
		{
			this.addUser(ControlUtil.convertUserAccountVM(usr));
		}
		catch (NoSuchAlgorithmException | InvalidKeySpecException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean setUserPassword(String password, String userName)throws ItemNotFoundException, DuplicateItemException
	{
		User tmp = ud.getByName(userName);
		
		if (tmp==null)
		{
			throw new ItemNotFoundException("User not found.");
		}
		
		tmp.setPassword(password);
		
		return this.update(tmp);
	}
	
	

}
