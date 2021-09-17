package controllers;

import dataAccess.department.Department;
import dataAccess.department.DepartmentDAO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class DepartmentController {
    DepartmentDAO departmentDAO = new DepartmentDAO();

    @GetMapping("/departmentsList")
    public String filmsListPage(Model model) {
        List<Department> departments = departmentDAO.loadAll();
        model.addAttribute("departments", departments);
        return "departmentsList";
    }

    @GetMapping("/department")
    public String departmentPage(@RequestParam(name = "departmentID", required = false) Integer departmentID,
                                 @RequestParam(name = "msg", required = false) String msg,
                                 Model model) {
        // if departmentID=None => add ne department
        // else => update existing department
        if (departmentID == null) {
            model.addAttribute("department", new Department());
            return "department";
        }

        Department department = departmentDAO.findById(departmentID);
        if (department == null) {
            model.addAttribute("errorMsg", "There is no department with id=" + departmentID);
            return "errorPage";
        }
        model.addAttribute("department", department);
        model.addAttribute("msg", msg);
        return "department";
    }

    @GetMapping("/departmentSave")
    public String departmentSavePage(@RequestParam(name = "departmentID", required = false) Integer departmentID,
                                     @RequestParam(name = "departmentAddress", required = true) String departmentAddress,
                                     Model model) {
        Department department;

        if ((departmentID == null) || (departmentID == -1)) { // save new department
            department = new Department(departmentAddress);
            if (!departmentDAO.save(department)) {
                model.addAttribute("errorMsg", "Adding new was not successful");
                return "errorPage";
            }
        } else {  // update department
            department = departmentDAO.findById(departmentID);
            if (department == null) {
                model.addAttribute("errorMsg", "Try to update non existing user");
                return "errorPage";
            }
            department.setAddress(departmentAddress);
            if (!departmentDAO.update(department)) {
                model.addAttribute("errorMsg", "department updating was not successful");
                return "errorPage";
            }
        }

        return String.format("redirect:/department?departmentID=%d&msg=The department '%s' is updated",
                department.getDepartmentID(), department.getAddress());
    }

    @GetMapping("/departmentDelete")
    public String departmentDeletePage(@RequestParam(name = "departmentID", required = true) Integer departmentID,
                                       Model model) {
        boolean result = departmentDAO.deleteById(departmentID);
        if (!result) {
            model.addAttribute("errorMsg", "Can not delete a department while it has accounts");
            return "errorPage";
        }
        return "redirect:/departmentsList";
    }

}
