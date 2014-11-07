package red.dragon.pojos;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the UserGroup database table.
 * 
 */
@Entity
@NamedQuery(name = "UserGroup.findAll", query = "SELECT u FROM UserGroup u")
public class UserGroup implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    // bi-directional many-to-one association to User
    @OneToMany(mappedBy = "userGroup")
    private List<User> users;

    // bi-directional many-to-one association to Permission
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PermissionID")
    private Permission permission;

    public UserGroup() {
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

    public List<User> getUsers() {
	return this.users;
    }

    public void setUsers(List<User> users) {
	this.users = users;
    }

    public User addUser(User user) {
	getUsers().add(user);
	user.setUserGroup(this);

	return user;
    }

    public User removeUser(User user) {
	getUsers().remove(user);
	user.setUserGroup(null);

	return user;
    }

    public Permission getPermission() {
	return this.permission;
    }

    public void setPermission(Permission permission) {
	this.permission = permission;
    }

}