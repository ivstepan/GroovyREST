package ru.mera.ivstepan.groovy.entity

import com.fasterxml.jackson.annotation.JsonIgnore

import javax.persistence.*

@Entity
@Table(name = 'worker', schema = 'groovy')
class Worker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id

    @Column(name = 'name')
    String name

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = 'worker')
    List<Skill> skills

    @JsonIgnore
//    @ManyToMany(/*cascade = CascadeType.ALL, */mappedBy = 'workers')
    @ManyToMany
    @JoinTable(name = 'task_worker', schema = 'groovy', joinColumns = @JoinColumn(name = 'worker_id'), inverseJoinColumns = @JoinColumn(name = 'task_id'))
    List<Task> tasks

    boolean equals(o) {
        if (this.is(o)) return true
        if (getClass() != o.class) return false

        Worker worker = (Worker) o

        if (id != worker.id) return false

        return true
    }

    int hashCode() {
        return (id != null ? id.hashCode() : 0)
    }

    @Override
    public String toString() {
        return "Worker{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", skills=" + skills +
                '}';
    }
}
