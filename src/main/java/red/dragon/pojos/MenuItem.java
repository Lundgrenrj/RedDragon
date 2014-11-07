package red.dragon.pojos;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the MenuItem database table.
 * 
 */
@Entity
@NamedQuery(name = "MenuItem.findAll", query = "SELECT m FROM MenuItem m")
public class MenuItem implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String description;

    private String name;

    @Lob
    private byte[] picture;

    @Lob
    private byte[] pictureThumbnail;

    private double price;

    // bi-directional many-to-one association to ActiveOrderDetail
    @OneToMany(mappedBy = "menuItem")
    private List<ActiveOrderDetail> activeOrderDetails;

    // bi-directional many-to-many association to Bundle
    @ManyToMany
    @JoinTable(name = "BundleManager", joinColumns = { @JoinColumn(name = "MenuItem_ID") }, inverseJoinColumns = { @JoinColumn(name = "Bundle_ID") })
    private List<Bundle> bundles;

    // bi-directional many-to-one association to FoodCategory
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FoodCategoryID")
    private FoodCategory foodCategory;

    // bi-directional many-to-many association to Menu
    @ManyToMany
    @JoinTable(name = "MenuManager", joinColumns = { @JoinColumn(name = "MenuItem_ID") }, inverseJoinColumns = { @JoinColumn(name = "Menu_ID") })
    private List<Menu> menus;

    // bi-directional many-to-one association to OrderHistoryDetail
    //@OneToMany(mappedBy = "menuItem")
    //private List<OrderHistoryDetail> orderHistoryDetails;

    // bi-directional many-to-many association to ActiveOrders
    @ManyToMany
    @JoinTable(name = "ActiveOrderDetails", joinColumns = { @JoinColumn(name = "MenuItemID") }, inverseJoinColumns = { @JoinColumn(name = "orderID") })
    private List<ActiveOrder> activeOrders;

    public MenuItem() {
    }

    public int getId() {
	return this.id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public String getDescription() {
	return this.description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    public String getName() {
	return this.name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public byte[] getPicture() {
	return this.picture;
    }

    public void setPicture(byte[] picture) {
	this.picture = picture;
    }

    public byte[] getPictureThumbnail() {
	return this.pictureThumbnail;
    }

    public void setPictureThumbnail(byte[] pictureThumbnail) {
	this.pictureThumbnail = pictureThumbnail;
    }

    public double getPrice() {
	return this.price;
    }

    public void setPrice(double price) {
	this.price = price;
    }

    public List<ActiveOrderDetail> getActiveOrderDetails() {
	return this.activeOrderDetails;
    }

    public void setActiveOrderDetails(List<ActiveOrderDetail> activeOrderDetails) {
	this.activeOrderDetails = activeOrderDetails;
    }

    public List<ActiveOrder> getActiveOrders() {
	return this.activeOrders;
    }

    public void setActiveOrders(List<ActiveOrder> activeOrders) {
	this.activeOrders = activeOrders;
    }

    public ActiveOrderDetail addActiveOrderDetail(
	    ActiveOrderDetail activeOrderDetail) {
	getActiveOrderDetails().add(activeOrderDetail);
	activeOrderDetail.setMenuItem(this);

	return activeOrderDetail;
    }

    public ActiveOrderDetail removeActiveOrderDetail(
	    ActiveOrderDetail activeOrderDetail) {
	getActiveOrderDetails().remove(activeOrderDetail);
	activeOrderDetail.setMenuItem(null);

	return activeOrderDetail;
    }

    public List<Bundle> getBundles() {
	return this.bundles;
    }

    public void setBundles(List<Bundle> bundles) {
	this.bundles = bundles;
    }

    public FoodCategory getFoodCategory() {
	return this.foodCategory;
    }

    public void setFoodCategory(FoodCategory foodCategory) {
	this.foodCategory = foodCategory;
    }

    public List<Menu> getMenus() {
	return this.menus;
    }

    public void setMenus(List<Menu> menus) {
	this.menus = menus;
    }

	@Override
	public String toString()
	{
		return "MenuItem [id=" + id + ", description=" + description
				+ ", name=" + name + ", price="	+ price + ", foodCategory="
				+ foodCategory.getName()+"]";
	}

}