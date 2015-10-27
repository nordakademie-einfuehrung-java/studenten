package de.nordakademie.github.beans;

import java.util.ArrayList;

public class Student implements Comparable<Student> {

    private String githubUser;
    private String name;
    private String photo;
    private String company;
    private ArrayList<String> experience;

    public Student(String githubUser, String name, String photo, String company, ArrayList<String> experience) {
        this.githubUser = githubUser;
        this.name = name;
        this.photo = photo;
        this.company = company;
        this.experience = experience;
    }

    public String getGithubUser() {
        return githubUser;
    }

    public String getName() {
        return name;
    }

    public String getPhoto() {
        return photo;
    }

    public String getCompany() {
        return company;
    }

    public ArrayList<String> getExperience() {
        return experience;
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
