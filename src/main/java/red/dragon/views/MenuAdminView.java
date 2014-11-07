package red.dragon.views;

import red.dragon.Exceptions.ItemNotFoundException;
import red.dragon.ViewModels.MenuItemVM;
import red.dragon.controller.MenuAdminController;
import red.dragon.controllers.menu.AddMenuItem;
import red.dragon.controllers.menu.EditMenuItem;
import red.dragon.views.master.MenuView;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class MenuAdminView extends MenuView {

	private static final long serialVersionUID = 1L;
	public static final String NAME = "MENUADMIN";
	private  Button editButton;
	private Button deleteButton;
	private Button addButton;

	public MenuAdminView() {
		super();
		
		menuTable.setWidth("800px");
		menuTable.setPageLength(5);
		menuTable.addValueChangeListener(new ValueChangeListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				updateButtons();
			}

		});
		 
		setWidth("100%");
		getButtons();
		addFooter();
	}

	private void getButtons() {
		HorizontalLayout buttonLayout = new HorizontalLayout();

		buttonLayout.setWidth("800px");
		buttonLayout.setSpacing(true);
		buttonLayout.setMargin(true);

		addButton = new Button("Add Menu Item");
		editButton = new Button("Edit Menu Item");
		deleteButton = new Button("Delete Menu Item");

		addButton.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				AddMenuItem item = new AddMenuItem(container);
				UI.getCurrent().addWindow(item);
				editButton.setEnabled(false);
				deleteButton.setEnabled(false);
			}
		});
		buttonLayout.addComponent(addButton);

		editButton.setImmediate(true);
		editButton.setEnabled(false);
		editButton.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				EditMenuItem item = new EditMenuItem(menuTable, container);
				UI.getCurrent().addWindow(item);
				editButton.setEnabled(false);
				deleteButton.setEnabled(false);
			}
		});
		buttonLayout.addComponent(editButton);

		deleteButton.setImmediate(true);
		deleteButton.setEnabled(false);
		deleteButton.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				MenuItemVM itemSelected = (MenuItemVM) menuTable.getValue();
//				int itemId = itemSelected.getItemId();
//				api.deleteMenuItem(itemId, container);
				try {
					controler.deleteMenuItem(itemSelected);
				} catch (ItemNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				menuTable.removeItem(itemSelected);
				editButton.setEnabled(false);
				deleteButton.setEnabled(false);
			}

		});
		buttonLayout.addComponent(deleteButton);
		addComponent(buttonLayout);
		setComponentAlignment(buttonLayout, Alignment.TOP_CENTER);
	}

	private  void updateButtons() {
		addButton.setEnabled(true);
		menuTable.setSelectable(true);
		if (menuTable.getValue() != null) {
			editButton.setEnabled(true);
			deleteButton.setEnabled(true);
		} else {
			editButton.setEnabled(false);
			deleteButton.setEnabled(false);
		}
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
