package red.dragon.controller;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;

import red.dragon.Exceptions.DuplicateItemException;
import red.dragon.Exceptions.ItemNotFoundException;
import red.dragon.ViewModels.MenuItemVM;
import red.dragon.ViewModels.MenuVM;
import red.dragon.controller.interfaces.IOrder;
import red.dragon.controller.interfaces.IUsers;
import red.dragon.dao.CustomerDao;
import red.dragon.dao.UserDao;
import red.dragon.pojos.ActiveOrder;
import red.dragon.pojos.ActiveOrderDetail;
import red.dragon.pojos.ActiveOrderDetailDetails;
import red.dragon.pojos.Customer;
import red.dragon.pojos.Menu;
import red.dragon.pojos.MenuItem;
import red.dragon.pojos.OrderHistory;
import red.dragon.pojos.Permission;
import red.dragon.pojos.DailyTransaction;
import red.dragon.pojos.User;
import red.dragon.pojos.UserGroup;

public class ControllerTester
{

//	************OrderController Testing below******************
//	
//	submitOrder(ActiveOrder object)----------------SUCCESS
//	updateOrder(ActiveOrder object)----------------SUCCESS
//	cancelOrder(ActiveOrder object)----------------SUCCESS
//	getOrder(int ActiveOrderNumber)----------------SUCCESS
//	getOrders(Customer customer)-------------------SUCCESS
//	getOrders(User employee)-----------------------SUCCESS
//	getOrders(Date from, Date to)------------------SUCCESS
//	getAllOrders()---------------------------------SUCCESS
//	completeOrder(ActiveOrder object) -------------SUCCESS
//  getOrderHistory(Customer cust)-----------------SUCCESS
//	getOrderHistory(User employee)-----------------SUCCESS
//	deleteOrder(ActiveOrder object)----------------SUCCESS
//	The below functions need to be implemented on the database side because of speed issues.
//	getOrderHistoryList(int items)-----------------Not implemented yet
//	getOrderHistory(Date from, Date to)------------Not implemented yet
//	deleteOrder(OrderHistory pastOrder)------------Need to test
	
//	************* UserController testing below****************
	
//	public boolean validate(Object user)-----------SUCCESS
//	addUser(User u)--------------------------------SUCCESS
//	deleteUser(User u)-----------------------------SUCCESS
//	update(User u)---------------------------------SUCCESS
//	getAllUsers()----------------------------------SUCCESS
//	addUserGroup(UserGroup group)------------------SUCCESS
//	removeUserGroup(UserGroup group)---------------SUCCESS
//	updateUserGroup(UserGroup group)---------------SUCCESS
//	getAllUserGroups()-----------------------------SUCCESS
//	removeFromGroup(User u, UserGroup group)-------
//	addToGroup(User u, UserGroup group)------------
//	getUserGroups(User u)--------------------------
//	getUsers(UserGroup group)----------------------
//	getPermissions(UserGroup group)----------------
//	addPermissions(Permission perms)---------------SUCCESS
//	getAllPermissions()----------------------------SUCCESS
//	addCustomer(Customer cust)---------------------
//	deleteCustomer(Customer cust)------------------
//	update(Customer cust)--------------------------
//	getAllCustomers();-----------------------------
//	getPermissions(String name);-------------------
//	getUserGroup(String name);---------------------SUCCESS
//	getCustomer(String name);----------------------
//	getUser(String name);--------------------------
		
//	************* MenuAdminController testing below****************
	
//	createMenuItem(MenuItem item)------------------SUCCESS
//	editMenuItem(MenuItem item)--------------------SUCCESS
//	deleteMenuItem(MenuItem item)------------------SUCCESS
//	getMenuItem(String name)-----------------------SUCCESS
//	createMenu(Menu menu)--------------------------SUCCESS
//	editMenu(Menu menu)----------------------------SUCCESS
//	deleteMenu(Menu menu)--------------------------SUCCESS
//	getMenu(String name)---------------------------SUCCESS
//	getMenuItemVM(String name);--------------------SUCCESS
//	getMenuItemVM(int id);-------------------------SUCCESS
//	getAllMenuItemVMs();---------------------------SUCCESS



	
	
	private int				recordsToShow;
	private ControlUtil		control;
	private long			mils;
	private StringBuilder	sb;
	private OrderController	or;
	private java.sql.Date	from, to;
	private ActiveOrder		ao;
	List<ActiveOrder>		temp;

	public ControllerTester()
	{
		sb = new StringBuilder();
		or = new OrderController();
		control = new ControlUtil();
		recordsToShow = 10;
	}

	public static void main(String[] args)
	{
		ControllerTester c = new ControllerTester();
		c.runTests();
	}

	private void runTests()
	{
//		runUserTests();
		runReportsTests();
//		runMenuControllerTests();
	}
	
