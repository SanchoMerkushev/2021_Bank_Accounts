package controllers;

import dataAccess.account.Account;
import dataAccess.account.AccountDAO;
import dataAccess.client.Client;
import dataAccess.client.ClientDAO;
import dataAccess.creditType.CreditType;
import dataAccess.creditType.CreditTypeDAO;
import dataAccess.department.Department;
import dataAccess.department.DepartmentDAO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AccountController {
    AccountDAO accountDAO = new AccountDAO();
    ClientDAO clientDAO = new ClientDAO();
    CreditTypeDAO creditTypeDAO = new CreditTypeDAO();
    DepartmentDAO departmentDAO = new DepartmentDAO();


    @GetMapping("/accountsList")
    public String filmsListPage(@RequestParam(name = "findClientID", required = false) Integer findClientID,
                                @RequestParam(name = "findCreditTypeID", required = false) Integer findCreditTypeID,
                                @RequestParam(name = "findDepartmentID", required = false) Integer findDepartmentID,
                                @RequestParam(name = "findMinBalance", required = false) Long findMinBalance,
                                @RequestParam(name = "findMaxBalance", required = false) Long findMaxBalance,
                                Model model) {
        List<Account> accounts;
        if (findClientID != null) {
            accounts = accountDAO.getAccountsOfSpecifiedClient(findClientID);
        } else if (findCreditTypeID != null) {
            accounts = accountDAO.getAccountsOfSpecifiedCreditType(findCreditTypeID);
        } else if (findDepartmentID != null) {
            accounts = accountDAO.getAccountsOfSpecifiedDepartment(findDepartmentID);
        } else if ((findMinBalance != null) || (findMaxBalance != null)) {
            accounts = accountDAO.getAccountsOfSpecifiedBalance(findMinBalance, findMaxBalance);
        } else {
            accounts = accountDAO.loadAll();
        }
        model.addAttribute("accounts", accounts);

        List<Client> clients = clientDAO.loadAll();
        model.addAttribute("clients", clients);
        List<CreditType> creditTypes = creditTypeDAO.loadAll();
        model.addAttribute("creditTypes", creditTypes);
        List<Department> departments = departmentDAO.loadAll();
        model.addAttribute("departments", departments);

        return "accountsList";
    }

    @GetMapping("/account")
    public String accountPage(@RequestParam(name = "accountID", required = false) Integer accountID,
                              @RequestParam(name = "msg", required = false) String msg,
                              Model model) {
        // if accountID=None => add new account
        // else => update existing account
        Account account;
        boolean isAccountNew = true;
        if (accountID == null) {
            account = new Account();
        } else {
            account = accountDAO.findById(accountID);
            if (account == null) {
                model.addAttribute("errorMsg", "There is no account with id=" + accountID);
                return "errorPage";
            }
            isAccountNew = false;
        }

        List<Client> clients = clientDAO.loadAll();
        model.addAttribute("clients", clients);
        Long selectedClientId = null;
        if (account.getClient() != null) {
            selectedClientId = account.getClient().getClientID();
        }
        model.addAttribute("selectedClientId", selectedClientId);

        List<CreditType> creditTypes = creditTypeDAO.loadAll();
        model.addAttribute("creditTypes", creditTypes);
        Long selectedCreditTypeId = null;
        if (account.getCreditType() != null) {
            selectedCreditTypeId = account.getCreditType().getCreditTypeID();
        }
        model.addAttribute("selectedCreditTypeId", selectedCreditTypeId);

        List<Department> departments = departmentDAO.loadAll();
        model.addAttribute("departments", departments);
        Long selectedDepartmentId = null;
        if (account.getDepartment() != null) {
            selectedDepartmentId = account.getDepartment().getDepartmentID();
        }
        model.addAttribute("selectedDepartmentId", selectedDepartmentId);

        model.addAttribute("account", account);
        model.addAttribute("msg", msg);
        model.addAttribute("isAccountNew", isAccountNew);
        return "account";
    }

    @GetMapping("/accountSave")
    public String accountSavePage(@RequestParam(name = "accountID", required = false) Integer accountID,
                                  @RequestParam(name = "clientID", required = false) Integer clientID,
                                  @RequestParam(name = "departmentID", required = false) Integer departmentID,
                                  @RequestParam(name = "creditTypeID", required = false) Integer creditTypeID,
                                  @RequestParam(name = "balance", required = false) Integer balance,
                                  Model model) {
        if (clientID == null || departmentID == null || creditTypeID == null || balance == null) {
            model.addAttribute("errorMsg",
                    "To create an account info about " +
                            "clientID, departmentID,  creditTypeID, balance is required.\n" +
                            "Provided: " + clientID + ", " + departmentID + ", " + creditTypeID + ", " + balance);
            return "errorPage";
        }

        Client client = clientDAO.findById(clientID);
        if (client == null) {
            model.addAttribute("errorMsg", "There is no client with id=" + clientID);
            return "errorPage";
        }
        CreditType creditType = creditTypeDAO.findById(creditTypeID);
        if (creditType == null) {
            model.addAttribute("errorMsg", "There is no creditType with id=" + creditTypeID);
            return "errorPage";
        }
        Department department = departmentDAO.findById(departmentID);
        if (department == null) {
            model.addAttribute("errorMsg", "There is no department with id=" + departmentID);
            return "errorPage";
        }

        if (balance < -creditType.getCreditLimit()) {
            model.addAttribute("errorMsg", String.format("Try to assign balance=%d < creditLimit=-%d",
                    balance, creditType.getCreditLimit()));
            return "errorPage";
        }

        Account account;
        if ((accountID == null) || (accountID == -1)) { // save new account
            account = new Account(client, department, creditType, balance);
            if (!accountDAO.save(account)) {
                model.addAttribute("errorMsg", "Adding new was not successful");
                return "errorPage";
            }
        } else {  // update account
            account = accountDAO.findById(accountID);
            if (account == null) {
                model.addAttribute("errorMsg", "Try to update non existing user");
                return "errorPage";
            }

            account.setClient(client);
            account.setDepartment(department);
            account.setCreditType(creditType);
            account.changeBalance(balance);

            if (!accountDAO.update(account)) {
                model.addAttribute("errorMsg", "Account updating was not successful");
                return "errorPage";
            }
        }

        return String.format("redirect:/account?accountID=%d&msg=The account of '%s' is updated",
                account.getAccountID(), account.getClient().getClientName());
    }

    @GetMapping("/accountDelete")
    public String accountDeletePage(@RequestParam(name = "accountID", required = true) Integer accountID,
                                    Model model) {
        boolean result = accountDAO.deleteById(accountID);
        if (!result) {
            model.addAttribute("errorMsg", "Can not not delete an account");
            return "errorPage";
        }
        return "redirect:/accountsList";
    }
}
