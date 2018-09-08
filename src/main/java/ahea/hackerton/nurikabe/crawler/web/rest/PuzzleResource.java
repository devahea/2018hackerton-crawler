package ahea.hackerton.nurikabe.crawler.web.rest;

import com.codahale.metrics.annotation.Timed;
import ahea.hackerton.nurikabe.crawler.domain.Puzzle;
import ahea.hackerton.nurikabe.crawler.service.PuzzleService;
import ahea.hackerton.nurikabe.crawler.web.rest.errors.BadRequestAlertException;
import ahea.hackerton.nurikabe.crawler.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Puzzle.
 */
@RestController
@RequestMapping("/api")
public class PuzzleResource {

    private final Logger log = LoggerFactory.getLogger(PuzzleResource.class);

    private static final String ENTITY_NAME = "puzzle";

    private final PuzzleService puzzleService;

    public PuzzleResource(PuzzleService puzzleService) {
        this.puzzleService = puzzleService;
    }

    /**
     * POST  /puzzles : Create a new puzzle.
     *
     * @param puzzle the puzzle to create
     * @return the ResponseEntity with status 201 (Created) and with body the new puzzle, or with status 400 (Bad Request) if the puzzle has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/puzzles")
    @Timed
    public ResponseEntity<Puzzle> createPuzzle(@RequestBody Puzzle puzzle) throws URISyntaxException {
        log.debug("REST request to save Puzzle : {}", puzzle);
        if (puzzle.getId() != null) {
            throw new BadRequestAlertException("A new puzzle cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Puzzle result = puzzleService.save(puzzle);
        return ResponseEntity.created(new URI("/api/puzzles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /puzzles : Updates an existing puzzle.
     *
     * @param puzzle the puzzle to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated puzzle,
     * or with status 400 (Bad Request) if the puzzle is not valid,
     * or with status 500 (Internal Server Error) if the puzzle couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/puzzles")
    @Timed
    public ResponseEntity<Puzzle> updatePuzzle(@RequestBody Puzzle puzzle) throws URISyntaxException {
        log.debug("REST request to update Puzzle : {}", puzzle);
        if (puzzle.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Puzzle result = puzzleService.save(puzzle);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, puzzle.getId().toString()))
            .body(result);
    }

    /**
     * GET  /puzzles : get all the puzzles.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of puzzles in body
     */
    @GetMapping("/puzzles")
    @Timed
    public List<Puzzle> getAllPuzzles() {
        log.debug("REST request to get all Puzzles");
        return puzzleService.findAll();
    }

    /**
     * GET  /puzzles/:id : get the "id" puzzle.
     *
     * @param id the id of the puzzle to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the puzzle, or with status 404 (Not Found)
     */
    @GetMapping("/puzzles/{id}")
    @Timed
    public ResponseEntity<Puzzle> getPuzzle(@PathVariable Long id) {
        log.debug("REST request to get Puzzle : {}", id);
        Optional<Puzzle> puzzle = puzzleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(puzzle);
    }

    /**
     * DELETE  /puzzles/:id : delete the "id" puzzle.
     *
     * @param id the id of the puzzle to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/puzzles/{id}")
    @Timed
    public ResponseEntity<Void> deletePuzzle(@PathVariable Long id) {
        log.debug("REST request to delete Puzzle : {}", id);
        puzzleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
