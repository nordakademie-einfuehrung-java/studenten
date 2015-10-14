import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class HtmlGenerator {

    private StringBuffer html = new StringBuffer();
    private String filename;
    private Map<String, Integer> preKnowledge = new HashMap<>();

    public HtmlGenerator(String filename) {
        this.filename = filename;
        preKnowledge.put("keine", 0);
    }

    public void openHtmlPage(String title) {
        html.append("<html><title>").append(title).append("</title>");
        html.append("<head>");
        html.append("<link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css'>");
        html.append("<link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css'></head>");
        html.append("<body>");
    }

    public void openTable(String... headers) {
        html.append("<div class='col-md-6'><table class='table table-striped'>");
        html.append("<tr>");
        for (String header : headers) {
            html.append("<th>").append(header).append("</th>");
        }
        html.append("</tr>");
    }

    public void writeStudentAsTableRow(Map<String, Object> student, String ymlFilename) {
        String githubUser = ymlFilename.substring(0, ymlFilename.lastIndexOf("."));
        String name = String.valueOf(student.get("name"));
        String foto = String.valueOf(student.get("foto"));
        String firma = String.valueOf(student.get("firma"));
        ArrayList<String> vorkenntnisse = (ArrayList<String>) (student.get("vorkenntnisse"));

        name = name + "<br><a href='https://github.com/" + githubUser + "'>@" + githubUser + "</a>";
        foto = "<img src='" + foto + "' width='100' class='img-thumbnail'>";

        writeRow(foto, name, firma, asUnorderedList(vorkenntnisse));
    }

    private String asUnorderedList(ArrayList<String> vorkenntnisse) {
        StringBuffer ul = new StringBuffer();
        if (vorkenntnisse != null && !vorkenntnisse.isEmpty()) {
            ul.append("<ul>");
            for (String vorkenntnis : vorkenntnisse) {
                ul.append("<li>").append(vorkenntnis).append("</li>");
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

    public void closeCurrentTable() {
        html.append("</table></div>");
    }

    public void closeCurrentHtmlPage() {
        html.append("</body></html>");
    }

    public void generateFile() {
        System.out.println("Filename: " + filename);
        System.out.println("Content: " + html);
        try (PrintStream ps = new PrintStream(filename)) {
            ps.println(html);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Map<String, Integer> getPreKnowledge() {
        return preKnowledge;
    }

    public void writePreknowledgeProgressBars() {
        html.append("<div class='col-md-4'><div class='panel panel-default'>");
        html.append("<div class='panel-heading'><h3 class='panel-title'>Verteilung der Vorkenntnisse in der Zenturie</h3></div><div class='panel-body'>");
        preKnowledge.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .forEach(e -> html.append(e.getKey() + " <span class='badge'>" + e.getValue() + "</span><br>"));
        html.append("</div></div></div>");

    }
}
