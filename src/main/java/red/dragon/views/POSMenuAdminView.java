package red.dragon.views;

import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FileResource;
import com.vaadin.server.Page;
import com.vaadin.server.ThemeResource;
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
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Window;
import com.vaadin.ui.Upload.ProgressListener;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.StartedEvent;
import com.vaadin.ui.Upload.StartedListener;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.themes.Reindeer;

import red.dragon.Exceptions.DuplicateItemException;
import red.dragon.Exceptions.ItemNotFoundException;
import red.dragon.ViewModels.MenuItemVM;
import red.dragon.controller.MenuAdminController;
import red.dragon.controllers.api.MenuApi;
import red.dragon.views.master.MenuView;

public class POSMenuAdminView extends MenuView {	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String NAME = "POSMENUADMIN";
	VerticalLayout root = new VerticalLayout();
	VerticalLayout vItemAttribute = new VerticalLayout();
	HorizontalLayout horPageTitle = new HorizontalLayout();
	HorizontalLayout horlayout = new HorizontalLayout();
	HorizontalLayout horlayoutMenu = new HorizontalLayout();
	HorizontalLayout imageLayout = new HorizontalLayout();
	Panel menuCategoryPanel = new Panel("Food Category");
	Panel menuItemPanel = new Panel("Menu Items");
	Panel itemAttributePanel = new Panel("Attributes");
	Table itemAttributeTable = new Table();
	Table itemCategoryTable = new Table();
	Table itemDescriptionTable = new Table();
	
	TextField itemNameTF = new TextField("NAME");
	TextField priceTF = new TextField("PRICE");
	TextArea descriptionTA = new TextArea("DESCRIPTION");
	final NativeSelect select = new NativeSelect("SELECT A CATEGORY");
	
	final Button add = new Button("ADD");
	final Button search = new Button("SEARCH");
	final Button delete = new Button("DELETE");
	final Button clear = new Button("CLEAR");
	
	final String itemAttributePanelWidth = "260px";
	final String menuItemPanelWidth = "370px";
	final String itemImageWidth = "249px";
	final String itemImageHeight = "207px";
	final String gridInfoWidth = "257px";
//	final String basePath = "./"; //this to run on local computer
	final String basePath = "/tmp/"; //run on Server
	BufferedImage itemPicture = null;
	
	int counter = 2;
	int menuPictureWidth = 100;
	int menuPriceWidth = 35;
	int priceWidth = 60;
	int currentSelectItem = 0;
	

	public POSMenuAdminView() {
		super();
		addComponent(root);
		initHorPageTitle();
		root.addComponent(horPageTitle);
		root.setComponentAlignment(horPageTitle, Alignment.TOP_CENTER);
		root.addComponent(horlayout);
		root.setComponentAlignment(horlayout, Alignment.TOP_CENTER);
		initPOSButton();
		addCategory();
		initTable();
		updateMenuItemLayout("COMBINATION");
		initShowMenuItem();
		initvItemAttribute();
		addFooter();
//		initAddListenerImageLayout();
		
	}
	
//	private void initAddListenerImageLayout() {
//		// TODO Auto-generated method stub
//		imageLayout.addLayoutClickListener(new LayoutClickListener() {
//		    /**
//			 * 
//			 */
//			private static final long serialVersionUID = 1L;
//
//			public void layoutClick(LayoutClickEvent event) {
//				if (event != null) {
//					Notification.show("image");
//					
//				}
//		    }
//		});
//	}


	private void initvItemAttribute() {
		// TODO Auto-generated method stub	
		VerticalLayout bSpace = new VerticalLayout();
		bSpace.setWidth("10px");
		horlayoutMenu.addComponent(bSpace);
		
		setItemNamePrice();
		vItemAttribute.addComponent(select);
		vItemAttribute.addComponent(descriptionTA);
		descriptionTA.setWidth(gridInfoWidth);
		initImageUploadAttribute();
		
		
		itemAttributePanel.setContent(vItemAttribute);
		horlayoutMenu.addComponent(itemAttributePanel);
		root.addComponent(horlayoutMenu);
		root.setComponentAlignment(horlayoutMenu, Alignment.TOP_CENTER);
	}

