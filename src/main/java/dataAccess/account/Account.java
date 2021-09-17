package dataAccess.account;

import dataAccess.EntityWithId;
import dataAccess.client.Client;
import dataAccess.creditType.CreditType;
import dataAccess.department.Department;

import javax.persistence.*;

@Entity
@Table(name = "Accounts")
public class Account implements EntityWithId {
    private long accountID = -1;
    private Client client;
    private Department department;
    private CreditType creditType;
    private long balance;
    private String lastChange;

    public Account() {
    }

    public Account(Client client, Department department, CreditType creditType, long balance) {
        this.client = client;
        this.department = department;
        this.creditType = creditType;
        this.balance = balance;
        this.lastChange = "+ 0 rub.";
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "accountID")
    public long getAccountID() {
        return accountID;
    }

    public void setAccountID(long accountID) {
        this.accountID = accountID;
    }

    @ManyToOne(targetEntity = Client.class)
    @JoinColumn(name = "clientID")
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @ManyToOne(targetEntity = Department.class)
    @JoinColumn(name = "departmentID")
    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @ManyToOne(targetEntity = CreditType.class)
    @JoinColumn(name = "creditTypeID")
    public CreditType getCreditType() {
        return creditType;
    }

    public void setCreditType(CreditType creditType) {
        this.creditType = creditType;
    }

    @Column(name = "balance")
    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    @Column(name = "lastChange")
    public String getLastChange() {
        return lastChange;
    }

    public void setLastChange(String lastChange) {
        this.lastChange = lastChange;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        final Account other = (Account) obj;
        return (this.accountID == other.accountID) &&
                (this.client.equals(other.client)) &&
                (this.department.equals(other.department)) &&
                (this.creditType.equals(other.creditType)) &&
                (this.balance == other.balance) &&
                (this.lastChange.equals(other.lastChange));
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountID=" + accountID +
                ", client=" + client +
                ", department=" + department +
                ", creditType=" + creditType +
                ", balance=" + balance +
                ", lastChange='" + lastChange + '\'' +
                '}';
    }

    @Override
    public long receivePrimaryKey() {
        return getAccountID();
    }

    public void changeBalance(long newBalance) {
        long changeValue = newBalance - this.balance;
        if (changeValue >= 0) {
            this.balance += changeValue;
            lastChange = String.format("+ %d rub.", changeValue);
        } else {
            this.balance -= changeValue;
            lastChange = String.format("- %d rub.", changeValue);
        }
    }
}
