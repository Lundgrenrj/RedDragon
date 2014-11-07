package red.dragon.views.master;

import org.tepi.filtertable.FilterTable;

import red.dragon.ViewModels.MenuItemVM;
import red.dragon.controller.interfaces.IMenuAdmin;
import red.dragon.controllers.MenuController;
//import red.dragon.models.MenuItemVM;



import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Alignment;

public abstract class MenuView extends MasterView {

	private static final long serialVersionUID = 1L;
	protected FilterTable menuTable;
	protected BeanItemContainer<MenuItemVM> container;
	protected IMenuAdmin controler;

	public MenuView() {
		super();
	}

	protected void initializeTable() {
		menuTable = new FilterTable();
		menuTable.setWidth("100%");
		menuTable.setImmediate(true);
		menuTable.setSelectable(true);
		menuTable.setFilterBarVisible(true);
		container = new BeanItemContainer<MenuItemVM>(MenuItemVM.class);
		menuTable.setContainerDataSource(container);
		menuTable.setVisibleColumns(new Object[] {"name", //"description", 
				"price", "foodCategoryName", "picture" });
		addComponent(menuTable);
		setComponentAlignment(menuTable, Alignment.TOP_CENTER);
	}

	@Override
	protected void getContent() {
		addStyleName("Menu");
//		setMargin(true);
		setSpacing(true);
		initializeTable();
		getMenuItems();
	}

	protected void getMenuItems() {
		menuTable.removeAllItems();
		controler = new MenuController();
		container.addAll(controler.getAllMenuItemVMs());
	}
}
