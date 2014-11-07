package red.dragon.pojos;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the Bundle database table.
 * 
 */
@Entity
@NamedQuery(name = "Bundle.findAll", query = "SELECT b FROM Bundle b")
public class Bundle implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    private double price;

    // bi-directional many-to-one association to ActiveOrder
    @OneToMany(mappedBy = "bundle")
    private List<ActiveOrder> activeOrders;

    // bi-directional many-to-many association to MenuItem
    @ManyToMany(mappedBy = "bundles")
    private List<MenuItem> menuItems;

    public Bundle() {
    }

    public int getId() {
	return this.id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public String getName() {
	return this.name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public double getPrice() {
	return this.price;
    }

    public void setPrice(double price) {
	this.price = price;
    }

    public List<ActiveOrder> getActiveOrders() {
	return this.activeOrders;
    }

    public void setActiveOrders(List<ActiveOrder> activeOrders) {
	this.activeOrders = activeOrders;
    }

    public ActiveOrder addActiveOrder(ActiveOrder activeOrder) {
	getActiveOrders().add(activeOrder);
	activeOrder.setBundle(this);

	return activeOrder;
    }

    public ActiveOrder removeActiveOrder(ActiveOrder activeOrder) {
	getActiveOrders().remove(activeOrder);
	activeOrder.setBundle(null);

	return activeOrder;
    }

    public List<MenuItem> getMenuItems() {
	return this.menuItems;
    }

    public void setMenuItems(List<MenuItem> menuItems) {
	this.menuItems = menuItems;
    }

	@Override
	public String toString()
	{
		return "Bundle [id=" + id + ", name=" + name + ", price=" + price + "]";
	}

}