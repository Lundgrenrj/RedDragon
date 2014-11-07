package red.dragon.pojos;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the OrderHistoryDetails database table.
 * 
 */
@Entity
@Table(name = "OrderHistoryDetails")
@NamedQuery(name = "OrderHistoryDetail.findAll", query = "SELECT o FROM OrderHistoryDetail o")
public class OrderHistoryDetail implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int orderDetailsID;

    private int itemSequence;

    private String specialInstruction;

    // bi-directional many-to-one association to MenuItem
	@Column(nullable=false, length=50)
	private String menuItemName;

    // bi-directional many-to-one association to OrderHistory
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OrderID")
    private OrderHistory orderHistory;

    public OrderHistoryDetail() {
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

    public String getMenuItem() {
	return this.menuItemName;
    }

    public void setMenuItem(String menuItemName) {
	this.menuItemName = menuItemName;
    }

    public OrderHistory getOrderHistory() {
	return this.orderHistory;
    }

    public void setOrderHistory(OrderHistory orderHistory) {
	this.orderHistory = orderHistory;
    }

}