package application.Controller;

import application.Entity.Account;
import application.Entity.Client;
import application.Entity.Log;
import application.Entity.Users;
import application.Repository.AccountRepository;
import application.Repository.ClientRepository;
import application.Repository.LogRepository;
import application.Repository.UserRepository;
import application.Service.AccountService;
import application.Validator.AccountValidator;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.sql.Timestamp;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private LogRepository logRepository;

    @Autowired
    private UserRepository userRepository;

    private AccountValidator validator;

    public AccountController() {
    }

    @RequestMapping(value = "new",method = RequestMethod.GET)
    public String newAccount() {
        return "account/new";
    }

    @RequestMapping(value = "new", method = RequestMethod.POST)
    public String addAccount(HttpServletRequest request) {
        return accountService.addAccount(request);
    }


    @RequestMapping(value = "{id}/edit",method = RequestMethod.GET)
    public String update(@PathVariable long id, Model model) {
        return accountService.update(id, model);
    }

    @RequestMapping(value = "update",method = RequestMethod.POST)
    public String updateAccount(HttpServletRequest request) {
        return accountService.updateAccount(request);
    }

    @RequestMapping(value = "/{id}/view",method = RequestMethod.GET)
    public String view(@PathVariable long id, Model model) {
       return accountService.view(id, model);
    }

    @RequestMapping(value = "/{id}/delete",method = RequestMethod.GET)
    public String delete(@PathVariable long id) {
       return accountService.delete(id);
    }

    @RequestMapping(value = "/search",method = RequestMethod.GET)
    public String displaySearch(){
        return "account/search";
    }
    @RequestMapping(value = "/search",method = RequestMethod.POST)
    public String search(HttpServletRequest request) {
       return accountService.search(request);
    }


}