	public void runReportsTests()
	{
		ReportsController r = new ReportsController();
		OrderController or = new OrderController();
		UserController u = new UserController();
		MenuAdminController men = new MenuAdminController();	
		Calendar cal = Calendar.getInstance();
		Calendar[] tmp;
		
//		System.out.println("Testing subtractDays method");
//		r.printCalendar(cal);
//		cal = r.subtractDays(cal,365);
//		System.out.println("-365 days");
//		r.printCalendar(cal);
//		
//		System.out.println("\nTesting getDayStartEnd method");
//		cal =  Calendar.getInstance();
//		
//		r.printCalendar(cal);
//		System.out.println("Current time and date:");
//		tmp = r.getDayStartEnd(cal);
//		System.out.println("Beginning of day:");
//		r.printCalendar(tmp[0]);
//		System.out.println("End of day:");
//		r.printCalendar(tmp[1]);
//		System.out.println("");
//		
//		System.out.println("Testing isWithinRange method");
//		System.out.println("Is within range(today, today morning,today evening) = "+r.isWithinRange(cal, tmp[0],tmp[1]));
//		cal.roll(Calendar.DAY_OF_YEAR, 1);
//		System.out.println("Is today+1 day. Still in range? = "+r.isWithinRange(cal, tmp[0],tmp[1]));
//		cal.roll(Calendar.DAY_OF_YEAR, -2);
//		System.out.println("Is today-1 day. Still in range? = "+r.isWithinRange(cal, tmp[0],tmp[1]));
//		System.out.println("");		
			
		
		for (int c=0;c<20;c++)
		{
			System.out.println("");
			
		}
		
		System.out.println("Total of todays transactions = " +r.getTodaysTransactionSummary());
		List<DailyTransaction> trans = r.getLast30DaysTransactions();
		System.out.println("30 days transactions = " +trans.size());
		
		for (int c=0;c<trans.size();c++)
		{
			System.out.println("Transaction #"+c+" total = "+trans.get(c).getCost());			
		}
		
		System.out.println("\nTesting getLast30DaysTransactionsDoubles()");
		int[] a = r.getLast30DaysTransactionsInts();
		
		for (int c=0;c<a.length;c++)
		{
			System.out.println("Transaction day "+c+" total = "+a[c]);			
		}
		
		System.out.println("\nTesting getLast30DaysTransactionsDates()");
		String[] s = r.getLast30DaysTransactionsDates();
		
		for (int c=0;c<s.length;c++)
		{
			System.out.println("Transaction day# "+c+" Date = "+s[c]);			
		}
	}
	
	public void runGetMenuTests()
	{
		MenuAdminController man = new MenuAdminController();
		
		//SUCCESS
		List <MenuItemVM> l = man.getAllMenuItemVMs();
		System.out.println("Get all menuItemVM's list size = "+l.size());
		
		//SUCCESS WITH GET BY STRING
		MenuItemVM men = l.get(0);
		System.out.println("food category "+men.getFoodCategoryName());
		
	}
	
	public void runMenuControllerTests()
	{
		OrderController order = new OrderController();
		UserController usr = new UserController();
		MenuAdminController men = new MenuAdminController();
		ActiveOrderDetail detail = new ActiveOrderDetail();
		detail.setMenuItem(men.getAllMenuItems().get(0));
		detail.setSpecialInstruction("Anthony's mamma is so fat!");
					
		ActiveOrder act = new ActiveOrder();
		act.setBundle(null);
		act.setCustomer(usr.getAllCustomers().get(0));
		act.addActiveOrderDetail(detail);
		
		act.setPrice(22);
		act.setStatus("Awesome");
		act.setTimestamp(new Date(System.currentTimeMillis()));
		
		try
		{
			order.submitOrder(act);
		}
		catch (DuplicateItemException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try
		{
			act = order.getActiveOrder(683);
			for(ActiveOrderDetailDetails bob : order.getActiveOrderDetailDetails(act))
			 {
				System.out.println("Description = "+bob.getDescription());
				System.out.println("Price = "+bob.getPrice());
				System.out.println("sequence = "+bob.getSequence());
				System.out.println("Special instructions = "+bob.getSpecialInstructions());
				System.out.println("");
			 }
		}
		catch (ItemNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void runUserTests()
	{
		UserController usr = new UserController();
		User u = new User();
		User tmp;
		u.setName("Test user 1");
		u.setPassword("Password 1");
		u.setEmail("Test@test1.com");
		try
		{
			tmp = usr.getUser(u.getName());
			if (tmp==null)
			{
			System.out.println("Added user id number is: "+usr.addUser(u));
			}
			else
			{
				System.out.println("User already in system. Their ID is: "+tmp.getId());
			}
		}
		catch (NoSuchAlgorithmException | InvalidKeySpecException
				| DuplicateItemException e)
		{
			System.out.println("Add user error! Stack trace below:");
			e.printStackTrace();
		}
		
		System.out.println("Attempting to get user by name: "+usr.getUser(u.getName()));
		System.out.println("Original Password length = "+usr.getUser(u.getName()).getSalt().length);
		System.out.println("Original Salt Length = "+usr.getUser(u.getName()).getPassword().length);
		u.setEmail("sam@llama.com");
		try
		{			
			System.out.println("Attempting to edit the user. Successful? "+usr.update(u));
			System.out.println("New info: "+usr.getUser(u.getName()));
		}
		catch (DuplicateItemException e)
		{
			System.out.println("Update user error! Stack trace below:");
			e.printStackTrace();
		}
		System.out.println("Password length = "+usr.getUser(u.getName()).getSalt().length);
		System.out.println("Salt Length = "+usr.getUser(u.getName()).getPassword().length);
		System.out.println("Attempting to validate the user: Validated? "+usr.validate(u));
		
		try
		{
			System.out.println("Attempting to delete the user. Successful? "+usr.deleteUser(u));
		}
		catch (ItemNotFoundException e)
		{
			System.out.println("Delete user error! Stack trace below:");
			e.printStackTrace();
		}
	}
	
	private <T> void printRecords(List<T> tmp)
	{
		
		for (int c = 0; c < tmp.size() && c < this.recordsToShow + 1; c++)
		{
			System.out.println(tmp.get(c).toString());
		}
	}


}
