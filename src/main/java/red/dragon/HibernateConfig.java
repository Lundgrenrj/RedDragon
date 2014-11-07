package red.dragon;

import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.search.cfg.SearchMapping;

public class HibernateConfig {

	private static EntityManager entityManager;
	static SearchMapping mapping;

	public static EntityManager createEntityManager() {
		if (entityManager == null) {
			entityManager = getEntityManager();
		}
		return entityManager;
	}

	public static EntityManager getEntityManager() {

		Properties properties = new Properties();

//		properties.put("connection.provider_class", "org.hibernate.connection.C3P0ConnectionProvider");
//		properties.put("hibernate.c3p0.testConnectionOnCheckout", "true");
//		properties.put("hibernate.c3p0.acquire_increment", "3");
//		properties.put("hibernate.c3p0.idle_test_period", "14400");
//		properties.put("hibernate.c3p0.max_size", "100");
//		properties.put("hibernate.c3p0.max_statements", "100");
//		properties.put("hibernate.c3p0.min_size", "3");
//		properties.put("hibernate.c3p0.timeout", "25200");
//		
//		properties.put("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
//		properties.put("hibernate.connection.password", "reddragon");
//		properties.put("hibernate.connection.url", "jdbc:mysql://db.luivillage.com:3306/reddragon");
//		properties.put("hibernate.connection.username", "reddragon");
//		properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");



		properties.put("javax.persistence.provider", "org.hibernate.ejb.HibernatePersistence");
		properties.put("javax.persistence.transactionType", "RESOURCE_LOCAL");
		
		properties.put("javax.persistence.jdbc.url", "jdbc:mysql://db.luivillage.com:3306/reddragon");
//		properties.put("javax.persistence.jdbc.url", "jdbc:mysql://localhost:3306/reddragon");


		properties.put("javax.persistence.jdbc.user", "reddragon");
//		properties.put("javax.persistence.jdbc.user", "root");
		properties.put("javax.persistence.jdbc.password", "reddragon");
//		properties.put("javax.persistence.jdbc.password", "cs4350");
		properties.put("javax.persistence.jdbc.driver", "com.p6spy.engine.spy.P6SpyDriver");


		properties.put("hibernate.hbm2ddl.auto", "update");
		properties.put("hibernate.show_sql", "false");
		properties.put("hibernate.format_sql", "false");
		properties.put("hibernate.current_session_context_class", "managed");
		properties.put("hibernate.transaction.factory_class", "org.hibernate.transaction.JDBCTransactionFactory");
		properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
		
		
		
		

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("reddragon", properties);
		EntityManager em = emf.createEntityManager();
		return em;
	}

}
