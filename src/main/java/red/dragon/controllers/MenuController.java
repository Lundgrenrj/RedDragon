package red.dragon.controllers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.server.FileResource;
import com.vaadin.ui.Image;

import red.dragon.Exceptions.DuplicateItemException;
import red.dragon.Exceptions.ItemNotFoundException;
import red.dragon.ViewModels.MenuItemVM;
import red.dragon.controller.ControlUtil;
import red.dragon.controller.interfaces.IMenuAdmin;
import red.dragon.pojos.Menu;
import red.dragon.pojos.MenuItem;

public class MenuController implements IMenuAdmin {
	
	List<MenuItemVM> menuItemCache;
	List<String> foodCategoryCache;
	
	public MenuController() {
		init();
	}
	
	public List<MenuItemVM> getAllMenuItems() {
		return menuItemCache;
	}

	public boolean deleteMenuItem(int itemId) {
		boolean removed = false;
		for (MenuItemVM item : menuItemCache) {
			if (item.getId() == itemId) {
				menuItemCache.remove(item);
				removed = true;
				break;
			}
		}
		return removed;
	}
	
	public boolean addMenuItem(MenuItemVM item) {
		return menuItemCache.add(item);
	}
	
	private MenuItemVM find(int itemId) {
		for (MenuItemVM item : menuItemCache) {
			if (item.getId() == itemId) {
				return item;
			}
		}
		return null;
	}

