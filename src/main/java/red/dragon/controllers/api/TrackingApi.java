package red.dragon.controllers.api;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.vaadin.data.util.BeanItemContainer;

//import red.dragon.models.TrackingItem;

public class TrackingApi extends AbstractApi {
//	Connection conn;
//
//	public TrackingApi() {
//		super();
//	}
//
//	public BeanItemContainer<TrackingItem> getOrders(BeanItemContainer<TrackingItem> container) {
//		container.removeAllItems();
//		int ID = 0;
//		int menuID = 0;
//		String status = null;
//		int customerID = 0;
//		Date timestamp = null;
//		int orderNumber = 0;
//		ResultSet set = null;
//
//		try {
//
//			set = query("SELECT * FROM ActiveOrder");
//			while (set.next()) {
//				ID = set.getInt("ID");
//				menuID = set.getInt("MenuID");
//				status = set.getString("Status");
//				customerID = set.getInt("CustomerID");
//				timestamp = set.getTimestamp("Timestamp");
//				orderNumber = set.getInt("OrderNum");
//				TrackingItem newItem = new TrackingItem(ID, menuID, status, customerID,
//						timestamp, orderNumber);
//				container.addItem(newItem);
//			}
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		close();
//		return container;
//	}
//	
//	public void editTrackingItem(int id, String status, BeanItemContainer<TrackingItem> container) {
//		update("UPDATE ActiveOrders SET Status = '" + status + "' WHERE ID = " + id);
//		getOrders(container);
//		close();
//	}
}
