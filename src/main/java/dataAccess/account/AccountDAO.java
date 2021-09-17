package dataAccess.account;

import dataAccess.GenericDAO;
import dataAccess.client.Client;
import org.hibernate.Session;
import org.hibernate.query.Query;
import utils.HibernateSessionFactoryUtil;

import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;

public class AccountDAO extends GenericDAO<Account>{

    public List<Account> getAccountsOfSpecifiedClient(long clientID){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Query<Account> q = session.createQuery("select acc from Account acc " +
                "where acc.client.clientID = :clientID", Account.class)
                .setParameter("clientID", clientID);
        q.getResultList();
        List<Account> accounts = q.list();
        session.close();
        return accounts;
    }

    public List<Account> getAccountsOfSpecifiedCreditType(long creditTypeID){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Query<Account> q = session.createQuery("select acc from Account acc " +
                "where acc.creditType.creditTypeID = :creditTypeID", Account.class)
                .setParameter("creditTypeID", creditTypeID);
        q.getResultList();
        List<Account> accounts = q.list();
        session.close();
        return accounts;
    }

    public List<Account> getAccountsOfSpecifiedDepartment(long departmentID){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Query<Account> q = session.createQuery("select acc from Account acc " +
                "where acc.department.departmentID = :departmentID", Account.class)
                .setParameter("departmentID", departmentID);
        q.getResultList();
        List<Account> accounts = q.list();
        session.close();
        return accounts;
    }

    public List<Account> getAccountsOfSpecifiedBalance(Long minBalance, Long maxBalance) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();

        if (minBalance == null) { minBalance = Long.MIN_VALUE; }
        if (maxBalance == null) { maxBalance = Long.MAX_VALUE; }

        Query<Account> q = session.createQuery("select acc from Account acc " +
                "where :minBalance <= acc.balance and " +
                "acc.balance <= :maxBalance", Account.class)
                .setParameter("minBalance", minBalance)
                .setParameter("maxBalance", maxBalance);
        q.getResultList();
        List<Account> accounts = q.list();
        session.close();
        return accounts;
    }
}
