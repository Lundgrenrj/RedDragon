package red.dragon.dao;

import red.dragon.Exceptions.DuplicateItemException;
import red.dragon.pojos.*;

public class TestMain {
    static ActiveOrderDao activeOrderDao = new ActiveOrderDao();
//    static ActiveOrderDetailDao activeOrderDetailDao = new ActiveOrderDetailDao();
    static BundleDao bundleDao = new BundleDao();
    static ContactInfoDao contactInfoDao = new ContactInfoDao();
    static CustomerDao customerDao = new CustomerDao();
    static FoodCategoryDao foodCategoryDao = new FoodCategoryDao();
    static MenuDao menuDao = new MenuDao();
    static MenuItemDao menuItemDao = new MenuItemDao();
    static OrderHistoryDao orderHistoryDao = new OrderHistoryDao();
    static PermissionsDao permissiondDao = new PermissionsDao();
    static UserDao userDao = new UserDao();
    static UserGroupDao userGroupDao = new UserGroupDao();

    public static void main(String[] args) {
        ActiveOrder activeOrder1 = new ActiveOrder();
        ActiveOrderDetail activeOrderDetail1 = new ActiveOrderDetail();
        Bundle bundle1 = new Bundle();
        ContactInfo contactInfo1 = new ContactInfo();
        Customer customer1 = new Customer();
        FoodCategory foodCategory1 = new FoodCategory();
        Menu menu1 = new Menu();
        MenuItem menuItem1 = new MenuItem();
        OrderHistory orderHistory1 = new OrderHistory();
        Permission permissions1 = new Permission();
        User user1 = new User();
        UserGroup userGroup1 = new UserGroup();

        activeOrderDetail1.setMenuItem(menuItemDao.getById(176));
        activeOrderDetail1.setSpecialInstruction("Food Instruction 2");
        
        
        activeOrder1.setStatus("testingStatus2");
        activeOrder1.addActiveOrderDetail(activeOrderDetail1);
        activeOrder1.setCustomer(customerDao.getByID(1));
        
        submitOrder(activeOrder1);
        
        /*
         * bundleDao.add(bundle1); contactInfoDao.add(contactInfo1);
         * customerDao.add(customer1); foodCategoryDao.add(foodCategory1);
         * menuDao.add(menu1); menuItemDao.add(menuItem1);
         * orderHistoryDao.add(orderHistory1);
         * permissiondDao.add(permissions1); userDao.add(user1);
         * userGroupDao.add(userGroup1);
         */

    }

    static public void submitOrder(ActiveOrder order) {
        try {
            activeOrderDao.add(order);
            
            for (int i = 0; i < order.getActiveOrderDetails().size(); i++) {
                System.out.println("ActiveOrderDetails special Instructions  is: "+ order.getActiveOrderDetails().get(i).getSpecialInstruction());
                System.out.println("ActiveOrder Status : " + order.getStatus());
//                activeOrderDetailDao.add(order.getActiveOrderDetails().get(i));
            }
        } catch (DuplicateItemException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
