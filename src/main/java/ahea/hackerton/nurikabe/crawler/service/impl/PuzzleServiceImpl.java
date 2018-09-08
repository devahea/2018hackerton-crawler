package ahea.hackerton.nurikabe.crawler.service.impl;

import ahea.hackerton.nurikabe.crawler.service.PuzzleService;
import ahea.hackerton.nurikabe.crawler.domain.Puzzle;
import ahea.hackerton.nurikabe.crawler.repository.PuzzleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
/**
 * Service Implementation for managing Puzzle.
 */
@Service
@Transactional
public class PuzzleServiceImpl implements PuzzleService {

    private final Logger log = LoggerFactory.getLogger(PuzzleServiceImpl.class);

    private final PuzzleRepository puzzleRepository;

    public PuzzleServiceImpl(PuzzleRepository puzzleRepository) {
        this.puzzleRepository = puzzleRepository;
    }

    /**
     * Save a puzzle.
     *
     * @param puzzle the entity to save
     * @return the persisted entity
     */
    @Override
    public Puzzle save(Puzzle puzzle) {
        log.debug("Request to save Puzzle : {}", puzzle);        return puzzleRepository.save(puzzle);
    }

    /**
     * Get all the puzzles.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Puzzle> findAll() {
        log.debug("Request to get all Puzzles");
        return puzzleRepository.findAll();
    }


    /**
     * Get one puzzle by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Puzzle> findOne(Long id) {
        log.debug("Request to get Puzzle : {}", id);
        return puzzleRepository.findById(id);
    }

    /**
     * Delete the puzzle by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Puzzle : {}", id);
        puzzleRepository.deleteById(id);
    }
}
