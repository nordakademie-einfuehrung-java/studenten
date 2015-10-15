import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

public class StudentenAuswertung {

    private static Yaml yaml = new Yaml();

    public static void main(String... args) {
        Set<Zenturie> zenturien = new HashSet<>();

        try {
            Path startPath = Paths.get("zenturien");
            Files.walkFileTree(startPath, new SimpleFileVisitor<Path>() {

                Zenturie zenturie;

                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                    if (!dir.toString().equals("zenturien")) {
                        zenturie = new Zenturie(dir);
                        zenturien.add(zenturie);
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
                        String ymlFilename = file.getFileName().toString();
                        zenturie.addStudent(new Student(ymlFilename.substring(0, ymlFilename.lastIndexOf(".")), String.valueOf(student.get("name")), String.valueOf(student.get("foto")), String.valueOf(student.get("firma")), (ArrayList<String>) (student.get("vorkenntnisse"))));
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException e) {
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException e) {
                    return FileVisitResult.CONTINUE;
                }

            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Zenturie zenturie : zenturien) {
            new HtmlGenerator(zenturie).generateZenturienPage();
        }

    }

}
