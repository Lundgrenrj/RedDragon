package red.dragon.pojos;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the ContactInfo database table.
 * 
 */
@Entity
@NamedQuery(name = "ContactInfo.findAll", query = "SELECT c FROM ContactInfo c")
public class ContactInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String contact;

    private String name;

    public ContactInfo() {
    }

    public int getId() {
	return this.id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public String getContact() {
	return this.contact;
    }

    public void setContact(String contact) {
	this.contact = contact;
    }

    public String getName() {
	return this.name;
    }

    public void setName(String name) {
	this.name = name;
    }

}