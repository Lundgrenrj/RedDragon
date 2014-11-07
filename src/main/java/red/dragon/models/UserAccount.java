package red.dragon.models;

import java.io.Serializable;
import com.vaadin.ui.Image;

public class UserAccount implements Serializable {

	private static final long serialVersionUID = 1L;
	private int userId = 0;
	private String fullName = null;
	private int groupId = 0;
	private String groupName = null;
	private Image picture = null;
	private String email = null;
	
	
	public UserAccount(int userId, String fullName, int groupId, String groupName, Image picture, String email) {
		this.userId = userId;
		this.fullName = fullName;
		this.groupId = groupId;
		this.groupName = groupName;
		this.picture = picture;
		this.email = email;
		
	}


	public int getUserId() {
		return userId;
	}


	public void setUserId(int userId) {
		this.userId = userId;
	}


	public String getFullName() {
		return fullName;
	}


	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public int getGroupId() {
		return groupId;
	}


	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}


	public String getGroupName() {
		return groupName;
	}


	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}


	public Image getPicture() {
		return picture;
	}


	public void setPicture(Image picture) {
		this.picture = picture;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}
	
}
