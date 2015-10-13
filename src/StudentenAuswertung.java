import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Map;

public class StudentenAuswertung {

    private static Yaml yaml = new Yaml();

    public static void main(String... args) {
        try {
            Path startPath = Paths.get("zenturien");
            Files.walkFileTree(startPath, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws FileNotFoundException {
                    InputStream stream = new FileInputStream(file.toFile());
                    Map<String, Object> student = (Map<String, Object>) yaml.load(stream);

                    System.out.println(file.toString() + " -> " + student);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException e) {
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        // TODO Generieren von Statistiken über Vorkenntnisse je Zenturie
        // TODO Generieren einer HTML-Seite mit allen Daten inkl. Fotos als <img>
    }

}
