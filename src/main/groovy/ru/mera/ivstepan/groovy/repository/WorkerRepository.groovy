package ru.mera.ivstepan.groovy.repository

import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository
import ru.mera.ivstepan.groovy.entity.Worker

@Repository
interface WorkerRepository extends PagingAndSortingRepository<Worker, Integer> {

}