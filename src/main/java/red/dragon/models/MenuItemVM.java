package red.dragon.models;

import com.vaadin.ui.Image;

public class MenuItemVM {
	
	private int id;
	private String name;
	private double price;
	private Image picture;
	private String foodCategoryName;

	public int getId() {
		// TODO Auto-generated method stub
		return id;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	public void setName(String name) {
		// TODO Auto-generated method stub
		this.name = name;
	}

	public double getPrice() {
		// TODO Auto-generated method stub
		return price;
	}

	public void setPrice(double price) {
		// TODO Auto-generated method stub
		this.price = price;
	}

	public Image getPicture() {
		// TODO Auto-generated method stub
		return picture;
	}

	public void setPicture(Image picture) {
		// TODO Auto-generated method stub
		this.picture = picture;
	}

	public String getFoodCategoryName() {
		// TODO Auto-generated method stub
		return foodCategoryName;
	}

	public void setFoodCategoryName(String foodCategoryName) {
		// TODO Auto-generated method stub
		this.foodCategoryName = foodCategoryName;
	}

	public void setId(int id) {
		// TODO Auto-generated method stub
		this.id = id;
	}

}
