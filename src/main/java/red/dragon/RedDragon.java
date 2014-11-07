package red.dragon;

import javax.servlet.annotation.WebServlet;

import red.dragon.views.LoginWindow;
import red.dragon.views.MenuAdminView;
import red.dragon.views.MenuCustomerView;
import red.dragon.views.OrderTrackingAdminView;
import red.dragon.views.POSMenuAdminView;
import red.dragon.views.POSUserAccount;
import red.dragon.views.POSView;
import red.dragon.views.ReportView;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;

@Theme("mytheme")
@SuppressWarnings("serial")
public class RedDragon extends UI {

	Navigator navigator = null;

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = RedDragon.class, widgetset = "red.dragon.AppWidgetSet")
	public static class Servlet extends VaadinServlet {
	}

	@Override
	protected void init(VaadinRequest request) {
		getPage().setTitle("RedDragon");

		
		navigator = new Navigator(this, this);
      
        navigator.addView(LoginWindow.NAME, new LoginWindow());
//        navigator.addView(MenuCustomerView.NAME, new MenuCustomerView());
//        navigator.addView(MenuAdminView.NAME, new MenuAdminView());
        navigator.addView(OrderTrackingAdminView.NAME, new OrderTrackingAdminView());
        navigator.addView(POSView.NAME, new POSView());
        navigator.addView(POSMenuAdminView.NAME, new POSMenuAdminView());
        navigator.addView(POSUserAccount.NAME, new POSUserAccount());
        navigator.addView(ReportView.NAME, new ReportView());
        navigator.navigateTo(POSView.NAME);

        
        
        // We use a view change handler to ensure the user is always redirected
        // to the login view if the user is not logged in.
        //
        navigator.addViewChangeListener(new ViewChangeListener() {
            
            @Override
            public boolean beforeViewChange(ViewChangeEvent event) {
                
                // Check if a user has logged in
                boolean isLoggedIn = getSession().getAttribute("user") != null;
                boolean isLoginView = event.getNewView() instanceof LoginWindow;

                if (!isLoggedIn && !isLoginView) {
                    // Redirect to login view always if a user has not yet
                    // logged in
                	navigator.navigateTo(LoginWindow.NAME);
                    return false;

                } 
                else if (isLoggedIn && isLoginView) {
                    // If someone tries to access to login view while logged in,
                    // then cancel
                    return false;
                }
                else if (isLoggedIn && event.getNewView() instanceof POSMenuAdminView && !getSession().getAttribute("group").toString().equals("Managers")) {
                	Notification.show("ACCESS DENIED", Type.ERROR_MESSAGE);
                    return false;
                }
                else if (isLoggedIn && event.getNewView() instanceof POSUserAccount && !getSession().getAttribute("group").toString().equals("Managers")) {
                	Notification.show("ACCESS DENIED", Type.ERROR_MESSAGE);
                    return false;
                }
                else if (isLoggedIn && event.getNewView() instanceof ReportView && !getSession().getAttribute("group").toString().equals("Managers")) {
                	Notification.show("ACCESS DENIED", Type.ERROR_MESSAGE);
                    return false;
                }
                return true;
                
            }
            
            @Override
            public void afterViewChange(ViewChangeEvent event) {
                
            }
        });
	}
}
