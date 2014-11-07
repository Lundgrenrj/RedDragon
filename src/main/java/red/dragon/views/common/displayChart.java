package red.dragon.views.common;

import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.HorizontalAlign;
import com.vaadin.addon.charts.model.Labels;
import com.vaadin.addon.charts.model.ListSeries;
import com.vaadin.addon.charts.model.PlotOptionsColumn;
import com.vaadin.addon.charts.model.XAxis;
import com.vaadin.addon.charts.model.YAxis;
import com.vaadin.addon.charts.model.style.Style;

public class displayChart {

	public displayChart() {
		
	}
	
	public Chart getDisplayChart(String[] dates, ListSeries dollarEarning) {
		
		Chart chart = new Chart(ChartType.COLUMN);
		chart.setWidth("640px");
		chart.setHeight("450px");
		        
		// Modify the default configuration a bit
		Configuration conf = chart.getConfiguration();
		conf.setTitle("Total Earnings");
		conf.setSubTitle("Total in Dollars Per Day");
		conf.getLegend().setEnabled(false); // Disable legend
		        
		PlotOptionsColumn plotOptions = new PlotOptionsColumn();
		plotOptions.setPointWidth(5);
		conf.setPlotOptions(plotOptions);
		        
		// The data
		conf.addSeries(dollarEarning);

		// Set the category labels on the axis correspondingly
		XAxis xaxis = new XAxis();
		Labels labels = new Labels();
        labels.setRotation(-45);
        labels.setAlign(HorizontalAlign.RIGHT);
        Style style = new Style();
        style.setFontSize("13px");
        style.setFontFamily("Verdana, sans-serif");
        labels.setStyle(style);
        xaxis.setLabels(labels);
		xaxis.setCategories(dates);

		xaxis.setTitle("Date");
		conf.addxAxis(xaxis);

		// Set the Y axis title
		YAxis yaxis = new YAxis();
		yaxis.setTitle("Earnings");
		yaxis.getLabels().setFormatter(
		  "function() {return \'$\' + Math.floor(this.value);}");
		yaxis.getLabels().setStep(2);
		conf.addyAxis(yaxis);
		       
		return chart;
	}
}
