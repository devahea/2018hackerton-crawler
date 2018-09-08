package ahea.hackerton.nurikabe.crawler.repository;

import ahea.hackerton.nurikabe.crawler.domain.Puzzle;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Puzzle entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PuzzleRepository extends JpaRepository<Puzzle, Long> {

}
