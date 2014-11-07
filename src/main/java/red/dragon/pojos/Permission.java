package red.dragon.pojos;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the Permission database table.
 * 
 */
@Entity
@NamedQuery(name = "Permission.findAll", query = "SELECT p FROM Permission p")
public class Permission implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    // bi-directional many-to-one association to UserGroup
    @OneToMany(mappedBy = "permission")
    private List<UserGroup> userGroups;

    public Permission() {
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

    public List<UserGroup> getUserGroups() {
	return this.userGroups;
    }

    public void setUserGroups(List<UserGroup> userGroups) {
	this.userGroups = userGroups;
    }

    public UserGroup addUserGroup(UserGroup userGroup) {
	getUserGroups().add(userGroup);
	userGroup.setPermission(this);

	return userGroup;
    }

    public UserGroup removeUserGroup(UserGroup userGroup) {
	getUserGroups().remove(userGroup);
	userGroup.setPermission(null);

	return userGroup;
    }

}