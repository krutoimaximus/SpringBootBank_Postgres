package application.Service;

import application.Entity.Account;
import application.Entity.Client;
import application.Entity.Log;
import application.Entity.Users;
import application.Repository.AccountRepository;
import application.Repository.ClientRepository;
import application.Repository.LogRepository;
import application.Repository.UserRepository;
import application.Validator.AccountValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.sql.Timestamp;

@Service
@RequiredArgsConstructor
public class AccountService {

    private AccountValidator validator;
    private final ClientRepository clientRepository;
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final LogRepository logRepository;

    public String addAccount(HttpServletRequest request) {
        Account account = new Account();
        account.setBalance(Double.parseDouble(request.getParameter("balance")));
        account.setDate(new Date(System.currentTimeMillis()));
        account.setType(request.getParameter("type"));
        validator = new AccountValidator();
        Client client = clientRepository.findByName(request.getParameter("name"));
        if (client != null)
            account.setOwner(client);
        else
            return "account/new?error=true";
        if (validator.validate(account))
            accountRepository.save(account);
        else
            return "account/new?error=true";

        Log log = new Log();
        log.setOperation("Account added. ID: " + account.getId() + "; Owner: " + account.getOwner().getName());
        log.setTimestamp(new Timestamp(System.currentTimeMillis()));

        String username = request.getRemoteUser();

        Users user = userRepository.findByUsername(username);
        log.setUser(user);

        logRepository.save(log);

        return "redirect:/index";
    }

    public String update(@PathVariable long id, Model model) {
        Account account = accountRepository.findOne(id);
        Client client = clientRepository.findOne(account.getOwner().getId());
        model.addAttribute("owner",client);
        model.addAttribute("account",account);
        return "account/edit";
    }

    public String updateAccount(HttpServletRequest request) {
        Account account = accountRepository.findOne(Long.parseLong(request.getParameter("id")));
        account.setBalance(Double.parseDouble(request.getParameter("balance")));
        account.setType(request.getParameter("type"));

        Client client = clientRepository.findOne(account.getOwner().getId());
        account.setOwner(client);
        if(client != null)
            account.setOwner(client);
        else
            return "account/new?error=true";
        if(validator.validate(account))
            accountRepository.save(account);
        else
            return "account/new?error=true";
        accountRepository.save(account);
        Log log = new Log();
        log.setOperation("Account updated. ID: " + account.getId() + "; Owner: " + account.getOwner().getName());
        log.setTimestamp(new Timestamp(System.currentTimeMillis()));

        String username = request.getRemoteUser();

        Users user = userRepository.findByUsername(username);
        log.setUser(user);

        logRepository.save(log);

        return "redirect:/index";
    }

    public String view(@PathVariable long id, Model model) {
        Account account = accountRepository.findOne(id);
        Client client = account.getOwner();
        model.addAttribute("client",client);
        model.addAttribute("account",account);
        return "account/view";
    }

    public String delete(@PathVariable long id) {
        accountRepository.delete(id);
        return "redirect:/index";
    }

    public String search(HttpServletRequest request) {
        Account account = accountRepository.findOne(Long.parseLong(request.getParameter("id")));
        String option = request.getParameter("option");
        if(option.equals("VIEW")) {
            return "redirect:/account/" + account.getId() + "/view";
        }
        if(option.equals("EDIT")) {
            return "redirect:/account/" + account.getId() + "/edit";
        }
        if(option.equals("DELETE")) {
            return "redirect:/account/" + account.getId() + "/delete";
        }
        return "redirect:/account/search";
    }

}
