package red.dragon.views.master;



import red.dragon.views.common.NavigableMenuBar;

import com.vaadin.navigator.View;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public abstract class MasterView extends VerticalLayout implements View {

	private static final long serialVersionUID = 1L;
	
	public MasterView() {
		addMenuBar();
		addTitleBar();
		setController();
		getContent();
	}
	
	private void addMenuBar() {
		NavigableMenuBar bar = new NavigableMenuBar();
		addComponent(bar);
		setComponentAlignment(bar, Alignment.TOP_CENTER);
	}

	protected void addTitleBar() {
		HorizontalLayout titleBar = new HorizontalLayout();
		titleBar.setWidth("800px");
		titleBar.setHeight("100px");
		Label title = new Label();
		title.addStyleName("title");
		title.setValue("New Dragon Diner");

		title.setHeight("100px");
		titleBar.addComponent(title);

		Label titleComment = new Label();
		titleComment.addStyleName("titleComment");
		titleComment.setSizeUndefined();
		titleBar.addComponent(titleComment);
		titleBar.setExpandRatio(title, 1.0f);
		addComponent(titleBar);
		setComponentAlignment(titleBar, Alignment.TOP_CENTER);
	}
	
	protected void addFooter() {
		HorizontalLayout footerLayout = new HorizontalLayout();
		footerLayout.setWidth("800px");
		Label footer = new Label("PRIVACY POLICY • © 2013 Dr. Jamm");
		footer.addStyleName("footer");
		footerLayout.addComponent(footer);
		addComponent(footerLayout);
		setComponentAlignment(footerLayout, Alignment.TOP_CENTER);
	}
	
	protected abstract void getContent();
	protected abstract void setController();
	
}