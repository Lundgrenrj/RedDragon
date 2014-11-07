package red.dragon.pojos;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the User database table.
 * 
 */
@Entity
@NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String email;

    private String name;

    @Lob
    private byte[] password;

    @Lob
    private byte[] picture;
//    TODO change picture to vaadin image
    @Lob
    private byte[] pictureThumbnail;
//    TODO change picture to vaadin image
    @Lob
    private byte[] salt;

    // bi-directional many-to-one association to ActiveOrder
    @OneToMany(mappedBy = "userBean")
    private List<ActiveOrder> activeOrders;

    // bi-directional many-to-one association to OrderHistory
    @OneToMany(mappedBy = "user")
    private List<OrderHistory> OrderHistory;

    // bi-directional many-to-one association to UserGroup
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GroupID")
    private UserGroup userGroup;

    public User() {
    	id = -1;
    }

    public int getId() {
	return this.id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public String getEmail() {
	return this.email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public String getName() {
	return this.name;
    }

    public void setName(String name) {
	this.name = name;
    }


    public byte[] getPassword() {
	return this.password;
    }

    public void setPassword(String password)
    {
    	this.password = password.getBytes();
    }
    
    public void setPassword(byte[] password) {
	this.password = password;
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

    public byte[] getSalt() {
	return this.salt;
    }

    public void setSalt(byte[] salt) {
	this.salt = salt;
    }

    public List<ActiveOrder> getActiveOrders() {
	return this.activeOrders;
    }

    public void setActiveOrders(List<ActiveOrder> activeOrders) {
	this.activeOrders = activeOrders;
    }

    public ActiveOrder addActiveOrder(ActiveOrder activeOrder) {
	getActiveOrders().add(activeOrder);
	activeOrder.setUserBean(this);

	return activeOrder;
    }


    public ActiveOrder removeActiveOrder(ActiveOrder activeOrder) {
	getActiveOrders().remove(activeOrder);
	activeOrder.setUserBean(null);

	return activeOrder;
    }

    public List<OrderHistory> getOrderHistory() {
	return this.OrderHistory;
    }

    public void setOrderHistory(List<OrderHistory> OrderHistory) {
	this.OrderHistory = OrderHistory;
    }

    public OrderHistory addOrderHistory(OrderHistory orderHistory) {
	getOrderHistory().add(orderHistory);
	orderHistory.setUser(this);

	return orderHistory;
    }

    public OrderHistory removeOrderHistory(OrderHistory orderHistory) {
	getOrderHistory().remove(orderHistory);
	orderHistory.setUser(null);

	return orderHistory;
    }

    public UserGroup getUserGroup() {
	return this.userGroup;
    }

    public void setUserGroup(UserGroup userGroup) {
	this.userGroup = userGroup;
    }

    public User clone()
    {
    	User tmp = new User();
    	tmp.setActiveOrders(this.getActiveOrders());
    	tmp.setEmail(this.getEmail());
    	tmp.setId(this.getId());
    	tmp.setName(this.getName());
    	tmp.setOrderHistory(this.getOrderHistory());
    	tmp.setPassword(this.getPassword());
    	tmp.setPicture(this.getPicture());
    	tmp.setPictureThumbnail(this.getPictureThumbnail());
    	tmp.setSalt(this.getSalt());
    	tmp.setUserGroup(this.getUserGroup());
    	return tmp;
    }
    
	@Override
	public String toString()
	{
		String s = "User [id=" + id+"]";
		s=s+", email = " + email;
		s=s+", name=" + name;
		s=s+", password=" + password;	
		s=s+(activeOrders==null?", activeOrders = Null":", activeOrders size = " + activeOrders.size());
		s=s+(userGroup==null?", userGroup = Null":", userGroup =" + userGroup.getName());
		return s;
	}
	
}