	private void setItemNamePrice() {
		// TODO Auto-generated method stub
		GridLayout grid = new GridLayout(2, 1);
		grid.setWidth(gridInfoWidth);
			
		itemNameTF.setWidth("170px");
		priceTF.setWidth("70px");
		grid.addComponent(itemNameTF, 0, 0);
		grid.addComponent(priceTF, 1, 0);
		vItemAttribute.addComponent(grid);
	}

	private void initImageUploadAttribute() {
		// Show uploaded file in this placeholder
		final Image itemImage = new Image();
		itemImage.setVisible(true);
		imageLayout.setHeight(itemImageHeight);

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
//		        	File file2 = new File(basePath + filename);
//		        	itemPicture = ImageIO.read(file2);
		            file = new File(basePath + filename);
		            fos = new FileOutputStream(file);
		            
		        } catch (final java.io.FileNotFoundException e) {
		            new Notification("Could not open file<br/>",
		                             e.getMessage(),
		                             Notification.Type.ERROR_MESSAGE)
		                .show(Page.getCurrent());
		            return null;
		        } catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        
		        return fos; // Return the output stream to write to
		    }

		    public void uploadSucceeded(SucceededEvent event) {
		        // Show the uploaded file in the image viewer
		    	itemImage.setVisible(true);
		    	itemImage.setWidth(itemAttributePanelWidth);
		    	itemImage.setSource(new FileResource(file));
		    	itemImage.setWidth(itemImageWidth);
		    	itemImage.setHeight(itemImageHeight);
		    	setPictureTable(itemImage);
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
		vItemAttribute.addComponent(upload);
		vItemAttribute.addComponent(imageLayout);
	}
	
	
	private void initShowMenuItem() {
		VerticalLayout vMenuItem = new VerticalLayout();
		
		//add space between menuCategoryPanel and menuItem
		VerticalLayout vSpace = new VerticalLayout();
		vSpace.setWidth("10px");
		horlayoutMenu.addComponent(vSpace);
		
		menuTable.setVisibleColumns(new Object[] { "name",
				"price", "picture" });
		menuTable.setColumnWidth("price", menuPriceWidth);
		menuTable.setColumnWidth("picture", menuPictureWidth);
		vMenuItem.addComponent(menuTable);
		menuItemPanel.setContent(vMenuItem);
		menuItemPanel.setWidth(menuItemPanelWidth);
		horlayoutMenu.addComponent(menuItemPanel);
	}
	
	
	@SuppressWarnings({ "serial" })
	private void initPOSButton() {
		//add space between
		VerticalLayout sSpace = new VerticalLayout();
		sSpace.setWidth("160px");
		horlayout.addComponent(sSpace);
			
		search.setWidth("100px");
		search.addStyleName("menuBarStyle");
		search.addClickListener(new Button.ClickListener() {
		    public void buttonClick(ClickEvent event) {
		    	if (event.getButton().getCaption() == "SEARCH") {
			    	menuTable.setFilterBarVisible(true);
			    	updateMenuItemLayout("All");
			    	search.setCaption("SEARCH OFF");
			    	menuCategoryPanel.setEnabled(false);
			    	add.setEnabled(false);
			    	delete.setEnabled(false);
			    	clear.setEnabled(false);
		    	}
		    	else if (event.getButton().getCaption() == "SEARCH OFF") {
		    		menuTable.setFilterBarVisible(false);
			    	updateMenuItemLayout("COMBINATION");
			    	search.setCaption("SEARCH");
			    	menuCategoryPanel.setEnabled(true);
			    	add.setEnabled(true);
			    	delete.setEnabled(true);
			    	clear.setEnabled(true);
		    	}
		    }
		});   
		horlayout.addComponent(search);
		//add space between
		VerticalLayout eSpace = new VerticalLayout();
		eSpace.setWidth("280px");
		horlayout.addComponent(eSpace);
				
		add.setWidth("70px");
		add.addStyleName("menuBarStyle");
		add.addClickListener(new Button.ClickListener() {
		    public void buttonClick(ClickEvent event) {			    	
		        if (event.getButton().getCaption() == "ADD") {
		        	addItem();
		        }		    	
		    }
		});        
		horlayout.addComponent(add);
				
		//add space between
		VerticalLayout dSpace = new VerticalLayout();
		dSpace.setWidth("24px");
		horlayout.addComponent(dSpace);
					
		delete.setWidth("70px");
		delete.addStyleName("menuBarStyle");
		delete.addClickListener(new Button.ClickListener() {
		    public void buttonClick(ClickEvent event) {
		    	deleteItem();
		    }
		});        
		horlayout.addComponent(delete);
			
		//add space between
		VerticalLayout cSpace = new VerticalLayout();
		cSpace.setWidth("24px");
		horlayout.addComponent(cSpace);
		
		clear.setWidth("70px");
		clear.addStyleName("menuBarStyle");
		clear.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
		    	if (event.getButton().getCaption() != null) { 		
		    		if (event.getButton().getCaption() != null) {
		    			clearAttributePanel();
					}
				}				    	
		    }
		});        
		horlayout.addComponent(clear);	
	}
	

	protected void addItem() {
		// TODO Auto-generated method stub
		MenuItemVM cVM = new MenuItemVM();
		
		if (currentSelectItem > 0){
			cVM.setId(currentSelectItem);
			cVM.setName(itemNameTF.getValue());
			cVM.setPrice(Double.parseDouble(priceTF.getValue()));
			cVM.setDescription(descriptionTA.getValue());
			cVM.setFoodCategoryName((String) select.getValue());
			if (itemPicture != null) {
				cVM.setItemPicture(itemPicture);
			}
			try {
				if(controler.editMenuItem(cVM)){
					Notification.show("UPDATED MENU ITEM: " + currentSelectItem);
					clearAttributePanel();
					reinitMenuTable();
				}
			} catch (ItemNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DuplicateItemException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		} else {
			if (itemNameTF.getValue() != null && !itemNameTF.getValue().equals("")
					&& select.getValue() != null) {
				cVM.setName(itemNameTF.getValue());
				cVM.setPrice(Double.parseDouble(priceTF.getValue()));
				cVM.setDescription(descriptionTA.getValue());
				cVM.setFoodCategoryName((String) select.getValue());
				if (itemPicture != null) {
					cVM.setItemPicture(itemPicture);
				}
				int newItem = 0;
				try {
					newItem = controler.createMenuItem(cVM);
				} catch (DuplicateItemException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				clearAttributePanel();
				reinitMenuTable();
				if (newItem > 0){
					Notification.show("ADDED ITEM: " + newItem);
				} else {
					Notification.show("UNABLE ADD ITEM");
				}
			} else {
				Notification.show("UNABLE ADD ITEM");
			}
			
		}
	}

	protected void deleteItem() {
		// TODO Auto-generated method stub
		if (currentSelectItem > 0){
			MenuItemVM cVM = new MenuItemVM();
			try {
				cVM = controler.getMenuItemVM(currentSelectItem);
//				Notification.show(cVM.getName());
			} catch (ItemNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			try {
				if (controler.deleteMenuItem(cVM)) {
					clearAttributePanel();
					reinitMenuTable();
					Notification.show("DELETED ITEM: " + currentSelectItem);
				}
			} catch (ItemNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		} else {
			Notification.show("SELECT ITEM TO DELETE");
		}
		
	}
	
	protected void clearAttributePanel() {
		// TODO Auto-generated method stub
		itemNameTF.setValue("");
		priceTF.setValue("");
		select.setValue(null);
		descriptionTA.setValue("");
		imageLayout.removeAllComponents();
		currentSelectItem = 0;
	}
	
	
	private void reinitMenuTable() {
		// TODO Auto-generated method stub
		menuTable.removeAllItems();
		container.removeAllItems();
		
		container.addAll(controler.getAllMenuItemVMs());
		menuTable.setContainerDataSource(container);
		menuTable.setVisibleColumns(new Object[] { "name",
				"price", "picture" });
//		menuTable.setColumnWidth("price", menuPriceWidth);
//		menuTable.setColumnWidth("picture", menuPictureWidth);		
		//Add Listener to the user account table
		menuTableAddListener();		
	}
	

	private void initHorPageTitle() {
		// TODO Auto-generated method stub
		Label pageTitle = new Label("Menu Management");
		pageTitle.setSizeFull();
		pageTitle.setWidth("800px");
		pageTitle.setHeight("45px");
		pageTitle.addStyleName("posPageTitle");
		horPageTitle.addComponent(pageTitle);
	}
	
	
	private void addCategory() {
		VerticalLayout vMenuCategory = new VerticalLayout();
		menuCategoryPanel.addStyleName(Reindeer.PANEL_LIGHT);
		vMenuCategory.setWidth("150px");
		String[] categories = controler.getAllFoodCategories();
		for(String category : categories) {
			Button menuButton = new Button(category);
			select.addItem(category);
			menuButton.setWidth("100%");
			menuButton.addClickListener(new Button.ClickListener() {
			    /**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				public void buttonClick(ClickEvent event) {
			        //Notification.show(event.getButton().getCaption());
			    	if (event.getButton().getCaption() != null) {
						updateMenuItemLayout(event.getButton().getCaption());					
					}       
			    }			    
			});
			vMenuCategory.addComponent(menuButton);
			menuCategoryPanel.setContent(vMenuCategory);
			horlayoutMenu.addComponent(menuCategoryPanel);
			root.addComponent(horlayoutMenu);
		}
	}
	
	private void updateMenuItemLayout(String filter) {
		container.removeAllContainerFilters();
		if (filter != "All")
			container.addContainerFilter("foodCategoryName", filter, false,
					true);
	}
	
	private void initTable() {
		menuTable.setPageLength(5);
		menuTable.setFilterBarVisible(false);
		
		menuTableAddListener();
	}
	
	
	private void menuTableAddListener() {
		// TODO Auto-generated method stub
		menuTable.addValueChangeListener(new ValueChangeListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				if (event.getProperty().getValue() != null) {
					Object item = menuTable.getValue();
					
					if (item instanceof byte[]) {
						item = new ImageIcon((byte[])item).getImage();
					}
					currentSelectItem = (int) menuTable.getItem(item).getItemProperty("id").getValue();
//					Notification.show(String.valueOf(currentSelectItem));
					String itemName = menuTable.getItem(item).getItemProperty("name").getValue().toString();
					Object price = menuTable.getItem(item).getItemProperty("price").getValue();
					String itemCategoryName = menuTable.getItem(item).getItemProperty("foodCategoryName").getValue().toString();
					String itemDescriptionName = menuTable.getItem(item).getItemProperty("description").getValue().toString();
					String itemPrice = String.format("%10.2f", price);
					Image picture = (Image)menuTable.getItem(item).getItemProperty("picture").getValue();
					

					itemSetAttributeTable(itemName, itemPrice, itemCategoryName, itemDescriptionName);
					setPictureTable(new Image(picture.getCaption(), picture.getSource()));
					add.setEnabled(true);
					delete.setEnabled(true);
					search.setCaption("SEARCH");
					menuTable.setFilterBarVisible(false);
					menuCategoryPanel.setEnabled(true);
				}
			}
		});
	}

	protected void itemSetAttributeTable(String itemName, String itemPrice, String itemCategoryName, String itemDescriptionName) {
		// TODO Auto-generated method stub
		itemNameTF.setValue(itemName);
		priceTF.setValue(itemPrice);
		descriptionTA.setValue(itemDescriptionName);
		select.setValue(itemCategoryName);
	}
	

	private void setPictureTable(Image picture) {
		// TODO Auto-generated method stub
		picture.setWidth("255px");
		picture.setHeight("190px");
		picture.setVisible(true);
		imageLayout.removeAllComponents();
		imageLayout.addComponent(picture);
	}

	@Override
	protected void getContent() {
		addStyleName("Menu");
//		setMargin(true);
		setSpacing(true);
		initializeTable();
		getMenuItems();
	}
	
	@Override
	protected void setController() {
//		this.controler = new MenuController();
		this.controler = new MenuAdminController();
	}
	
	protected void getMenuItems() {
		menuTable.removeAllItems();
		container.removeAllItems();
		container.addAll(controler.getAllMenuItemVMs());
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
//		addComponent(root);
//		initHorPageTitle();
//		root.addComponent(horPageTitle);
//		
//		root.addComponent(horlayout);
//		initPOSButton();
//		addCategory();
//		initTable();
//		updateMenuItemLayout("COMBINATION");
//		initShowMenuItem();
//		initvItemAttribute();
	}

}
