package ru.mera.ivstepan.groovy.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository
import ru.mera.ivstepan.groovy.entity.Task

@Repository
interface TaskRepository extends PagingAndSortingRepository<Task, Integer> {

}