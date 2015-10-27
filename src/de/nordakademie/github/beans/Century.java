package de.nordakademie.github.beans;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Century {

    private Path directoryPath;
    private String name;
    private List<Student> students = new ArrayList<>();

    public Century(Path directoryPath) {
        this.directoryPath = directoryPath;
        String directory = directoryPath.toString();
        this.name = directory.replaceAll("\\\\", "/").substring(directory.lastIndexOf("/")+1);
    }

    public Path getDirectoryPath() {
        return directoryPath;
    }

    public String getName() {
        return name;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Century century = (Century) o;

        return directoryPath.equals(century.directoryPath);

    }

    @Override
    public int hashCode() {
        return directoryPath.hashCode();
    }

}
