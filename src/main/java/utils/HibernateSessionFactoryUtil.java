package utils;

import dataAccess.account.Account;
import dataAccess.client.Client;
import dataAccess.creditType.CreditType;
import dataAccess.department.Department;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateSessionFactoryUtil {
    private static SessionFactory sessionFactory;

    private HibernateSessionFactoryUtil() {}

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            Configuration configuration = new Configuration().configure();
            configuration.addAnnotatedClass(Account.class);
            configuration.addAnnotatedClass(Client.class);
            configuration.addAnnotatedClass(CreditType.class);
            configuration.addAnnotatedClass(Department.class);
            StandardServiceRegistryBuilder builder =
                    new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
            sessionFactory = configuration.buildSessionFactory(builder.build());
        }
        if (sessionFactory == null){
            throw new RuntimeException("sessionFactory did not initialized");
        }
        return sessionFactory;
    }
}
