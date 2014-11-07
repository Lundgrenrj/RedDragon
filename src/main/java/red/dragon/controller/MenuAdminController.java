package red.dragon.controller;

import java.util.List;

import red.dragon.Exceptions.DuplicateItemException;
import red.dragon.Exceptions.ItemNotFoundException;
import red.dragon.ViewModels.MenuItemVM;
import red.dragon.controller.interfaces.IMenuAdmin;
import red.dragon.dao.FoodCategoryDao;
import red.dragon.dao.MenuDao;
import red.dragon.dao.MenuItemDao;
import red.dragon.pojos.FoodCategory;
import red.dragon.pojos.Menu;
import red.dragon.pojos.MenuItem;

public class MenuAdminController implements IMenuAdmin
{
	private MenuDao		menuDao		= new MenuDao();
	private MenuItemDao	menuItemDao	= new MenuItemDao();
	private FoodCategoryDao foodDao = new FoodCategoryDao();

	public MenuAdminController()
	{
		menuDao = new MenuDao();
		menuItemDao = new MenuItemDao();
	}

	@Override
	public int createMenuItem(MenuItemVM item) throws DuplicateItemException
	{
		MenuItem newItem = ControlUtil.getMenuItem(item);
		if (newItem != null) {
			menuItemDao.add(newItem);
			return menuItemDao.getById(newItem.getId()).getId();
		} else
			return -1;
	}
	
	public List<MenuItem> getAllMenuItems()
	{
		return menuItemDao.getAll(MenuItem.class);
	}

	@Override
	public boolean editMenuItem(MenuItemVM item) throws ItemNotFoundException,
			DuplicateItemException
	{
		MenuItem newItem = ControlUtil.getMenuItem(item);
		MenuItem tmp = menuItemDao.getById(newItem.getId());
		if (tmp == null) { return false; }
		tmp = ControlUtil.merge(tmp, newItem);
		menuItemDao.update(tmp);
		if (menuItemDao.getById(item.getId()).equals(tmp))			
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	@Override
	public boolean deleteMenuItem(MenuItemVM item) throws ItemNotFoundException {
		menuItemDao.delete(menuItemDao.getById(item.getId()));
		return true;
	}

	@Override
	public int createMenu(Menu menu) throws DuplicateItemException
	{
		menuDao.add(menu);
		return menuDao.getByName(menu.getName()).getId();
	}

	@Override
	public boolean editMenu(Menu menu) throws ItemNotFoundException,
			DuplicateItemException
	{
		Menu tmp = menuDao.getByName(menu.getName());
		if (tmp == null) { return false; }
		tmp = ControlUtil.merge(tmp, menu);
		menuDao.update(tmp);
		if (menuDao.getByName(menu.getName()).equals(tmp))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	@Override
	public boolean deleteMenu(Menu menu) throws ItemNotFoundException
	{
		menuDao.delete(menuDao.getByName(menu.getName()));
		if (menuDao.getByName(menu.getName()) != null) { return false; }
		return true;
	}

	@Override
	public MenuItemVM getMenuItemVM(String name)
	{
		MenuItem men = menuItemDao.getByName(name);
		return ControlUtil.getMenuItemVM(men);
	}

	@Override
	public MenuItemVM getMenuItemVM(int id)
	{
		MenuItem men = menuItemDao.getByID(id);
		return ControlUtil.getMenuItemVM(men);
	}

	@Override
	public List<MenuItemVM> getAllMenuItemVMs()
	{
		return ControlUtil.getMenuItemVMs(menuItemDao.getAll(MenuItem.class));
	}

	@Override
	public MenuItem getMenuItem(String name) throws ItemNotFoundException
	{
		return menuItemDao.getByName(name);
	}

	@Override
	public Menu getMenu(String name) throws ItemNotFoundException
	{
		return menuDao.getByName(name);
	}
	
	@Override
	public String[] getAllFoodCategories()
	{
		//Food catagory table items.
		List<FoodCategory> f = foodDao.getAll(FoodCategory.class);
		String[] s = new String[f.size()];
		
		for (int c=0;c<f.size();c++)
		{
			s[c]=f.get(c).getName();	
		}
		return s;
	}
	

	// @Override
	// public MenuVM getMenuVM(String name)
	// {
	// // TODO Auto-generated method stub
	// return null;
	// }
	//
	// @Override
	// public MenuVM getMenuVM(int id)
	// {
	// // TODO Auto-generated method stub
	// return null;
	// }
	//
	// @Override
	// public List<MenuVM> getAllMenuVMs()
	// {
	// // TODO Auto-generated method stub
	// return null;
	// }

	// @Override
	// public boolean addMenuItemVM(MenuItemVM mivm) throws
	// DuplicateItemException
	// {
	// menuItemDao.add(menuItem);
	// return false;
	// }

	// @Override
	// public boolean addMenuVM(MenuVM mivm) throws DuplicateItemException
	// {
	// // TODO Auto-generated method stub
	// return false;
	// }

	// @Override
	// public boolean updateMenuItemVM(MenuItemVM mivm)
	// throws DuplicateItemException
	// {
	// // TODO Auto-generated method stub
	// return false;
	// }
	//
	// @Override
	// public boolean addMenuItemVM(MenuItemVM mivm) throws
	// DuplicateItemException
	// {
	// // TODO Auto-generated method stub
	// return false;
	// }
	//
	// @Override
	// public boolean deleteMenuItemVM(MenuItemVM mivm)
	// throws ItemNotFoundException
	// {
	// // TODO Auto-generated method stub
	// return false;
	// }
	//
	// // @Override
	// // public boolean deleteMenuItemVM(MenuItemVM mivm)
	// // throws ItemNotFoundException
	// // {
	// // // TODO Auto-generated method stub
	// // return false;
	// // }
	//
	// // @Override
	// // public boolean updateMenuVM(MenuVM mivm) throws DuplicateItemException
	// // {
	// // // TODO Auto-generated method stub
	// // return false;
	// // }
	// //
	// // @Override
	// // public boolean deleteMenuVM(MenuVM mivm) throws ItemNotFoundException
	// // {
	// // // TODO Auto-generated method stub
	// // return false;
	// // }

}