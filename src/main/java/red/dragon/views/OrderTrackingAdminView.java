package red.dragon.views;

import java.util.ArrayList;
import java.util.Date;
import java.sql.Timestamp;

import org.tepi.filtertable.FilterTable;

import red.dragon.Exceptions.ItemNotFoundException;
import red.dragon.controller.OrderController;
import red.dragon.pojos.*;
import red.dragon.views.master.MasterView;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

public class OrderTrackingAdminView extends MasterView {

	private static final long serialVersionUID = 1L;
	public static final String NAME = "ORDERTRACINGADMIN";
	private OrderController orderController;
	private FilterTable orderTable;
	private Table detailTable;
	private  Button detailButton;
	private  Button statusUpdateButton;
	final String[] statusList = {"Open","Cooking","Ready","On Hold","Done", "Cancel", "Complete"};
	ArrayList<Button> toggleButtonList = new ArrayList<Button>();
	VerticalLayout root = new VerticalLayout();
	HorizontalLayout buttonsAndTable = new HorizontalLayout();
	HorizontalLayout horPageTitle = new HorizontalLayout();
	
	////CONSTRUCTOR////
	public OrderTrackingAdminView() {
		super();
		
		addComponent(root);
		initHorPageTitle();
		root.addComponent(horPageTitle);
		root.setComponentAlignment(horPageTitle, Alignment.TOP_CENTER);
		root.addComponent(buttonsAndTable);
		root.setComponentAlignment(buttonsAndTable, Alignment.TOP_CENTER);
		initStatusButtons();
		initOrderTable();
		
		getOrders();
		
		orderTable.addValueChangeListener(new ValueChangeListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				updateButtons();
			}
		});
		
		initializeDetailTable();
		addDetailButton();
		addFooter();
	}
	
	private void initHorPageTitle() {
		// TODO Auto-generated method stub
		Label pageTitle = new Label("Active Order Status");
		pageTitle.setSizeFull();
		pageTitle.setWidth("800px");
		pageTitle.setHeight("45px");
		pageTitle.addStyleName("posPageTitle");
		horPageTitle.addComponent(pageTitle);
	}
	
	private void initStatusButtons() {
		VerticalLayout vButtonLayout = new VerticalLayout();
		vButtonLayout.setCaption("Change Status");
		vButtonLayout.setWidth("150px");
//		buttonLayout.setDefaultComponentAlignment(Alignment.TOP_CENTER);
		
		for (final String status : statusList) {
		
			statusUpdateButton = new Button(status);
			statusUpdateButton.setImmediate(true);
			statusUpdateButton.setEnabled(false);
			statusUpdateButton.setWidth("90%");
			statusUpdateButton.setHeight("27px");
			statusUpdateButton.addClickListener(new ClickListener() {
				private static final long serialVersionUID = 1L;
	
				@Override
				public void buttonClick(ClickEvent event) {
					ActiveOrder updateItem;
					
					//Get the layout value of the item select, get the item on that layout, get the property as defined earlier by string, and then cast that to the class data type that was used for that field.
					Integer selected = (Integer)orderTable.getItem(orderTable.getValue()).getItemProperty("Order #").getValue();
					//convert Integer to in for controller call
					int orderID = selected.intValue();
					
					try {
						updateItem = orderController.getActiveOrder(orderID);
//						System.out.println("Update Status: update item: " + updateItem);
						updateItem.setStatus(status);
						java.util.Date date= new java.util.Date();
						updateItem.setTimestamp(new Timestamp(date.getTime()));
						orderController.updateOrder(updateItem);
					} catch(ItemNotFoundException INFE) {
						System.out.println("Status Update: Item Not Found Exception: " + INFE);
					} finally {
						getOrders();
					}
				}
			});
			
			toggleButtonList.add(statusUpdateButton);
			if (status.equals("Cancel") || status.equals("Complete")) {
				HorizontalLayout buttonLayoutSpacing = new HorizontalLayout();
				buttonLayoutSpacing.setWidth("150px");
				if(status.equals("Cancel"))
					buttonLayoutSpacing.setHeight("35px");
				else
					buttonLayoutSpacing.setHeight("10px");
				vButtonLayout.addComponent(buttonLayoutSpacing);
			}
			vButtonLayout.addComponent(statusUpdateButton);
			vButtonLayout.setComponentAlignment(statusUpdateButton, Alignment.TOP_LEFT);
			vButtonLayout.setStyleName(Reindeer.PANEL_LIGHT);
		}
		
		buttonsAndTable.addComponent(vButtonLayout);
	}
	
	protected void initOrderTable() {
		
		VerticalLayout orderTableHolder = new VerticalLayout();
		orderTableHolder.setCaption("Active Orders");
		orderTableHolder.setWidth("650px");
		
		orderTable = new FilterTable();
		orderTable.setWidth("100%");
		orderTable.setPageLength(10);
		orderTable.setImmediate(true);
		orderTable.setSelectable(true);
		orderTable.setFilterBarVisible(true);
		orderTable.addContainerProperty("Order #", Integer.class, null);
		orderTable.addContainerProperty("Status", String.class, null);
		orderTable.addContainerProperty("Time", Date.class, null);
		orderTable.addContainerProperty("Price", Double.class, null);
		//orderTable.addContainerProperty("Order Taker", String.class, null);
		orderTable.setSortContainerPropertyId("Time");
		orderTable.setSortAscending(false);
		
		orderTableHolder.addComponent(orderTable);
		orderTableHolder.setStyleName(Reindeer.PANEL_LIGHT);
		
		buttonsAndTable.addComponent(orderTableHolder);	
	}
	
	public void getOrders() {
		orderTable.removeAllItems();
		for ( ActiveOrder AO : orderController.getAllActiveOrders() )
		{
			if( !AO.getStatus().equals("Cancel") && !AO.getStatus().equals("Complete") ) {
				orderTable.addItem(new Object[] {
						AO.getOrderID(),
						AO.getStatus(),
						AO.getTimestamp(),
						AO.getPrice(),
						//AO.getUserBean().getName()
						}, null);
			}
		}
		orderTable.sort();
	}
	
	protected void initializeDetailTable() {
		detailTable = new Table();
		detailTable.setCaption("Order Details");
		detailTable.setWidth("800px");
		detailTable.setPageLength(10);
		detailTable.setImmediate(true);
		detailTable.setSelectable(false);
		detailTable.addContainerProperty("Seq", Integer.class, null);
		detailTable.addContainerProperty("Item", String.class, null);
		detailTable.addContainerProperty("Price", Double.class, null);
		detailTable.addContainerProperty("Instructions", String.class, null);
		
		detailTable.setSortContainerPropertyId("Seq");
		detailTable.setSortAscending(true);
		
		detailTable.setVisible(false);
		detailTable.setStyleName(Reindeer.PANEL_LIGHT);
		
		root.addComponent(detailTable);
		root.setComponentAlignment(detailTable, Alignment.TOP_CENTER);
	}
	
	private void addDetailButton() {
		HorizontalLayout buttonLayout = new HorizontalLayout();
//		buttonLayout.setWidth("800px");
		buttonLayout.setSpacing(true);
		buttonLayout.setMargin(true);
		buttonLayout.setDefaultComponentAlignment(Alignment.TOP_CENTER);
		buttonLayout.setHeight("30px");
		
		detailButton = new Button("Details");
		detailButton.setImmediate(true);
		detailButton.setEnabled(false);
		detailButton.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				
				ActiveOrder orderForDetail;
				
				//Get the layout value of the item select, get the item on that layout, get the property as defined earlier by string, and then cast that to the class data type that was used for that field.
				Integer selected = (Integer)orderTable.getItem(orderTable.getValue()).getItemProperty("Order #").getValue();
				//convert Integer to in for controller call
				int orderID = selected.intValue();
				
				try {
					orderForDetail = orderController.getActiveOrder(orderID);
//					System.out.println("Order Details: detail item: " + orderForDetail);
					
					getOrderDetails(orderForDetail);
				} catch(ItemNotFoundException INFE) {
					System.out.println("Order Details: Item Not Found Exception: " + INFE);
				}
				detailButton.setVisible(false);
			}
		});
		
		toggleButtonList.add(detailButton);
		buttonLayout.addComponent(detailButton);
		root.addComponent(buttonLayout);
		root.setComponentAlignment(buttonLayout, Alignment.TOP_CENTER);
	}
	
	public void getOrderDetails(ActiveOrder orderForDetail) {
		detailTable.removeAllItems();
		for ( ActiveOrderDetailDetails AOD : orderController.getActiveOrderDetailDetails(orderForDetail) )
		{
			detailTable.addItem(new Object[] {
					AOD.getSequence(),
					AOD.getName(),
					AOD.getPrice(),
					AOD.getSpecialInstructions()
					}, null);
		}
		detailTable.sort();
		detailTable.setVisible(true);
	}
	
	private  void updateButtons() {
		detailTable.setVisible(false);
		detailButton.setVisible(true);
		orderTable.setSelectable(true);
		if (orderTable.getValue() != null) {
			for (Button button : toggleButtonList) {
				button.setEnabled(true);
			}
		} else {
			for (Button button : toggleButtonList) {
				button.setEnabled(false);
			}
		}
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		getOrders();
		
	}

	@Override
	protected void setController() {
		orderController = new OrderController();
	}
	
	@Override
	protected void getContent() {
		addStyleName("Order Tracking");
//		setMargin(true);
		setSpacing(true);
	}
}