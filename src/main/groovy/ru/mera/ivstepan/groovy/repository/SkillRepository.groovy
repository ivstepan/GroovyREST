package ru.mera.ivstepan.groovy.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.mera.ivstepan.groovy.entity.Skill

@Repository
interface SkillRepository extends CrudRepository<Skill, Integer> {

}