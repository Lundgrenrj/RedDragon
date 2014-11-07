package red.dragon.views;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.swing.ImageIcon;

import red.dragon.Exceptions.DuplicateItemException;
import red.dragon.Exceptions.ItemNotFoundException;
import red.dragon.controller.MenuAdminController;
import red.dragon.controller.OrderController;
import red.dragon.controller.UserController;
import red.dragon.dao.ActiveOrderDao;
import red.dragon.pojos.ActiveOrder;
import red.dragon.pojos.ActiveOrderDetail;
import red.dragon.pojos.MenuItem;
import red.dragon.views.master.MenuView;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;
public class POSView extends MenuView {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String NAME = "POSVIEW";
	VerticalLayout root = new VerticalLayout();
	HorizontalLayout horPageTitle = new HorizontalLayout();
	HorizontalLayout horLayout = new HorizontalLayout();
	HorizontalLayout horLayoutMenu = new HorizontalLayout();
	Panel menuCategoryPanel = new Panel("Food Category");
	Panel menuItemPanel = new Panel("Menu Items");
	Panel receiptPanel = new Panel("Receipt");
	Label totalLabel = new Label();
	Label taxTotalLabel = new Label();
	VerticalLayout vReceipt = new VerticalLayout();
	Table receiptTable = new Table();
	Table otherFeesTable = new Table();
	Button placeOrder = new Button("Place Order");
	final String receiptWidth = "260px";
	final String itemNameWidth = "190px";
	final String itemPriceWidth = "45px";
	final String itemReceiptTableHeight = "302px";
	final String menuItemPanelWidth = "370px";
	final Button search = new Button("SEARCH");
	final Button edit = new Button("EDIT");
	final Button delete = new Button("DELETE");
	int menuPictureWidth = 100;
	int menuPriceWidth = 35;
	int menuItemNameWidth = 235;
	int counter = 2;
	double totalPrice = 0;
	double taxTotal = 0;
	double tax = 0;
	double otherFees = 0;
	ActiveOrder order = new ActiveOrder();
	int itemSequence = 0;
	List<ActiveOrderDetail> ordetailList = new ArrayList<ActiveOrderDetail>();
	List<TextArea> instructionList = new ArrayList<TextArea>();
	
	public POSView() {
		super();
		
		addComponent(root);
		initHorPageTitle();
		root.addComponent(horPageTitle);
		root.setComponentAlignment(horPageTitle, Alignment.TOP_CENTER);
		root.addComponent(horLayout);
		root.setComponentAlignment(horLayout, Alignment.TOP_CENTER);
		initPOSButton();
		addCategory();
		initTable();
		updateMenuItemLayout("COMBINATION");
		initShowMenuItem();
		initReceipt();		
		addFooter();
	}
	

	private void initHorPageTitle() {
		// TODO Auto-generated method stub
		Label pageTitle = new Label("Point of Sale");
		pageTitle.setSizeFull();
		pageTitle.setWidth("800px");
		pageTitle.setHeight("45px");
		pageTitle.addStyleName("posPageTitle");
		horPageTitle.addComponent(pageTitle);
	}


	private void initReceipt() {
		// TODO Auto-generated method stub
		//add space between Receipt
		VerticalLayout vMenuItemSpace = new VerticalLayout();
		vMenuItemSpace.setWidth("10px");
		horLayoutMenu.addComponent(vMenuItemSpace);
		initPlaceOrderButton();
		receiptHeader();
		initReceiptTable();
		initOtherFees();
		initReceiptTax();
	}


	private void initOtherFees() {
		// TODO Auto-generated method stub
		otherFees = 0;
		otherFeesTable.addContainerProperty("otherFees", String.class, null);
		otherFeesTable.addContainerProperty("Price",  Double.class, null);
		otherFeesTable.setWidth(receiptWidth);
		otherFeesTable.setPageLength(1);
		otherFeesTable.setColumnHeaderMode(Table.ColumnHeaderMode.HIDDEN);
		otherFeesTable.setColumnWidth("Price", 45);
		
		final ColumnGenerator generator = new ColumnGenerator() {
	        /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
	        public Object generateCell(Table source, Object itemId,
	                Object columnId) {
	            return "Other Fees";
	        }
	    };
	    
	    String otherFeesString = "Other Fees";
	    int Id = 1;
	    Object itemId = new Integer(Id);
	    
	    otherFeesTable.addItem(new Object[]{otherFeesString, otherFees}, itemId);
	    
	    otherFeesTable.addGeneratedColumn("otherFees", generator);
	    
	    vReceipt.addComponent(otherFeesTable);
	}

	

