package ahea.hackerton.nurikabe.crawler.web.rest;

import ahea.hackerton.nurikabe.crawler.AheaCrawlerApp;

import ahea.hackerton.nurikabe.crawler.domain.Puzzle;
import ahea.hackerton.nurikabe.crawler.repository.PuzzleRepository;
import ahea.hackerton.nurikabe.crawler.service.PuzzleService;
import ahea.hackerton.nurikabe.crawler.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


import static ahea.hackerton.nurikabe.crawler.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PuzzleResource REST controller.
 *
 * @see PuzzleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AheaCrawlerApp.class)
public class PuzzleResourceIntTest {

    private static final Integer DEFAULT_SIZE = 1;
    private static final Integer UPDATED_SIZE = 2;

    private static final String DEFAULT_SOURCE = "AAAAAAAAAA";
    private static final String UPDATED_SOURCE = "BBBBBBBBBB";

    @Autowired
    private PuzzleRepository puzzleRepository;

    

    @Autowired
    private PuzzleService puzzleService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPuzzleMockMvc;

    private Puzzle puzzle;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PuzzleResource puzzleResource = new PuzzleResource(puzzleService);
        this.restPuzzleMockMvc = MockMvcBuilders.standaloneSetup(puzzleResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Puzzle createEntity(EntityManager em) {
        Puzzle puzzle = new Puzzle()
            .size(DEFAULT_SIZE)
            .source(DEFAULT_SOURCE);
        return puzzle;
    }

    @Before
    public void initTest() {
        puzzle = createEntity(em);
    }

    @Test
    @Transactional
    public void createPuzzle() throws Exception {
        int databaseSizeBeforeCreate = puzzleRepository.findAll().size();

        // Create the Puzzle
        restPuzzleMockMvc.perform(post("/api/puzzles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(puzzle)))
            .andExpect(status().isCreated());

        // Validate the Puzzle in the database
        List<Puzzle> puzzleList = puzzleRepository.findAll();
        assertThat(puzzleList).hasSize(databaseSizeBeforeCreate + 1);
        Puzzle testPuzzle = puzzleList.get(puzzleList.size() - 1);
        assertThat(testPuzzle.getSize()).isEqualTo(DEFAULT_SIZE);
        assertThat(testPuzzle.getSource()).isEqualTo(DEFAULT_SOURCE);
    }

    @Test
    @Transactional
    public void createPuzzleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = puzzleRepository.findAll().size();

        // Create the Puzzle with an existing ID
        puzzle.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPuzzleMockMvc.perform(post("/api/puzzles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(puzzle)))
            .andExpect(status().isBadRequest());

        // Validate the Puzzle in the database
        List<Puzzle> puzzleList = puzzleRepository.findAll();
        assertThat(puzzleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPuzzles() throws Exception {
        // Initialize the database
        puzzleRepository.saveAndFlush(puzzle);

        // Get all the puzzleList
        restPuzzleMockMvc.perform(get("/api/puzzles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(puzzle.getId().intValue())))
            .andExpect(jsonPath("$.[*].size").value(hasItem(DEFAULT_SIZE)))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE.toString())));
    }
    

    @Test
    @Transactional
    public void getPuzzle() throws Exception {
        // Initialize the database
        puzzleRepository.saveAndFlush(puzzle);

        // Get the puzzle
        restPuzzleMockMvc.perform(get("/api/puzzles/{id}", puzzle.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(puzzle.getId().intValue()))
            .andExpect(jsonPath("$.size").value(DEFAULT_SIZE))
            .andExpect(jsonPath("$.source").value(DEFAULT_SOURCE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingPuzzle() throws Exception {
        // Get the puzzle
        restPuzzleMockMvc.perform(get("/api/puzzles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePuzzle() throws Exception {
        // Initialize the database
        puzzleService.save(puzzle);

        int databaseSizeBeforeUpdate = puzzleRepository.findAll().size();

        // Update the puzzle
        Puzzle updatedPuzzle = puzzleRepository.findById(puzzle.getId()).get();
        // Disconnect from session so that the updates on updatedPuzzle are not directly saved in db
        em.detach(updatedPuzzle);
        updatedPuzzle
            .size(UPDATED_SIZE)
            .source(UPDATED_SOURCE);

        restPuzzleMockMvc.perform(put("/api/puzzles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPuzzle)))
            .andExpect(status().isOk());

        // Validate the Puzzle in the database
        List<Puzzle> puzzleList = puzzleRepository.findAll();
        assertThat(puzzleList).hasSize(databaseSizeBeforeUpdate);
        Puzzle testPuzzle = puzzleList.get(puzzleList.size() - 1);
        assertThat(testPuzzle.getSize()).isEqualTo(UPDATED_SIZE);
        assertThat(testPuzzle.getSource()).isEqualTo(UPDATED_SOURCE);
    }

    @Test
    @Transactional
    public void updateNonExistingPuzzle() throws Exception {
        int databaseSizeBeforeUpdate = puzzleRepository.findAll().size();

        // Create the Puzzle

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restPuzzleMockMvc.perform(put("/api/puzzles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(puzzle)))
            .andExpect(status().isBadRequest());

        // Validate the Puzzle in the database
        List<Puzzle> puzzleList = puzzleRepository.findAll();
        assertThat(puzzleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePuzzle() throws Exception {
        // Initialize the database
        puzzleService.save(puzzle);

        int databaseSizeBeforeDelete = puzzleRepository.findAll().size();

        // Get the puzzle
        restPuzzleMockMvc.perform(delete("/api/puzzles/{id}", puzzle.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Puzzle> puzzleList = puzzleRepository.findAll();
        assertThat(puzzleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Puzzle.class);
        Puzzle puzzle1 = new Puzzle();
        puzzle1.setId(1L);
        Puzzle puzzle2 = new Puzzle();
        puzzle2.setId(puzzle1.getId());
        assertThat(puzzle1).isEqualTo(puzzle2);
        puzzle2.setId(2L);
        assertThat(puzzle1).isNotEqualTo(puzzle2);
        puzzle1.setId(null);
        assertThat(puzzle1).isNotEqualTo(puzzle2);
    }
}
