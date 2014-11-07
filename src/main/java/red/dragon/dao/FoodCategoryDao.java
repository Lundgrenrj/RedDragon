package red.dragon.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import red.dragon.pojos.FoodCategory;

public class FoodCategoryDao extends AbstractDao<FoodCategory> {

    public FoodCategory getByName(String name) {
        Session session = getSession();
        FoodCategory foodCategory = null;
        try {
            foodCategory = (FoodCategory) session.createCriteria(FoodCategory.class).add(Restrictions.eq("name", name))
                    .uniqueResult();
            commitNow();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return foodCategory;
    }
}
