package red.dragon.controller.interfaces;

//import java.util.Date;
import java.sql.Date;
import java.util.List;

import red.dragon.Exceptions.*;
import red.dragon.pojos.ActiveOrder;
import red.dragon.pojos.Customer;
import red.dragon.pojos.OrderHistory;
import red.dragon.pojos.User;

public interface IOrder {

	public List<ActiveOrder> getAllActiveOrders();
	
	public int submitOrder(ActiveOrder object) throws DuplicateItemException;
	
	public boolean completeOrder(ActiveOrder object) throws DuplicateItemException;

	public boolean updateOrder(ActiveOrder object) throws ItemNotFoundException;

	public boolean cancelOrder(ActiveOrder object) throws ItemNotFoundException;
	
	public boolean deleteOrder(ActiveOrder object) throws ItemNotFoundException;

	public ActiveOrder getActiveOrder(int ActiveOrderNumber) throws ItemNotFoundException;

	public List<ActiveOrder> getActiveOrders(Customer customer) throws ItemNotFoundException;

	public List<ActiveOrder> getActiveOrders(User employee) throws ItemNotFoundException;

	public List<ActiveOrder> getActiveOrders(Date from, Date to) throws InvalidDateException;
 
	//getorders by status?
	
	//Gets a list of the most recent orders. Such as the most recent 500 items.
	public List<OrderHistory> getOrderHistoryList(int items);
	
	public List<OrderHistory> getOrderHistory(Customer cust) throws ItemNotFoundException;
	
	public List<OrderHistory> getOrderHistory(User employee) throws ItemNotFoundException;
	
	public List<OrderHistory> getOrderHistory(Date from, Date to) throws InvalidDateException;
	
//	public List<OrderHistory> getOrderHistory(int ActiveOrderNumber) throws ItemNotFoundException;
	
	public boolean deleteOrder(OrderHistory pastOrder) throws ItemNotFoundException;
}
