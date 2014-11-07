package red.dragon.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.context.internal.ManagedSessionContext;
import org.hibernate.ejb.HibernateEntityManager;

import red.dragon.HibernateConfig;
import red.dragon.Exceptions.DuplicateItemException;
import red.dragon.Exceptions.ItemNotFoundException;
import red.dragon.daoInterfaces.ICommonInterface;

public abstract class AbstractDao<T> implements ICommonInterface<T> {

    protected static final Log log = LogFactory.getLog(AbstractDao.class);
    protected Class<T> entityClass;

    String test;

    @PersistenceContext(type = PersistenceContextType.TRANSACTION)
    public EntityManager entityManager;
    HibernateEntityManager hem;

    // Creates JPA EntityManager from the configurations in HibernateConfig
    @SuppressWarnings("unchecked")
    public AbstractDao() {
        entityManager = HibernateConfig.createEntityManager();
        HibernateEntityManager hem = entityManager.unwrap(HibernateEntityManager.class);
        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        this.entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
    }

    // Returns all Objects of Type T
    @SuppressWarnings("unchecked")
    public List<T> getAll(Class<T> classObject) {
        Criteria crit = getSession().createCriteria(classObject);
        return crit.list();
    }

    // Returns a single object of T, throws item not found exception
    @SuppressWarnings("unchecked")
    public T getByID(int id) {
        System.out.println("getting Object instance with id: " + id);
        try {
            System.out.println(entityClass);
            T object = (T) getSession().get(entityClass, id);
            System.out.println("Find successful");
            return object;
        } catch (RuntimeException re) {
            log.error("Find failed", re);
            throw re;
        }
    }

    // Adds this object of type T to the T table
    public void add(T object) throws DuplicateItemException {
        System.out.println("Saving Object instance");
        try {

            if (!getSession().contains(object)) {
                System.out.println("Object is not persistent. Adding it and making persistent");
                getSession().save(object);
                commitNow();
                System.out.println("Save successful");
            } else {
                System.out.println("Object is already persistent. Not added.");
            }

        } catch (RuntimeException re) {
            log.error("Add failed", re);
            re.printStackTrace();
        }
    }

    // Updates this object of type T to the T table. Returns true if successful.
    public boolean update(T object) throws DuplicateItemException {
        System.out.println("Saving Object instance");
        try {
            if (getSession().contains(object)) {
                System.out.println("Object is persistent. Updating object");
                commitNow();
                System.out.println("Update successful");
                return true;
            } else {
                System.out.println("Did not update. It first needs to be made persistent");
                return false;
            }
        } catch (RuntimeException re) {
            log.error("Update failed", re);
            throw new DuplicateItemException("Unable to add. Duplicate name found");
        }
    }

    // Removes this object of type T from the T table. Returns true if
    // successful.
    public boolean delete(T object) {
        System.out.println("Deleting Object instance");
        try {
            this.remove(object);
            commitNow();
            System.out.println("Delete successful");
            return true;
        } catch (RuntimeException re) {
            log.error("Remove failed", re);
            throw re;
        }
    }

    // Removes object with this id from table. Returns true if successful.
    public boolean deleteByID(int id) throws ItemNotFoundException {
        System.out.println("Deleting Object with ID: " + id);
        try {
            T object = getByID(id);
            if (object != null) {
                this.remove(object);
                commitNow();
                System.out.println("Delete successful");
                return true;
            } else {
                System.out.println("No object with id: " + id);
                return false;
            }
        } catch (RuntimeException re) {
            log.error("Remove failed", re);
            throw re;
        }
    }

    public void closeConnection() {
        entityManager.close();
    }

    private void persist(T transientInstance) {
        System.out.println("Persisting Object instance");
        try {
            entityManager.persist(transientInstance);
            System.out.println("Persist successful");
        } catch (RuntimeException re) {
            log.error("Persist failed", re);
            throw re;
        }
    }

    private void saveOrUpdate(T transientInstance) {
        System.out.println("Persisting Object instance");
        try {
            getSession().saveOrUpdate(transientInstance);
            System.out.println("Persist successful");
        } catch (RuntimeException re) {
            log.error("Persist failed", re);
            throw re;
        }
    }

    private T merge(T detachedInstance) {
        System.out.println("merging Object instance");
        try {
            T result = entityManager.merge(detachedInstance);
            System.out.println("Merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("Merge failed", re);
            throw re;
        }
    }

    private void remove(T persistentInstance) {
        System.out.println("removing Object instance");
        try {
            getSession().delete(persistentInstance);
            System.out.println("Remove successful");
        } catch (RuntimeException re) {
            log.error("Remove failed", re);
            throw re;
        }
    }

    protected Session getSession() {
        HibernateEntityManager hem = entityManager.unwrap(HibernateEntityManager.class);
        Session currentSession = hem.getSession();

        if (!currentSession.isOpen()) {
            System.out.println("It is closed!!!!!!!!!!!!!!!!!!");
            currentSession = createNewSession();
        }

        if (!currentSession.getTransaction().isActive()) {
            currentSession.beginTransaction();
        }
        return currentSession;
    }

    protected void commitNow() {
        HibernateEntityManager hem = entityManager.unwrap(HibernateEntityManager.class);
        Session currentSession = hem.getSession();
        ManagedSessionContext.unbind(((Session) entityManager.getDelegate()).getSessionFactory());
        currentSession.flush();
        currentSession.getTransaction().commit();
        // currentSession.close();
    }

    private void closeSession() {
        HibernateEntityManager hem = entityManager.unwrap(HibernateEntityManager.class);
        Session currentSession = hem.getSession();
        if (currentSession.getTransaction().isActive()) {
            currentSession.getTransaction().commit();
        }
        currentSession.flush();
        currentSession.close();
    }

    protected Session createNewSession() {
        Session session = ((Session) entityManager.getDelegate()).getSessionFactory().openSession();
        session.setFlushMode(FlushMode.MANUAL);
        ManagedSessionContext.bind(session);
        return (Session) session;
    }

    /**
     * Start a new Transaction in the given session
     * 
     * @param session
     *            The session to create the transaction in
     */
    protected void startNewTransaction(Session session) {
        session.beginTransaction();
    }

    /**
     * Shortcut method that creates a new session and begins a transaction in it
     * 
     * @return A new session with a transaction started
     */
    protected Session createNewSessionAndTransaction() {
        Session session = createNewSession();
        startNewTransaction(session);
        return session;
    }
    
    public void refreshObject(Object object) {
        HibernateEntityManager hem = entityManager.unwrap(HibernateEntityManager.class);
        Session currentSession = hem.getSession();
        currentSession.refresh(object);
    }
    

}
