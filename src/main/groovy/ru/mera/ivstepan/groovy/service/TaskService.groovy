package ru.mera.ivstepan.groovy.service

import org.apache.log4j.Logger
import org.hibernate.Hibernate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.mera.ivstepan.groovy.entity.Task
import ru.mera.ivstepan.groovy.repository.TaskRepository

import javax.persistence.EntityNotFoundException

@Service
@Transactional
class TaskService {

    static final Logger LOGGER = Logger.getLogger(TaskService)

    @Autowired
    TaskRepository taskRepository
    @Autowired
    WorkerService workerService

    List findAll() {
        LOGGER.debug('(Server side) Request for all tasks data received')
        taskRepository.findAll(Sort.by(Sort.Order.desc('date'))).asList()
    }

    Task findById(Integer id) {
        LOGGER.debug('(Server side) Request for task data received: ' + id)
        taskRepository.findById(id)/*.orElse(null)*/.get()
    }

    Task findByIdOrError(Integer id) {
        taskRepository.findById(id).orElseThrow({
            new EntityNotFoundException()
        })
    }

    Task createTask(Task task) {
        LOGGER.debug('(Server side) The request to create task received')
        task.isResolved = false
        taskRepository.save(task)
    }

    Task updateTask(Integer id, Task task) throws EntityNotFoundException {
        LOGGER.debug('(Server side) Task update request received')
        def update = findByIdOrError(id)
        update.with {
            title = task.title
            location = task.location
        }
        taskRepository.save(update)
    }

    Task deleteById(Integer id) {
        LOGGER.debug('(Server side) Task removal request received')
        Task delete = findByIdOrError(id)
        Hibernate.initialize(delete.getWorkers())
        taskRepository.delete(delete)
        delete
    }

    Task assignWorker(Integer idTask, Integer idWorker) {
        LOGGER.debug('(Server side) Task assign new worker received')
        def task = findById(idTask)
        def worker = workerService.findById(idWorker)
        if (worker && task) {
            task.workers.add(worker)
            taskRepository.save(task)
        } else
            throw new Exception()
    }

    Task resolveTask(Integer id) {
        LOGGER.debug('(Server side) Task resolve task request received')
        def task = findById(id)
        if (task) {
            task.isResolved = true
            taskRepository.save(task)
        } else
            throw new Exception()
    }

    Task removeWorker(Integer idTask, Integer idWorker) {
        LOGGER.debug('(Server side) Task to remove a worker with the current task received')
        def task = findById(idTask)
        def worker = workerService.findById(idWorker)
        if (worker && task) {
            task.workers.remove(worker)
            taskRepository.save(task)
        } else
            throw new Exception()
    }

}
