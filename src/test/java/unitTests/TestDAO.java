package unitTests;

import dataAccess.account.Account;
import dataAccess.account.AccountDAO;
import dataAccess.client.Client;
import dataAccess.client.ClientDAO;
import dataAccess.creditType.CreditType;
import dataAccess.creditType.CreditTypeDAO;
import dataAccess.department.Department;
import dataAccess.department.DepartmentDAO;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static unitTests.DataExamples.nonExistentId;

public class TestDAO {
    private AccountDAO accountDAO;
    private ClientDAO clientDAO;
    private CreditTypeDAO creditTypeDAO;
    private DepartmentDAO departmentDAO;


    @BeforeClass
    public void setUp() {
        this.accountDAO = new AccountDAO();
        this.clientDAO = new ClientDAO();
        this.creditTypeDAO = new CreditTypeDAO();
        this.departmentDAO = new DepartmentDAO();
    }

    @Test
    public void testClientDAO() {
        final Client[] dataClients = DataExamples.getClients();
        final Client oneMoreClient = DataExamples.getClients()[0];

        // fill the table
        for (Client client : dataClients) {
            Assert.assertTrue(clientDAO.save(client));
        }

        // read the whole table
        List<Client> clients = clientDAO.loadAll();
        Assert.assertEquals(clients.size(), dataClients.length);
        for (int i = 0; i < clients.size(); ++i) {
            Assert.assertEquals(clients.get(i), dataClients[i]);
        }

        // find by id & update
        long existentId = dataClients[0].getClientID();
        Assert.assertNull(clientDAO.findById(nonExistentId));
        oneMoreClient.setClientID(nonExistentId);
        Assert.assertFalse(clientDAO.update(oneMoreClient));
        oneMoreClient.setClientID(existentId);
        Assert.assertTrue(clientDAO.update(oneMoreClient));
        Assert.assertEquals(oneMoreClient, clientDAO.findById(existentId));

        // save object with initialized filed "id"
        Assert.assertFalse(clientDAO.save(oneMoreClient));

        // remove all objects from the table
        for (Client client : clients) {
            Assert.assertTrue(clientDAO.deleteById(client.getClientID()));
        }
    }

    @Test
    public void testCreditTypeDAO() {
        final CreditType[] dataCreditTypes = DataExamples.getCreditTypes();
        final CreditType oneMoreCreditType = DataExamples.getCreditTypes()[0];

        // fill the table
        for (CreditType CreditType : dataCreditTypes) {
            Assert.assertTrue(creditTypeDAO.save(CreditType));
        }

        // read the whole table
        List<CreditType> creditTypes = creditTypeDAO.loadAll();
        Assert.assertEquals(creditTypes.size(), dataCreditTypes.length);
        for (int i = 0; i < creditTypes.size(); ++i) {
            Assert.assertEquals(creditTypes.get(i), dataCreditTypes[i]);
        }

        // find by id & update
        long existentId = dataCreditTypes[0].getCreditTypeID();
        Assert.assertNull(creditTypeDAO.findById(nonExistentId));
        oneMoreCreditType.setCreditTypeID(nonExistentId);
        Assert.assertFalse(creditTypeDAO.update(oneMoreCreditType));
        oneMoreCreditType.setCreditTypeID(existentId);
        Assert.assertTrue(creditTypeDAO.update(oneMoreCreditType));
        Assert.assertEquals(oneMoreCreditType, creditTypeDAO.findById(existentId));

        // save object with initialized filed "id"
        Assert.assertFalse(creditTypeDAO.save(oneMoreCreditType));

        // remove all objects from the table
        for (CreditType CreditType : creditTypes) {
            Assert.assertTrue(creditTypeDAO.deleteById(CreditType.getCreditTypeID()));
        }
    }

