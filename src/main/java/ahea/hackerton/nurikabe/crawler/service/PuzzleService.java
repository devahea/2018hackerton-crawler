package ahea.hackerton.nurikabe.crawler.service;

import ahea.hackerton.nurikabe.crawler.domain.Puzzle;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Puzzle.
 */
public interface PuzzleService {

    /**
     * Save a puzzle.
     *
     * @param puzzle the entity to save
     * @return the persisted entity
     */
    Puzzle save(Puzzle puzzle);

    /**
     * Get all the puzzles.
     *
     * @return the list of entities
     */
    List<Puzzle> findAll();


    /**
     * Get the "id" puzzle.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Puzzle> findOne(Long id);

    /**
     * Delete the "id" puzzle.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
