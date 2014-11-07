package red.dragon.pojos;

import javax.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
* The persistent class for the ActiveOrder database table.
*/
@Entity
@NamedQuery(name = "ActiveOrder.findAll", query = "SELECT a FROM ActiveOrder a")
public class ActiveOrder implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int orderID;

    private double price;

    private String status;

    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    // bi-directional many-to-one association to Bundle
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BundleID")
    private Bundle bundle;

    // bi-directional many-to-one association to Customer
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CustomerID")
    private Customer customer;

    // bi-directional many-to-one association to User
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "User")
    private User userBean;

    // bi-directional many-to-one association to ActiveOrderDetail
    @OneToMany(mappedBy = "activeOrder")
    private List<ActiveOrderDetail> activeOrderDetails;

    // bi-directional many-to-many association to MenuItems
    @ManyToMany(mappedBy = "activeOrders")
    private List<MenuItem> menuItems;

    public ActiveOrder() 
    {
    	activeOrderDetails = new ArrayList<>();
        menuItems = new ArrayList<>();
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

    public Bundle getBundle() {
        return this.bundle;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public User getUserBean() {
        return this.userBean;
    }

    public void setUserBean(User userBean) {
        this.userBean = userBean;
    }

    public List<ActiveOrderDetail> getActiveOrderDetails() {
        return this.activeOrderDetails;
    }

    public void setActiveOrderDetails(List<ActiveOrderDetail> activeOrderDetails) {
        this.activeOrderDetails = activeOrderDetails;
    }

    public List<MenuItem> getMenuItems() {
        return this.menuItems;
    }

    public void setMenuItems(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    @Override
    public String toString() {
        //TODO NEED TO FORMAT THE TIMESTAMP TO WHATEVER DATE FORMAT IS NEEDED.
        return "ActiveOrder [orderID=" + orderID + ", price=" + price
                + ", status=" + status + ", timestamp=" + timestamp
                + ", bundle=" + bundle.getName() + ", Customer=" + customer.getName()
                + ", Employee=" + userBean.getName() + "]";

    }

    public ActiveOrderDetail addActiveOrderDetail(
            ActiveOrderDetail activeOrderDetail) {
        getActiveOrderDetails().add(activeOrderDetail);
        activeOrderDetail.setActiveOrder(this);

        return activeOrderDetail;
    }

    public ActiveOrderDetail removeActiveOrderDetail(
            ActiveOrderDetail activeOrderDetail) {
        getActiveOrderDetails().remove(activeOrderDetail);
        activeOrderDetail.setActiveOrder(null);

        return activeOrderDetail;
    }

}