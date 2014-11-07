/*
 * 
 */
package red.dragon.controller;

import java.util.ArrayList;
//import java.util.Date;
import java.sql.Date;
import java.util.List;

import red.dragon.Exceptions.DuplicateItemException;
import red.dragon.Exceptions.InvalidDateException;
import red.dragon.Exceptions.ItemNotFoundException;
import red.dragon.controller.interfaces.IOrder;
import red.dragon.dao.*;
import red.dragon.pojos.*;
import red.dragon.controller.ControlUtil;

import com.vaadin.server.VaadinSession;


public class OrderController implements IOrder
{
	ActiveOrderDao			order;
	OrderHistoryDao			hist;
	ActiveOrderDetailDao orderDetail;

	 //Item description, price, special instructions, sequence, 
	 
	 public List<ActiveOrderDetailDetails> getActiveOrderDetailDetails(ActiveOrder active)
	 {
		 List<ActiveOrderDetailDetails> list = new ArrayList<ActiveOrderDetailDetails>();//active.getActiveOrderDetails();
		 ActiveOrderDetailDetails details;// = new ActiveOrderDetailDetails(); 
		 if (active !=null && active.getActiveOrderDetails()!=null)
		 {
		 for(ActiveOrderDetail detail : active.getActiveOrderDetails())
		 {
			 details = new ActiveOrderDetailDetails();
			 details.setName(detail.getMenuItem().getName());
			 details.setDescription(detail.getMenuItem().getDescription());
			 details.setPrice(detail.getMenuItem().getPrice());
			 details.setSequence(detail.getItemSequence());
			 details.setSpecialInstructions(detail.getSpecialInstruction());
			 list.add(details);
		 }
		 }
		 
		 return list;
	 }
	 
	public OrderController()
	{
		order = new ActiveOrderDao();
		hist = new OrderHistoryDao();
		orderDetail = new ActiveOrderDetailDao();
	}

	public boolean cancelOrder(ActiveOrder object)
			throws ItemNotFoundException
	{
		// RECORDING CANCELED ORDER IN ORDER HISTORY TABLE
		OrderHistory orhist = ControlUtil.convert(object, "CANCELED");
		try
		{
			hist.add(orhist);
		}
		catch (DuplicateItemException e)
		{// Do nothing if canceled order already in order history table.
			e.printStackTrace();
		}
		order.delete(object);
		
		return true;//returns true to satisfy interface requirements. Kept for later if one wants to verify that the transaction worked.
	}

	// Add String getMenuItems()

	@Override
	public int submitOrder(ActiveOrder object)
			throws DuplicateItemException
	{
		order.add(object);// ORDER THROWS DUP EXCEPTION BUT IF THE CUSTOMER ID
							// IS INCORRECT THEN IT COULD ALSO HAVE
		if (object.getActiveOrderDetails() != null) 
		{
            for (ActiveOrderDetail detail : object.getActiveOrderDetails()) 
            {
                orderDetail.add(detail);
            }
		}

		// AN EXCEPTION.
		order.entityManager.clear();
		return -1;//returns -1 to satisfy interface requirements. Kept for later if one wants to verify that the transaction worked.
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see red.dragon.controller.IOrder#getOrder(int)
	 */
	@Override
	public ActiveOrder getActiveOrder(int OrderNumber)
			throws ItemNotFoundException
	{
		return order.getByID(OrderNumber);
	}

	public boolean updateOrder(ActiveOrder object)
			throws ItemNotFoundException
	{
		try
		{
			order.update(object);
		}
		catch (DuplicateItemException e)
		{
			// An update should throw DuplicateItemException??? Or no
			// exception???
			e.printStackTrace();
		}
		return true;//returns true to satisfy interface requirements. Kept for later if one wants to verify that the transaction worked.
	}

	/*
	 * @param Customer A customer object that will be compared against the
	 * active orders table.
	 * 
	 * @return List<ActiveOrder> Returns a list of all currently active orders
	 * for this customer
	 */
	@Override
	public List<ActiveOrder> getActiveOrders(Customer customer)
			throws ItemNotFoundException
	{
		return customer.getActiveOrders();
	}

	@Override
	public List<ActiveOrder> getAllActiveOrders()
	{
		return order.getAll(ActiveOrder.class);
	}

	/*
	 * @param ActiveOrder Currently active order to complete.
	 * 
	 * @return AcriveOrder Returns the object sent in if successful.
	 */
	@Override
	public boolean completeOrder(ActiveOrder object)
			throws DuplicateItemException
	{
		OrderHistory orhist = ControlUtil.convert(object, "COMPLETE");
		hist.add(orhist);
		order.delete(object);
		return true;//returns true to satisfy interface requirements. Kept for later if one wants to verify that the transaction worked.
	}

	/*
	 * @param User Accepts a user object in order to find all orders processed
	 * by a specific employee.
	 * 
	 * @return List<ActiveOrder> Returns a list of all currently active orders
	 * that this employee has processed.
	 */
	@Override
	public List<ActiveOrder> getActiveOrders(User employee)
			throws ItemNotFoundException
	{
		return employee.getActiveOrders();
	}

	/*
	 * @param from
	 * 
	 * @return List<ActiveOrder> Returns a list of all active orders between the
	 * two date ranges. Both date ranges are inclusive.
	 */
	@Override
	public List<ActiveOrder> getActiveOrders(Date from, Date to)
	{
		List<ActiveOrder> l = order.getAll(ActiveOrder.class);
		List<ActiveOrder> tmp = new ArrayList<ActiveOrder>();
		ActiveOrder ao;

		for (int c = 0; c < l.size(); c++)
		{
			ao = l.get(c);
			if (ao.getTimestamp().after(from)
					|| ao.getTimestamp().compareTo(from) == 0)
			{
				if (ao.getTimestamp().before(to)
						|| ao.getTimestamp().compareTo(to) == 0)
				{
					tmp.add(ao);
				}
			}
		}

		return tmp;
	}

	@Override
	public List<OrderHistory> getOrderHistory(Customer cust)
	{
		return cust.getOrderHistory();
	}

	@Override
	public List<OrderHistory> getOrderHistory(User employee)
	{
		return employee.getOrderHistory();
	}

	@Override
	public boolean deleteOrder(ActiveOrder object)
			throws ItemNotFoundException
	{
		order.deleteByID(object.getOrderID());// .delete(object);
		return true;//returns true to satisfy interface requirements. Kept for later if one wants to verify that the transaction worked.
	}

	@Override
	public List<OrderHistory> getOrderHistoryList(int items)
	{
		// TODO Rob will generate the code for these to work on the database side
		return null;
	}

	@Override
	public List<OrderHistory> getOrderHistory(Date from, Date to)
			throws InvalidDateException
	{
		// TODO Rob will generate the code for these to work on the database side
		return null;
	}

//	@Override
//	public List<OrderHistory> getOrderHistory(int ActiveOrderNumber)
//			throws ItemNotFoundException
//	{
////		hist.get
//		return null;
//	}

	@Override
	public boolean deleteOrder(OrderHistory pastOrder)
			throws ItemNotFoundException
	{
		this.hist.delete(pastOrder);
		return true;//returns true to satisfy interface requirements. Kept for later if one wants to verify that the transaction worked.
	}

}
