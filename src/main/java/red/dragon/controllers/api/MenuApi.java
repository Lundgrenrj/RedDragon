package red.dragon.controllers.api;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Image;

import red.dragon.models.MenuItem;

public class MenuApi extends AbstractApi {
	Connection conn;

	public MenuApi() {
		super();
	}

	public void addMenuItem(String itemName, String description,
			double price, String pictureString, String foodCategory,
			BeanItemContainer<MenuItem> container) {
		int foodCategoryID = getFoodCategoryId(foodCategory);
		update("INSERT INTO MenuItem (ID, Name, Description, Price, Picture, FoodCategoryID) VALUES ("
				+ "NULL"
				+ ", '"
				+ itemName
				+ "', '"
				+ description
				+ "', "
				+ price + ", '" + pictureString + "', '" + foodCategoryID + "')");
		close();
		getMenuItems(container);
	}

	public void editMenuItem(int ID, String itemName,
			String description, double price, String pictureString,
			String foodCategory, BeanItemContainer<MenuItem> container) {
		int foodCategoryID = getFoodCategoryId(foodCategory);
		update("UPDATE MenuItem SET ID = " + ID + ", Name = '"
				+ itemName + "', Description = '" + description + "', Price = "
				+ price + ", Picture = '" + pictureString
				+ "', foodCategoryID = '" + foodCategoryID + "' WHERE ID = " + ID);
		close();
		getMenuItems(container);
	}

	private int getFoodCategoryId(String foodCategory) {
		int ID = 0;
		try{
			ResultSet res = query("SELECT ID FROM Menu WHERE Name = '" + foodCategory + "'");
			while (res.next()) {
				ID = (res.getInt("ID"));
			}
		} catch (Exception e){
			
		}
		return ID;
	}

	public void deleteMenuItem(int ID, BeanItemContainer<MenuItem> container) {
		update("DELETE FROM MenuItem WHERE ID = " + ID);
		close();
		getMenuItems(container);
	}

	public List<String> getFoodCategories() {
		List<String> foodCategories = new ArrayList<String>();
		try {
			ResultSet res = query("SELECT DISTINCT name FROM FoodCategory ORDER BY name");
			while (res.next()) {
				foodCategories.add(res.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		close();
		return foodCategories;
	}
	
	public BeanItemContainer<MenuItem> getMenuItems(
			BeanItemContainer<MenuItem> container) {

		container.removeAllItems();
		int itemID = 0;
		String itemName = null;
		String description = null;
		String pictureString = null;
		Image picture = null;
		double itemPrice = 0;
		String foodCategory = null;
		ResultSet set = null;

		try {

			set = query("SELECT A.ID AS ID, A.Name AS Name, A.Description AS Description, A.Picture AS Picture, A.Price AS Price, B.Name AS FoodCategory FROM MenuItem A, FoodCategory B WHERE A.FoodCategoryID = B.ID;");
			while (set.next()) {
				itemID = set.getInt("ID");
				itemName = set.getString("Name");
				description = set.getString("Description");
				pictureString = set.getString("Picture");
				picture = new Image(null, new ThemeResource("images/"
						+ pictureString));
				picture.setWidth("100px");
				picture.setHeight("75px");
				itemPrice = set.getDouble("Price");
				foodCategory = set.getString("FoodCategory");
				MenuItem newItem = new MenuItem(itemID, itemName,
						description, picture, pictureString, itemPrice,
						foodCategory);
				container.addItem(newItem);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		close();
		return container;
	}

}
