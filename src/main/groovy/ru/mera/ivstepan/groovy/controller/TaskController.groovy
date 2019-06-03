package ru.mera.ivstepan.groovy.controller

import org.apache.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.mera.ivstepan.groovy.entity.Task
import ru.mera.ivstepan.groovy.service.TaskService

import javax.persistence.EntityNotFoundException
import java.text.SimpleDateFormat

@RestController
@RequestMapping('task')
class TaskController {

    static final Logger LOGGER = Logger.getLogger(TaskController)

    @Autowired
    TaskService taskService

    @GetMapping(value = '', produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity getAllTasks() {
        List<Task> tasks = taskService.findAll()
        if (tasks) {
            LOGGER.debug('(Server side) Tasks data received successfully')
            new ResponseEntity(tasks, HttpStatus.OK)
        } else {
            LOGGER.debug('(Server side) Tasks data not found')
            new ResponseEntity(null, HttpStatus.NO_CONTENT)
        }
    }

    @GetMapping(value = '{id}', produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity getTask(@PathVariable Integer id) {
        try {
            Task task = taskService.findById(id)
            LOGGER.debug('(Server side) Task data received successfully')
            new ResponseEntity(task, HttpStatus.OK)
        } catch (Exception e) {
            LOGGER.debug('(Server side) Task data not found', e.printStackTrace())
            new ResponseEntity(null, HttpStatus.NO_CONTENT)
        }
    }

    @PostMapping(value = '', consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity createTask(@RequestBody Task task) {
        if (task) {
//            String newstring = new SimpleDateFormat("yyyy-MM-dd").format(date)
            task.setDate(new Date())
            Task tempTask = taskService.createTask(task)
            LOGGER.debug('(Server side) Task saved successfully')
            ResponseEntity.status(HttpStatus.CREATED).body(tempTask)
        } else
            LOGGER.debug('(Server side) Mistake. Check the correctness of the entered data.')
        new ResponseEntity(task, HttpStatus.BAD_REQUEST)
    }

    @PutMapping(value = '{id}', consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity updateTask(@PathVariable Integer id, @RequestBody Task task) {
        try {
            Task tempTask = taskService.updateTask(id, task)
            LOGGER.debug('(Server side) Task updated successfully')
            new ResponseEntity(tempTask, HttpStatus.OK)
        } catch (EntityNotFoundException e) {
            LOGGER.debug(e.printStackTrace())
            new ResponseEntity(HttpStatus.NO_CONTENT)
        }
    }

    @DeleteMapping(value = '{id}', produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity deleteTask(@PathVariable Integer id) {
        try {
            Task task = taskService.deleteById(id)
            LOGGER.debug('(Server side) Task data deleted successfully')
            new ResponseEntity(task, HttpStatus.OK)
        } catch (EntityNotFoundException e) {
            LOGGER.debug('(Server side) Task data not found. ID: ' + id, e.printStackTrace())
            new ResponseEntity(null, HttpStatus.NO_CONTENT)
        }
    }

    @PostMapping(value = '{id}/worker/{idWorker}', consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity assignWorker(@PathVariable Integer id, @PathVariable Integer idWorker) {
        try {
            Task task = taskService.assignWorker(id, idWorker)
            LOGGER.debug('(Server side) Worker assigned to task successfully')
            new ResponseEntity(task, HttpStatus.OK)
        } catch (Exception e) {
            LOGGER.debug('(Server side) Task/Worker data not found. ID task: ' + id + '. ID worker: ' + idWorker, e.printStackTrace())
            new ResponseEntity(null, HttpStatus.NO_CONTENT)
        }
    }

    @PostMapping(value = '{id}', consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity resolveTask(@PathVariable Integer id) {
        try {
            Task task = taskService.resolveTask(id)
            LOGGER.debug('(Server side) Task resolve successfully')
            new ResponseEntity(task, HttpStatus.OK)
        } catch (Exception e) {
            LOGGER.debug('(Server side) Task/Worker data not found. ID task: ' + id, e.printStackTrace())
            new ResponseEntity(null, HttpStatus.NO_CONTENT)
        }
    }

    @DeleteMapping(value = '{id}/worker/{idWorker}', produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity removeWorker(@PathVariable Integer id, @PathVariable Integer idWorker) {
        try {
            Task task = taskService.removeWorker(id, idWorker)
            LOGGER.debug('(Server side) Worker remove successfully')
            new ResponseEntity(task, HttpStatus.OK)
        } catch (Exception e) {
            LOGGER.debug('(Server side) Task/Worker data not found. ID task: ' + id, e.printStackTrace())
            new ResponseEntity(null, HttpStatus.NO_CONTENT)
        }
    }
}