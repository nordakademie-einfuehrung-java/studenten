package de.nordakademie.github.html;

import de.nordakademie.github.beans.Student;
import de.nordakademie.github.beans.Century;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class HtmlGenerator {

    private Century century;
    private String filename;
    private StringBuilder html = new StringBuilder();
    private Map<String, Integer> experience = new HashMap<>();

    public HtmlGenerator(Century century) {
        this.century = century;
        experience.put("keine", 0);
    }

    public void generateCenturyPage() {
        filename = century.getDirectoryPath().toString() + ".html";
        openHtmlPage(filename);
        openTable("Foto", "Name", "Firma", "Vorkenntnisse");
        century.getStudents().stream().sorted().forEach(s -> {
            writeStudentAsTableRow(s);
            accumulateExperience(s.getExperience());
        });
        closeCurrentTable();
        writeExperienceProgressBars();
        closeCurrentHtmlPage();
        generateFile();
    }

    private void openHtmlPage(String title) {
        html.append("<!DOCTYPE html><html lang='de'><title>").append(title).append("</title>");
        html.append("<head>");
        html.append("<meta name='viewport' content='width=device-width, initial-scale=1'>");
        html.append("<link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css'>");
        html.append("<link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css'></head>");
        html.append("<body><div class='container'><div class='row'>");
    }

    private void openTable(String... headers) {
        html.append("<div class='col-md-8'><table class='table table-striped'>");
        html.append("<tr>");
        for (String header : headers) {
            html.append("<th>").append(header).append("</th>");
        }
        html.append("</tr>");
    }

    private void writeStudentAsTableRow(Student student) {
        String name = student.getName() + "<br><a href='https://github.com/" + student.getGithubUser() + "'>@" + student.getGithubUser() + "</a>";
        String photo = "<img src='" + student.getPhoto() + "' width='100' class='img-thumbnail'>";
        writeRow(photo, name, student.getCompany(), asUnorderedList(student.getExperience()));
    }

    private String asUnorderedList(ArrayList<String> items) {
        StringBuilder ul = new StringBuilder();
        if (items != null && !items.isEmpty()) {
            ul.append("<ul>");
            items.stream().forEach(i -> ul.append("<li>").append(i).append("</li>"));
            ul.append("</ul>");
        }
        return ul.toString();
    }

    private void writeRow(Object... data) {
        html.append("<tr>");
        for (Object d : data) {
            if (d != null) {
                html.append("<td>").append(d).append("</td>");
            } else {
                html.append("<td></td>");
            }
        }
        html.append("</tr>");
    }

    private void closeCurrentTable() {
        html.append("</table></div>");
    }

    private void closeCurrentHtmlPage() {
        html.append("</div></div></body></html>");
    }

    private void generateFile() {
        try (PrintStream ps = new PrintStream(filename)) {
            ps.println(html);
            System.out.println("Generated HTML file " + filename + ": " + html);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void writeExperienceProgressBars() {
        html.append("<div class='col-md-4'><div class='panel panel-default'>");
        html.append("<div class='panel-heading'><h3 class='panel-title'>Verteilung der Vorkenntnisse in der Zenturie</h3></div><div class='panel-body'>");
        int numberOfStudents = century.getStudents().size();
        experience.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .forEach(e -> html.append(writeProgressBar(e.getKey(), e.getValue(), numberOfStudents)));
        html.append("</div></div></div>");

    }

    private String writeProgressBar(String language, Integer numberOfStudentsWithPreknowledge, int totalNumberOfStudents) {
        StringBuilder progressBar = new StringBuilder("<div class='progress'>");
        progressBar.append("<span>&nbsp;" + language + "</span>");
        progressBar.append("<div class='progress-bar' role='progressbar' ");
        progressBar.append("aria-valuenow='" + numberOfStudentsWithPreknowledge + "' ");
        progressBar.append("aria-valuemin='0' ");
        progressBar.append("aria-valuemax='" + totalNumberOfStudents + "' ");
        progressBar.append("style='width: " + (totalNumberOfStudents*numberOfStudentsWithPreknowledge > 0 ? (100L/totalNumberOfStudents)*numberOfStudentsWithPreknowledge : 0) + "%; min-width: 2em;'>");
        progressBar.append(numberOfStudentsWithPreknowledge);
        progressBar.append("</div></div>");
        return progressBar.toString();
    }

    private void accumulateExperience(ArrayList<String> exp) {
        if (exp != null && !exp.isEmpty()) {
            exp.stream().forEach(e -> experience.compute(e.toLowerCase(), (vKey, oldValue) -> oldValue == null ? 1 : oldValue + 1));
        } else {
            experience.put("keine", experience.get("keine") + 1);
        }
    }

}

