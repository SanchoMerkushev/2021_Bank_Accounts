package controllers;

import dataAccess.client.Client;
import dataAccess.client.ClientDAO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ClientController {
    ClientDAO clientDAO = new ClientDAO();

    @GetMapping("/clientsList")
    public String filmsListPage(Model model) {
        List<Client> clients = clientDAO.loadAll();
        model.addAttribute("clients", clients);
        return "clientsList";
    }

    @GetMapping("/client")
    public String clientPage(@RequestParam(name = "clientID", required = false) Integer clientID,
                             @RequestParam(name = "msg", required = false) String msg,
                             Model model) {
        // if clientID=None => add new client
        // else => update existing client
        if (clientID == null) {
            model.addAttribute("client", new Client());
            return "client";
        }

        Client client = clientDAO.findById(clientID);
        if (client == null) {
            model.addAttribute("errorMsg", "There is no client with id=" + clientID);
            return "errorPage";
        }
        model.addAttribute("client", client);
        model.addAttribute("msg", msg);
        return "client";
    }

    @GetMapping("/clientSave")
    public String clientSavePage(@RequestParam(name = "clientID", required = false) Integer clientID,
                                 @RequestParam(name = "clientName", required = true) String clientName,
                                 @RequestParam(name = "clientType", required = true) String clientType,
                                 Model model) {
        Client client;

        if ((clientID == null) || (clientID == -1)) { // save new client
            client = new Client(clientName, clientType);
            if (!clientDAO.save(client)) {
                model.addAttribute("errorMsg", "Adding new was not successful");
                return "errorPage";
            }
        } else {  // update client
            client = clientDAO.findById(clientID);
            if (client == null) {
                model.addAttribute("errorMsg", "Try to update non existing user");
                return "errorPage";
            }
            client.setClientName(clientName);
            client.setClientType(clientType);
            if (!clientDAO.update(client)) {
                model.addAttribute("errorMsg", "Client updating was not successful");
                return "errorPage";
            }
        }

        return String.format("redirect:/client?clientID=%d&msg=The client '%s' is updated",
                client.getClientID(), client.getClientName());
    }

    @GetMapping("/clientDelete")
    public String clientDeletePage(@RequestParam(name = "clientID", required = true) Integer clientID,
                                   Model model) {
        boolean result = clientDAO.deleteById(clientID);
        if (!result) {
            model.addAttribute("errorMsg", "Can not delete a client while it has accounts");
            return "errorPage";
        }
        return "redirect:/clientsList";
    }
}
