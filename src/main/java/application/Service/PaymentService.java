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

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private AccountRepository accountRepository;
    private TransactionValidator validator;
    private UserRepository userRepository;
    private LogRepository logRepository;

    public String process(HttpServletRequest request) {
        Account account = accountRepository.getOne(Long.parseLong(request.getParameter("id")));
        Double amount = Double.parseDouble(request.getParameter("amount"));
        String type = request.getParameter("bill");
        validator = new TransactionValidator();
        if(validator.validate(account,amount)) {
            account.setBalance(account.getBalance() - amount);
            accountRepository.save(account);
        }
        else
            return "redirect:/payment?error=true";


        Log log = new Log();
        log.setOperation("Utilities payment: Account ID: " + account.getId() + " Amount: " + amount + " Type: " + type);
        log.setTimestamp(new Timestamp(System.currentTimeMillis()));

        String username = request.getRemoteUser();

        Users user = userRepository.findByUsername(username);
        log.setUser(user);

        logRepository.save(log);

        return "redirect:/index";
    }
}
