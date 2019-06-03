package ru.mera.ivstepan.groovy.service

import org.apache.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.mera.ivstepan.groovy.entity.Worker
import ru.mera.ivstepan.groovy.repository.WorkerRepository

import javax.persistence.EntityNotFoundException

@Service
@Transactional
class WorkerService {

    static final Logger LOGGER = Logger.getLogger(WorkerService)

    @Autowired
    WorkerRepository workerRepository

    List findAll() {
        LOGGER.debug('(Server side) Request for all worker data received')
        workerRepository.findAll(Sort.by('id')).asList()
    }

    Worker findById(Integer id) {
        LOGGER.debug('(Server side) Request for worker data received: ' + id)
        workerRepository.findById(id).orElse(null)
    }

    Worker findByIdOrError(Integer id) {
        workerRepository.findById(id).orElseThrow({
            new EntityNotFoundException()
        })
    }

    Worker saveWorker(String name) {
        LOGGER.debug('(Server side) The request to save worker data received')
        workerRepository.save(new Worker(name: name))
    }

    Worker updateWorker(Integer id, String newName) {
        LOGGER.debug('(Server side) Worker update request received')
        def update = findById(id)
        update.with {
            name = newName
        }
        workerRepository.save(update)
    }

    ResponseEntity<Worker> deleteWorker(Integer id) throws Exception {
        LOGGER.debug('(Server side) Worker removal request received')
        def delete = findById(id)
        if (delete) {
//            ResponseEntity.noContent().build();
            workerRepository.delete(delete)
            ResponseEntity.ok(delete)
        } else {
//            ResponseEntity.notFound().build();
            ResponseEntity.status(HttpStatus.NO_CONTENT).body(null)
        }
    }
}
