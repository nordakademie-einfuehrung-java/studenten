package de.nordakademie.github.html;

import de.nordakademie.github.beans.Student;
import de.nordakademie.github.beans.Zenturie;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class HtmlGenerator {

    private Zenturie zenturie;
    private String filename;
    private StringBuilder html = new StringBuilder();
    private Map<String, Integer> preKnowledge = new HashMap<>();

    public HtmlGenerator(Zenturie zenturie) {
        this.zenturie = zenturie;
        preKnowledge.put("keine", 0);
    }

    public void generateZenturienPage() {
        filename = zenturie.getDirectoryPath().toString() + ".html";
        openHtmlPage(filename);
        openTable("Foto", "Name", "Firma", "Vorkenntnisse");
        zenturie.getStudents().stream().sorted().forEach(s -> {
            writeStudentAsTableRow(s);
            accumulatePreknowledge(s.getVorkenntnisse());
        });
        closeCurrentTable();
        writePreknowledgeProgressBars();
        closeCurrentHtmlPage();
        generateFile();
    }

    private void openHtmlPage(String title) {
        html.append("<!DOCTYPE html><html lang='de'><title>").append(title).append("</title>");
        html.append("<head>");
        html.append("<meta name='viewport' content='width=device-width, initial-scale=1'>");
        html.append("<link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css'>");
        html.append("<link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css'></head>");
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
        String foto = "<img src='" + student.getFoto() + "' width='100' class='img-thumbnail'>";
        writeRow(foto, name, student.getFirma(), asUnorderedList(student.getVorkenntnisse()));
    }

    private String asUnorderedList(ArrayList<String> items) {
        StringBuilder ul = new StringBuilder();
        if (items != null && !items.isEmpty()) {
            ul.append("<ul>");
            for (String item : items) {
                ul.append("<li>").append(item).append("</li>");
            }
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

    private void writePreknowledgeProgressBars() {
        html.append("<div class='col-md-4'><div class='panel panel-default'>");
        html.append("<div class='panel-heading'><h3 class='panel-title'>Verteilung der Vorkenntnisse in der Zenturie</h3></div><div class='panel-body'>");
        int numberOfStudents = zenturie.getStudents().size();
        preKnowledge.entrySet().stream()
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
        progressBar.append("style='width: " + (100/totalNumberOfStudents*numberOfStudentsWithPreknowledge) + "%;'>");
        progressBar.append(numberOfStudentsWithPreknowledge);
        progressBar.append("</div></div>");
        return progressBar.toString();
    }

    private void accumulatePreknowledge(ArrayList<String> vorkenntnisse) {
        if (vorkenntnisse != null && !vorkenntnisse.isEmpty()) {
            for (String vorkenntnis : vorkenntnisse) {
                if (!preKnowledge.containsKey(vorkenntnis.toLowerCase())) {
                    preKnowledge.put(vorkenntnis.toLowerCase(), 0);
                }
                preKnowledge.put(vorkenntnis.toLowerCase(), preKnowledge.get(vorkenntnis.toLowerCase()) + 1);
            }
        } else {
            preKnowledge.put("keine", preKnowledge.get("keine") + 1);
        }
    }

}

