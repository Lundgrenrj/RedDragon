package red.dragon.pojos;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the ActiveOrderDetails database table.
 * 
 */
@Entity
@Table(name = "ActiveOrderDetails")
@NamedQuery(name = "ActiveOrderDetail.findAll", query = "SELECT a FROM ActiveOrderDetail a")
public class ActiveOrderDetail implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int orderDetailsID;

    private int itemSequence;

    private String specialInstruction;

    // bi-directional many-to-one association to ActiveOrder
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OrderID")
    private ActiveOrder activeOrder;

    // bi-directional many-to-one association to MenuItem
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MenuItemID")
    private MenuItem menuItem;

    public ActiveOrderDetail() {
    }

    public int getOrderDetailsID() {
	return this.orderDetailsID;
    }

    public void setOrderDetailsID(int orderDetailsID) {
	this.orderDetailsID = orderDetailsID;
    }

    public int getItemSequence() {
	return this.itemSequence;
    }

    public void setItemSequence(int itemSequence) {
	this.itemSequence = itemSequence;
    }

    public String getSpecialInstruction() {
	return this.specialInstruction;
    }

    public void setSpecialInstruction(String specialInstruction) {
	this.specialInstruction = specialInstruction;
    }

    public ActiveOrder getActiveOrder() {
	return this.activeOrder;
    }

    public void setActiveOrder(ActiveOrder activeOrder) {
	this.activeOrder = activeOrder;
    }

    public MenuItem getMenuItem() {
	return this.menuItem;
    }

    public void setMenuItem(MenuItem menuItem) {
	this.menuItem = menuItem;
    }

}