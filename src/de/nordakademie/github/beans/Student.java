package de.nordakademie.github.beans;

import java.util.ArrayList;

public class Student implements Comparable<Student> {

    private String githubUser;
    private String name;
    private String foto;
    private String firma;
    private ArrayList<String> vorkenntnisse;

    public Student(String githubUser, String name, String foto, String firma, ArrayList<String> vorkenntnisse) {
        this.githubUser = githubUser;
        this.name = name;
        this.foto = foto;
        this.firma = firma;
        this.vorkenntnisse = vorkenntnisse;
    }

    public String getGithubUser() {
        return githubUser;
    }

    public String getName() {
        return name;
    }

    public String getFoto() {
        return foto;
    }

    public String getFirma() {
        return firma;
    }

    public ArrayList<String> getVorkenntnisse() {
        return vorkenntnisse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;

        return githubUser.equals(student.githubUser);

    }

    @Override
    public int hashCode() {
        return githubUser.hashCode();
    }


    @Override
    public int compareTo(Student that) {
        if (this.name.compareTo(that.name) < 0) {
            return -1;
        } else if (this.name.compareTo(that.name) > 0) {
            return 1;
        }

        if (this.githubUser.compareTo(that.githubUser) < 0) {
            return -1;
        } else if (this.githubUser.compareTo(that.githubUser) > 0) {
            return 1;
        }
        return 0;
    }
}