	private void initReceiptTax() {
		// TODO Auto-generated method stub
		tax = getTax();
		taxTotal = 0;
		GridLayout grid = new GridLayout(2, 1);
		grid.setWidth(receiptWidth);
		Label name = new Label("Sales Tax ");
		name.setWidth(itemNameWidth);
		name.addStyleName("salesTaxStyle");
		grid.addComponent(name, 0, 0);
		grid.setComponentAlignment(name, Alignment.MIDDLE_LEFT);
		
		taxTotalLabel.addStyleName("salesTaxTotalStyle");
		taxTotalLabel.setWidth(itemPriceWidth);
		grid.addComponent(taxTotalLabel, 1, 0);
		grid.setComponentAlignment(taxTotalLabel, Alignment.MIDDLE_LEFT);

		updateTaxLabel();
		
		vReceipt.addComponent(grid);
	}

	private void updateTaxLabel() {
		// TODO Auto-generated method stub
		taxTotal = totalPrice * tax;
		double displayTaxTotalPrice = Math.round(taxTotal*100)/100.0; // Round to two decimals
		
		String taxTotalPrice = String.format("%10.2f", displayTaxTotalPrice);
		
		//String t = "$ " + itemPrice;
		taxTotalLabel.setValue(taxTotalPrice);
	}

	private void updateTotalLabel() {
		
		updateTaxLabel();
		double displayTotalPrice = taxTotal + totalPrice;
		displayTotalPrice = Math.round(displayTotalPrice*100)/100.0; // Round to two decimals
		
		String itemPrice = String.format("%10.2f", displayTotalPrice);
		
		String t = "$ " + itemPrice;
		totalLabel.setValue(t);
		
	}
	
	private double getTax() {
		// TODO Auto-generated method stub
		double tempTax = .06;
		return tempTax;
	}

	private void initReceiptTable() {
		// TODO Auto-generated method stub
		receiptTable.addContainerProperty("Name", String.class, null);
		receiptTable.addContainerProperty("Price",  Double.class, null);
		receiptTable.addContainerProperty("Instruction", TextArea.class, null);
		receiptTable.addContainerProperty("Del", CheckBox.class, null);
		
		receiptTable.setWidth(receiptWidth);
		receiptTable.setColumnWidth("Name", 185);
		receiptTable.setColumnWidth("Price", 45);
		//receiptTable.setWidth("100%");
		receiptTable.setVisible(true);
		receiptTable.setHeight(itemReceiptTableHeight);
		receiptTable.setColumnCollapsingAllowed(true);
		receiptTable.setColumnCollapsed("Instruction", true);
		receiptTable.setColumnCollapsed("Del", true);
		
		counter = 2;
		totalPrice = 0;
		updateTotalLabel();
		vReceipt.addComponent(receiptTable);
		receiptPanel.setContent(vReceipt);
		
		
		horLayoutMenu.addComponent(receiptPanel);
		root.addComponent(horLayoutMenu);
		root.setComponentAlignment(horLayoutMenu, Alignment.TOP_CENTER);
	}


	private void initPlaceOrderButton() {
		placeOrder.addStyleName(Reindeer.BUTTON_SMALL);
		placeOrder.addClickListener(new Button.ClickListener() {
		    /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void buttonClick(ClickEvent event) {
				if (event != null){
					placeOrder();
					deleteReceipt();
				}

		    }
		});
	}
	
	
	private void receiptHeader() {
		// TODO Auto-generated method stub
		GridLayout grid = new GridLayout(2, 1);
		grid.setWidth(receiptWidth);
		Label name = new Label("New Dragon Dine");
		name.setWidth("170px");
		name.setStyleName("dragonNameStyle");

		grid.addComponent(name, 0, 0);
		grid.addComponent(placeOrder, 1, 0);
	
		grid.setComponentAlignment(name, Alignment.MIDDLE_LEFT);
		grid.setComponentAlignment(placeOrder, Alignment.MIDDLE_RIGHT);
	
		vReceipt.addComponent(grid);
//		Label ticket = new Label("Ticket: 12345");
//		vReceipt.addComponent(ticket);
		
		totalLabel.setHeight("25px");
		totalLabel.setWidth("120px");
		totalLabel.addStyleName("totalStyle");
		vReceipt.addComponent(totalLabel);
		//add the select option to the receipt header
		receiptSelectOption();
		
		vReceipt.setWidth(receiptWidth);
		
		updateTotalLabel();
	}

