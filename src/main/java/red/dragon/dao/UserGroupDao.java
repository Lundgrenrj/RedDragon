package red.dragon.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import red.dragon.Exceptions.DuplicateItemException;
import red.dragon.pojos.UserGroup;

public class UserGroupDao extends AbstractDao<UserGroup> {

    public UserGroup getByName(String name) {
        Session session = getSession();
        UserGroup userGroup = null;
        try {
            userGroup = (UserGroup) session.createCriteria(UserGroup.class).add(Restrictions.eq("name", name))
                    .uniqueResult();
            commitNow();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }
        return userGroup;
    }

    @Override
    public void add(UserGroup userGroup) throws DuplicateItemException {
        Session session = getSession();
        try {
            if (!session.contains(userGroup)) {
                UserGroup temp = (UserGroup) session.createCriteria(UserGroup.class)
                        .add(Restrictions.eq("name", userGroup.getName())).uniqueResult();
                if (temp != null) {
                    System.out.println("Duplicate name was found. No changes made.");
                    commitNow();
                    throw new DuplicateItemException("Unable to add. Duplicate name found of: " + userGroup.getName());
                } else {
                    session.save(userGroup);
                    commitNow();
                    System.out.println("Save successful: " + userGroup.getName());
                }
            } else {
                System.out.println("SessionIdentifier: " + session.getIdentifier(userGroup));
                System.out.println("It is already Peristent. Shouln't be adding");
            }

        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

}