    @Test
    public void testDepartmentDAO() {
        final Department[] dataDepartments = DataExamples.getDepartments();
        final Department oneMoreDepartment = DataExamples.getDepartments()[0];

        // fill the table
        for (Department Department : dataDepartments) {
            Assert.assertTrue(departmentDAO.save(Department));
        }

        // read the whole table
        List<Department> departments = departmentDAO.loadAll();
        Assert.assertEquals(departments.size(), dataDepartments.length);
        for (int i = 0; i < departments.size(); ++i) {
            Assert.assertEquals(departments.get(i), dataDepartments[i]);
        }

        // find by id & update
        long existentId = dataDepartments[0].getDepartmentID();
        Assert.assertNull(departmentDAO.findById(nonExistentId));
        oneMoreDepartment.setDepartmentID(nonExistentId);
        Assert.assertFalse(departmentDAO.update(oneMoreDepartment));
        oneMoreDepartment.setDepartmentID(existentId);
        Assert.assertTrue(departmentDAO.update(oneMoreDepartment));
        Assert.assertEquals(oneMoreDepartment, departmentDAO.findById(existentId));

        // save object with initialized filed "id"
        Assert.assertFalse(departmentDAO.save(oneMoreDepartment));

        // remove all objects from the table
        for (Department Department : departments) {
            Assert.assertTrue(departmentDAO.deleteById(Department.getDepartmentID()));
        }
    }

    @Test
    public void testAccountDAO() {
        final Client[] dataClients = DataExamples.getClients();
        for (Client client : dataClients) {
            clientDAO.save(client);
        }
        final CreditType[] dataCreditTypes = DataExamples.getCreditTypes();
        for (CreditType creditType : dataCreditTypes) {
            creditTypeDAO.save(creditType);
        }
        final Department[] dataDepartments = DataExamples.getDepartments();
        for (Department department : dataDepartments) {
            departmentDAO.save(department);
        }

        final Account[] dataAccounts = DataExamples.getAccounts(dataClients, dataDepartments, dataCreditTypes);
        final Account oneMoreAccount = DataExamples.getAccounts(dataClients, dataDepartments, dataCreditTypes)[0];

        // fill the table
        for (Account Account : dataAccounts) {
            Assert.assertTrue(accountDAO.save(Account));
        }

        // read the whole table
        List<Account> accounts = accountDAO.loadAll();
        Assert.assertEquals(accounts.size(), dataAccounts.length);
        for (int i = 0; i < accounts.size(); ++i) {
            Assert.assertEquals(accounts.get(i), dataAccounts[i]);
        }

        // find by id & update
        long existentId = dataAccounts[0].getAccountID();
        Assert.assertNull(accountDAO.findById(nonExistentId));
        oneMoreAccount.setAccountID(nonExistentId);
        Assert.assertFalse(accountDAO.update(oneMoreAccount));
        oneMoreAccount.setAccountID(existentId);
        Assert.assertTrue(accountDAO.update(oneMoreAccount));
        Assert.assertEquals(oneMoreAccount, accountDAO.findById(existentId));

        // save object with initialized filed "id"
        Assert.assertFalse(accountDAO.save(oneMoreAccount));

        // check specific methods of AccountDAO
        {
            long existedClientID = dataAccounts[0].getClient().getClientID();
            List<Account> accountsOfClient = accountDAO.getAccountsOfSpecifiedClient(existedClientID);
            Assert.assertTrue(accountsOfClient.size() >= 1);
        }
        {
            long existedCreditTypeID = dataAccounts[0].getCreditType().getCreditTypeID();
            List<Account> accountsOfCreditType = accountDAO.getAccountsOfSpecifiedCreditType(existedCreditTypeID);
            Assert.assertTrue(accountsOfCreditType.size() >= 1);
        }
        {
            long existedDepartmentID = dataAccounts[0].getDepartment().getDepartmentID();
            List<Account> accountsOfDepartments = accountDAO.getAccountsOfSpecifiedClient(existedDepartmentID);
            Assert.assertTrue(accountsOfDepartments.size() >= 1);
        }

        // remove all objects from the table
        for (Account account : accounts) {
            Assert.assertTrue(accountDAO.deleteById(account.getAccountID()));
        }
        for (Client client : dataClients) {
            clientDAO.deleteById(client.getClientID());
        }
        for (CreditType creditType : dataCreditTypes) {
            creditTypeDAO.deleteById(creditType.getCreditTypeID());
        }
        for (Department department : dataDepartments) {
            departmentDAO.deleteById(department.getDepartmentID());
        }
    }
}
