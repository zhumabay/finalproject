package kz.bitlab.bootcamp.finalproject.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping(value = "/index")
    public String indexPage(Model model){
        return "adminIndex";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping(value = "/categories")
    public String categoriesPage(Model model){
        return "adminCategories";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping(value = "/courses")
    public String coursesPage(Model model){
        return "adminCourses";
    }

}
