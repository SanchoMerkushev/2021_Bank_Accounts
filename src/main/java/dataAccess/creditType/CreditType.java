package dataAccess.creditType;

import dataAccess.EntityWithId;

import javax.persistence.*;

@Entity
@Table(name = "CreditTypes")
public class CreditType implements EntityWithId {
    private long creditTypeID = -1;
    private String creditName;
    private long creditLimit;
    private long creditPeriod;  // number of days
    private double interestRate;
    private String fineFrequency;

    public CreditType() {}

    public CreditType(String creditName, long creditLimit, long creditPeriod, double interestRate, String fineFrequency) {
        this.creditName = creditName;
        this.creditLimit = creditLimit;
        this.creditPeriod = creditPeriod;
        this.interestRate = interestRate;
        this.fineFrequency = fineFrequency;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CreditTypeID")
    public long getCreditTypeID() {
        return creditTypeID;
    }

    public void setCreditTypeID(long creditTypeID) {
        this.creditTypeID = creditTypeID;
    }

    @Column(name = "creditName")
    public String getCreditName() {
        return creditName;
    }

    public void setCreditName(String creditName) {
        this.creditName = creditName;
    }

    @Column(name = "creditLimit")
    public long getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(long creditLimit) {
        this.creditLimit = creditLimit;
    }

    @Column(name = "creditPeriod")
    public long getCreditPeriod() {
        return creditPeriod;
    }

    public void setCreditPeriod(long creditPeriod) {
        this.creditPeriod = creditPeriod;
    }

    @Column(name = "interestRate")
    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    @Column(name = "fineFrequency")
    public String getFineFrequency() {
        return fineFrequency;
    }

    public void setFineFrequency(String fineFrequency) {
        this.fineFrequency = fineFrequency;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) { return false; }
        if (obj.getClass() != this.getClass()) { return false; }
        final CreditType other = (CreditType) obj;
        return (this.creditTypeID == other.creditTypeID) &&
                (this.creditName.equals(other.creditName)) &&
                (this.creditLimit == other.creditLimit) &&
                (this.creditPeriod == other.creditPeriod) &&
                (this.interestRate == other.interestRate) &&
                (this.fineFrequency.equals(other.fineFrequency));

    }

    @Override
    public String toString() {
        return "CreditType{" +
                "creditTypeID=" + creditTypeID +
                ", creditName='" + creditName + '\'' +
                ", creditLimit=" + creditLimit +
                ", creditPeriod=" + creditPeriod +
                ", interestRate=" + interestRate +
                ", fineFrequency='" + fineFrequency + '\'' +
                '}';
    }

    @Override
    public long receivePrimaryKey() {
        return getCreditTypeID();
    }
}
