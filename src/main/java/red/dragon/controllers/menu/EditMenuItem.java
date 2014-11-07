package red.dragon.controllers.menu;

import org.tepi.filtertable.FilterTable;

import red.dragon.ViewModels.MenuItemVM;
import red.dragon.views.master.AddEditMenuView;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class EditMenuItem extends AddEditMenuView
{

	private static final long serialVersionUID = 1L;
	private FilterTable menuTable;

	public EditMenuItem(FilterTable menuTable, BeanItemContainer<MenuItemVM> container)
	{
		super();
		setCaption("Edit Menu Item");
		this.container = container;
		this.menuTable = menuTable;
		setFormFieldValues();
	}

	@Override
	protected void getButtonListeners()
	{
		addEditButton.setCaption("Edit");
		addEditButton.addClickListener(new ClickListener()
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event)
			{
				try
				{
					MenuItemVM itemSelected = (MenuItemVM) menuTable.getValue();
					int itemId = itemSelected.getId();
					//String menuName = (String) menuNameComboBox.getValue();
					String itemName = itemNameTextField.getValue();
					String description = descriptionTextField.getValue();
					Double price = Double.parseDouble(priceTextField.getValue());
					String pictureString = pictureTextField.getValue();
					String foodCategory = (String) foodCategoryComboBox.getValue();
//					api.editMenuItem(itemId, itemName, description,
//							price, pictureString, foodCategory, container);
//					controler.editMenuItem(itemId, itemName, description, price, pictureString, foodCategory);
					controler.editMenuItem(itemSelected);
					container.addAll(controler.getAllMenuItemVMs());
					Notification.show("Edit Me!");
					close();
					menuTable.setSelectable(true);

				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	private void setFormFieldValues()
	{
		MenuItemVM selectedItem = (MenuItemVM) menuTable.getValue();
		itemNameTextField.setValue(selectedItem.getName());
//		descriptionTextField.setValue(selectedItem.getDescription());
		descriptionTextField.setValue("Description");
		priceTextField.setValue(Double.toString(selectedItem.getPrice()));
//		pictureTextField.setValue(selectedItem.getPicture());
		foodCategoryComboBox.setValue(selectedItem.getFoodCategoryName());
	}
}