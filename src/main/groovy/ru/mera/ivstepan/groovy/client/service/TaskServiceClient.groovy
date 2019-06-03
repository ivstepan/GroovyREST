package ru.mera.ivstepan.groovy.client.service

import org.apache.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import ru.mera.ivstepan.groovy.entity.Task

@Service
class TaskServiceClient {

    static final Logger LOGGER = Logger.getLogger(TaskServiceClient)
    final static String REST_SERVICE_TASK_HOST = 'http://localhost:8080/task/'

    @Autowired
    WebClient webClient

    Task createTask(String title, String location) {
        def task = webClient.post()
                .uri('/task')
                .body(Mono.just(new Task(title: title, location: location)), Task)
                .exchange()
                .block()
                .bodyToMono(Task)
                .block()
        if (task) {
            LOGGER.debug('(Client side) Task successfully saved!')
            task
        } else {
            LOGGER.debug('(Client side) Task not saved')
            null
        }
    }

    Task updateTask(Integer id, String title, String location) {
        def task = webClient.put()
                .uri('/task/{id}', id)
                .body(Mono.just(new Task(title: title, location: location)), Task)
                .exchange()
                .block()
                .bodyToMono(Task)
                .block()
        if (task) {
            LOGGER.debug('(Client side) Task successfully updated!')
            task
        } else {
            LOGGER.debug('(Client side) Task not updated')
            null
        }
    }

    Task assignWorker(Integer idTask, Integer idWorker) {
        def task = webClient.post()
                .uri('/task/{id}/worker/{idWorker}', idTask, idWorker)
                .retrieve()
                .bodyToMono(Task)
                .block()
        if (task) {
            LOGGER.debug('(Client side) Worker assigned to task successfully!')
            task
        } else {
            LOGGER.debug('(Client side) Worker not assigned task')
            null
        }
    }

    Task resolveTask(Integer id) {
        def task = webClient.post()
                .uri('/task/{id}', id)
                .retrieve()
                .bodyToMono(Task)
                .block()
        if (task) {
            LOGGER.debug('(Client side) Task resolve successfully!')
            task
        } else {
            LOGGER.debug('(Client side) Task status not changed')
            null
        }
    }

    Task getTask(Integer id) {
        HttpHeaders headers = new HttpHeaders()
        headers.add('Accept', MediaType.APPLICATION_JSON_UTF8_VALUE)
        headers.add('Content-Type', MediaType.APPLICATION_JSON_UTF8_VALUE)
        HttpEntity entity = new HttpEntity(headers)

        RestTemplate restTemplate = new RestTemplate()
        ResponseEntity responseEntity = restTemplate.exchange(REST_SERVICE_TASK_HOST + id, HttpMethod.GET, entity, Task)
        if (responseEntity.getStatusCode() == HttpStatus.OK && responseEntity) {
            LOGGER.debug('(Client side) Task data successfully received!')
            responseEntity.getBody()
        } else if (responseEntity.getStatusCode() == HttpStatus.NO_CONTENT) {
            LOGGER.debug('(Client side) Task not found. ID: ' + id)
            responseEntity.getBody()
        } else
            throw new Exception('Unknown error')
    }

    List getTasks() {
        HttpHeaders headers = new HttpHeaders()
        headers.add('Accept', MediaType.APPLICATION_JSON_UTF8_VALUE)
        headers.add('Content-Type', MediaType.APPLICATION_JSON_UTF8_VALUE)
        HttpEntity<Task[]> entity = new HttpEntity<Task[]>(headers)

        RestTemplate restTemplate = new RestTemplate()
        ResponseEntity<Task[]> responseEntity = restTemplate.exchange(REST_SERVICE_TASK_HOST, HttpMethod.GET, entity, Task[])
        if (responseEntity.getStatusCode() == HttpStatus.OK && responseEntity) {
            LOGGER.debug('(Client side) Tasks data successfully received!')
            responseEntity.getBody()
        } else if (responseEntity.getStatusCode() == HttpStatus.NO_CONTENT) {
            LOGGER.debug('(Client side) Tasks not found.')
            responseEntity.getBody()
        } else
            throw new Exception('Unknown error')
    }

    Task deleteTask(Integer id) {
        HttpHeaders headers = new HttpHeaders()
        headers.add('Accept', MediaType.APPLICATION_JSON_UTF8_VALUE)
        headers.add('Content-Type', MediaType.APPLICATION_JSON_UTF8_VALUE)
        HttpEntity entity = new HttpEntity(headers)

        RestTemplate restTemplate = new RestTemplate()
        ResponseEntity responseEntity = restTemplate.exchange(REST_SERVICE_TASK_HOST + id, HttpMethod.DELETE, entity, Task)
        if (responseEntity.getStatusCode() == HttpStatus.OK && responseEntity) {
            LOGGER.debug('(Client side) Task data successfully received!')
            responseEntity.getBody()
        } else if (responseEntity.getStatusCode() == HttpStatus.NO_CONTENT) {
            LOGGER.debug('(Client side) Task not found. ID: ' + id)
            responseEntity.getBody()
        } else
            throw new Exception('Unknown error')
    }

    Task removeWorker(Integer idTask, Integer idWorker) {
        HttpHeaders headers = new HttpHeaders()
        headers.add('Accept', MediaType.APPLICATION_JSON_UTF8_VALUE)
        headers.add('Content-Type', MediaType.APPLICATION_JSON_UTF8_VALUE)
        HttpEntity entity = new HttpEntity(headers)

        RestTemplate restTemplate = new RestTemplate()
        ResponseEntity response = restTemplate.exchange(REST_SERVICE_TASK_HOST + idTask + '/worker/' + idWorker, HttpMethod.DELETE, entity, Task)
        if (response.getStatusCode() == HttpStatus.OK && response) {
            LOGGER.debug('(Client side) Worker remove successfully!')
            response.getBody()
        } else if (response.getStatusCode() == HttpStatus.NO_CONTENT) {
            LOGGER.debug('(Client side) Worker not removed. ID worker: ' + idWorker)
            response.getBody()
        } else
            throw new Exception('Unknown error')
    }
}
