package com.demo.web.rest;

import com.demo.ServerApp;
import com.demo.domain.Tasks;
import com.demo.repository.TasksRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TasksResource} REST controller.
 */
@SpringBootTest(classes = ServerApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class TasksResourceIT {

    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long UPDATED_USER_ID = 2L;

    private static final Instant DEFAULT_CREARED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREARED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DUE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DUE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_IMPORTANCE = false;
    private static final Boolean UPDATED_IMPORTANCE = true;

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    private static final String DEFAULT_NOI_DUNG = "AAAAAAAAAA";
    private static final String UPDATED_NOI_DUNG = "BBBBBBBBBB";

    @Autowired
    private TasksRepository tasksRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTasksMockMvc;

    private Tasks tasks;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tasks createEntity(EntityManager em) {
        Tasks tasks = new Tasks()
            .userId(DEFAULT_USER_ID)
            .crearedDate(DEFAULT_CREARED_DATE)
            .dueDate(DEFAULT_DUE_DATE)
            .importance(DEFAULT_IMPORTANCE)
            .status(DEFAULT_STATUS)
            .noiDung(DEFAULT_NOI_DUNG);
        return tasks;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tasks createUpdatedEntity(EntityManager em) {
        Tasks tasks = new Tasks()
            .userId(UPDATED_USER_ID)
            .crearedDate(UPDATED_CREARED_DATE)
            .dueDate(UPDATED_DUE_DATE)
            .importance(UPDATED_IMPORTANCE)
            .status(UPDATED_STATUS)
            .noiDung(UPDATED_NOI_DUNG);
        return tasks;
    }

    @BeforeEach
    public void initTest() {
        tasks = createEntity(em);
    }

    @Test
    @Transactional
    public void createTasks() throws Exception {
        int databaseSizeBeforeCreate = tasksRepository.findAll().size();

        // Create the Tasks
        restTasksMockMvc.perform(post("/api/tasks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tasks)))
            .andExpect(status().isCreated());

        // Validate the Tasks in the database
        List<Tasks> tasksList = tasksRepository.findAll();
        assertThat(tasksList).hasSize(databaseSizeBeforeCreate + 1);
        Tasks testTasks = tasksList.get(tasksList.size() - 1);
        assertThat(testTasks.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testTasks.getCrearedDate()).isEqualTo(DEFAULT_CREARED_DATE);
        assertThat(testTasks.getDueDate()).isEqualTo(DEFAULT_DUE_DATE);
        assertThat(testTasks.isImportance()).isEqualTo(DEFAULT_IMPORTANCE);
        assertThat(testTasks.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testTasks.getNoiDung()).isEqualTo(DEFAULT_NOI_DUNG);
    }

    @Test
    @Transactional
    public void createTasksWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tasksRepository.findAll().size();

        // Create the Tasks with an existing ID
        tasks.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTasksMockMvc.perform(post("/api/tasks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tasks)))
            .andExpect(status().isBadRequest());

        // Validate the Tasks in the database
        List<Tasks> tasksList = tasksRepository.findAll();
        assertThat(tasksList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = tasksRepository.findAll().size();
        // set the field null
        tasks.setUserId(null);

        // Create the Tasks, which fails.

        restTasksMockMvc.perform(post("/api/tasks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tasks)))
            .andExpect(status().isBadRequest());

        List<Tasks> tasksList = tasksRepository.findAll();
        assertThat(tasksList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTasks() throws Exception {
        // Initialize the database
        tasksRepository.saveAndFlush(tasks);

        // Get all the tasksList
        restTasksMockMvc.perform(get("/api/tasks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tasks.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].crearedDate").value(hasItem(DEFAULT_CREARED_DATE.toString())))
            .andExpect(jsonPath("$.[*].dueDate").value(hasItem(DEFAULT_DUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].importance").value(hasItem(DEFAULT_IMPORTANCE.booleanValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].noiDung").value(hasItem(DEFAULT_NOI_DUNG)));
    }

    @Test
    @Transactional
    public void getTasks() throws Exception {
        // Initialize the database
        tasksRepository.saveAndFlush(tasks);

        // Get the tasks
        restTasksMockMvc.perform(get("/api/tasks/{id}", tasks.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tasks.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()))
            .andExpect(jsonPath("$.crearedDate").value(DEFAULT_CREARED_DATE.toString()))
            .andExpect(jsonPath("$.dueDate").value(DEFAULT_DUE_DATE.toString()))
            .andExpect(jsonPath("$.importance").value(DEFAULT_IMPORTANCE.booleanValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.noiDung").value(DEFAULT_NOI_DUNG));
    }

    @Test
    @Transactional
    public void getNonExistingTasks() throws Exception {
        // Get the tasks
        restTasksMockMvc.perform(get("/api/tasks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTasks() throws Exception {
        // Initialize the database
        tasksRepository.saveAndFlush(tasks);

        int databaseSizeBeforeUpdate = tasksRepository.findAll().size();

        // Update the tasks
        Tasks updatedTasks = tasksRepository.findById(tasks.getId()).get();
        // Disconnect from session so that the updates on updatedTasks are not directly saved in db
        em.detach(updatedTasks);
        updatedTasks
            .userId(UPDATED_USER_ID)
            .crearedDate(UPDATED_CREARED_DATE)
            .dueDate(UPDATED_DUE_DATE)
            .importance(UPDATED_IMPORTANCE)
            .status(UPDATED_STATUS)
            .noiDung(UPDATED_NOI_DUNG);

        restTasksMockMvc.perform(put("/api/tasks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTasks)))
            .andExpect(status().isOk());

        // Validate the Tasks in the database
        List<Tasks> tasksList = tasksRepository.findAll();
        assertThat(tasksList).hasSize(databaseSizeBeforeUpdate);
        Tasks testTasks = tasksList.get(tasksList.size() - 1);
        assertThat(testTasks.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testTasks.getCrearedDate()).isEqualTo(UPDATED_CREARED_DATE);
        assertThat(testTasks.getDueDate()).isEqualTo(UPDATED_DUE_DATE);
        assertThat(testTasks.isImportance()).isEqualTo(UPDATED_IMPORTANCE);
        assertThat(testTasks.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTasks.getNoiDung()).isEqualTo(UPDATED_NOI_DUNG);
    }

    @Test
    @Transactional
    public void updateNonExistingTasks() throws Exception {
        int databaseSizeBeforeUpdate = tasksRepository.findAll().size();

        // Create the Tasks

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTasksMockMvc.perform(put("/api/tasks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tasks)))
            .andExpect(status().isBadRequest());

        // Validate the Tasks in the database
        List<Tasks> tasksList = tasksRepository.findAll();
        assertThat(tasksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTasks() throws Exception {
        // Initialize the database
        tasksRepository.saveAndFlush(tasks);

        int databaseSizeBeforeDelete = tasksRepository.findAll().size();

        // Delete the tasks
        restTasksMockMvc.perform(delete("/api/tasks/{id}", tasks.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Tasks> tasksList = tasksRepository.findAll();
        assertThat(tasksList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
