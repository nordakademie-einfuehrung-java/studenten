import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Zenturie {

    private Path directoryPath;
    private String name;
    private List<Student> students = new ArrayList<>();

    public Zenturie(Path directoryPath) {
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

        Zenturie zenturie = (Zenturie) o;

        return directoryPath.equals(zenturie.directoryPath);

    }

    @Override
    public int hashCode() {
        return directoryPath.hashCode();
    }

}
