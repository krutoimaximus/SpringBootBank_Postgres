package application.Controller;

import application.Entity.Account;
import application.Entity.Log;
import application.Entity.Users;
import application.Repository.AccountRepository;
import application.Repository.LogRepository;
import application.Repository.UserRepository;
import application.Service.ClientService;
import application.Service.PaymentService;
import application.Validator.TransactionValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;

@Controller
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private LogRepository logRepository;

    @Autowired
    private UserRepository userRepository;

    private TransactionValidator validator;

    @RequestMapping(method = RequestMethod.GET)
    public String show(){
        return "payment";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String process(HttpServletRequest request) {
      return paymentService.process(request);
    }
}
