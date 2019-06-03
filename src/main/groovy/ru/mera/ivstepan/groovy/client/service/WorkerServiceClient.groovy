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
import ru.mera.ivstepan.groovy.entity.Worker

@Service
class WorkerServiceClient {

    static final Logger LOGGER = Logger.getLogger(WorkerServiceClient)
    final static String REST_SERVICE_WORKER_HOST = 'http://localhost:8080//worker//'

    @Autowired
    WebClient webClient

    Worker saveWorker(String name) {
        def worker = webClient.post()
                        .uri('/worker')
                        .body(Mono.just(name), String)
                        .retrieve()
                        .bodyToMono(Worker)
                        .block()
        if (worker) {
            LOGGER.debug('(Client side) Worker data successfully saved!')
            worker
        } else {
            LOGGER.debug('(Client side) Worker data not saved')
            null
        }
    }

    Worker updateWorker(Integer id, String name) {
        def worker = webClient.put()
                        .uri('/worker/{id}', id)
                        .body(Mono.just(name), String)
                        .retrieve()
                        .bodyToMono(Worker)
                        .block()
        if (worker) {
            LOGGER.debug('(Client side) Worker data successfully updated!')
            worker
        } else {
            LOGGER.debug('(Client side) Worker data not updated')
            null
        }
    }

    Worker deleteWorker(Integer id) {
        HttpHeaders headers = new HttpHeaders()
        headers.add('Accept', MediaType.APPLICATION_JSON_UTF8_VALUE)
        headers.add('Content-Type', MediaType.APPLICATION_JSON_UTF8_VALUE)
        HttpEntity<Worker> entity = new HttpEntity<Worker>(headers)

        RestTemplate restTemplate = new RestTemplate()
        ResponseEntity response = restTemplate.exchange(REST_SERVICE_WORKER_HOST + id, HttpMethod.DELETE, entity, Worker)
        if (response.getStatusCode() == HttpStatus.OK && response.getBody()) {
            LOGGER.debug('(Client side) Worker data successfully deleted!')
            response.getBody()
        } else if (response.getStatusCode() == HttpStatus.NO_CONTENT) {
            LOGGER.debug('(Client side) Worker not found. ID: ' + id)
            response.getBody()
        } else
            throw new Exception('Unknown error')
    }

    Worker getWorker(Integer id) {
        HttpHeaders headers = new HttpHeaders()
        headers.add('Accept', MediaType.APPLICATION_JSON_UTF8_VALUE)
        headers.add('Content-Type', MediaType.APPLICATION_JSON_UTF8_VALUE)
        HttpEntity<Worker> entity = new HttpEntity<Worker>(headers)

        RestTemplate restTemplate = new RestTemplate()
        ResponseEntity response = restTemplate.exchange(REST_SERVICE_WORKER_HOST + id, HttpMethod.GET, entity, Worker)
        if (response.getStatusCode() == HttpStatus.OK && response.getBody()) {
            LOGGER.debug('(Client side) Worker data successfully received!')
            response.getBody()
        } else if (response.getStatusCode() == HttpStatus.NO_CONTENT) {
            LOGGER.debug('(Client side) Worker not found. ID: ' + id)
            response.getBody()
        } else
            throw new Exception('Unknown error')
    }

    List getWorkers() {
        HttpHeaders headers = new HttpHeaders()
        headers.add('Accept', MediaType.APPLICATION_JSON_UTF8_VALUE)
        headers.add('Content-Type', MediaType.APPLICATION_JSON_UTF8_VALUE)
        HttpEntity<Worker[]> entity = new HttpEntity<Worker[]>(headers)

        RestTemplate restTemplate = new RestTemplate()
        ResponseEntity<Worker[]> response = restTemplate.exchange(REST_SERVICE_WORKER_HOST, HttpMethod.GET, entity, Worker[])
        if (response.getStatusCode() == HttpStatus.OK) {
            LOGGER.debug('(Client side) Workers data successfully received!')
            response.getBody()
        } else if (response.getStatusCode() == HttpStatus.NO_CONTENT) {
            LOGGER.debug('(Client side) Workers not found.')
            response.getBody()
        } else
            throw new Exception('Unknown error')
    }

}
