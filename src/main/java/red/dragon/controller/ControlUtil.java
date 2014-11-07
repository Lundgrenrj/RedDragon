package red.dragon.controller;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.imageio.ImageIO;

import red.dragon.ViewModels.MenuItemVM;
import red.dragon.ViewModels.UserAccountVM;
import red.dragon.dao.FoodCategoryDao;
import red.dragon.pojos.ActiveOrder;
import red.dragon.pojos.Customer;
import red.dragon.pojos.FoodCategory;
import red.dragon.pojos.Menu;
import red.dragon.pojos.MenuItem;
import red.dragon.pojos.OrderHistory;
import red.dragon.pojos.User;
import red.dragon.pojos.UserGroup;

import com.vaadin.ui.Image;
import com.vaadin.ui.Notification;
import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamResource.StreamSource;

public class ControlUtil
{

	// Pass in image byte[] array and return a Vaadin Image
	public static Image byteArrayToImage(final byte[] byteArray,
			String imageName)
	{
		if (byteArray != null)
			System.out.println("Got " + byteArray.length + " bytes!");
		// From com.vaadin.server.StreamResource.StreamSource
		StreamSource imageResourceStream = new StreamSource()
		{
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			// From java.io.InputStream
			public InputStream getStream()
			{
				// From java.io.ByteArrayInputStream.ByteArrayInputStream(byte[]
				// buf)
				return new ByteArrayInputStream(byteArray);
			}
		};
		StreamResource imageResource = new StreamResource(imageResourceStream,
				imageName);
		Image image = new Image(imageName, imageResource);
		return image;
	}
	
	//Converts from image to byte[] for storage in the database.
	public static byte[] imageToBytes(BufferedImage img)
	{
		if (img != null) {
			byte[] imageInByte = null;
			try {
				// convert BufferedImage to byte array
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ImageIO.write(img, "jpg", baos);
				baos.flush();
				imageInByte = baos.toByteArray();
				baos.close();
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return imageInByte;
		} else {
			return null;
		}
	}
	
	

	public static java.sql.Date getDate(String dateTime) throws ParseException
	{
		DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		java.util.Date d = formatter.parse(dateTime);
		java.sql.Date sqldate = new java.sql.Date(d.getTime());
		return sqldate;
	}

	/*
	 * Converts the ActiveOrder object into a OrderHistory object
	 */
	public static OrderHistory convert(ActiveOrder object, String status)
	{
		OrderHistory orhist = new OrderHistory();
		Customer cust = new Customer();
		cust.setId(object.getCustomer().getId());
		orhist.setCustomer(cust);
		orhist.setOrderID(object.getOrderID());
		orhist.setStatus(status);
		orhist.setTimestamp(object.getTimestamp());
		orhist.setPrice(object.getPrice());
		orhist.setUser(object.getUserBean());
		return orhist;
	}

	/*
	 * Generates a salt to be used with password hashing. *Note only to be used
	 * for salts stored on the server.*
	 */
	public static final byte[] generateSalt(int length)
	{
		SecureRandom random;
		try
		{
			random = SecureRandom.getInstance("SHA1PRNG", "SUN");
		}
		catch (NoSuchAlgorithmException | NoSuchProviderException e)
		{
			System.out.println(e.getMessage());
			random = new SecureRandom();
		}
		byte[] salt = new byte[length];
		random.nextBytes(salt);
		return salt;
	}

	// Generates a password hash with the parameters provided, current
	// difficulty is 1000 cycles, and length is 64 bytes.
	public static final byte[] generatePasswordHash(String password, byte[] salt)
			throws NoSuchAlgorithmException, InvalidKeySpecException
	{
		PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 1000,
				128);
		SecretKeyFactory skf = SecretKeyFactory
				.getInstance("PBKDF2WithHmacSHA1");
		byte[] b = skf.generateSecret(spec).getEncoded();
		spec.clearPassword();
		return b;
	}

