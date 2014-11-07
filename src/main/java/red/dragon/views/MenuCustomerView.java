package red.dragon.views;

import red.dragon.controller.MenuAdminController;
//import red.dragon.models.Cart;
import red.dragon.views.master.MenuView;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;

public class MenuCustomerView extends MenuView {

	private static final long serialVersionUID = 1L;
	public static final String NAME = "MENUCUSTOMER";
	private Tree menu = new Tree();
	private HorizontalLayout layout;
	private Panel menuContainer;
	private Panel detailsPanel;
	private VerticalLayout detailsLayout;
//	private Cart cart;
//	private CartView cartView;
	private String detailsPanelWidth = "635px";
	private String menuContainerWidth = "160px";

	public MenuCustomerView() {
		super();

		layout = new HorizontalLayout();
		layout.setWidth("800px");
		
//		layout.setSizeFull();
		layout.setSpacing(true);

		addComponent(layout);
		
		setExpandRatio(layout, 1);

		detailsLayout = new VerticalLayout();

		initTree();
		initTable();
		initShowMenuItem();
		getPanels();
		getCart();
		addFooter();
		setComponentAlignment(layout, Alignment.TOP_CENTER);
	}

	private void initShowMenuItem() {
		//VerticalLayout vMenuItem = new VerticalLayout();

		
		menuTable.setVisibleColumns(new Object[] { "name",// "description",
				"price", "picture" });
		//vMenuItem.addComponent(menuTable);

		
	}
	
	private Component addCategory() {
		menu.setSizeUndefined();
		String[] categories = controler.getAllFoodCategories();
//		List<String> categories = new ArrayList<>();
		for(String category : categories) {
			menu.addItem(category);
			menu.setChildrenAllowed(category, false);
		}
		menu.setImmediate(true);
		return menu;
	}

	private void getCart() {
//		cart = new Cart();
//		cartView = new CartView(cart);
//		addComponent(cartView);
	}

	private void getPanels() {
	
		menuContainer = new Panel("Food Category");
		menuContainer.setWidth(menuContainerWidth);
		detailsPanel = new Panel("");
	
		// Layout for the menu area. Wrap the menu in a Panel to allow
		menuContainer.addStyleName("menucontainer");
		menuContainer.addStyleName("light");
//		menuContainer.setWidth(null);
		menuContainer.setHeight("100%");
		layout.addComponent(menuContainer);
	
		// A menu tree, fill it later.
		menuContainer.setContent(addCategory());
	
		// A panel for the main view area on the right side
		detailsPanel.addStyleName("detailspanel");
		detailsPanel.addStyleName("light"); // No borders
		//detailsPanel.setSizeFull();
		detailsPanel.setWidth(detailsPanelWidth);
		layout.addComponent(detailsPanel);
	
		detailsLayout.setSizeFull();
		detailsLayout.addComponent(menuTable);
		detailsPanel.setContent(detailsLayout);
	
		layout.setExpandRatio(detailsPanel, 1);
		layout.setExpandRatio(menuContainer, 0);
		
	}

	private void initTree() {
		menu.addItem("All");
		menu.setChildrenAllowed("All", false);

		// Add the Listener to the Category
		menu.addValueChangeListener(new ValueChangeListener() {
			private static final long serialVersionUID = 1L;

			public void valueChange(ValueChangeEvent event) {
				if (event.getProperty() != null
						&& event.getProperty().getValue() != null) {
					updateDetailsLayout(event.getProperty().getValue()
							.toString());
				}
			}

			private void updateDetailsLayout(String filter) {
				container.removeAllContainerFilters();
				if (filter != "All")
					container.addContainerFilter("foodCategory", filter, false,
							true);
			}
		});
		menu.setImmediate(true);

	}

	private void initTable() {
		menuTable.setPageLength(5);
		menuTable.setFilterBarVisible(false);
		menuTable.addValueChangeListener(new ValueChangeListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				if (event.getProperty().getValue() != null) {
					Object item = menuTable.getValue();
					new MenuItemDetail(menuTable.getItem(item));
				}
			}
		});

	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected void setController() {
//		this.controler = new MenuController();
		this.controler = new MenuAdminController();
	}
}