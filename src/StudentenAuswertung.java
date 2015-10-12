import org.yaml.snakeyaml.Yaml;
import java.util.Map;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class StudentenAuswertung {

  private static Yaml yaml = new Yaml();

  public static void main(String... args) throws IOException {
    InputStream bkimminich = new FileInputStream(new File("zenturien/i99a/bkimminich.yml"));
    Map<String, Object> dozent = (Map<String, Object>) yaml.load(bkimminich);
    System.out.println(dozent);

    // TODO Iteration über alle Zenturienordner und Ausgabe aller jeweils enthaltenen Studenten
    // TODO Generieren von Statistiken über Vorkenntnisse je Zenturie
    // TODO Generieren einer HTML-Seite mit allen Daten inkl. Fotos als <img>
  }

}
