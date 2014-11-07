package red.dragon.controllers.api;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import red.dragon.models.UserAccount;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FileResource;
import com.vaadin.ui.Image;

public class UserAccountApi extends AbstractApi {
	Connection conn;
	
	public UserAccountApi() {
		super();
	}
	
	
	
	public Image getUserImage(int userId) {
		Image userImage = new Image();

		ResultSet set = null;

		try {

			set = query("SELECT Picture FROM User WHERE ID =" + userId +";");
			while (set.next()) {
				String picName = String.valueOf(userId);
				File image = new File(picName + ".jpg");
				
                FileOutputStream fos = null;
				try {
					fos = new FileOutputStream(image);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
 
                byte[] buffer = new byte[256];
 
                // Get the binary stream of our BLOB data
                InputStream is = set.getBinaryStream("Picture");
                while (is.read(buffer) > 0) {
                    fos.write(buffer);
                }
				fos.write(buffer);
                fos.close();

                userImage.setSource(new FileResource(image));
//                userImage.setWidth("100px");
//                userImage.setHeight("75px");

			}

		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
		close();
		return userImage;
	}
	
	public BeanItemContainer<UserAccount> getUserAccountList(
			BeanItemContainer<UserAccount> container) {

		container.removeAllItems();
		int userId = 0;
		String fullName = null;
		int groupId = 0;
		String groupName = null;
		Image picture = null;
		String email = null;
		ResultSet set = null;

		try {

			set = query("SELECT A.ID AS ID, A.Name AS FullName, A.Picture AS Picture, A.Email As Email, B.ID AS GroupID, B.Name AS GroupName FROM User A, UserGroup B WHERE A.GroupID = B.ID;");
			while (set.next()) {
				userId = set.getInt("ID");
				fullName = set.getString("FullName");
				groupId = set.getInt("GroupID");
				groupName = set.getString("GroupName");
				email = set.getString("Email");

				String picName = String.valueOf(userId);
				File image = new File(picName + ".jpg");
				
                FileOutputStream fos = null;
				try {
					fos = new FileOutputStream(image);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
 
                byte[] buffer = new byte[256];
 
                // Get the binary stream of our BLOB data
                InputStream is = set.getBinaryStream("Picture");
                while (is.read(buffer) > 0) {
                    fos.write(buffer);
                }
				fos.write(buffer);
                fos.close();

                picture = new Image();
                picture.setSource(new FileResource(image));
                picture.setWidth("100px");
				picture.setHeight("75px");

				UserAccount newItem = new UserAccount(userId, fullName,
						groupId, groupName, picture, email);
				container.addItem(newItem);
			}

		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
		close();
		return container;
	}

	public int getUserGroupsId(String group) {
		int ID = 0;
		try{
			ResultSet res = query("SELECT ID FROM UserGroup WHERE Name = '" + group + "'");
			while (res.next()) {
				ID = (res.getInt("ID"));
			}
		} catch (Exception e){
			
		}
		return ID;
	}
	
	public List<String> getUserGroups() {
		List<String> userGroups = new ArrayList<String>();
		try {
			ResultSet res = query("SELECT DISTINCT name FROM UserGroup ORDER BY name");
			while (res.next()) {
				userGroups.add(res.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		close();
		return userGroups;
	}
	

}
