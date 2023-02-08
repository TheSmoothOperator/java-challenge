package jp.co.axa.apidemo.controllers;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employees")
    public List<Employee> getEmployees() {
        List<Employee> employees = employeeService.retrieveEmployees();
        return employees;
    }

    @GetMapping("/employees/{employeeId}")
    public Employee getEmployee(@PathVariable(name = "employeeId") Long employeeId) {
        return employeeService.getEmployee(employeeId);
    }

    /**
     * Suggestion to use a standard employee that accpets JSON and uses the url call
     * to understand what is being asked. ie, GET/POST/etc
     * 
     * @param employee
     */
    @PostMapping("/employee")
    public void saveEmployee(@RequestBody Employee employee) {
        try {
            employeeService.saveEmployee(employee);
            System.out.println("Employee Saved Successfully");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Also suggest using try/catch overtop of these to remove any downtime that
     * might be caused from bad data being sent. The use of better exceptions or
     * using finally would also make these methods more concrete for an API.
     * 
     * @param employeeId
     */
    @DeleteMapping("/employee")
    public void deleteEmployee(@RequestBody Long employeeId) {
        try {
            employeeService.deleteEmployee(employeeId);
            System.out.println("Employee " + employeeId + " Deleted Successfully");
        } catch (Exception e) {
            System.out.println("Employee" + employeeId + " does not exist or was not deleted.");
            System.out.println(e);
        }
    }

    /**
     * Also better logging. Whether using another package, storing in H2, etc.
     * Using verbose wording for logging to understand not only the method used, but
     * what data might be going with it. ie: the employeeid for example
     * 
     * @param employee
     */
    @PutMapping("/employee")
    public void updateEmployee(@RequestBody Employee employee) {
        try {
            Long employeeId = employee.getId();
            Employee emp = employeeService.getEmployee(employeeId);
            if (emp != null) {
                employeeService.updateEmployee(employee);
                System.out.println("Employee" + emp.getId() + " update was Successful");
            } else {
                System.out.println("Employee" + employee.getId() + " does not exist");
            }
        } catch (Exception e) {
            System.out.println("Employee" + employee.getId() + " something went wrong");
            System.out.println(e);
        }

    }

}