	public void init() {
		menuItemCache = new ArrayList<>();
		MenuItemVM item;
		File file;
		
		try {
			item = new MenuItemVM();
			item.setId(46);
			item.setName("Egg Roll (2)");
			file = new File("c:/users/jason/pictures/reddragon/chop_suey.jpg");
			item.setPicture(read(file));
			item.setPrice(2.5);
			item.setFoodCategoryName("APPETIZERS");
			menuItemCache.add(item);
		} catch (IOException e) {
			e.printStackTrace();
		}
			
			
		try {
			item = new MenuItemVM();
			item.setId(71);
			item.setName("Jumbo Shrimps");
			file = new File("c:/users/jason/pictures/reddragon/chicken_brocolli.jpg");
			item.setPicture(read(file));
			item.setPrice(6.5);
			item.setFoodCategoryName("APPETIZERS");
			menuItemCache.add(item);
		} catch (IOException e) {
			e.printStackTrace();
		}
			
			
		try {
			item = new MenuItemVM();
			item.setId(72);
			item.setName("Paper-Wrapped Chicken");
			file = new File("c:/users/jason/pictures/reddragon/chop_suey.jpg");
			item.setPicture(read(file));
			item.setPrice(4.5);
			item.setFoodCategoryName("APPETIZERS");
			menuItemCache.add(item);
		} catch (IOException e) {
			e.printStackTrace();
		}
			
			
		try {
			item = new MenuItemVM();
			item.setId(73);
			item.setName("Pot Stickers (8)");
			file = new File("c:/users/jason/pictures/reddragon/chicken_brocolli.jpg");
			item.setPicture(read(file));
			item.setPrice(4.5);
			item.setFoodCategoryName("APPETIZERS");
			menuItemCache.add(item);
		} catch (IOException e) {
			e.printStackTrace();
		}
			
			
		try {
			item = new MenuItemVM();
			item.setId(74);
			item.setName("Fried Won Ton (10)");
			file = new File("c:/users/jason/pictures/reddragon/chop_suey.jpg");
			item.setPicture(read(file));
			item.setPrice(3.5);
			item.setFoodCategoryName("APPETIZERS");
			menuItemCache.add(item);
		} catch (IOException e) {
			e.printStackTrace();
		}
			
			
		try {
			item = new MenuItemVM();
			item.setId(75);
			item.setName("Crab & Cream Cheese Won Ton");
			file = new File("c:/users/jason/pictures/reddragon/chicken_brocolli.jpg");
			item.setPicture(read(file));
			item.setPrice(4.5);
			item.setFoodCategoryName("APPETIZERS");
			menuItemCache.add(item);
		} catch (IOException e) {
			e.printStackTrace();
		}
			
			
		try {
			item = new MenuItemVM();
			item.setId(76);
			item.setName("B. B. Q Pork");
			file = new File("c:/users/jason/pictures/reddragon/chop_suey.jpg");
			item.setPicture(read(file));
			item.setPrice(4.5);
			item.setFoodCategoryName("APPETIZERS");
			menuItemCache.add(item);
		} catch (IOException e) {
			e.printStackTrace();
		}
			
			
		try {
			item = new MenuItemVM();
			item.setId(77);
			item.setName("Egg Flower Soup");
			file = new File("c:/users/jason/pictures/reddragon/chicken_brocolli.jpg");
			item.setPicture(read(file));
			item.setPrice(4.5);
			item.setFoodCategoryName("SOUPS");
			menuItemCache.add(item);
		} catch (IOException e) {
			e.printStackTrace();
		}
			
			
		try {
			item = new MenuItemVM();
			item.setId(78);
			item.setName("Hot and Sour Soup");
			file = new File("c:/users/jason/pictures/reddragon/chop_suey.jpg");
			item.setPicture(read(file));
			item.setPrice(4.5);
			item.setFoodCategoryName("SOUPS");
			menuItemCache.add(item);
		} catch (IOException e) {
			e.printStackTrace();
		}
			
			
		try {
			item = new MenuItemVM();
			item.setId(79);
			item.setName("Won Ton Soup");
			file = new File("c:/users/jason/pictures/reddragon/chicken_brocolli.jpg");
			item.setPicture(read(file));
			item.setPrice(4.5);
			item.setFoodCategoryName("SOUPS");
			menuItemCache.add(item);
		} catch (IOException e) {
			e.printStackTrace();
		}
			
			
		try {
			item = new MenuItemVM();
			item.setId(80);
			item.setName("Yangchow Won Ton");
			file = new File("c:/users/jason/pictures/reddragon/chop_suey.jpg");
			item.setPicture(read(file));
			item.setPrice(4.99);
			item.setFoodCategoryName("SOUPS");
			menuItemCache.add(item);
		} catch (IOException e) {
			e.printStackTrace();
		}
			
			
		try {
			item = new MenuItemVM();
			item.setId(81);
			item.setName("Pork Chow Mein");
			file = new File("c:/users/jason/pictures/reddragon/chop_suey.jpg");
			item.setPicture(read(file));
			item.setPrice(7.25);
			item.setFoodCategoryName("CHOW MEIN");
			menuItemCache.add(item);
		} catch (IOException e) {
			e.printStackTrace();
		}
			
			
		try {
			item = new MenuItemVM();
			item.setId(82);
			item.setName("Beef Chew Mein");
			file = new File("c:/users/jason/pictures/reddragon/chop_suey.jpg");
			item.setPicture(read(file));
			item.setPrice(7.25);
			item.setFoodCategoryName("CHOW MEIN");
			menuItemCache.add(item);
		} catch (IOException e) {
			e.printStackTrace();
		}
			
			
		try {
			item = new MenuItemVM();
			item.setId(83);
			item.setName("Chicken Chow Mein");
			file = new File("c:/users/jason/pictures/reddragon/chop_suey.jpg");
			item.setPicture(read(file));
			item.setPrice(7.25);
			item.setFoodCategoryName("CHOW MEIN");
			menuItemCache.add(item);
		} catch (IOException e) {
			e.printStackTrace();
		}
			
			
		try {
			item = new MenuItemVM();
			item.setId(84);
			item.setName("Shrimp Chow Mein");
			file = new File("c:/users/jason/pictures/reddragon/chop_suey.jpg");
			item.setPicture(read(file));
			item.setPrice(7.25);
			item.setFoodCategoryName("CHOW MEIN");
			menuItemCache.add(item);
		} catch (IOException e) {
			e.printStackTrace();
		}
			
			
		try {
			item = new MenuItemVM();
			item.setId(85);
			item.setName("Vegetable Chow Mein");
			file = new File("c:/users/jason/pictures/reddragon/chop_suey.jpg");
			item.setPicture(read(file));
			item.setPrice(7.25);
			item.setFoodCategoryName("CHOW MEIN");
			menuItemCache.add(item);
		} catch (IOException e) {
			e.printStackTrace();
		}
			
			
		try {
			item = new MenuItemVM();
			item.setId(86);
			item.setName("Seafood Chow Mein");
			file = new File("c:/users/jason/pictures/reddragon/chop_suey.jpg");
			item.setPicture(read(file));
			item.setPrice(7.25);
			item.setFoodCategoryName("CHOW MEIN");
			menuItemCache.add(item);
		} catch (IOException e) {
			e.printStackTrace();
		}
			
			
		try {
			item = new MenuItemVM();
			item.setId(87);
			item.setName("Combination Fried Rice");
			file = new File("c:/users/jason/pictures/reddragon/chop_suey.jpg");
			item.setPicture(read(file));
			item.setPrice(5.95);
			item.setFoodCategoryName("FRIED RICE");
			menuItemCache.add(item);
		} catch (IOException e) {
			e.printStackTrace();
		}
			
			
		try {
			item = new MenuItemVM();
			item.setId(88);
			item.setName("Pork Fried Rice");
			file = new File("c:/users/jason/pictures/reddragon/chop_suey.jpg");
			item.setPicture(read(file));
			item.setPrice(5.95);
			item.setFoodCategoryName("FRIED RICE");
			menuItemCache.add(item);
		} catch (IOException e) {
			e.printStackTrace();
		}
			
			
		try {
			item = new MenuItemVM();
			item.setId(89);
			item.setName("Ham Fried Rice");
			file = new File("c:/users/jason/pictures/reddragon/chop_suey.jpg");
			item.setPicture(read(file));
			item.setPrice(4.65);
			item.setFoodCategoryName("FRIED RICE");
			menuItemCache.add(item);
		} catch (IOException e) {
			e.printStackTrace();
		}
			
			
		try {
			item = new MenuItemVM();
			item.setId(90);
			item.setName("Shrimp Fried Rice");
			file = new File("c:/users/jason/pictures/reddragon/chicken_brocolli.jpg");
			item.setPicture(read(file));
			item.setPrice(7.95);
			item.setFoodCategoryName("FRIED RICE");
			menuItemCache.add(item);
		} catch (IOException e) {
			e.printStackTrace();
		}
			
			
		try {
			item = new MenuItemVM();
			item.setId(91);
			item.setName("Chicken Fried Rice");
			file = new File("c:/users/jason/pictures/reddragon/chop_suey.jpg");
			item.setPicture(read(file));
			item.setPrice(5.95);
			item.setFoodCategoryName("FRIED RICE");
			menuItemCache.add(item);
		} catch (IOException e) {
			e.printStackTrace();
		}
			
			
		try {
			item = new MenuItemVM();
			item.setId(92);
			item.setName("Vegetable Fried Rice");
			file = new File("c:/users/jason/pictures/reddragon/chop_suey.jpg");
			item.setPicture(read(file));
			item.setPrice(5.95);
			item.setFoodCategoryName("FRIED RICE");
			menuItemCache.add(item);
		} catch (IOException e) {
			e.printStackTrace();
		}
			
			
		try {
			item = new MenuItemVM();
			item.setId(93);
			item.setName("Pork Noodles");
			file = new File("c:/users/jason/pictures/reddragon/chicken_brocolli.jpg");
			item.setPicture(read(file));
			item.setPrice(6.5);
			item.setFoodCategoryName("NOODLES");
			menuItemCache.add(item);
		} catch (IOException e) {
			e.printStackTrace();
		}
			
			
		try {
			item = new MenuItemVM();
			item.setId(94);
			item.setName("Beef Noodles");
			file = new File("c:/users/jason/pictures/reddragon/chop_suey.jpg");
			item.setPicture(read(file));
			item.setPrice(6.5);
			item.setFoodCategoryName("NOODLES");
			menuItemCache.add(item);
		} catch (IOException e) {
			e.printStackTrace();
		}
			
			
		try {
			item = new MenuItemVM();
			item.setId(95);
			item.setName("Fresh Chicken Noodles");
			file = new File("c:/users/jason/pictures/reddragon/chicken_brocolli.jpg");
			item.setPicture(read(file));
			item.setPrice(6.5);
			item.setFoodCategoryName("NOODLES");
			menuItemCache.add(item);
		} catch (IOException e) {
			e.printStackTrace();
		}
			
			
		try {
			item = new MenuItemVM();
			item.setId(96);
			item.setName("Shrimp Noodles");
			file = new File("c:/users/jason/pictures/reddragon/chop_suey.jpg");
			item.setPicture(read(file));
			item.setPrice(7.5);
			item.setFoodCategoryName("NOODLES");
			menuItemCache.add(item);
		} catch (IOException e) {
			e.printStackTrace();
		}
			
			
		try {
			item = new MenuItemVM();
			item.setId(97);
			item.setName("Beef Broccoli");
			file = new File("c:/users/jason/pictures/reddragon/chop_suey.jpg");
			item.setPicture(read(file));
			item.setPrice(5.99);
			item.setFoodCategoryName("COMBINATION");
			menuItemCache.add(item);
		} catch (IOException e) {
			e.printStackTrace();
		}
			
			
		try {
			item = new MenuItemVM();
			item.setId(98);
			item.setName("Beef Pea Pod");
			file = new File("c:/users/jason/pictures/reddragon/chicken_brocolli.jpg");
			item.setPicture(read(file));
			item.setPrice(5.99);
			item.setFoodCategoryName("COMBINATION");
			menuItemCache.add(item);
		} catch (IOException e) {
			e.printStackTrace();
		}
			
			
		try {
			item = new MenuItemVM();
			item.setId(99);
			item.setName("Cashew Chicken");
			file = new File("c:/users/jason/pictures/reddragon/chop_suey.jpg");
			item.setPicture(read(file));
			item.setPrice(5.99);
			item.setFoodCategoryName("COMBINATION");
			menuItemCache.add(item);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		foodCategoryCache = new ArrayList<>();
		
		foodCategoryCache.add("APPETIZERS");
		foodCategoryCache.add("BEEF DISHES");
		foodCategoryCache.add("BEVERAGES");
		foodCategoryCache.add("CHEF'S SUGGESTIONS");
		foodCategoryCache.add("CHICKEN DISHES");
		foodCategoryCache.add("CHOP SUEY");
		foodCategoryCache.add("CHOW MEIN");
		foodCategoryCache.add("COMBINATION");
		foodCategoryCache.add("EGG FOO YOUNG");
		foodCategoryCache.add("FRIED RICE");
		foodCategoryCache.add("HOT & SPICY");
		foodCategoryCache.add("HOUSE SPECIAL");
		foodCategoryCache.add("NOODLES");
		foodCategoryCache.add("SOUPS");
		foodCategoryCache.add("SWEET & SOUR");
		foodCategoryCache.add("LO MEIN");
	}
	
//	public MenuItemVM createMenuItem(MenuItemVM item) throws DuplicateItemException {
//		menuItemCache.add(item);
//		return item;
//	}

	@Override
	public int createMenuItem(MenuItemVM item) throws DuplicateItemException {
		menuItemCache.add(item);
		return item.getId();
	}

//	public MenuItemVM editMenuItem(MenuItemVM item) throws ItemNotFoundException,
//			DuplicateItemException {
//		MenuItemVM itemVM = find(item.getId());
//		itemVM.setName(item.getName());
//		itemVM.setFoodCategoryName(item.getFoodCategoryName());
//		itemVM.setPicture(item.getPicture());
//		itemVM.setPrice(item.getPrice());
//		return item;
//	}

	@Override
	public boolean editMenuItem(MenuItemVM item) throws ItemNotFoundException,
			DuplicateItemException {
		MenuItemVM itemVM = find(item.getId());
		itemVM.setName(item.getName());
		itemVM.setFoodCategoryName(item.getFoodCategoryName());
		itemVM.setPicture(item.getPicture());
		itemVM.setPrice(item.getPrice());
		return true;
	}

	@Override
	public boolean deleteMenuItem(MenuItemVM item) throws ItemNotFoundException {
		MenuItemVM itemVM = new MenuItemVM();
		itemVM.setId(item.getId());
		itemVM.setName(item.getName());
		itemVM.setPicture(item.getPicture());
		itemVM.setPrice(item.getPrice());
		return menuItemCache.remove(itemVM);
	}

	public boolean deleteMenuItem(MenuItem item) throws ItemNotFoundException {
		return deleteMenuItem(ControlUtil.getMenuItemVM(item));
	}

	@Override
	public int createMenu(Menu menu) throws DuplicateItemException {
		foodCategoryCache.add(menu.getName());
		return menu.getId();
	}

	@Override
	public boolean editMenu(Menu menu) throws ItemNotFoundException,
			DuplicateItemException {
		return foodCategoryCache.add(menu.getName());
	}

	@Override
	public boolean deleteMenu(Menu menu) throws ItemNotFoundException {
		boolean deleted = false;
		for (String item : foodCategoryCache) {
			if (item == menu.getName()) {
				foodCategoryCache.remove(item);
				deleted = true;
			}
		}
		return deleted;
	}
	
	private Image read(File file) throws IOException {

		Image image = new Image();
		image.setSource(new FileResource(file));
	    return image;
	}

	@Override
	public MenuItem getMenuItem(String name) throws ItemNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Menu getMenu(String name) throws ItemNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public red.dragon.ViewModels.MenuItemVM getMenuItemVM(String name)
			throws ItemNotFoundException {
		for (MenuItemVM vm : menuItemCache) {
			if (vm.getName() == name) {
				return vm;
			} else {
				throw new ItemNotFoundException();
			}
		}
		return null;
	}

	@Override
	public red.dragon.ViewModels.MenuItemVM getMenuItemVM(int id)
			throws ItemNotFoundException {
		return find(id);
	}

	@Override
	public List<red.dragon.ViewModels.MenuItemVM> getAllMenuItemVMs() {
		return menuItemCache;
	}

	@Override
	public String[] getAllFoodCategories() {
		String[] categories = new String[foodCategoryCache.size()];
		for (int i = 0; i < categories.length; i++){
			categories[i] = foodCategoryCache.get(i);
		}
		return categories;
	}
}
