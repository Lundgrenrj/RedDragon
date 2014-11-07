package red.dragon.controllers.menu;

import red.dragon.ViewModels.MenuItemVM;
import red.dragon.views.master.AddEditMenuView;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification;

public class AddMenuItem extends AddEditMenuView
{
	
	private static final long serialVersionUID = 1L;

	public AddMenuItem(BeanItemContainer<MenuItemVM> container)
	{
		super();
		setCaption("Add Menu Item");
		this.container = container;
	}
	
	@Override
	protected void getButtonListeners() {
		addEditButton.setCaption("Add");
		addEditButton.addClickListener(new ClickListener()
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event)
			{
				try
				{
					String itemName = itemNameTextField.getValue();
					String description = descriptionTextField.getValue();
					Double price = Double.parseDouble(priceTextField.getValue());
					String pictureString = pictureTextField.getValue();
					String foodCategory = (String) foodCategoryComboBox.getValue();
//					api.addMenuItem(itemName, description, price,
//							pictureString, foodCategory, container);
					MenuItemVM item = new MenuItemVM();
					item.setName(itemName);
					item.setPrice(price);
//					item.setPicture(pictureString);
					item.setFoodCategoryName(foodCategory);
//					controler.addMenuItem(itemName, description, price, pictureString, foodCategory);
					controler.createMenuItem(item);
					Notification.show("Add Me!");
					container.addItem(item);
					close();
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
		
	}
}
