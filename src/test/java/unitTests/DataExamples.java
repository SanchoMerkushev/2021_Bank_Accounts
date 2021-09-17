package unitTests;

import dataAccess.account.Account;
import dataAccess.client.Client;
import dataAccess.creditType.CreditType;
import dataAccess.department.Department;

public class DataExamples {
    public static final long nonExistentId = 1000;

    public static Account[] getAccounts(
            Client[] clients, Department[] departments, CreditType[] creditTypes) {
        return new Account[]{
                new Account(clients[0], departments[0], creditTypes[0], 100),
                new Account(clients[0], departments[1], creditTypes[1], 0),
                new Account(clients[0], departments[1], creditTypes[2], -80000),
        };
    }

    public static Client[] getClients() {
        return new Client[]{
                new Client("ООО Лупа и партнёры", "juridical"),
                new Client("ИП Пупа", "juridical"),
                new Client("Александр Меркушев", "natural"),
        };
    }

    public static CreditType[] getCreditTypes() {
        return new CreditType[]{
                new CreditType("для нищебродов", 100000, 365,
                        31.3, "еженедельно"),
                new CreditType("для буржуев", 900000, 3650,
                        9.1, "ежемесячно"),
                new CreditType("для олигархов", 9900000, 3650,
                        0.5, "ежегодно"),
        };
    }

    public static Department[] getDepartments() {
        return new Department[]{
                new Department("Патриаршие прруды, д. 1"),
                new Department("Красная площадь, д. 14Б"),
                new Department("Канализационный переулок, д. 41, стр. 5"),
        };
    }
}
