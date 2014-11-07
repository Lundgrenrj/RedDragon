package red.dragon.pojos;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the OrderHistory database table.
 * 
 */
@Entity
@NamedQuery(name = "OrderHistory.findAll", query = "SELECT o FROM OrderHistory o")
public class OrderHistory implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int orderID;

    private double price;

    private String status;

    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    // bi-directional many-to-one association to Customer
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CustomerID")
    private Customer customer;

    // bi-directional many-to-one association to User
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserID")
    private User user;

    // bi-directional many-to-one association to OrderHistoryDetail
    @OneToMany(mappedBy = "orderHistory")
    private List<OrderHistoryDetail> orderHistoryDetails;

    public OrderHistory() {
    }

    public int getOrderID() {
	return this.orderID;
    }

    public void setOrderID(int orderID) {
	this.orderID = orderID;
    }

    public double getPrice() {
	return this.price;
    }

    public void setPrice(double price) {
	this.price = price;
    }

    public String getStatus() {
	return this.status;
    }

    public void setStatus(String status) {
	this.status = status;
    }

    public Date getTimestamp() {
	return this.timestamp;
    }

    public void setTimestamp(Date timestamp) {
	this.timestamp = timestamp;
    }

    public Customer getCustomer() {
	return this.customer;
    }

    public void setCustomer(Customer customer) {
	this.customer = customer;
    }

    public User getUser() {
	return this.user;
    }

    public void setUser(User user) {
	this.user = user;
    }

    public List<OrderHistoryDetail> getOrderHistoryDetails() {
	return this.orderHistoryDetails;
    }

    public void setOrderHistoryDetails(
	    List<OrderHistoryDetail> orderHistoryDetails) {
	this.orderHistoryDetails = orderHistoryDetails;
    }

    public OrderHistoryDetail addOrderHistoryDetail(
	    OrderHistoryDetail orderHistoryDetail) {
	getOrderHistoryDetails().add(orderHistoryDetail);
	orderHistoryDetail.setOrderHistory(this);

	return orderHistoryDetail;
    }

    public OrderHistoryDetail removeOrderHistoryDetail(
	    OrderHistoryDetail orderHistoryDetail) {
	getOrderHistoryDetails().remove(orderHistoryDetail);
	orderHistoryDetail.setOrderHistory(null);

	return orderHistoryDetail;
    }

}