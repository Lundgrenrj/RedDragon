package red.dragon.views;

import com.vaadin.data.Item;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;

public class MenuItemDetail {
	private String pathString = "images/";

	public MenuItemDetail(Item item) {
		String itemNameString = "";
		String itemDescString = "";
		String itemFoodCategory = "";
		String itemPictureString = "";

		if (item.getItemProperty("itemName").getValue() != null) {
			itemNameString = item.getItemProperty("itemName").getValue()
					.toString();
		}

		if (item.getItemProperty("description").getValue() != null) {
			itemDescString = item.getItemProperty("description").getValue()
					.toString();
		}

		if (item.getItemProperty("foodCategory").getValue() != null) {
			itemFoodCategory = item.getItemProperty("foodCategory").getValue()
					.toString();
		}

		if (item.getItemProperty("pictureString").getValue() != null) {
			itemPictureString = item.getItemProperty("pictureString")
					.getValue().toString();
		}

		ThemeResource resource = new ThemeResource(pathString
				+ itemPictureString);

		Window subWindow = new Window("Name: " + itemNameString);
		VerticalLayout content = new VerticalLayout();
		GridLayout grid = new GridLayout(2, 100);
		Panel panel1 = new Panel();

		Image image = new Image(null, resource);

		Label labelFoodCategory = new Label("Food Category: "
				+ itemFoodCategory);
		Label labelDesc = new Label("Description: " + itemDescString);

		grid.setWidth("500px");
		grid.setHeight("200px");
		panel1.setContent(image);
		grid.addComponent(image, 0, 0, 0, 99);
		grid.addComponent(labelFoodCategory, 1, 0);
		grid.addComponent(labelDesc, 1, 1);

		Button button = new Button("Add to cart", new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				
				System.out.println("You Clicked on Add to cart");
			}
		});

		grid.addComponent(button, 1, 99);
		grid.setComponentAlignment(button, Alignment.BOTTOM_LEFT);
		grid.setComponentAlignment(image, Alignment.TOP_CENTER);

		content.addStyleName("contentStyle");
		subWindow.addStyleName("subWindowStyle");
		grid.addStyleName("gridBorder");
		grid.setWidth("100%");
		grid.setHeight("100%");
		content.addComponent(grid);
		content.setComponentAlignment(grid, Alignment.TOP_CENTER);

		subWindow.setWidth("600px");
		subWindow.setHeight("500px");

		content.setMargin(new MarginInfo(true, false, true, false));

		content.setSizeFull();
		subWindow.setModal(true);
		subWindow.setDraggable(false);
		subWindow.setResizable(false);

		subWindow.setContent(content);

		// Attach it to the root component
		UI.getCurrent().addWindow(subWindow);

	}

}
