package red.dragon.views;


import java.util.Calendar;
import java.util.Date;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.addon.charts.model.ListSeries;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Panel;
import com.vaadin.ui.themes.Reindeer;

















import red.dragon.controller.ReportsController;
import red.dragon.views.common.displayChart;
import red.dragon.views.master.MenuView;



public class ReportView extends MenuView {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String NAME = "REPORSTVIEW";
	VerticalLayout root = new VerticalLayout();
	HorizontalLayout horPageTitle = new HorizontalLayout();
	HorizontalLayout horLayout = new HorizontalLayout();
	VerticalLayout reportLayout = new VerticalLayout();
	VerticalLayout vReportDisplayLayout = new VerticalLayout();
	Panel tReportPanel = new Panel("Report Type");
	Panel dReportPanel = new Panel("Dispaly Report");
	ReportsController controler = new ReportsController();
	
	
	public ReportView() {
		super();
		addComponent(root);
		initHorPageTitle();
		reportType();
		initDisplayLayout();
		addFooter();
	}
	
	

	private void reportType() {
		// TODO Auto-generated method stub
		VerticalLayout vReportLayout = new VerticalLayout();
		vReportLayout.addStyleName(Reindeer.PANEL_LIGHT);
		vReportLayout.setWidth("150px");
		VerticalLayout bSpace = new VerticalLayout();
		bSpace.setHeight("25px");
		root.addComponent(bSpace);
		
		Button todayButton = new Button("TODAY");
		todayButton.setWidth("100%");
		todayButton.addClickListener(new Button.ClickListener() {
		    /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void buttonClick(ClickEvent event) {
		        //Notification.show(event.getButton().getCaption());
		    	if (event.getButton().getCaption() != null) {
					updateReportLayout(event.getButton().getCaption());
				}
		        
		    }
		    
		});
		vReportLayout.addComponent(todayButton);
		
		Button last30DaysButton = new Button("LAST 30 DAYS");
		last30DaysButton.setWidth("100%");
		last30DaysButton.addClickListener(new Button.ClickListener() {
		    /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void buttonClick(ClickEvent event) {
		    	if (event.getButton().getCaption() != null) {
					updateReportLayout(event.getButton().getCaption());
				}
		        
		    }
		    
		});
		vReportLayout.addComponent(last30DaysButton);
		
		tReportPanel.setContent(vReportLayout);
		tReportPanel.addStyleName(Reindeer.PANEL_LIGHT);
		horLayout.addComponent(tReportPanel);
		root.addComponent(horLayout);
	}

	
	protected void initDisplayLayout() {
		vReportDisplayLayout.addStyleName(Reindeer.PANEL_LIGHT);

		HorizontalLayout space = new HorizontalLayout();
		space.setWidth("10px");
		horLayout.addComponent(space);
		
		vReportDisplayLayout.addComponent(dReportPanel);
		horLayout.addComponent(vReportDisplayLayout);
		root.addComponent(horLayout);
		root.setComponentAlignment(horLayout, Alignment.TOP_CENTER);
		todayReport();
	}

	protected void updateReportLayout(String caption) {
		// TODO Auto-generated method stub
		if (caption.equals("TODAY")) {
			todayReport();
		} else if (caption.equals("LAST 30 DAYS")) {
			last30DaysReport();
		}
		
	}


	private void last30DaysReport() {
		// TODO Auto-generated method stub
		ListSeries dollarEarning = new ListSeries();
//		String[] dates = controler.getLast30DaysTransactionsDates();
		String[] dates = getLast30Dates();
		
		vReportDisplayLayout.removeAllComponents();
		
		dollarEarning = getLast30DaysData();
		displayChart chart = new displayChart();
		
		dReportPanel.setContent(chart.getDisplayChart(dates, dollarEarning));
		vReportDisplayLayout.addComponent(dReportPanel);
	}
	
	private String[] getLast30Dates() {
		// TODO Auto-generated method stub
		Calendar calendar = Calendar.getInstance();
		String[] dates = new String[30];
		for (int i=29; i>=0; i-- ) {
			dates[i] = String.valueOf(calendar.get(Calendar.MONTH)+1) + "/" + 
					String.valueOf(calendar.get(Calendar.DATE)) + "/" + 
					String.valueOf(calendar.get(Calendar.YEAR));
			calendar.add(Calendar.DAY_OF_MONTH, -1);
		}
		return dates;
	}


	private void todayReport() {
		// TODO Auto-generated method stub
		ListSeries dollarEarning = new ListSeries();
		String[] dates = new String[1];
		/* Create a DateField with the default style. */
        Calendar calendar=Calendar.getInstance();
		
		vReportDisplayLayout.removeAllComponents();
		dates[0] = String.valueOf(calendar.get(Calendar.MONTH)+1) + "/" + 
				String.valueOf(calendar.get(Calendar.DATE)) + "/" + 
				String.valueOf(calendar.get(Calendar.YEAR));
		dollarEarning = getTodayData();
		
		displayChart chart = new displayChart();
		
		dReportPanel.setContent(chart.getDisplayChart(dates, dollarEarning));
		vReportDisplayLayout.addComponent(dReportPanel);
	}

	
	private ListSeries getTodayData() {
		// TODO Auto-generated method stub
		ListSeries dollarEarning = new ListSeries();
		double number = 0;
		number = controler.getTodaysTransactionSummary();
		dollarEarning.addData(number);
		
		return dollarEarning;
	}

	
	private ListSeries getLast30DaysData() {
		ListSeries dollarEarning = new ListSeries();
		double[] number = controler.getLast30DaysTransactionsDoubles();
//		double[] number = null;
		
		if (number != null) {
			for (int i=0; i <number.length; i++) {
				dollarEarning.addData(number[i]);
			}
			return dollarEarning;
		} else {
			for (int i=0; i <30; i++) {
				dollarEarning.addData(0);
			}
			return dollarEarning;
		}
	}

	private void initHorPageTitle() {
		// TODO Auto-generated method stub
		Label pageTitle = new Label("Reports");
		pageTitle.setSizeFull();
		pageTitle.setWidth("800px");
		pageTitle.setHeight("45px");
		pageTitle.addStyleName("posPageTitle");
		horPageTitle.addComponent(pageTitle);
		root.addComponent(horPageTitle);
		root.setComponentAlignment(horPageTitle, Alignment.TOP_CENTER);
	}



	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
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
		
	}

}