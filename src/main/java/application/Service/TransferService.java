package application.Service;

import application.Entity.Account;
import application.Entity.Log;
import application.Entity.Users;
import application.Repository.AccountRepository;
import application.Repository.LogRepository;
import application.Repository.UserRepository;
import application.Validator.TransactionValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;

@Service
@RequiredArgsConstructor
public class TransferService {

    private AccountRepository accountRepository;
    private TransactionValidator validator;
    private UserRepository userRepository;
    private LogRepository logRepository;


    public String search(@RequestParam(value = "id1") long id1, @RequestParam(value = "id2") long id2, Model model) {
        Account from = accountRepository.getOne(id1);
        Account to = accountRepository.getOne(id2);
        model.addAttribute("from",from);
        model.addAttribute("to",to);
        return "transfer/search";
    }

    public String processTransaction(HttpServletRequest request) {
        Double amount = Double.parseDouble(request.getParameter("amount"));
        Account from = accountRepository.getOne(Long.parseLong(request.getParameter("id1")));
        Account to = accountRepository.getOne(Long.parseLong(request.getParameter("id2")));

        validator = new TransactionValidator();
        if(validator.validate(from,amount)) {
            from.setBalance(from.getBalance() - amount);
            to.setBalance(to.getBalance() + amount);

            accountRepository.save(from);
            accountRepository.save(to);
        }
        else
            return "redirect:/transfer?error=true";


        Log log = new Log();
        log.setOperation("Transaction: From " + from.getId() + " To " + to.getId() + " Amount " + amount);
        log.setTimestamp(new Timestamp(System.currentTimeMillis()));

        String username = request.getRemoteUser();

        Users user = userRepository.findByUsername(username);
        log.setUser(user);

        logRepository.save(log);

        return "redirect:/index";
    }
}
