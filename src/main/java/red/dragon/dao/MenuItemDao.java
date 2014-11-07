package red.dragon.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import red.dragon.Exceptions.DuplicateItemException;
import red.dragon.pojos.MenuItem;

public class MenuItemDao extends AbstractDao<MenuItem> {

    public MenuItem getByName(String name) {
        Session session = getSession();
        MenuItem menuItem = null;
        try {
            menuItem = (MenuItem) session.createCriteria(MenuItem.class).add(Restrictions.eq("name", name)).list().get(0);
            commitNow();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }
        return menuItem;
    }

    public MenuItem getById(int id) {
        Session session = getSession();
        MenuItem menuItem = null;
        try {
            menuItem = (MenuItem) session.createCriteria(MenuItem.class).add(Restrictions.eq("id", id)).uniqueResult();
            commitNow();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }
        return menuItem;
    }

    @Override
    public void add(MenuItem menuItem) throws DuplicateItemException {
        Session session = getSession();
        try {
            if (!session.contains(menuItem)) {
                MenuItem temp = (MenuItem) session.createCriteria(MenuItem.class)
                        .add(Restrictions.eq("name", menuItem.getName())).uniqueResult();
                if (temp != null) {
                    System.out.println("Duplicate name was found. No changes made.");
                    commitNow();
                    throw new DuplicateItemException("Unable to add. Duplicate name found of: " + menuItem.getName());
                } else {
                    session.save(menuItem);
                    commitNow();
                    System.out.println("Save successful: " + menuItem.getName());
                }
            } else {
                System.out.println("SessionIdentifier: " + session.getIdentifier(menuItem));
                System.out.println("It is already Peristent. Shouln't be adding");
            }

        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

}