import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;

public class StudentenAuswertung {

    private static Map<String, HtmlGenerator> htmlFiles = new HashMap<>();

    public static void main(String... args) {
        try {
            Path startPath = Paths.get("zenturien");
            Files.walkFileTree(startPath, new SimpleFileVisitor<Path>() {

                Yaml yaml = new Yaml();
                HtmlGenerator generator;

                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                    if (!dir.toString().equals("zenturien")) {
                        String filename = dir.toString()+".html";
                        if (!htmlFiles.containsKey(filename)) {
                            generator = new HtmlGenerator(filename);
                            generator.openHtmlPage(dir.toString());
                            generator.openTable("Foto", "Name", "Firma", "Vorkenntnisse");
                            htmlFiles.put(filename, generator);
                        }
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    if (file.toString().endsWith(".html")) {
                        Files.delete(file);
                    } else {
                        InputStream stream = new FileInputStream(file.toFile());
                        Map<String, Object> student = (Map<String, Object>) yaml.load(stream);
                        System.out.println(file.toString() + " -> " + student);
                        generator.writeStudentAsTableRow(student, file.getFileName().toString());
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException e) {
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException e) {
                    generator.closeCurrentTable();
                    generator.closeCurrentHtmlPage();
                    return FileVisitResult.CONTINUE;
                }

            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (HtmlGenerator htmlFile : htmlFiles.values()) {
            htmlFile.generateFile();
        }

    }

}
