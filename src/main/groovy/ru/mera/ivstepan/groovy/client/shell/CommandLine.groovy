package ru.mera.ivstepan.groovy.client.shell

import org.apache.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod
import org.springframework.shell.standard.ShellOption
import ru.mera.ivstepan.groovy.client.service.TaskServiceClient
import ru.mera.ivstepan.groovy.client.service.WorkerServiceClient
import ru.mera.ivstepan.groovy.controller.TaskController
import ru.mera.ivstepan.groovy.entity.Task
import ru.mera.ivstepan.groovy.entity.Worker

@ShellComponent
class CommandLine {

    static final Logger LOGGER = Logger.getLogger(TaskController)

    @Autowired
    WorkerServiceClient workerServiceClient
    @Autowired
    TaskServiceClient taskServiceClient

    @ShellMethod(value = 'Save worker. Arguments: -n String name', key = 'wsave')
    void saveWorker(@ShellOption('-n') String name) {
        LOGGER.debug('Employee data retention request sent')
        println(workerServiceClient.saveWorker(name))

    }

    @ShellMethod(value = 'Update worker. Arguments: -id Integer workerId, -n String name', key = 'wupdate')
    void updateWorker(@ShellOption('-id') Integer id, @ShellOption('-n') String name) {
        LOGGER.debug('Request to update employee data sent')
        println(workerServiceClient.updateWorker(id, name))
    }

    @ShellMethod(value = 'Delete worker. Arguments: -id Integer workerId', key = 'wdelete')
    void deleteWorker(@ShellOption('-id') Integer workerId) {
        LOGGER.debug('Employee deletion request sent.')
        println(workerServiceClient.deleteWorker(workerId))
    }

    @ShellMethod(value = 'Get worker by id. Arguments: -id Integer workerId', key = 'wget')
    void getWorker(@ShellOption('-id') Integer workerId) {
        LOGGER.debug('Request for employee data sent')
        println(workerServiceClient.getWorker(workerId).toString())
    }

    @ShellMethod(value = 'Get all workers. Arguments: none', key = 'wgetall')
    void getWorkers() {
        LOGGER.debug('Request to receive data of all employees sent')
        Worker[] list = workerServiceClient.getWorkers()
        if (list) {
            for (Worker worker : list) {
                println('(Client side) Worker: ' + worker)
            }
        }
    }

    @ShellMethod(value = 'Create task. Arguments: -t String title, -l String location', key = 'tsave')
    void saveTask(@ShellOption('-t') String title,
                  @ShellOption('-l') String location) {
        LOGGER.debug('Task retention request sent')
        println(taskServiceClient.createTask(title, location))
    }

    @ShellMethod(value = 'Update task. Arguments: -id Integer taskId, -t String title, -l String location', key = 'tupdate')
    void updateTask(@ShellOption('-id') Integer id,
                    @ShellOption('-t') String title,
                    @ShellOption('-l') String location) {
        LOGGER.debug('Request to update task sent')
        println(taskServiceClient.updateTask(id, title, location))
    }

    @ShellMethod(value = 'Get task. Arguments: -id Integer taskId', key = 'tget')
    void getTask(@ShellOption('-id') Integer id) {
        LOGGER.debug('Request for task data sent')
        println(taskServiceClient.getTask(id))
    }

    @ShellMethod(value = 'Get all tasks. Arguments: none', key = 'tgetall')
    void getTasks() {
        LOGGER.debug('Request to receive data on all tasks sent')
        Task[] tasks = taskServiceClient.getTasks()
        if (tasks) {
            for (Task task : tasks)
                println('(Client side) Task: ' + task)
        }
    }

    @ShellMethod(value = 'Delete task. Arguments: -id Integer id', key = 'tdelete')
    void deleteTask(@ShellOption('-id') Integer id) {
        LOGGER.debug('Task deletion request sent.')
        println(taskServiceClient.deleteTask(id))
    }

    @ShellMethod(value = 'Assign worker to task. Arguments: -idt Integer idTask, -idw Integer idWorker', key = 'assign')
    void assignWorker(@ShellOption('-idt') Integer idTask, @ShellOption('-idw') Integer idWorker) {
        LOGGER.debug('Request to assign task to worker sent')
        println(taskServiceClient.assignWorker(idTask, idWorker))
    }

    @ShellMethod(value = 'Change task status to "resolved". Arguments: -id Integer idTask', key = 'tresolve')
    void resolveTask(@ShellOption('-id') Integer id) {
        LOGGER.debug('Task status change request sent')
        println(taskServiceClient.resolveTask(id))
    }

    @ShellMethod(value = 'Remove worker from task. Arguments: -idt Integer idTask, -idw Integer idWorker', key = 'wremove')
    void removeWorker(@ShellOption('-idt') Integer idTask, @ShellOption('-idw') Integer idWorker) {
        LOGGER.debug('Request to remove worker from task sent')
        println(taskServiceClient.removeWorker(idTask, idWorker))
    }

}
