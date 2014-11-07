package red.dragon.pojos;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the FoodCategory database table.
 * 
 */
@Entity
@NamedQuery(name = "FoodCategory.findAll", query = "SELECT f FROM FoodCategory f")
public class FoodCategory implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    // bi-directional many-to-one association to MenuItem
    @OneToMany(mappedBy = "foodCategory")
    private List<MenuItem> menuItems;

    public FoodCategory() {
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

    public List<MenuItem> getMenuItems() {
	return this.menuItems;
    }

    public void setMenuItems(List<MenuItem> menuItems) {
	this.menuItems = menuItems;
    }

    public MenuItem addMenuItem(MenuItem menuItem) {
	getMenuItems().add(menuItem);
	menuItem.setFoodCategory(this);

	return menuItem;
    }

    public MenuItem removeMenuItem(MenuItem menuItem) {
	getMenuItems().remove(menuItem);
	menuItem.setFoodCategory(null);

	return menuItem;
    }

}