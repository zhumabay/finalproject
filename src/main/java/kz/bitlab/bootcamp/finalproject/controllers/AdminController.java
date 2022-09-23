package kz.bitlab.bootcamp.finalproject.controllers;

import kz.bitlab.bootcamp.finalproject.models.Report;
import kz.bitlab.bootcamp.finalproject.services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    @Autowired
    private ReportService reportService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping(value = "/index")
    public String indexPage(Model model){
        model.addAttribute("reports", reportService.getReports());
        return "adminIndex";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping(value = "/checkreport")
    public String checkReport(@RequestParam(value = "report_id") Long reportId){
        Report report = reportService.getReport(reportId);
        report.setHandled(true);
        reportService.updateReport(report);
        return "redirect:/usersprofile/"+report.getPost().getUser().getId();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping(value = "/deletereport")
    public String deleteReport(@RequestParam(value = "report_id") Long reportId){
        reportService.deleteReport(reportId);
        return "redirect:/admin/index";
    }

}
