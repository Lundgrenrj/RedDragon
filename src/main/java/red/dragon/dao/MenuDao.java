package red.dragon.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import red.dragon.pojos.Menu;

public class MenuDao extends AbstractDao<Menu> {

    public Menu getByName(String name) {

        Session session = getSession();
        Menu menu = null;
        try {
            menu = (Menu) session.createCriteria(Menu.class).add(Restrictions.eq("name", name)).uniqueResult();
            commitNow();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return menu;
    }

}
