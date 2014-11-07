package red.dragon.pojos;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the Menu database table.
 * 
 */
@Entity
@NamedQuery(name = "Menu.findAll", query = "SELECT m FROM Menu m")
public class Menu implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    @Lob
    private byte[] picture;

    @Lob
    private byte[] pictureThumbnail;

    // bi-directional many-to-many association to MenuItem
    @ManyToMany(mappedBy = "menus")
    private List<MenuItem> menuItems;


    public Menu() {
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

    public List<MenuItem> getMenuItems() {
	return this.menuItems;
    }

    public void setMenuItems(List<MenuItem> menuItems) {
	this.menuItems = menuItems;
    }

	@Override
	public String toString()
	{
		return "Menu [id=" + id + ", name=" + name + "]";
	}

}