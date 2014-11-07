package red.dragon.ViewModels;

import java.io.Serializable;
import com.vaadin.ui.Image;

public class UserAccountVM implements Serializable {

	private static final long serialVersionUID = 1L;
	private int userId = 0;
	private String userName = null;
	private int groupId = 0;
	private String groupName = null;
	private Image picture = null;
	private String email = null;
	
	public UserAccountVM()
	{}
	
	public UserAccountVM(int userId, String userName, int groupId, String groupName, Image picture, String email) {
		this.userId = userId;
		this.userName = userName;
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


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
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
		if (picture != null) {
			picture.setWidth("100px");
			picture.setHeight("75px");
		}
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
