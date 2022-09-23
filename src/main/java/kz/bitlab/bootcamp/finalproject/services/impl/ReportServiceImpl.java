package kz.bitlab.bootcamp.finalproject.services.impl;

import kz.bitlab.bootcamp.finalproject.models.Post;
import kz.bitlab.bootcamp.finalproject.models.Report;
import kz.bitlab.bootcamp.finalproject.repositories.ReportRepository;
import kz.bitlab.bootcamp.finalproject.services.ReportService;
import kz.bitlab.bootcamp.finalproject.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private ReportRepository reportRepository;
    @Autowired
    private UserService userService;

    @Override
    public List<Report> getReports() {
        return reportRepository.findAllByOrderByIdDesc();
    }

    @Override
    public Report getReport(Long reportId) {
        return reportRepository.findById(reportId).orElseThrow();
    }

    @Override
    public Report addReport(Report report) {
        return reportRepository.save(report);
    }

    @Override
    public Report updateReport(Report report) {
        if (reportRepository.findById(report.getId())!=null){
            return reportRepository.save(report);
        }
        return null;
    }

    @Override
    public List<Post> getReportedPosts(List<Post> posts) {
        List<Post> reportedPosts = new ArrayList<>();
        for (Post p : posts) {
            if (reportRepository.findByUserAndPost(userService.getCurrentUser(), p)!=null){
                reportedPosts.add(p);
            }
        }
        return reportedPosts;
    }

    @Override
    public void deleteReport(Long reportId) {
        reportRepository.deleteById(reportId);
    }
}
