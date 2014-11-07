package red.dragon.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import red.dragon.dao.OrderHistoryDao;
import red.dragon.pojos.OrderHistory;
import red.dragon.pojos.DailyTransaction;

public class ReportsController 
{
	private OrderHistoryDao ord;
	
	public ReportsController()
	{
			ord = new OrderHistoryDao();		
	}

	/*
	 * This method returns a summary of the value of the transactions that have been completed on the current day
	 */
	public double getTodaysTransactionSummary()
	{
		double cost = 0;
		Calendar today = Calendar.getInstance();
		Calendar[] tmp = this.getDayStartEnd(today);
		tmp[1] = this.subtractDays(tmp[1], 1);
		tmp[0] = this.getDayStartEnd(today)[1];
		
		List<OrderHistory> l = this.getDateRange(tmp[1].getTime(), tmp[0].getTime());
		
		for (int c=0;c<l.size();c++)
		{
			cost = cost+l.get(c).getPrice();
		}		
		return cost;
	}
	
	/*
	 * A simple wrapper method...
	 */
	public List<OrderHistory> getDateRange(Date d1, Date d2) 
	{
			return ord.getDateRange(d1, d2);	
	}
	
	/*
	 * This method returns an unsorted list of DailyTransaction objects that contains a Calendar object for that day and the total for that day also.
	 */
	public List<DailyTransaction> getLast30DaysTransactions()
	{
		Calendar today = Calendar.getInstance();
		Calendar[] tmp = this.getDayStartEnd(today);
		tmp[0] = this.subtractDays(tmp[0],30);
		List<OrderHistory> l = this.getDateRange(tmp[0].getTime(),tmp[1].getTime());
		return getDailyTotals(l);
	}
	
	/*
	 * This method returns a transactions sorted and arranged by day. Locations that are zero have no transactions for that day.
	 */
	public double[] getLast30DaysTransactionsDoubles()
	{
		Calendar today = Calendar.getInstance();
		Calendar[] tmp = this.getDayStartEnd(today);
		tmp[0] = this.subtractDays(tmp[0],30);
		return getDailyTotalsInDoubleForm(getLast30DaysTransactions(), 30, tmp[0]);
	}
	
	public String[] getLast30DaysTransactionsDates()
	{
		Calendar today = Calendar.getInstance();
		Calendar[] tmp = this.getDayStartEnd(today);
		tmp[0] = this.subtractDays(tmp[0],30);
		return this.getDailyTotalsDates(getLast30DaysTransactions(), 30, tmp[0]);
	}
	
	/*
	 * This method returns an array of totals for the last 30 days. If there are zeroes that means there was no transactions for that day.
	 */
	public int[] getLast30DaysTransactionsInts()
	{
		double[] d = this.getLast30DaysTransactionsDoubles();
		int[] arr = new int[d.length];
		
		for (int c=0;c<d.length;c++)
		{
			arr[c] = (int)d[c];
		}
		return arr;
	}
		
	private double[] getDailyTotalsInDoubleForm(List<DailyTransaction> list, int days, Calendar startingDate)
	{
		if (list==null || list.isEmpty())
		{
			return null;
		}
		long milsPerDay = 86400000;
		double tmpMils = 0;
		double[] arr = new double[days+1];//using double array to prevent the accumulations from loosing precision.
		Date d;
		DailyTransaction trans;
		
		for (int c=0;c<list.size();c++)
		{
			trans = list.get(c);
            tmpMils = trans.getDate().getTimeInMillis() - startingDate.getTimeInMillis();
//            System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&& "+trans.getDate().getTimeInMillis()+" "+startingDate.getTimeInMillis()+" "+(trans.getDate().getTimeInMillis() - startingDate.getTimeInMillis()));
            //2531112000 = 29.295277778
            //2583510000 = 29.901736111
            this.printCalendar(trans.getDate());
			if (tmpMils>-1)
			{
				tmpMils = tmpMils/(double)milsPerDay;
//				System.out.println("****************** "+(int)Math.round(tmpMils));
				arr[(int)Math.round(tmpMils)] = trans.getCost();
			}
			else
			{
//				System.out.println("Value not in range "+tmp);
			}
		}
		
		return arr;
	}
	
	private String[] getDailyTotalsDates(List<DailyTransaction> list, int days, Calendar startingDate)
	{
		if (list==null || list.isEmpty())
		{
			return null;
		}
		
		long milsPerDay = 86400000;
		double tmpMils = 0;
		String[] arr = new String[days+1];
		Date d;
		DailyTransaction trans;
		
		for (int c=0;c<list.size();c++)
		{
			trans = list.get(c);
			tmpMils = trans.getDate().getTimeInMillis()-startingDate.getTimeInMillis();
			if (tmpMils>-1)
			{
				tmpMils = tmpMils/(double)milsPerDay;
				arr[(int)Math.round(tmpMils)] = this.calendarToShortString(trans.getDate());
			}
			else
			{
//				System.out.println("Value not in range "+tmp);
			}
		}
		
		return arr;
	}
	
	private Date getOldestDate(List<OrderHistory> list)
	{
		int tmp = 0;
		Date d = list.get(0).getTimestamp();
		for (int c=0;c<list.size();c++)
		{//find oldest date in unsorted list.
			tmp = d.compareTo(list.get(c).getTimestamp());
			if (tmp<=0)
			{//do nothing. they are either equal or the currently selected date is after date d.
			}
			else
			{//The selected date is after date d.
				d = list.get(c).getTimestamp();
			}
		}
		return d;
	}
	
