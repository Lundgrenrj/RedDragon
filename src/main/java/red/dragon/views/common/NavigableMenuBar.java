package red.dragon.views.common;

import java.util.HashMap;

import red.dragon.views.LoginWindow;
import red.dragon.views.MenuAdminView;
import red.dragon.views.MenuCustomerView;
import red.dragon.views.OrderTrackingAdminView;
import red.dragon.views.POSMenuAdminView;
import red.dragon.views.POSUserAccount;
import red.dragon.views.POSView;
import red.dragon.views.ReportView;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Notification;


public class NavigableMenuBar extends MenuBar {
	private static final long serialVersionUID = 1L;
	private MenuItem previous = null; // Previously selected item
    private MenuItem current  = null; // Currently selected item

    // Map view IDs to corresponding menu items
    HashMap<String,MenuItem> menuItems = new HashMap<String,MenuItem>();
    MenuItem homeMenu = null;
    String cUserName = "";
    
    public NavigableMenuBar() {
    	setWidth("800px");
    	addStyleName("mybarmenu");

		// Add items in the menu and associate them with a view ID
    	AddDropDownMenu();
 	
//		addView(MenuCustomerView.NAME, "Web View", null);
//		addView(MenuAdminView.NAME, "Web Admin", null);
		addView(OrderTrackingAdminView.NAME, "Active Orders", null);
		addView(POSView.NAME, "POS View", null);
		addView(POSMenuAdminView.NAME, "POS Menu Admin", null);
		addView(POSUserAccount.NAME, "User Account", null);
		addView(ReportView.NAME, "Reports", null);

	}
    
    private void AddDropDownMenu() {
		// TODO Auto-generated method stub
    	homeMenu = addItem("", new ThemeResource("images/home.png"), null);
    	
    	//Add Print
    	homeMenu.addItem("Print Receipt", new ThemeResource("images/tea-16px.png"), mycommand);	
    

		// Another submenu item
    	homeMenu.addItem("Cancel Order", new ThemeResource("images/coffee-16px.png"), mycommand);

		// Another submenu item
    	homeMenu.addItem("Order Status", new ThemeResource("images/coffee-16px.png"), mycommand);
				
		// A sub-menu item after a separator
    	homeMenu.addSeparator();
    	homeMenu.addItem("LOGOUT", null, mycommand);
	}

	/** Navigate to a view by menu selection */ 
    MenuBar.Command mycommand = new MenuBar.Command() {
        private static final long serialVersionUID = 1L;

		public void menuSelected(MenuItem selectedItem) {
			String dropDownName = selectedItem.getText();

            if (dropDownName.equals("Active Orders") || dropDownName.equals("POS View") 
            		|| dropDownName.equals("POS Menu Admin") || dropDownName.equals("User Account")
            		|| dropDownName.equals("Reports")) {
            	getUI().getNavigator().navigateTo(selectItem(selectedItem));
            }
            else {
            	if (dropDownName.equals("LOGOUT")){
					getSession().setAttribute("user", null);
				    getSession().setAttribute("group", null);
				    // Navigate to main view
				    getUI().getNavigator().navigateTo(LoginWindow.NAME);
				}
            	else {
//            		Notification.show(dropDownName);
            		
//            		boolean isLoggedIn = VaadinSession.getCurrent().getAttribute("user") != null;
            		if (VaadinSession.getCurrent().getAttribute("user") != null) {
//                		cUserName = VaadinSession.getCurrent().getAttribute("user").toString();
            			cUserName = VaadinSession.getCurrent().getAttribute("group").toString();
            			Notification.show(cUserName);
//            			Notification.show(VaadinSession.getCurrent().getAttribute("user").toString());
            		}
            	}
            }
        }
    };
    
    public void addView(String viewName, String caption, Resource icon) {
        menuItems.put(viewName, addItem(caption, icon, mycommand));
    }

    /** Select a menu item by its view ID **/
    protected boolean selectView(String viewName) {
        // Check that the menu item exists
        if (!menuItems.containsKey(viewName))
            return false;

        if (previous != null)
            previous.setStyleName(null);
        if (current == null)
            current = menuItems.get(viewName);
        current.setStyleName("highlight");
        previous = current;
        
        return true;
    }

    /** Selects a new menu item */
    public String selectItem(MenuItem selectedItem) {
        current = selectedItem;
    
        // Do reverse lookup for the view ID
        for (String key: menuItems.keySet())
            if (menuItems.get(key) == selectedItem)
                return key;
        
        return null;
    }

    public boolean beforeViewChange(ViewChangeEvent event) {
        
        // Check if a user has logged in
        boolean isLoggedIn = getSession().getAttribute("user") != null;
        boolean isLoginView = event.getNewView() instanceof LoginWindow;

        if (!isLoggedIn && !isLoginView) {
            // Redirect to login view always if a user has not yet
            // logged in
            getUI().getNavigator().navigateTo(LoginWindow.NAME);
            return false;

        } else if (isLoggedIn && isLoginView) {
            // If someone tries to access to login view while logged in,
            // then cancel
            return false;
        }
        
        return selectView(event.getViewName());
    }
        
    
    public void afterViewChange(ViewChangeEvent event) {}
    
}