	// Merges the new user into the original user, replacing only fields in
	// original that contain data in newU.
	public static User merge(User original, User newU)
	{
		if (newU.getActiveOrders() != null && newU.getActiveOrders().size() > 0)
		{
			original.setActiveOrders(newU.getActiveOrders());
		}
		if (newU.getEmail() != null && !newU.getEmail().isEmpty())
		{
			original.setEmail(newU.getEmail());
		}
		if (newU.getName() != null && !newU.getName().isEmpty())
		{
			original.setName(newU.getName());
		}
		if (newU.getOrderHistory() != null
				&& newU.getOrderHistory().size() != 0)
		{
			original.setOrderHistory(newU.getOrderHistory());
		}
		if (newU.getPassword() != null && newU.getPassword().length > 0)
		{
			byte[] b = ControlUtil.generateSalt(30);
			String password = new String(newU.getPassword());
			byte[] by = null;
			try {
				by = ControlUtil.generatePasswordHash(password, b);
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidKeySpecException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			original.setSalt(b);
			original.setPassword(by);
		}
		if (newU.getPicture() != null && newU.getPicture().length > 0)
		{
			original.setPicture(newU.getPicture());
		}
		if (newU.getPictureThumbnail() != null
				&& newU.getPictureThumbnail().length > 0)
		{
			original.setPictureThumbnail(newU.getPictureThumbnail());
		}
		if (newU.getSalt() != null && newU.getSalt().length > 0)
		{
			System.out.println("Original Salt Length = " + newU.getSalt());
			original.setSalt(newU.getSalt());
		}
		if (newU.getUserGroup() != null)
		{
			original.setUserGroup(newU.getUserGroup());
		}
		if (newU.getId() > -1)
		{
			original.setId(newU.getId());
		}
		return original;
	}

	public static UserGroup merge(UserGroup original, UserGroup newG)
	{
		if (newG.getId() > -1)
		{
			original.setId(newG.getId());
		}
		if (newG.getName() != null && !newG.getName().isEmpty())
		{
			original.setName(newG.getName());
		}
		if (newG.getPermission() != null)
		{
			original.setPermission(newG.getPermission());
		}
		if (newG.getUsers() != null && newG.getUsers().size() > 0)
		{
			original.setUsers(newG.getUsers());
		}
		return original;
	}

	public static Menu merge(Menu original, Menu newM)
	{
		if (newM.getId() > 0)
		{
			original.setId(newM.getId());
		}
		if (newM.getMenuItems() != null)
		{
			original.setMenuItems(newM.getMenuItems());
		}
		if (newM.getName() != null && !newM.getName().isEmpty())
		{
			original.setName(newM.getName());
		}
		if (newM.getPicture() != null && newM.getPicture().length > 0)
		{
			original.setPicture(newM.getPicture());
		}
		if (newM.getPictureThumbnail() != null
				&& newM.getPictureThumbnail().length > 0)
		{
			original.setPictureThumbnail(newM.getPictureThumbnail());
		}
		return original;
	}

	public static MenuItem merge(MenuItem original, MenuItem newM)
	{
		if (newM.getId() > 0)
		{
			original.setId(newM.getId());
		}
		if (newM.getActiveOrderDetails() != null)
		{
			original.setActiveOrderDetails(newM.getActiveOrderDetails());
		}
		if (newM.getActiveOrders() != null)
		{
			original.setActiveOrders(newM.getActiveOrders());
		}
		if (newM.getBundles() != null)
		{
			original.setBundles(newM.getBundles());
		}
		if (newM.getDescription() != null && !newM.getDescription().isEmpty())
		{
			original.setDescription(newM.getDescription());
		}
		if (newM.getFoodCategory() != null)
		{
			original.setFoodCategory(newM.getFoodCategory());
		}
		if (newM.getMenus() != null)
		{
			original.setMenus(newM.getMenus());
		}
		if (newM.getName() != null && !newM.getName().isEmpty())
		{
			original.setName(newM.getName());
		}
		if (newM.getPicture() != null && newM.getPicture().length > 0)
		{
			original.setPicture(newM.getPicture());
		}
		if (newM.getPictureThumbnail() != null
				&& newM.getPictureThumbnail().length > 0)
		{
			original.setPictureThumbnail(newM.getPictureThumbnail());
		}
		if (newM.getPrice() >= 0)
		{
			original.setPrice(newM.getPrice());
		}
		return original;
	}

	public static MenuItemVM getMenuItemVM(MenuItem menuItem)
	{
		MenuItemVM vm = new MenuItemVM();
		if (menuItem == null) { return null; }
		vm.setDescription(menuItem.getDescription());
		vm.setFoodCategoryName(menuItem.getFoodCategory().getName());
		vm.setId(menuItem.getId());
		vm.setName(menuItem.getName());
		vm.setPicture(ControlUtil.byteArrayToImage(menuItem.getPicture(),
				menuItem.getName()));
		vm.setPrice(menuItem.getPrice());
		return vm;
	}

	public static List<MenuItemVM> getMenuItemVMs(List<MenuItem> items)
	{
		if (items == null) { return null; }
		List<MenuItemVM> tmp = new ArrayList<MenuItemVM>(items.size());

		for (int c = 0; c < items.size(); c++)
		{
			tmp.add(ControlUtil.getMenuItemVM(items.get(c)));
		}
		return tmp;
	}
	
	public static MenuItem getMenuItem(MenuItemVM menuItemVM) {
		if (menuItemVM == null)
			return null;
		try {
			FoodCategoryDao fdc = new FoodCategoryDao();
			MenuItem item = new MenuItem();
			item.setId(menuItemVM.getId());
			item.setDescription(menuItemVM.getDescription());
			item.setFoodCategory(fdc.getByName(menuItemVM.getFoodCategoryName()));
			item.setName(menuItemVM.getName());
			item.setPicture(ControlUtil.imageToBytes(menuItemVM.getItemPicture()));
			item.setPrice(menuItemVM.getPrice());
			return item;
		} catch (NullPointerException e) {
			e.printStackTrace();
			return null;
		}
	}

//	Converts a user to a UserAccountVM
	public static UserAccountVM getUserAccountVM(User tmp)
	{
		UserAccountVM usr = new UserAccountVM();
		if (tmp != null)
		{
			usr.setEmail(tmp.getEmail());
			usr.setUserName(tmp.getName());
			usr.setGroupId(tmp.getUserGroup().getId());
			usr.setGroupName(tmp.getUserGroup().getName());
			usr.setPicture(ControlUtil.byteArrayToImage(
			tmp.getPicture(), tmp.getName()));
			usr.setUserId(tmp.getId());
			return usr;
		}
		else
		{
			return null;
		}
	}

	//Converts a list of users to a list of UserAccountVM's
	public static List<UserAccountVM> getAllUserAccountVMs(List<User> items)
	{
		if (items == null) { return null; }
		List<UserAccountVM> tmp = new ArrayList<UserAccountVM>(items.size());

		for (int c = 0; c < items.size(); c++)
		{
			tmp.add(ControlUtil.getUserAccountVM(items.get(c)));
		}
		return tmp;
		
	}
	
	public static User convertUserAccountVM(UserAccountVM usr)
	{
		User u = new User();
		u.setEmail(usr.getEmail());
		u.setName(usr.getUserName());
		
		UserGroup ug = new UserGroup();
		ug.setId(usr.getGroupId());
		ug.setName(usr.getGroupName());
		u.setUserGroup(ug);
//		u.setPicture(ControlUtil.imageToBytes(usr.getPicture()));
//		u.setPictureThumbnail(ControlUtil.imageToBytes(usr.getPicture()));
		u.setId(usr.getUserId());
		return u;
	}
	
}
