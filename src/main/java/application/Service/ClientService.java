package application.Service;


import application.Entity.Client;
import application.Entity.Log;
import application.Entity.Users;
import application.Exception.ClientNotFoundException;
import application.Repository.*;
import application.Validator.ClientValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService {

    private ClientRepository clientRepository;
    private ClientValidator validator;
    private final UserRepository userRepository;
    private final LogRepository logRepository;

    public Client findByName(String name){
        Optional<Client> foundedClient = Optional.ofNullable(clientRepository.findByName(name));
        return foundedClient.orElseThrow(ClientNotFoundException::new);
    }

    public String addClient(HttpServletRequest request) {
        Client client = new Client();
        client.setName(request.getParameter("name"));
        client.setAddress(request.getParameter("address"));
        client.setEmail(request.getParameter("email"));
        client.setCnp(request.getParameter("CNP"));
        validator = new ClientValidator();
        if(validator.validate(client))
            clientRepository.save(client);
        else
            return "redirect:/clientOp/new?error=true";

        Log log = new Log();
        log.setOperation("Client added. ID: " + client.getId() + "; Name: " + client.getName());
        log.setTimestamp(new Timestamp(System.currentTimeMillis()));

        String username = request.getRemoteUser();

        Users user = userRepository.findByUsername(username);
        log.setUser(user);

        logRepository.save(log);
        return "redirect:/index";
    }

    public String update(@PathVariable long id, Model model) {
        Client client = clientRepository.findOne(id);
        model.addAttribute("client",client);
        return "clientOp/edit";
    }

    public String updateClient(HttpServletRequest request) {
        Client client = clientRepository.findOne(Long.parseLong(request.getParameter("id")));
        client.setName(request.getParameter("name"));
        client.setAddress(request.getParameter("address"));
        client.setEmail(request.getParameter("email"));
        client.setCnp(request.getParameter("CNP"));
        validator = new ClientValidator();
        if(validator.validate(client))
            clientRepository.save(client);
        else
            return "redirect:/clientOp/new?error=true";
        clientRepository.save(client);

        Log log = new Log();

        log.setOperation("Client updated. ID: " + client.getId() + "; Name: " + client.getName());
        log.setTimestamp(new Timestamp(System.currentTimeMillis()));

        String username = request.getRemoteUser();

        Users user = userRepository.findByUsername(username);
        log.setUser(user);
        logRepository.save(log);
        return "redirect:/index";
    }

    public String view(@PathVariable long id, Model model) {
        Client client = clientRepository.findOne(id);
        model.addAttribute("client",client);
        return "clientOp/view";
    }

    public String search(HttpServletRequest request) {
        Client client = clientRepository.findOne(Long.parseLong(request.getParameter("id")));
        String option = request.getParameter("option");
        if(option.equals("VIEW")) {
            return "redirect:/clientOp/" + client.getId() + "/view";
        }
        if(option.equals("EDIT")) {
            return "redirect:/clientOp/" + client.getId() + "/edit";
        }
        return "redirect:/clientOp/search";
    }



}
