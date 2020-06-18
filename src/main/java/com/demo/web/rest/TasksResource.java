package com.demo.web.rest;

import com.demo.domain.Tasks;
import com.demo.repository.TasksRepository;
import com.demo.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.demo.domain.Tasks}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TasksResource {

    private final Logger log = LoggerFactory.getLogger(TasksResource.class);

    private static final String ENTITY_NAME = "tasks";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TasksRepository tasksRepository;

    public TasksResource(TasksRepository tasksRepository) {
        this.tasksRepository = tasksRepository;
    }

    /**
     * {@code POST  /tasks} : Create a new tasks.
     *
     * @param tasks the tasks to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tasks, or with status {@code 400 (Bad Request)} if the tasks has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tasks")
    public ResponseEntity<Tasks> createTasks(@Valid @RequestBody Tasks tasks) throws URISyntaxException {
        log.debug("REST request to save Tasks : {}", tasks);
        if (tasks.getId() != null) {
            throw new BadRequestAlertException("A new tasks cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Tasks result = tasksRepository.save(tasks);
        return ResponseEntity.created(new URI("/api/tasks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tasks} : Updates an existing tasks.
     *
     * @param tasks the tasks to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tasks,
     * or with status {@code 400 (Bad Request)} if the tasks is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tasks couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tasks")
    public ResponseEntity<Tasks> updateTasks(@Valid @RequestBody Tasks tasks) throws URISyntaxException {
        log.debug("REST request to update Tasks : {}", tasks);
        if (tasks.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Tasks result = tasksRepository.save(tasks);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tasks.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tasks} : get all the tasks.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tasks in body.
     */
    @GetMapping("/tasks")
    public List<Tasks> getAllTasks() {
        log.debug("REST request to get all Tasks");
        return tasksRepository.findAll();
    }

    /**
     * {@code GET  /tasks/:id} : get the "id" tasks.
     *
     * @param id the id of the tasks to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tasks, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tasks/{id}")
    public ResponseEntity<Tasks> getTasks(@PathVariable Long id) {
        log.debug("REST request to get Tasks : {}", id);
        Optional<Tasks> tasks = tasksRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(tasks);
    }

    /**
     * {@code DELETE  /tasks/:id} : delete the "id" tasks.
     *
     * @param id the id of the tasks to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<Void> deleteTasks(@PathVariable Long id) {
        log.debug("REST request to delete Tasks : {}", id);
        tasksRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