	protected void placeOrder() {
	    
	    System.out.println("Order ID is: "+order.getOrderID());
		//order = new ActiveOrder();
		// TODO Auto-generated method stub
		UserController user = new UserController();
		order.setCustomer(user.getAllCustomers().get(0));
		order.setPrice(totalPrice);
		order.setStatus("new");
		order.setTimestamp(new Date());
		
		Iterator<ActiveOrderDetail> it = ordetailList.iterator(); //iterator
		int i = 0;
		while(it.hasNext())
		{
		    ActiveOrderDetail current = it.next();
		    current.setSpecialInstruction(instructionList.get(i).getValue());
		    order.addActiveOrderDetail(current);
		    i++;
		}
		
		//order.setActiveOrderDetails(ordetailList);
		
		OrderController ctr = new OrderController();
		
		  try {
			ctr.submitOrder(order);
			Notification.show("Submited Order, Thanks You");
		} catch (DuplicateItemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Notification.show(e.getMessage());
		}
		
		deleteReceipt();
		order = new ActiveOrder();
		ordetailList = new ArrayList<ActiveOrderDetail>();
		instructionList = new ArrayList<TextArea>();
	}


	private void receiptSelectOption() {
		HorizontalLayout horLayoutSelect = new HorizontalLayout();
		HorizontalLayout horLayoutSpacePaid = new HorizontalLayout();
		HorizontalLayout horLayoutSpaceStatus = new HorizontalLayout();
		
		horLayoutSpacePaid.setWidth("15px");
		horLayoutSpaceStatus.setWidth("15px");
		horLayoutSelect.addComponent(dinerOption());
		
		horLayoutSelect.addComponent(horLayoutSpacePaid);
		horLayoutSelect.addComponent(paidOption());
		
		horLayoutSelect.addComponent(horLayoutSpaceStatus);
		horLayoutSelect.addComponent(orderStatus());
		vReceipt.addComponent(horLayoutSelect);
	}
	
	private Component orderStatus() {
		// TODO Auto-generated method stub
		NativeSelect select = new NativeSelect("Order Status");
		select.setWidth("70px");
		// Add items with given item IDs
		select.addItem("Cooking");
		select.addItem("Ready");
		select.addItem("END");

		//set default select
		select.select("None");
		return select;
	}

	private Component paidOption() {
		// TODO Auto-generated method stub
		NativeSelect select = new NativeSelect("Paid");
		select.setWidth("70px");
		// Add items with given item IDs
		select.addItem("None");
		select.addItem("Cost");
		select.addItem("Credit");
		select.addItem("Check");
		//set default select
		select.select("None");
		return select;
	}

	private Component dinerOption() {
		// TODO Auto-generated method stub
		NativeSelect select = new NativeSelect("Diner Option");
		select.setWidth("70px");
		// Add items with given item IDs
		select.addItem("Walk-In");
		select.addItem("Phone");
		select.addItem("Table 1");
		select.addItem("Table 2");
		select.addItem("Table 3");
		select.addItem("Table 4");
		select.addItem("Table 5");
		select.addItem("Table 6");
		return select;
	}

