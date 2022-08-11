package application.Controller;

import application.Entity.Employee;
import application.Entity.Log;
import application.Entity.Role;
import application.Entity.Users;
import application.Repository.EmployeeRepository;
import application.Repository.LogRepository;
import application.Repository.RoleRepository;
import application.Repository.UserRepository;
import application.Service.EmployeeService;
import application.Validator.EmployeeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private LogRepository logRepository;

    private EmployeeValidator validator;

    @RequestMapping(value = "new",method = RequestMethod.GET)
    public String showForm(ModelAndView modelAndView) {
        return "admin/new";
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public String registerUser(HttpServletRequest request) {
        return employeeService.registerUser(request);
    }

    @RequestMapping(value = "{id}/edit",method = RequestMethod.GET)
    public String update(@PathVariable long id, Model model) {
       return employeeService.update(id, model);
    }

    @RequestMapping(value = "update",method = RequestMethod.POST)
    public String updateEmployee(HttpServletRequest request) {
        return employeeService.updateEmployee(request);
    }

    @RequestMapping(value = "/search",method = RequestMethod.GET)
    public String displaySearch(){
        return "admin/search";
    }
    @RequestMapping(value = "/search",method = RequestMethod.POST)
    public String search(HttpServletRequest request) {
        return employeeService.search(request);
    }

    @RequestMapping(value = "/{id}/view",method = RequestMethod.GET)
    public String view(@PathVariable long id, Model model) {
        return employeeService.view(id, model);
    }

    @RequestMapping(value = "/{id}/delete",method = RequestMethod.GET)
    public String delete(@PathVariable long id) {
        employeeRepository.delete(id);
        return "redirect:/index";
    }

    @RequestMapping(value = "/report",method = RequestMethod.GET)
    public String showForm() {
        return "admin/report";
    }

    @RequestMapping(value = "/report",method = RequestMethod.POST)
    public ModelAndView searchEmployee(HttpServletRequest request) throws Exception {
        return employeeService.searchEmployee(request);
    }

    @RequestMapping(value = "/showReport",method = RequestMethod.GET)
    public String showReport() {
        return "admin/showReport";
    }

}
