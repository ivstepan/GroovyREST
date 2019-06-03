package ru.mera.ivstepan.groovy.controller

import org.apache.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.mera.ivstepan.groovy.entity.Worker
import ru.mera.ivstepan.groovy.service.WorkerService

import javax.persistence.EntityNotFoundException

@RestController
@RequestMapping('worker')
class WorkerController {

    static final Logger LOGGER = Logger.getLogger(TaskController)

    @Autowired
    WorkerService workerService

    @GetMapping(value = '', produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity getAllWorkers() {
        List workers = workerService.findAll()
        if (workers) {
            LOGGER.debug('(Server side) Employees data received successfully')
            new ResponseEntity(workers, HttpStatus.OK)
        } else {
            LOGGER.debug('(Server side) Employees data not found')
            new ResponseEntity(HttpStatus.NO_CONTENT)
        }
    }

    @GetMapping(value = '{id}', produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity getWorkersById(@PathVariable Integer id) {
        def worker = workerService.findById(id)
        if (worker) {
            LOGGER.debug('(Server side) Employee data received successfully')
            ResponseEntity.ok(worker)
        } else {
            LOGGER.debug('(Server side) Employee data not found')
            ResponseEntity.noContent().build()
        }
    }

    @PostMapping(value = '', consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity createWorker(@RequestBody String name) {
        def worker = workerService.saveWorker(name)
        if (worker) {
            LOGGER.debug('(Server side) Employee data saved successfully')
            new ResponseEntity(worker, HttpStatus.CREATED)
        } else {
            LOGGER.debug('(Server side) Mistake. Check the correctness of the entered data.')
            new ResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY)
        }
    }

    @PutMapping(value = '{id}', consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity updateWorker(@PathVariable Integer id, @RequestBody String name) {
        def worker = workerService.updateWorker(id, name)
        if (worker) {
            LOGGER.debug('(Server side) Employee data saved successfully')
            new ResponseEntity(worker, HttpStatus.CREATED)
        } else {
            LOGGER.debug('(Server side) Mistake. Check the correctness of the entered data.')
            new ResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY)
        }
    }

    @DeleteMapping(value = '{id}', produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity deleteWorker(@PathVariable Integer id) {
        ResponseEntity<Worker> tempWorker = workerService.deleteWorker(id)
        if (tempWorker.getStatusCode() == HttpStatus.OK) {
            LOGGER.debug('(Server side) Employee data deleted successfully')
            ResponseEntity.status(HttpStatus.OK).body(tempWorker.getBody())
        } else {
            LOGGER.debug('(Server side) Employees data not found. ID: ' + id)
            new ResponseEntity(null, HttpStatus.NO_CONTENT)
        }
    }

}
