package red.dragon.views.master;

import red.dragon.ViewModels.MenuItemVM;
import red.dragon.controller.interfaces.IMenuAdmin;
import red.dragon.controllers.MenuController;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Window;

public abstract class AddEditMenuView extends Window {

	private static final long serialVersionUID = 1L;
	protected ComboBox menuNameComboBox;
	
	protected TextField itemNameTextField;
	protected TextField priceTextField;
	protected TextField descriptionTextField;
	protected ComboBox foodCategoryComboBox;
	protected TextField pictureTextField;
	protected Button addEditButton;
	protected HorizontalLayout layout;
	protected BeanItemContainer<MenuItemVM> container;
	protected IMenuAdmin controler;
	
	public AddEditMenuView() {
		layout = new HorizontalLayout();
		layout.setSpacing(true);
		layout.setMargin(true);
		controler = new MenuController();
		setContent(layout);
		setHeight("200px");
		getFormFields();
		getButtons();
		getButtonListeners();
		setModal(true);
	}

	protected void getButtons()
	{
		addEditButton = new Button("");
		layout.addComponent(addEditButton);

		Button cancelButton = new Button("Cancel");
		cancelButton.addClickListener(new ClickListener()
		{

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event)
			{
				close();
			}

		});

		layout.addComponent(cancelButton);
	}
	
	protected void getFormFields()
	{

		Label menuIDLabel = new Label("Menu's: ");
//		menuNameComboBox = new ComboBox();
//		List<String> menuItemList = api.
//		for(String name : menuItemList)
//			menuNameComboBox.addItem(name);
		layout.addComponent(menuIDLabel);
//		layout.addComponent(menuNameComboBox);

		Label itemNameLabel = new Label("Item Name: ");
		itemNameTextField = new TextField();
		layout.addComponent(itemNameLabel);
		layout.addComponent(itemNameTextField);

		Label descriptionLabel = new Label("Description");
		descriptionTextField = new TextField();
		layout.addComponent(descriptionLabel);
		layout.addComponent(descriptionTextField);

		Label priceLabel = new Label("Price: ");
		priceTextField = new TextField();
		layout.addComponent(priceLabel);
		layout.addComponent(priceTextField);

		Label pictureLabel = new Label("Picture: ");
		pictureTextField = new TextField();
		layout.addComponent(pictureLabel);
		layout.addComponent(pictureTextField);

		Label foodCategoryLabel = new Label("Food Category: ");
		foodCategoryComboBox = new ComboBox();
		String[] foodCategoriesList = controler.getAllFoodCategories();
		for(String category : foodCategoriesList)
			foodCategoryComboBox.addItem(category);
		layout.addComponent(menuIDLabel);
		layout.addComponent(foodCategoryLabel);
		layout.addComponent(foodCategoryComboBox);

	}
	
	protected abstract void getButtonListeners();
}
