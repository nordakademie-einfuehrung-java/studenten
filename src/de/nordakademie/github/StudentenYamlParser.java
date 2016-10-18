package de.nordakademie.github;

import de.nordakademie.github.beans.Student;
import de.nordakademie.github.beans.Century;
import de.nordakademie.github.html.HtmlGenerator;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

public class StudentenYamlParser {

    private static Yaml yaml = new Yaml();

    public static void main(String... args) {
        Set<Century> centuries = new HashSet<>();

        try {
            Path startPath = Paths.get("zenturien");
            Files.walkFileTree(startPath, new SimpleFileVisitor<Path>() {

                Century century;

                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                    if (!dir.toString().equals("zenturien")) {
                        century = new Century(dir);
                        centuries.add(century);
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    if (file.toString().endsWith(".html")) {
                        Files.delete(file);
                    } else if (file.toString().endsWith(".yml") || file.toString().endsWith(".yaml")) {
                        InputStream stream = new FileInputStream(file.toFile());
                        Map<String, Object> student = (Map<String, Object>) yaml.load(stream);
                        String ymlFilename = file.getFileName().toString();
                        century.addStudent(new Student(ymlFilename.substring(0, ymlFilename.lastIndexOf(".")), String.valueOf(student.get("name")), String.valueOf(student.get("foto")), String.valueOf(student.get("firma")), (ArrayList<String>) (student.get("vorkenntnisse"))));
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

        for (Century century : centuries) {
            new HtmlGenerator(century).generateCenturyPage();
        }

    }

}
