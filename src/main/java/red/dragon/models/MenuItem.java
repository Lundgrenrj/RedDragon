package red.dragon.models;

import java.io.Serializable;

import com.vaadin.ui.Image;

public class MenuItem implements Serializable
{

	private static final long serialVersionUID = 1L;
	private int itemId;
	private String itemName;
	private String description;
	private Image picture;
	private String pictureString;
	private double price;
	private String foodCategory;

	public MenuItem(int itemId, String itemName,
			String description, Image picture, String pictureString,
			double price, String foodCategory)
	{
		this.itemId = itemId;
		this.itemName = itemName;
		this.description = description;
		this.picture = picture;
		this.pictureString = pictureString;
		this.price = price;
		this.foodCategory = foodCategory;
	}

	public int getItemId()
	{
		return itemId;
	}

	public String getItemName()
	{
		return itemName;
	}

	public String getDescription()
	{
		return description;
	}

	public Image getPicture()
	{
		return picture;
	}

	public String getPictureString()
	{
		return pictureString;
	}

	public double getPrice()
	{
		return price;
	}

	public String getFoodCategory()
	{
		return foodCategory;
	}

	public void setItemId(int itemId)
	{
		this.itemId = itemId;
	}

	public void setItemName(String itemName)
	{
		this.itemName = itemName;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public void setPicture(Image picture)
	{
		this.picture = picture;
	}

	public void setPictureString(String pictureString)
	{
		this.pictureString = pictureString;
	}

	public void setPrice(double price)
	{
		this.price = price;
	}

	public void setFoodCategort(String foodCategory)
	{
		this.foodCategory = foodCategory;
	}

}
