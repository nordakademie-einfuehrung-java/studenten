import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Map;

public class HtmlGenerator {

    private StringBuffer html = new StringBuffer();
    private String filename;

    public HtmlGenerator(String filename) {
        this.filename = filename;
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
        String vorkenntnisse = String.valueOf(student.get("vorkenntnisse"));

        name = name + "<br><a href='https://github.com/" + githubUser + "'>@" + githubUser + "</a>";
        foto = "<img src='" + foto + "' width='100' class='img-thumbnail'>";
        vorkenntnisse = renderYmlListAsUnorderedList(vorkenntnisse);

        writeRow(foto, name, firma, vorkenntnisse);
    }

    private String renderYmlListAsUnorderedList(String vorkenntnisse) {
        vorkenntnisse = vorkenntnisse.replace("[", "<ul><li>");
        vorkenntnisse = vorkenntnisse.replaceAll(", ", "</li><li>");
        vorkenntnisse = vorkenntnisse.replace("]", "</li></ul>");
        vorkenntnisse = vorkenntnisse.replace("<ul><li></li></ul>", "");
        return vorkenntnisse;
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

}
