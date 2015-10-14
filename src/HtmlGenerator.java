public class HtmlGenerator {

    private StringBuffer html = new StringBuffer();
    private String filename;

    public HtmlGenerator(String filename) {
        this.filename = filename;
    }

    public void openHtmlPage(String title) {
        html.append("<html><title>").append(title).append("</title>");
        html.append("<body>");
    }

    public void openTable(String... headers) {
        html.append("<table>");
        html.append("<tr>");
        for (String header : headers) {
            html.append("<th>").append(header).append("</th>");
        }
        html.append("</tr>");
    }

    public void writeRow(Object... data) {
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
        html.append("</table>");
    }

    public void closeCurrentHtmlPage() {
        html.append("</body></html>");
    }

    public void generateFile() {
        System.out.println("Filename: " + filename); // TODO Implement real file creation
        System.out.println("Content: " + html); // TODO Implement writing HTML into real file
    }

}
