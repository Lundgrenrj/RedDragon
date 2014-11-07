package red.dragon.views;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.tepi.filtertable.FilterTable;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FileResource;
import com.vaadin.server.Page;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.AbstractSelect.ItemCaptionMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Table.ColumnGenerator;
import com.vaadin.ui.Upload.ProgressListener;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.StartedEvent;
import com.vaadin.ui.Upload.StartedListener;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;
import com.vaadin.ui.themes.Reindeer;

import red.dragon.Exceptions.DuplicateItemException;
import red.dragon.Exceptions.ItemNotFoundException;
import red.dragon.ViewModels.UserAccountVM;
import red.dragon.controller.UserController;
import red.dragon.controller.interfaces.IUsers;
import red.dragon.pojos.User;
import red.dragon.pojos.UserGroup;
import red.dragon.views.master.MasterView;

public class POSUserAccount extends MasterView {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String NAME = "USERACCOUNT";
	VerticalLayout root = new VerticalLayout();
	HorizontalLayout horPageTitle = new HorizontalLayout();
	HorizontalLayout horLayout = new HorizontalLayout();
	HorizontalLayout horLayoutMenu = new HorizontalLayout();
	HorizontalLayout imageLayout = new HorizontalLayout();
	VerticalLayout vUserInfo = new VerticalLayout();
	VerticalLayout vGroup = new VerticalLayout();
	
	Panel vGroupPanel = new Panel("Groups");
	Panel userAccountListPanel = new Panel("Users");
	Panel userInfoPanel = new Panel("User Info");
	
	protected FilterTable userAccountTable;
	protected BeanItemContainer<UserAccountVM> container;
//	protected BeanItemContainer<UserGroup> container;
	
	final String groupWidth = "150px";
	final String pageWidth = "800px";
	final String pageTitleHeight = "45px";
	final String userAccountWidth = "370px";
	final String userAccountTableWidth = "365px";
	final String userAccountHeight = "407px";
	final String userInfoWidth = "260px";
	final String userImageWidth = "250px";
	final String userImageHeight = "208px";
	final String userIdWidth = "10px";
	final String gridInfoWidth = "257px";
//	final String basePath = "./"; //this to run on local computer
	final String basePath = "/tmp/"; //run on Server
	BufferedImage itemPicture = null;
	
	int currentSelectID = 0;
	Label userIdLB = new Label();
	TextField userNameTF = new TextField("UserName: ");
	TextField groupNameTF = new TextField("Group: ");
	TextField emailTF = new TextField("Email: ");
	final NativeSelect select = new NativeSelect("Select A Group");
	UserAccountVM cUser = new UserAccountVM();
	PasswordField userPassword1 = new PasswordField("Reset Password");
	PasswordField userPassword2 = new PasswordField("");
	private IUsers controler;
	
	public POSUserAccount() {
		super();
		
		addComponent(root);
		initHorPageTitle();
		root.addComponent(horLayout);
		root.setComponentAlignment(horLayout, Alignment.TOP_CENTER);
		initPOSButton();
		vGroupPanel.setContent(vGroup);
		horLayoutMenu.addComponent(vGroupPanel);
		addGroupPanel();
		initUserTable();
		initUserInfo();
		
		root.addComponent(horLayoutMenu);	
		root.setComponentAlignment(horLayoutMenu, Alignment.TOP_CENTER);
		addFooter();
	}

	
	private void initUserInfo() {
		// TODO Auto-generated method stub
		VerticalLayout bSpace = new VerticalLayout();
		bSpace.setWidth("10px");
		horLayoutMenu.addComponent(bSpace);
		
		userInfoPanel.setWidth(userInfoWidth);

		vUserInfo.removeAllComponents();
		
		setUserInfoTable();
		vUserInfo.addComponent(userIdLB);
		vUserInfo.addComponent(userNameTF);
		vUserInfo.addComponent(select);
		vUserInfo.addComponent(emailTF);  
		
		userPassword();
		
		imageLayout.removeAllComponents();
		imageLayout.setHeight(userImageHeight);
		initImageUploadAttribute();
		
		userInfoPanel.setContent(vUserInfo);
		horLayoutMenu.addComponent(userInfoPanel);
	}


