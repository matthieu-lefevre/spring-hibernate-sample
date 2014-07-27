package com.mlefevre.samples.hibernate3.entity;

import javax.persistence.*;

@Entity
@Table(name = "MY_CLASS", uniqueConstraints = {@UniqueConstraint(columnNames = {"TITLE", "VERSION"})})
public class MyClass {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    protected int id;

    @Column(name = "TITLE")
    protected String title;

    @Column(name = "VERSION")
    protected int version;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }


    @Override
    public String toString() {
        return "MyClass{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", version=" + version +
                '}';
    }
}
