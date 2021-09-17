package dataAccess.department;

import dataAccess.EntityWithId;

import javax.persistence.*;

@Entity
@Table(name = "Departments")
public class Department implements EntityWithId {
    private long departmentID = -1;
    private String address;

    public Department() {
    }

    public Department(String address) {
        this.address = address;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "departmentID")
    public long getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(long departmentID) {
        this.departmentID = departmentID;
    }

    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) { return false; }
        if (obj.getClass() != this.getClass()) { return false; }
        final Department other = (Department) obj;
        return (this.departmentID == other.departmentID) &&
                (this.address.equals(other.address));
    }

    @Override
    public String toString() {
        return "Department{" +
                "departmentID=" + departmentID +
                ", address='" + address + '\'' +
                '}';
    }

    @Override
    public long receivePrimaryKey() {
        return getDepartmentID();
    }
}

