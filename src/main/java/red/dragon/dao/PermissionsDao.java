package red.dragon.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import red.dragon.Exceptions.DuplicateItemException;
import red.dragon.pojos.Permission;

public class PermissionsDao extends AbstractDao<Permission> {

    public Permission getByName(String name) {

        Session session = getSession();
        Permission permission = null;
        try {
            permission = (Permission) session.createCriteria(Permission.class).add(Restrictions.eq("name", name))
                    .uniqueResult();
            commitNow();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }
        return permission;
    }

    @Override
    public void add(Permission permission) throws DuplicateItemException {
        Session session = getSession();
        try {
            if (!session.contains(permission)) {
                Permission temp = (Permission) session.createCriteria(Permission.class)
                        .add(Restrictions.eq("name", permission.getName())).uniqueResult();
                if (temp != null) {
                    System.out.println("Duplicate name was found. No changes made.");
                    commitNow();
                    throw new DuplicateItemException("Unable to add. Duplicate name found of: " + permission.getName());
                } else {
                    session.save(permission);
                    commitNow();
                    System.out.println("Save successful: " + permission.getName());
                }
            } else {
                System.out.println("SessionIdentifier: " + session.getIdentifier(permission));
                System.out.println("It is already Peristent. Shouln't be adding");
            }

        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

}
