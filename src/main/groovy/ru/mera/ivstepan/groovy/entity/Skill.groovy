package ru.mera.ivstepan.groovy.entity

import com.fasterxml.jackson.annotation.JsonIgnore


import javax.persistence.*

@Entity
@Table(name = 'skill', schema = 'groovy')
class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id

    @Column(name = 'description')
    String description

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "worker_id", referencedColumnName = "id")
    Worker worker

    boolean equals(o) {
        if (this.is(o)) return true
        if (getClass() != o.class) return false

        Skill skill = (Skill) o

        if (id != skill.id) return false

        return true
    }

    int hashCode() {
        return (id != null ? id.hashCode() : 0)
    }

    @Override
    public String toString() {
        return "Skill{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }
}
