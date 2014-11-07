package red.dragon.dao;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import red.dragon.pojos.OrderHistory;


public class OrderHistoryDao extends AbstractDao<OrderHistory> {
    OrderHistory orderHistory = null;

    public List<OrderHistory> getDateRange(Date d1, Date d2) {
        Session session = getSession();
        Criteria crit = session.createCriteria(OrderHistory.class);
        crit.add(Restrictions.between("timestamp", d1, d2));
        List<OrderHistory> list = crit.list();
        Iterator<OrderHistory> itr = list.iterator();
        while (itr.hasNext()) {
            orderHistory = itr.next();
            /*
             * System.out.printf("\t"); System.out.println("Order ID: " +
             * activeOrder.getOrderID()); System.out.printf("\t");
             * System.out.println("Price: " + activeOrder.getPrice());
             * System.out.printf("\t"); System.out.println("Status: " +
             * activeOrder.getStatus());
             */
        }
        return list;

	}

}
