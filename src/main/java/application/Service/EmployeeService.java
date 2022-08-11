package application.Service;

import application.Entity.*;
import application.Exception.EmployeeNotFoundException;
import application.Repository.*;
import application.Validator.AccountValidator;
import application.Validator.EmployeeValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ClientRepository clientRepository;
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final LogRepository logRepository;
    private final RoleRepository roleRepository;
    private EmployeeValidator validator;

    public Employee findByName(String name){
       Optional<Employee> foundedEmployee = Optional.ofNullable(employeeRepository.findByName(name));
       return foundedEmployee.orElseThrow(EmployeeNotFoundException::new);
    }

    Employee findByUsername(String username){
        Optional<Employee> foundedEmployeeByUserName = Optional.ofNullable(employeeRepository.findByUsername(username));
        return foundedEmployeeByUserName.orElseThrow(EmployeeNotFoundException::new);

    }

    public String registerUser(HttpServletRequest request) {
        List<Employee> employees = employeeRepository.findAll();


        Employee employee = new Employee();
        String salary = request.getParameter("salary");

        employee.setSalary(Double.parseDouble(salary));
        employee.setName(request.getParameter("name"));
        employee.setPhone(request.getParameter("phone"));
        employee.setEmail(request.getParameter("email"));
        validator = new EmployeeValidator();
        if(validator.validate(employee))
            employeeRepository.save(employee);
        else
            return "redirect:/admin/new?error=true";

        Users user = new Users();
        user.setUsername(request.getParameter("username"));
        user.setPassword(request.getParameter("password"));

        Role role = roleRepository.findByRole(request.getParameter("role"));
        user.setRoles(new HashSet<Role>(Arrays.asList(role)));
        userRepository.save(user);

        Log log = new Log();
        log.setOperation("Employee added: ID: " + employee.getId());
        log.setTimestamp(new Timestamp(System.currentTimeMillis()));

        String username = request.getRemoteUser();

        Users activeUser = userRepository.findByUsername(username);
        log.setUser(activeUser);

        logRepository.save(log);
        return "redirect:/index";
    }

    public String update(@PathVariable long id, Model model) {
        Employee employee = employeeRepository.findOne(id);
        model.addAttribute("employee",employee);
        return "admin/edit";
    }

    public String updateEmployee(HttpServletRequest request) {
        Employee employee = employeeRepository.findOne(Long.parseLong(request.getParameter("id")));
        employee.setSalary(Double.parseDouble(request.getParameter("salary")));
        employee.setEmail(request.getParameter("email"));
        employee.setName(request.getParameter("name"));
        employee.setPhone(request.getParameter("phone"));


        validator = new EmployeeValidator();
        if(validator.validate(employee))
            employeeRepository.save(employee);
        else
            return "redirect:/admin/new?error=true";
        Log log = new Log();
        log.setOperation("Employee updated. ID: " + employee.getId());
        log.setTimestamp(new Timestamp(System.currentTimeMillis()));

        String username = request.getRemoteUser();

        Users user = userRepository.findByUsername(username);
        log.setUser(user);

        logRepository.save(log);

        return "redirect:/index";
    }

    public String search(HttpServletRequest request) {
        Employee employee = employeeRepository.findOne(Long.parseLong(request.getParameter("id")));
        String option = request.getParameter("option");
        if(option.equals("VIEW")) {
            return "redirect:/admin/" + employee.getId() + "/view";
        }
        if(option.equals("EDIT")) {
            return "redirect:/admin/" + employee.getId() + "/edit";
        }
        if(option.equals("DELETE")) {
            return "redirect:/admin/" + employee.getId() + "/delete";
        }
        return "redirect:/admin/search";
    }

    public String view(@PathVariable long id, Model model) {
        Employee employee = employeeRepository.findOne(id);
        model.addAttribute("employee", employee);
        return "admin/view";
    }

    public ModelAndView searchEmployee(HttpServletRequest request) throws Exception {
        ModelAndView modelAndView = new ModelAndView("admin/showReport");
        Users user = userRepository.findOne(Long.parseLong(request.getParameter("id")));
        DateFormat format = new SimpleDateFormat("yyyy-M-dd");
        java.util.Date from = format.parse(request.getParameter("from"));
        Date to = format.parse(request.getParameter("to"));

        List<Log> report = logRepository.findByUserAndTimestampBetween(user,from,to);
        modelAndView.addObject("report",report);
        return modelAndView;
    }

}