	private void userPassword() {
		// TODO Auto-generated method stub
		GridLayout grid = new GridLayout(2, 1);
		grid.setWidth(gridInfoWidth);
		
		userPassword1.setWidth("120px");
		userPassword2.setWidth("120px");
		grid.addComponent(userPassword1, 0, 0);
		grid.addComponent(userPassword2, 1, 0);
		vUserInfo.addComponent(grid);
	}

	
	private void initImageUploadAttribute() {
		// TODO Auto-generated method stub
		// Show uploaded file in this placeholder
		final Image userImage = new Image();
		userImage.setVisible(true);

		// Implement both receiver that saves upload in a file and
		// listener for successful upload
		class ImageReceiver implements Receiver, SucceededListener {
		    /**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			public File file;
		    
		    public OutputStream receiveUpload(String filename,
		                                      String mimeType) {
		        // Create upload stream
		    	
		        FileOutputStream fos = null; // Stream to write to
		        try {
		            // Open the file for writing.
		            file = new File(basePath + filename);
		            fos = new FileOutputStream(file);
		        } catch (final java.io.FileNotFoundException e) {
		            new Notification("Could not open file<br/>",
		                             e.getMessage(),
		                             Notification.Type.ERROR_MESSAGE)
		                .show(Page.getCurrent());
		            return null;
		        }
		        return fos; // Return the output stream to write to
		    }

		    public void uploadSucceeded(SucceededEvent event) {
		        // Show the uploaded file in the image viewer
		    	userImage.setVisible(true);
		    	userImage.setWidth(userImageWidth);
		    	userImage.setHeight(userImageHeight);
		    	userImage.setSource(new FileResource(file));
		    	setPictureTable(userImage);    	
		    	try {
					itemPicture = ImageIO.read(file);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
		};
		ImageReceiver receiver = new ImageReceiver(); 

		// Create the upload with a caption and set receiver later
		final Upload upload = new Upload("Upload Image", receiver);
		upload.setImmediate(true);
		upload.setButtonCaption("Select file");
		upload.addSucceededListener(receiver);
		        
		// Prevent too big downloads
		final long UPLOAD_LIMIT = 10000000l;
		upload.addStartedListener(new StartedListener() {
		    /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
		    public void uploadStarted(StartedEvent event) {
		        if (event.getContentLength() > UPLOAD_LIMIT) {
		            Notification.show("Too big file",
		                Notification.Type.ERROR_MESSAGE);
		            upload.interruptUpload();
		        }
		    }
		});
		        
		// Check the size also during progress 
		upload.addProgressListener(new ProgressListener() {
		    /**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			public void updateProgress(long readBytes, long contentLength) {
		        if (readBytes > UPLOAD_LIMIT) {
		            Notification.show("Too big file",
		                Notification.Type.ERROR_MESSAGE);
		            upload.interruptUpload();
		        }
		    } 
		});	
		vUserInfo.addComponent(upload);
		vUserInfo.addComponent(imageLayout);
	}
	
	
	private void setPictureTable(Image picture) {
		// TODO Auto-generated method stub
		picture.setWidth("255px");
		picture.setHeight("190px");
		imageLayout.removeAllComponents();
		imageLayout.addComponent(picture);	
	}

	private void addGroupPanel() {
		// TODO Auto-generated method stub
		vGroup.removeAllComponents();
		vGroup.addStyleName(Reindeer.PANEL_LIGHT);
		vGroup.setWidth(groupWidth);
		List<UserGroup> userGroups = controler.getAllUserGroups();
//		List<String> userGroups = new ArrayList<>();
		
		for(UserGroup group : userGroups) {
			String gName = group.getName();
			select.addItem(gName);
			Button groupButton = new Button(gName);
			groupButton.setWidth("100%");
			groupButton.addClickListener(new Button.ClickListener() {
			    /**
				 * 
				 */
				private static final long serialVersionUID = 1L;
				public void buttonClick(ClickEvent event) {
			        //Notification.show(event.getButton().getCaption());
			    	if (event.getButton().getCaption() != null) {
						updateUserAcountLayout(event.getButton().getCaption());
					}			        
			    }			    
			});
			vGroup.addComponent(groupButton);		
		}
		
		Button groupButton = new Button("All Accounts");
		groupButton.setWidth("100%");
		groupButton.addClickListener(new Button.ClickListener() {
		    /**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			public void buttonClick(ClickEvent event) {
		        //Notification.show(event.getButton().getCaption());
		    	if (event.getButton().getCaption() != null) {
					updateUserAcountLayout(event.getButton().getCaption());
				}	        
		    }    
		});
		vGroup.addComponent(groupButton);
		vGroupPanel.setContent(vGroup);
		vGroupPanel.addStyleName(Reindeer.PANEL_LIGHT);
		horLayoutMenu.addComponent(vGroupPanel);
	}


	protected void updateUserAcountLayout(String filter) {
		// TODO Auto-generated method stub
		container.removeAllContainerFilters();
		if (filter != "All Accounts")
			container.addContainerFilter("groupName", filter, false,
					true);
	}


	@SuppressWarnings({ "serial" })
	private void initPOSButton() {
		//add space between
		VerticalLayout bSpace = new VerticalLayout();
		bSpace.setWidth("10px");
		horLayout.addComponent(bSpace);
		
		final Button search = new Button("SEARCH");
		final Button add = new Button("ADD");
		final Button delete = new Button("DELETE");
		final Button clear = new Button("CLEAR");
		
		//add space between
		VerticalLayout sSpace = new VerticalLayout();
		sSpace.setWidth("153px");
		horLayout.addComponent(sSpace);
				
		search.setWidth("100px");
		search.addStyleName("menuBarStyle");
		search.addClickListener(new Button.ClickListener() {
		    public void buttonClick(ClickEvent event) {
		    	if (event.getButton().getCaption() == "SEARCH") {
		    		userAccountTable.setFilterBarVisible(true);
		    		updateUserAcountLayout("All Accounts");
			    	search.setCaption("SEARCH OFF");
			    	vGroupPanel.setEnabled(false);
			    	add.setEnabled(false);
			    	delete.setEnabled(false);
		    	}
		    	else if (event.getButton().getCaption() == "SEARCH OFF") {
		    		userAccountTable.setFilterBarVisible(false);
		    		updateUserAcountLayout("Managers");
			    	search.setCaption("SEARCH");
			    	vGroupPanel.setEnabled(true);
			    	add.setEnabled(true);
			    	delete.setEnabled(true);
		    	}
		    }
		});   
		horLayout.addComponent(search);

		//add space between
		VerticalLayout eSpace = new VerticalLayout();
		eSpace.setWidth("280px");
		horLayout.addComponent(eSpace);
				
		add.setWidth("70px");
		add.addStyleName("menuBarStyle");
		add.addClickListener(new Button.ClickListener() {
		    public void buttonClick(ClickEvent event) {
		    	addUserAccount();		    	
		    }
		});        
		horLayout.addComponent(add);
		
		//add space between
		VerticalLayout dSpace = new VerticalLayout();
		dSpace.setWidth("24px");
		horLayout.addComponent(dSpace);
				
		delete.setWidth("70px");
		delete.addStyleName("menuBarStyle");
		delete.addClickListener(new Button.ClickListener() {
		    public void buttonClick(ClickEvent event) {
		    	if (event.getButton().getCaption() != null) {
					deleteUserAccount();
				}		    	
		    }
		});        
		horLayout.addComponent(delete);
		
		//add space between
		VerticalLayout cSpace = new VerticalLayout();
		cSpace.setWidth("24px");
		horLayout.addComponent(cSpace);
		
		clear.setWidth("70px");
		clear.addStyleName("menuBarStyle");
		clear.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
		    	if (event.getButton().getCaption() != null) { 		
		    		if (event.getButton().getCaption() != null) {
		    			userNameTF.setReadOnly(false);
		    			clearAllUserInfoPanel();
					}
				}				    	
		    }
		});        
		horLayout.addComponent(clear);		
	}


	protected void addUserAccount() {
		// TODO Auto-generated method stub
		if (currentSelectID > 0) {
			User user = new User();
			user.setName(cUser.getUserName());
			user.setId(cUser.getUserId());
			user.setEmail(emailTF.getValue());
			user.setPicture(setUserPicture());
			UserGroup ug = new UserGroup();
			if (select.getValue() != null){
				ug = controler.getUserGroup(select.getValue().toString());
				user.setUserGroup(ug);
				String uP1 = getUserPassword1();
				String uP2 = getUserPassword2();
				if (uP1.equals(uP2) && uP1.length() > 0 && uP1 != null){
					user.setPassword(uP1);
					try {
						boolean update = controler.update(user);
						if (update) {
							Notification.show("UPDATED ACCOUNT ID: " + String.valueOf(currentSelectID));
							userNameTF.setReadOnly(false);
							clearAllUserInfoPanel();
							reinitUserTable();
						}
					} catch (DuplicateItemException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else {
					try {
						boolean update = controler.update(user);
						if (update) {
							Notification.show("UPDATED ACCOUNT ID: " + String.valueOf(currentSelectID));
							userNameTF.setReadOnly(false);
							clearAllUserInfoPanel();
							reinitUserTable();
						}
					} catch (DuplicateItemException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			else {
				Notification.show("PLEASE SELECT GROUP");
			}

		}
		else {
			User user = new User();
			if (userNameTF.getValue() != null && userNameTF.getValue() != "") {
				user.setName(userNameTF.getValue());
				user.setEmail(emailTF.getValue());
				user.setPicture(setUserPicture());
				UserGroup ug = new UserGroup();
				if (select.getValue() != null){
					ug = controler.getUserGroup(select.getValue().toString());
					user.setUserGroup(ug);
					String uP1 = getUserPassword1();
					String uP2 = getUserPassword2();
					if (uP1.equals(uP2) && uP1.length() > 0 && uP1 != null){
						user.setPassword(uP1);
						try {
							int add = controler.addUser(user);						
							Notification.show("ADDED ACCOUNT: " + String.valueOf(add) );
							clearAllUserInfoPanel();
							reinitUserTable();
						} catch (NoSuchAlgorithmException | InvalidKeySpecException
								| DuplicateItemException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					else {
						Notification.show("PLEASE REENTER PASSWORD");
					}
				}
				else {
					Notification.show("PLEASE SELECT GROUP");
				}						
			}
			else {
				Notification.show("UNABLE to ADD ACCOUNT");
			}			
		}
	}


	private byte[] setUserPicture() {
		// TODO Auto-generated method stub
		byte[] imageInByte = null;
		if (itemPicture != null) {
			try {
				// convert BufferedImage to byte array
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ImageIO.write(itemPicture, "jpg", baos);
				baos.flush();
				imageInByte = baos.toByteArray();
				baos.close();
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return imageInByte;

	}


	private String getUserPassword1() {
		// TODO Auto-generated method stub
		String uP1 = userPassword1.getValue();
		return uP1;
	}


	private String getUserPassword2() {
		// TODO Auto-generated method stub
		String uP2 = userPassword2.getValue();
		return uP2;
	}
	
	protected void deleteUserAccount() {
		// TODO Auto-generated method stub
		if (currentSelectID > 0) {
			try {
				boolean del = controler.deleteUserVM(cUser);
				if (del){
					Notification.show("DELETED ACCOUNT ID: " + String.valueOf(currentSelectID));
					currentSelectID = 0;
					userNameTF.setReadOnly(false);
					clearAllUserInfoPanel();
					reinitUserTable();
				}
				else
					Notification.show("UNABLE DELETE ACCOUNT ID: " + String.valueOf(currentSelectID));
				
			} catch (ItemNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
		else {
			clearAllUserInfoPanel();
			Notification.show("Please select an Account to DELETE");
		}	
	}

	
	private void reinitUserTable() {
		// TODO Auto-generated method stub
		userAccountTable.removeAllItems();
		container.removeAllItems();
		
		container.addAll(controler.getAllUserVMs());
		userAccountTable.setContainerDataSource(container);
//		userAccountTable.setVisibleColumns(new Object[] {"userId", "userName", "groupName", "picture", "email"});
		userAccountTable.setVisibleColumns(new Object[] {"userId", "userName", "picture"});

		//Add Listener to the user account table
		userAccountTableAddListener();		
	}


	private void initHorPageTitle() {
		// TODO Auto-generated method stub
		Label pageTitle = new Label("User Management");

		pageTitle.setSizeFull();
		pageTitle.setWidth(pageWidth);
		pageTitle.setHeight(pageTitleHeight);
		pageTitle.addStyleName("posPageTitle");
		horPageTitle.addComponent(pageTitle);
		root.addComponent(horPageTitle);
		root.setComponentAlignment(horPageTitle, Alignment.TOP_CENTER);
	}
	
	protected void initUserTable() {
		userAccountTable = new FilterTable();
		userAccountTable.setImmediate(true);
		userAccountTable.setSelectable(true);
		userAccountTable.setFilterBarVisible(false);
		userAccountTable.setPageLength(5);
		userAccountTable.setWidth("100%");
		
		container = new BeanItemContainer<UserAccountVM>(UserAccountVM.class);	
		container.addAll(controler.getAllUserVMs());
		userAccountTable.setContainerDataSource(container);
//		userAccountTable.setVisibleColumns(new Object[] {"userId", "userName", "groupName", "picture", "email"});
		userAccountTable.setVisibleColumns(new Object[] {"userId", "userName", "picture"});
		
		//Add Listener to the user account table
		userAccountTableAddListener();
		
		VerticalLayout vSpace = new VerticalLayout();
		vSpace.setWidth("10px");
		horLayoutMenu.addComponent(vSpace);
		
		userAccountListPanel.setContent(userAccountTable);
		userAccountListPanel.setWidth(userAccountWidth);
		//userAccountListPanel.setHeight(userAccountHeight);
		
		horLayoutMenu.addComponent(userAccountListPanel);
	}
	
	private void userAccountTableAddListener() {
		// TODO Auto-generated method stub
		userAccountTable.addValueChangeListener(new ValueChangeListener() {
			private static final long serialVersionUID = 1L;
			@Override
			public void valueChange(ValueChangeEvent event) {
				if (event.getProperty().getValue() != null) {
					Object item = userAccountTable.getValue();			
					updateUserInfoPanel(item);
				}
			}
		});	
	}

	protected void updateUserInfoPanel(Object item) {
		// TODO Auto-generated method stub	
		userNameTF.setReadOnly(false);
		clearAllUserInfoPanel();
				
		int ID = (int) userAccountTable.getItem(item).getItemProperty("userId").getValue();
		String userName = userAccountTable.getItem(item).getItemProperty("userName").getValue().toString();
		currentSelectID = ID;
		int groupId = (int) userAccountTable.getItem(item).getItemProperty("groupId").getValue();
		String groupName = userAccountTable.getItem(item).getItemProperty("groupName").getValue().toString();
		String email = userAccountTable.getItem(item).getItemProperty("email").getValue().toString();
		
		userIdLB.setValue("User ID: " + String.valueOf(ID));
		userNameTF.setValue(userName);		
		userNameTF.setReadOnly(true);
		select.setValue(groupName);
		emailTF.setValue(email);
		
		cUser.setUserId(ID);
		cUser.setUserName(userName);
		cUser.setEmail(email);
		cUser.setGroupName(groupName);
		cUser.setGroupId(groupId);
	    
		Image userImage = (Image)userAccountTable.getItem(item).getItemProperty("picture").getValue();
		setPictureTable(new Image(userImage.getCaption(), userImage.getSource()));
	}


	private void clearAllUserInfoPanel() {
		// TODO Auto-generated method stub
		userIdLB.setValue("User ID: 0");
		userNameTF.setValue("");
		select.setValue(null);
		emailTF.setValue("");
		userPassword1.setValue("");
		userPassword2.setValue("");
		imageLayout.removeAllComponents();
		currentSelectID = 0;
	}

	private void setUserInfoTable() {
		currentSelectID = 0;
		userIdLB.setValue("User ID: 0");
		userNameTF.setValue("");
		userNameTF.setWidth("100%");
		userNameTF.setRequired(true);
		userNameTF.setInputPrompt("Your username (eg. joe@email.com)");
		userNameTF.addValidator(new EmailValidator("Username must be an email address"));
		userNameTF.setInvalidAllowed(false);

		emailTF.setValue("");
		emailTF.setWidth("100%");
		emailTF.setRequired(true);
		emailTF.setInputPrompt("Your username (eg. joe@email.com)");
		emailTF.addValidator(new EmailValidator("Username must be an email address"));
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
//		addComponent(root);
//		initHorPageTitle();
//		root.addComponent(horLayout);
//		initPOSButton();
//		vGroupPanel.setContent(vGroup);
//		horLayoutMenu.addComponent(vGroupPanel);
//		addGroupPanel();
//		initUserTable();
//		initUserInfo();
//		
//		root.addComponent(horLayoutMenu);	
	}

	@Override
	protected void getContent() {
		// TODO Auto-generated method stub
		addStyleName("Menu");
//		setMargin(true);
		setSpacing(true);
	}

	@Override
	protected void setController() {
		// TODO Auto-generated method stub
		controler = new UserController();
	}

}
