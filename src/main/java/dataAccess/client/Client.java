package dataAccess.client;

import dataAccess.EntityWithId;

import javax.persistence.*;

@Entity
@Table(name = "Clients")
public class Client implements EntityWithId {
    private long clientID = -1;
    private String clientName;
    private String clientType;

    public Client() { }

    public Client(String clientName, String clientType) {
        this.clientName = clientName;
        this.clientType = clientType;
    }

    @Column(name = "clientName")
    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    @Column(name = "clientType")
    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    @Id
    @Column(name = "clientID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getClientID() {
        return clientID;
    }

    public void setClientID(long clientID) {
        this.clientID = clientID;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) { return false; }
        if (obj.getClass() != this.getClass()) { return false; }
        final Client other = (Client) obj;
        return (this.clientID == other.clientID) &&
                this.clientName.equals(other.clientName) &&
                this.clientType.equals(other.clientType);
    }

    @Override
    public String toString() {
        return "Client{" +
                "clientID=" + clientID +
                ", clientName='" + clientName + '\'' +
                ", clientType='" + clientType + '\'' +
                '}';
    }

    @Override
    public long receivePrimaryKey() {
        return getClientID();
    }
}
