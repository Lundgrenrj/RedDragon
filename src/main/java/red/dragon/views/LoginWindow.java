package red.dragon.views;

import red.dragon.controller.*;
import red.dragon.pojos.User;
import red.dragon.views.POSView;

import com.vaadin.data.validator.AbstractValidator;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

public class LoginWindow extends CustomComponent implements View, Button.ClickListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String NAME = "LOGIN";
	private final TextField user;
	private final PasswordField password;
	private final Button loginButton;
	private String currentUserName = "";
	private String currentGroupName = "";

	public LoginWindow() {
		setSizeFull();

		user = createUserNameInputField();
		password = createPasswordInputField();
		loginButton = new Button("Login", this);
		loginButton.setClickShortcut(KeyCode.ENTER);

		setCompositionRoot(createRootLayout(createInputPanel(user, password, loginButton)));
	}

	private VerticalLayout createRootLayout(VerticalLayout fields) {
		VerticalLayout viewLayout = new VerticalLayout(fields);
		viewLayout.setSizeFull();
		viewLayout.setComponentAlignment(fields, Alignment.MIDDLE_CENTER);
		viewLayout.setStyleName(Reindeer.LAYOUT_BLUE);
		return viewLayout;
	}

	private VerticalLayout createInputPanel(Component... children) {
		VerticalLayout fields = new VerticalLayout(children);
		fields.setCaption("Please login to access the application. admin@newdragon.com/admin or demo@newdragon.com/demo");
		fields.setSpacing(true);
		fields.setMargin(new MarginInfo(true, true, true, false));
		fields.setSizeUndefined();
		return fields;
	}

	private PasswordField createPasswordInputField() {
		PasswordField passwordField = new PasswordField("Password:");
		passwordField.setWidth("300px");
		//passwordField.addValidator(new PasswordValidator());
		passwordField.setRequired(true);
		passwordField.setValue("");
		passwordField.setNullRepresentation("");
		return passwordField;
	}

	private TextField createUserNameInputField() {
		TextField userField = new TextField("User:");
		userField.setWidth("300px");
		userField.setRequired(true);
		userField.setInputPrompt("Your username (eg. joe@email.com)");
		userField.addValidator(new EmailValidator("Username must be an email address"));
		userField.setInvalidAllowed(false);
		return userField;
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// focus the username field when user arrives to the login view
		user.focus();
		user.setValue("");
		password.setValue(null);
	}

	//
	// Validator for validating the passwords
	//
	@SuppressWarnings("unused")
	private static final class PasswordValidator extends AbstractValidator<String> {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public PasswordValidator() {
		    super("The password provided is not valid");
		}

		@Override
		protected boolean isValidValue(String value) {
		    //
		    // Password must be at least 8 characters long and contain at least
		    // one number
		    //
		    if (value != null
		            && (value.length() < 8 || !value.matches(".*\\d.*"))) {
		        return false;
		    }
		    return true;
		}

		@Override
		public Class<String> getType() {
		    return String.class;
		}
	}

	@Override
	public void buttonClick(ClickEvent event) {
		//
		// Validate the fields using the navigator. By using validors for the
		// fields we reduce the amount of queries we have to use to the database
		// for wrongly entered passwords
		//
		if (!user.isValid() || !password.isValid()) {
		    return;
		}

		String username = user.getValue();
		this.currentUserName = username;
		String password = this.password.getValue();
		
		User user = new User();
		user.setName(username);
		user.setPassword(password);
		user.setEmail(username);

		UserController uc = new UserController();

		if(uc.validate(user)){
		    // Store the current user in the service session
		    getSession().setAttribute("user", username);

		    User cUser = uc.getUser(username);
		    String gName = cUser.getUserGroup().getName().toString();
		    getSession().setAttribute("group", gName);
		    Notification.show("Welcome : " + username);
		    
		    // Navigate to main view
		    getUI().getNavigator().navigateTo(POSView.NAME);
		} else {

		    // Wrong password clear the password field and refocuses it
			Notification.show("The email or password you entered is incorrect");
		    this.password.setValue(null);
		    this.password.focus();
		}
	}

	public void setCurrentGroupName(String userGroup) {
		// TODO Auto-generated method stub
		this.currentGroupName = userGroup;
	}

	public String getCurrentGroupName() {
		return currentGroupName;
	}

	public void setCurrentUserName(String username) {
		// TODO Auto-generated method stub
		this.currentUserName = username;

	}

	public String getCurrentUserName() {
		return currentUserName;
	}
}