	private List<DailyTransaction> getDailyTotals(List<OrderHistory> list)
	{
		if (list==null || list.isEmpty())
		{
			return new ArrayList<DailyTransaction>();			
		}
		
		double acc = 0;
		List<DailyTransaction> r = new ArrayList<DailyTransaction>();
		List<List<OrderHistory>> dlists = sortOrdersByDay(list);
		List<OrderHistory> tmp;
		DailyTransaction t;
		Calendar cal = Calendar.getInstance();
		
		for (int c=0;c<dlists.size();c++)
		{//go through one day at a time
			tmp = dlists.get(c);
			for (int d = 0;d<tmp.size();d++)
			{//add up the transactions for that day.
				acc += tmp.get(d).getPrice();
			}
			cal.setTimeInMillis(tmp.get(0).getTimestamp().getTime());
			t = new DailyTransaction(cal, (int)acc);
			r.add(t);
			acc = 0;
		}
		
		return r;
	}
	
	private List<List<OrderHistory>> sortOrdersByDay(List<OrderHistory> list)
	{
		if (list==null || list.isEmpty())
		{
			return new ArrayList<List<OrderHistory>>();
		}
		
		HashMap<Calendar, List<OrderHistory>> map = new HashMap<Calendar, List<OrderHistory>>();
		OrderHistory orhist;
		Calendar cal = Calendar.getInstance();
		List<OrderHistory> tmpl;
		
		for (int c=0;c<list.size();c++)
		{
			orhist = list.get(c);
			cal.setTimeInMillis(orhist.getTimestamp().getTime());
			cal = this.getDayStart(cal);
			
			if (map.containsKey(cal))
			{//add item to that days order list
				map.get(cal).add(orhist);
			}
			else
			{//add that days order list to the map
				tmpl = new ArrayList<OrderHistory>();
				tmpl.add(orhist);
				map.put(cal,tmpl);				
			}
		}
		return new ArrayList<List<OrderHistory>>(map.values());
	}
		
	public void printCalendar(String s, Calendar cal, String s2)
	{		
		System.out.println(s+this.calendarToString(cal)+s2);
	}
	
	public void printCalendar(Calendar cal)
	{
		System.out.println(this.calendarToString(cal));
	}
	
	public String calendarToString(Calendar cal)
	{
		return new String((cal.get(Calendar.MONTH)+1)+"/" +cal.get(Calendar.DAY_OF_MONTH)+"/"+cal.get(Calendar.YEAR)+" "+cal.get(Calendar.HOUR)+":"+cal.get(Calendar.MINUTE)+":"+cal.get(Calendar.SECOND));
	}
	
	public String calendarToShortString(Calendar cal)
	{
		return new String((cal.get(Calendar.MONTH)+1)+"/" +cal.get(Calendar.DAY_OF_MONTH)+"/"+cal.get(Calendar.YEAR));
	}
	
	/*
	 * This method determines if the given calendar object (theDate) is within the date ranges start and end.
	 */
	private boolean isWithinRange(Calendar theDate, Calendar start, Calendar end)
	{
		if (theDate.compareTo(start)>=0 && theDate.compareTo(end)<=0)
		{
			return true;
		}
		return false;
	}
	
	/*
	 * This method sets the calendar object to the beginning of the day.
	 */
	private Calendar getDayStart(Calendar cal)
	{
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);			
		return cal;
	}
	
	/*
	 * This method is used to get two calendar objects the first being the beginning of the day, and the last being the end of the day
	 */
	private Calendar[] getDayStartEnd(Calendar cal)
	{
		Calendar[] day = new Calendar[2];
		day[0] = Calendar.getInstance();//beginning of the day
		day[1] = Calendar.getInstance();//end of the day
		
		//set start to beginning of day
		day[0].setTimeInMillis(cal.getTimeInMillis());
		day[0].set(Calendar.HOUR, 0);
		day[0].set(Calendar.MINUTE, 0);
		day[0].set(Calendar.SECOND, 0);
		day[0].set(Calendar.MILLISECOND, 0);			
		
		//set end to the very last second of the day.
		day[1].setTimeInMillis(cal.getTimeInMillis());
		day[1].set(Calendar.HOUR, 0);
		day[1].set(Calendar.MINUTE, 0);
		day[1].set(Calendar.SECOND, 0);
		day[1].set(Calendar.MILLISECOND, 0);	
		day[1].roll(Calendar.HOUR, -1);
		day[1].roll(Calendar.MINUTE, -1);
		day[1].roll(Calendar.SECOND, -1);
		day[1].roll(Calendar.MILLISECOND, -1);
		//TODO test method
		return day;
	}
	
	/*
	 * This method shifts the date minus offset value
	 */
	private Calendar subtractDays(Calendar cal, int subtract)
	{
		int tmp = cal.get(Calendar.DAY_OF_YEAR);

		if (tmp>subtract)
		{	
			subtract = 0-subtract;
			cal.roll(Calendar.DAY_OF_YEAR, subtract);	
		}
		else
		{//move into previous year. set proper date
			
			cal.roll(Calendar.YEAR, 0);
			tmp = subtract-tmp;
			cal.set(Calendar.DAY_OF_YEAR, 0);
			cal.roll(Calendar.DAY_OF_YEAR, tmp*-1);
		}
		return cal;
	}
	
}
