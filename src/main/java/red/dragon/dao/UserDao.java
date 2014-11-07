package red.dragon.dao;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import red.dragon.Exceptions.DuplicateItemException;
import red.dragon.pojos.User;

public class UserDao extends AbstractDao<User> {
    User user = null;

    public User getByName(String name) {
        Session session = getSession();
        try {
            user = (User) session.createCriteria(User.class).add(Restrictions.eq("name", name)).uniqueResult();
            commitNow();
        } catch (HibernateException e) {
            getSession().getTransaction().rollback();
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public void add(User user) throws DuplicateItemException {
        Session session = getSession();
        try {
            if (!session.contains(user)) {
                User temp = (User) session.createCriteria(User.class).add(Restrictions.eq("name", user.getName()))
                        .uniqueResult();
                if (temp != null) {
                    System.out.println("Duplicate name was found. No changes made.");
                    commitNow();
                    throw new DuplicateItemException("Unable to add. Duplicate name found of: " + user.getName());
                } else {
                    session.save(user);
                    commitNow();
                    System.out.println("Save successful: " + user.getName());
                }
            } else {
                System.out.println("SessionIdentifier: " + session.getIdentifier(user));
                System.out.println("It is already Peristent. Shouln't be adding");
            }

        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }
}
