package red.dragon.dao;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import red.dragon.Exceptions.DuplicateItemException;
import red.dragon.pojos.ActiveOrder;

public class ActiveOrderDao extends AbstractDao<ActiveOrder> {

    ActiveOrder activeOrder = null;

    public List<ActiveOrder> getDateRange(Date d1, Date d2) {
        Session session = getSession();
        Criteria crit = session.createCriteria(ActiveOrder.class);
        crit.add(Restrictions.between("timestamp", d1, d2));
        List<ActiveOrder> list = crit.list();
        Iterator<ActiveOrder> itr = list.iterator();
        while (itr.hasNext()) {
            activeOrder = itr.next();
        }
        return list;


    }

    @Override
    public void add(ActiveOrder activeOrder) throws DuplicateItemException {
        Session session = getSession();
        try {
            if (!session.contains(activeOrder)) {
                session.saveOrUpdate(activeOrder);
                commitNow();
                System.out.println("Save successful for activeOrder ID: " + activeOrder.getOrderID());

            } else {
                System.out.println("It is already Peristent. Shouln't be adding");
            }

        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }
}
