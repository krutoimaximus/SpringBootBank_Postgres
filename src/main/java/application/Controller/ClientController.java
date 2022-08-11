package application.Controller;

import application.Entity.Client;
import application.Entity.Log;
import application.Entity.Users;
import application.Repository.ClientRepository;
import application.Repository.LogRepository;
import application.Repository.UserRepository;
import application.Service.ClientService;
import application.Validator.ClientValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;

@Controller
@RequestMapping("/clientOp")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private LogRepository logRepository;

    @Autowired
    private UserRepository userRepository;

    private ClientValidator validator;

    @RequestMapping(value = "new", method = RequestMethod.GET)
    public String newClient() {
        return "clientOp/new";
    }

    @RequestMapping(value = "new",method = RequestMethod.POST)
    public String addClient(HttpServletRequest request) {
       return clientService.addClient(request);
    }

    @RequestMapping(value = "/{id}/edit",method = RequestMethod.GET)
    public String update(@PathVariable long id, Model model) {
       return clientService.update(id, model);
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    public String updateClient(HttpServletRequest request) {
       return clientService.updateClient(request);
    }

    @RequestMapping(value = "/{id}/view",method = RequestMethod.GET)
    public String view(@PathVariable long id, Model model) {
        return clientService.view(id, model);
    }

    @RequestMapping(value = "/search",method = RequestMethod.GET)
    public String displaySearch(){
        return "clientOp/search";
    }
    @RequestMapping(value = "/search",method = RequestMethod.POST)
    public String search(HttpServletRequest request) {
       return clientService.search(request);
    }
}
