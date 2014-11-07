package red.dragon.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import red.dragon.Exceptions.DuplicateItemException;
import red.dragon.pojos.Customer;

public class CustomerDao extends AbstractDao<Customer> {

    public Customer getByName(String name) {

        Session session = getSession();
        Customer customer = null;
        try {
            customer = (Customer) session.createCriteria(Customer.class).add(Restrictions.eq("name", name))
                    .uniqueResult();
            commitNow();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }
        return customer;
    }

    @Override
    public void add(Customer customer) throws DuplicateItemException {
        Session session = getSession();
        try {
            if (session.contains(customer)) {
                Customer temp = (Customer) session.createCriteria(Customer.class)
                        .add(Restrictions.eq("name", customer.getName())).uniqueResult();
                if (temp != null) {
                    System.out.println("Duplicate name was found. No changes made.");
                    commitNow();
                    throw new DuplicateItemException("Unable to add. Duplicate name found of: " + customer.getName());
                } else {
                    session.save(customer);
                    commitNow();
                    System.out.println("Save successful: " + customer.getName());
                }
            } else {
                System.out.println("SessionIdentifier: " + session.getIdentifier(customer));
                System.out.println("It is already Peristent. Shouln't be adding");
            }

        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }
}
