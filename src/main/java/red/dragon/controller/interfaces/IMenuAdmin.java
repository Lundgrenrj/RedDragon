package red.dragon.controller.interfaces;

import java.util.List;

import red.dragon.Exceptions.DuplicateItemException;
import red.dragon.Exceptions.ItemNotFoundException;
import red.dragon.ViewModels.MenuItemVM;
import red.dragon.pojos.Menu;
import red.dragon.pojos.MenuItem;

public interface IMenuAdmin {

	public int createMenuItem(MenuItemVM item) throws DuplicateItemException;

	public boolean editMenuItem(MenuItemVM item) throws ItemNotFoundException, DuplicateItemException;

	public boolean deleteMenuItem(MenuItemVM item) throws ItemNotFoundException;
	
	public MenuItem getMenuItem(String name) throws ItemNotFoundException;
	

	public int createMenu(Menu menu) throws DuplicateItemException;

	public boolean editMenu(Menu menu) throws ItemNotFoundException, DuplicateItemException;

	public boolean deleteMenu(Menu menu) throws ItemNotFoundException;
	
	public Menu getMenu(String name)throws ItemNotFoundException;
	
	
	
	public MenuItemVM getMenuItemVM(String name)throws ItemNotFoundException;
	
	public MenuItemVM getMenuItemVM(int id)throws ItemNotFoundException;
	
	public List<MenuItemVM> getAllMenuItemVMs();

	public String[] getAllFoodCategories();
	
//	public boolean addMenuItemVM(MenuItemVM mivm)throws DuplicateItemException;
//	
//	public boolean updateMenuItemVM(MenuItemVM mivm)throws DuplicateItemException;
//	
//	public boolean deleteMenuItemVM(MenuItemVM mivm)throws ItemNotFoundException;
	
	
	
//	public MenuVM getMenuVM(String name)throws ItemNotFoundException;
//	
//	public MenuVM getMenuVM(int id)throws ItemNotFoundException;
//	
//	public List<MenuVM> getAllMenuVMs();
	
//	public boolean addMenuVM(MenuVM mivm)throws DuplicateItemException;
	
//	public boolean updateMenuVM(MenuVM mivm)throws DuplicateItemException;
	
//	public boolean deleteMenuVM(MenuVM mivm)throws ItemNotFoundException;
	
}