	private void addCategory() {
		VerticalLayout vMenuCategory = new VerticalLayout();
		menuCategoryPanel.addStyleName(Reindeer.PANEL_LIGHT);
		vMenuCategory.setWidth("150px");
		String[] categories = controler.getAllFoodCategories();
		
		for(String category : categories) {
			Button menuButton = new Button(category);
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
			horLayoutMenu.addComponent(menuCategoryPanel);
			root.addComponent(horLayoutMenu);
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
		
		menuTable.addValueChangeListener(new ValueChangeListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				if (event.getProperty().getValue() != null) {
					Object item = menuTable.getValue();
					if (item instanceof byte[]) {
						item = new ImageIcon((byte[])item).getImage();
					}

					receiptTableAddItem(item);
				}
			}
		});

	}

	protected void receiptTableAddItem(Object item) {
		// TODO Auto-generated method stub
		String itemName = menuTable.getItem(item).getItemProperty("name").getValue().toString();
		double itemPrice = (double) menuTable.getItem(item).getItemProperty("price").getValue();
		//String itemPrice = String.format("%10.2f", price);
		totalPrice = totalPrice + ((double) menuTable.getItem(item).getItemProperty("price").getValue());
		
		MenuItem menuItem;
		
		MenuAdminController mac = new MenuAdminController();
		try {
			menuItem = mac.getMenuItem(itemName);
			
			ActiveOrderDetail activeOrderDetail = new ActiveOrderDetail();
			activeOrderDetail.setItemSequence(++itemSequence);
			activeOrderDetail.setMenuItem(menuItem);
			activeOrderDetail.setSpecialInstruction("Good Testing for this hahaha");
			ordetailList.add(activeOrderDetail);
		} catch (ItemNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		final CheckBox delCheckBox = new CheckBox();
		Object itemId = new Integer(counter);
		delCheckBox.setData(itemId);
		delCheckBox.addValueChangeListener(new Property.ValueChangeListener() {
		    /**
			 * 
			 */
			private static final long serialVersionUID = -7882470528227608999L;

			public void valueChange(ValueChangeEvent event) {
		        //table.setEditable((Boolean) editable.getValue());
				Object itemId = delCheckBox.getData();
				if ((boolean) event.getProperty().getValue()) {
					receiptTableRemoveItem(itemId);
				}
		    }
		});
		delCheckBox.setImmediate(true);

		TextArea instruction = new TextArea();
		instruction.setWidth("80px");
		instructionList.add(instruction);
		receiptTable.addItem(new Object[]{itemName, itemPrice, instruction, delCheckBox}, itemId);
		
		counter = counter + 1;
		updateTotalLabel();
	}

	protected void receiptTableRemoveItem(Object itemId) {
		double t = (double) receiptTable.getItem(itemId).getItemProperty("Price").getValue();
		totalPrice = totalPrice - t;
		//double t = ((double) receiptTable.getItem(itemId).getItemProperty("Price").getValue());
		//Notification.show(String.valueOf(t));
		//Notification.show(t);
		updateTotalLabel();
		receiptTable.removeItem(itemId);
		receiptTable.setColumnWidth("Name", 90);
    	receiptTable.setColumnWidth("Price", 0);
    	receiptTable.setColumnWidth("Instruction", 83);
    	receiptTable.setColumnWidth("Del", 20);
	}


	private void initShowMenuItem() {
		VerticalLayout vMenuItem = new VerticalLayout();
		
		//add space between menuCategoryPanel and menuItem
		VerticalLayout vSpace = new VerticalLayout();
		vSpace.setWidth("10px");
		horLayoutMenu.addComponent(vSpace);
		
		
		menuTable.setVisibleColumns(new Object[] { "name",
				"price", "picture" });
		//menuTable.setColumnWidth("name", menuItemNameWidth);
		menuTable.setColumnWidth("price", menuPriceWidth);
		menuTable.setColumnWidth("picture", menuPictureWidth);
		vMenuItem.addComponent(menuTable);
		menuItemPanel.setContent(vMenuItem);
		menuItemPanel.setWidth(menuItemPanelWidth);
		
		horLayoutMenu.addComponent(menuItemPanel);
		
	}

	@SuppressWarnings({ "serial" })
	private void initPOSButton() {
		//add space between
		VerticalLayout sSpace = new VerticalLayout();
		sSpace.setWidth("105px");
		horLayout.addComponent(sSpace);
//			
		search.setWidth("100px");
		search.addStyleName("menuBarStyle");
		search.addClickListener(new Button.ClickListener() {
		    public void buttonClick(ClickEvent event) {
		    	if (event.getButton().getCaption() == "SEARCH") {
			    	menuTable.setFilterBarVisible(true);
			    	updateMenuItemLayout("All");
			    	search.setCaption("SEARCH OFF");
			    	menuCategoryPanel.setEnabled(false);
			    	edit.setEnabled(false);
			    	delete.setEnabled(false);
		    	}
		    	else if (event.getButton().getCaption() == "SEARCH OFF") {
		    		menuTable.setFilterBarVisible(false);
			    	updateMenuItemLayout("COMBINATION");
			    	search.setCaption("SEARCH");
			    	menuCategoryPanel.setEnabled(true);
			    	edit.setEnabled(true);
			    	delete.setEnabled(true);
		    	}
		    }
		});   
		horLayout.addComponent(search);
		
		//add space between
//				VerticalLayout sSpace = new VerticalLayout();
//				sSpace.setWidth("160px");
//				horLayout.addComponent(sSpace);
					
//				search.setWidth("100px");
//				search.addStyleName("menuBarStyle");
//				search.addClickListener(new Button.ClickListener() {
//				    public void buttonClick(ClickEvent event) {
//				    	if (event.getButton().getCaption() == "SEARCH") {
//					    	menuTable.setFilterBarVisible(true);
//					    	updateMenuItemLayout("All");
//					    	search.setCaption("SEARCH OFF");
//					    	menuCategoryPanel.setEnabled(false);
////					    	add.setEnabled(false);
//					    	delete.setEnabled(false);
////					    	clear.setEnabled(false);
//				    	}
//				    	else if (event.getButton().getCaption() == "SEARCH OFF") {
//				    		menuTable.setFilterBarVisible(false);
//					    	updateMenuItemLayout("COMBINATION");
//					    	search.setCaption("SEARCH");
//					    	menuCategoryPanel.setEnabled(true);
////					    	add.setEnabled(true);
//					    	delete.setEnabled(true);
////					    	clear.setEnabled(true);
//				    	}
//				    }
//				});   
//				horLayout.addComponent(search);

		//add space between
		VerticalLayout eSpace = new VerticalLayout();
		eSpace.setWidth("280px");
		horLayout.addComponent(eSpace);
		
		
		edit.setWidth("100px");
		edit.addStyleName("menuBarStyle");
		edit.addClickListener(new Button.ClickListener() {
		    public void buttonClick(ClickEvent event) {
		    	
		        if (event.getButton().getCaption() == "EDIT") {
		        	receiptTable.setColumnCollapsed("Instruction", false);
		        	receiptTable.setColumnCollapsed("Del", false);
		        	receiptTable.setColumnWidth("Name", 90);
		        	receiptTable.setColumnWidth("Price", 0);
		        	receiptTable.setColumnWidth("Instruction", 150);
		        	receiptTable.setColumnWidth("Del", 20);
		        	otherFeesTable.setEditable(true);
		        	edit.setCaption("SUBMIT");
		        	search.setEnabled(false);
		        	delete.setEnabled(false);
		        	placeOrder.setEnabled(false);
		        	menuTable.setEnabled(false);
		        	menuCategoryPanel.setEnabled(false);
		        }
		        else if(event.getButton().getCaption() == "SUBMIT") {
		        	receiptTable.setColumnCollapsed("Instruction", true);
		        	receiptTable.setColumnCollapsed("Del", true);
		        	
		        	receiptTable.setColumnWidth("Name", 185);
		        	receiptTable.setColumnWidth("Price", 45);
		        	receiptTable.setColumnWidth("Instruction", 0);
		        	receiptTable.setColumnWidth("Del", 0);
		        	receiptTable.setWidth("100%");
		        	receiptTable.setImmediate(true);
		        	otherFeesTable.setEditable(false);
		        	edit.setCaption("EDIT");
		        	search.setEnabled(true);
		        	delete.setEnabled(true);
		        	placeOrder.setEnabled(true);
		        	menuTable.setEnabled(true);
		        	menuCategoryPanel.setEnabled(true);
		        	int Id = 1;
		    	    Object itemId = new Integer(Id);
		    	    otherFeesTable.setImmediate(true);
		        	otherFees = (double) otherFeesTable.getItem(itemId).getItemProperty("Price").getValue();
		        	
		        	if (otherFees != 0){
		        		totalPrice = totalPrice + otherFees;
		        		updateTotalLabel();
		        	}
		        	
		        }
		    	
		    }
		});        
		horLayout.addComponent(edit);
		
		//add space between
		VerticalLayout dSpace = new VerticalLayout();
		dSpace.setWidth("10px");
		horLayout.addComponent(dSpace);
		
		
		delete.setWidth("100px");
		delete.addStyleName("menuBarStyle");
		delete.addClickListener(new Button.ClickListener() {
		    public void buttonClick(ClickEvent event) {
		        //reset receipt
		    	deleteReceipt();
		    	
		    }
		});        
		horLayout.addComponent(delete);
	}
	

	protected void deleteReceipt() {
		// TODO Auto-generated method stub
		vReceipt.removeAllComponents();
    	receiptTable.removeAllItems();
    	otherFeesTable.removeAllItems();
    	otherFeesTable.removeGeneratedColumn("otherFees");
    	
    	receiptTable.setColumnWidth("Name", 90);
    	receiptTable.setColumnWidth("Price", 0);
    	receiptTable.setColumnWidth("Instruction", 60);
    	receiptTable.setColumnWidth("Del", 20);
    	
    	receiptHeader();
    	initReceiptTable();
    	initOtherFees();
    	initReceiptTax();
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
		order = new ActiveOrder();
		ordetailList = new ArrayList<ActiveOrderDetail>();
		instructionList = new ArrayList<TextArea>();
		
//		addComponent(root);
//		initHorPageTitle();
//		root.addComponent(horPageTitle);
//		
//		root.addComponent(horLayout);
//		initPOSButton();
//		addCategory();
//		initTable();
//		updateMenuItemLayout("COMBINATION");
//		initShowMenuItem();
//		initReceipt();
	}
	
}
