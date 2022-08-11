package application.Controller;

import application.Entity.Account;
import application.Entity.Log;
import application.Entity.Users;
import application.Repository.AccountRepository;
import application.Repository.LogRepository;
import application.Repository.UserRepository;
import application.Service.TransferService;
import application.Validator.TransactionValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;

@Controller
@RequestMapping("/transfer")
public class TransferController {

    @Autowired
    private TransferService transferService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private LogRepository logRepository;

    @Autowired
    private UserRepository userRepository;

    private TransactionValidator validator;

    @RequestMapping(method = RequestMethod.GET)
    public String transfer(){
        return "transfer";
    }
    @RequestMapping(value = "/search",method = RequestMethod.GET)
    public String search(@RequestParam(value = "id1") long id1, @RequestParam(value = "id2") long id2, Model model) {
       return transferService.search(id1, id2, model);
    }

    @RequestMapping(value = "process", method = RequestMethod.POST)
    public String processTransaction(HttpServletRequest request) {
       return transferService.processTransaction(request);
    }

}
