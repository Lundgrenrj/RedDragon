package red.dragon.ViewModels;

import java.awt.image.BufferedImage;

import com.vaadin.ui.Image;

public class MenuItemVM {
	
	private int id = 0;
	private String name = null;
	private double price = 0;
	private Image picture = null;
	private String foodCategoryName = null;
	private String description = null;
	private BufferedImage itemPicture = null;

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

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
		if (picture != null) {
			picture.setWidth("100px");
			picture.setHeight("75px");
		}
		
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

	@Override
	public String toString()
	{
		return "MenuItemVM [id=" + id + ", name=" + name + ", price=" + price
				+ ", foodCategoryName=" + foodCategoryName + ", description="
				+ description + "]";
	}

	public BufferedImage getItemPicture() {
		return itemPicture;
	}

	public void setItemPicture(BufferedImage itemPicture) {
		this.itemPicture = itemPicture;
	}

	

}
