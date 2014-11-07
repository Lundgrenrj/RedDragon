package red.dragon.pojos;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;

/**
 * The persistent class for the Customer database table.
 * 
 */
@Entity
@NamedQuery(name = "Customer.findAll", query = "SELECT c FROM Customer c")
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String address;

    private String email;
    
    private String name;

    @Lob
    private byte[] password;

    private String phone;

    @Lob
    private byte[] salt;

    private String state;

    private String zip;

    // bi-directional many-to-one association to ActiveOrder
    @OneToMany(mappedBy = "customer")
    private List<ActiveOrder> activeOrders;

    // bi-directional many-to-one association to OrderHistory
    @OneToMany(mappedBy = "customer")
    private List<OrderHistory> OrderHistory;

    public Customer() {
    }

    public int getId() {
	return this.id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public String getAddress() {
	return this.address;
    }

    public void setAddress(String address) {
	this.address = address;
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

    public void setPassword(byte[] password) {
	this.password = password;
    }

    public String getPhone() {
	return this.phone;
    }

    public void setPhone(String phone) {
	this.phone = phone;
    }

    public byte[] getSalt() {
	return this.salt;
    }

    public void setSalt(byte[] salt) {
	this.salt = salt;
    }

    public String getState() {
	return this.state;
    }

    public void setState(String state) {
	this.state = state;
    }

    public String getZip() {
	return this.zip;
    }

    public void setZip(String zip) {
	this.zip = zip;
    }

    public List<ActiveOrder> getActiveOrders() {
	return this.activeOrders;
    }

    public void setActiveOrders(List<ActiveOrder> activeOrders) {
	this.activeOrders = activeOrders;
    }

    public ActiveOrder addActiveOrder(ActiveOrder activeOrder) {
	getActiveOrders().add(activeOrder);
	activeOrder.setCustomer(this);

	return activeOrder;
    }

    public ActiveOrder removeActiveOrder(ActiveOrder activeOrder) {
	getActiveOrders().remove(activeOrder);
	activeOrder.setCustomer(null);

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
	orderHistory.setCustomer(this);

	return orderHistory;
    }

    public OrderHistory removeOrderHistory(OrderHistory orderHistory) {
	getOrderHistory().remove(orderHistory);
	orderHistory.setCustomer(null);

	return orderHistory;
    }

}