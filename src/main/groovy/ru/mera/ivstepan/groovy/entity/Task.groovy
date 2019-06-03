package ru.mera.ivstepan.groovy.entity

import org.springframework.format.annotation.DateTimeFormat

import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.Table
import javax.persistence.Temporal
import javax.persistence.TemporalType

@Entity
@Table(name = 'task', schema = 'groovy')
class Task {

    Task() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id

    @Column(name = 'title')
    String title

    @Column(name = 'location')
    String location

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Column(name = 'date')
    Date date

    @Column(name = 'isResolved')
    boolean isResolved = false

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = 'task_worker', schema = 'groovy', joinColumns = @JoinColumn(name = 'task_id'), inverseJoinColumns = @JoinColumn(name = 'worker_id'))
    List<Worker> workers

    boolean equals(o) {
        if (this.is(o)) return true
        if (getClass() != o.class) return false

        Task task = (Task) o

        if (id != task.id) return false

        return true
    }

    int hashCode() {
        return (id != null ? id.hashCode() : 0)
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", location='" + location + '\'' +
                ", date=" + date +
                ", isResolved=" + isResolved +
                ", workers=" + workers +
                '}';
    }
}
