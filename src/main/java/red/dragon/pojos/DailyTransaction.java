package red.dragon.pojos;

import java.util.Calendar;

public class DailyTransaction
{
	private Calendar date;
	private int cost;
	
	public DailyTransaction(Calendar cal, int value)
	{
		date = Calendar.getInstance();
		date.setTimeInMillis(cal.getTimeInMillis());
		cost = value;
	}

	public Calendar getDate()
	{
		return date;
	}

	public void setDate(Calendar date)
	{
		this.date = date;
	}

	public int getCost()
	{
		return cost;
	}

	public void setCost(int cost)
	{
		this.cost = cost;
	}

	
}
