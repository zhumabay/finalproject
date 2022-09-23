package kz.bitlab.bootcamp.finalproject.services;

import kz.bitlab.bootcamp.finalproject.models.Post;
import kz.bitlab.bootcamp.finalproject.models.Report;

import java.util.List;

public interface ReportService {
    List<Report> getReports();
    Report getReport(Long reportId);
    Report addReport(Report report);
    Report updateReport(Report report);
    List<Post> getReportedPosts(List<Post> posts);
    void deleteReport(Long reportId);
}
