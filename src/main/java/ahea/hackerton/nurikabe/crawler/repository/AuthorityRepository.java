package ahea.hackerton.nurikabe.crawler.repository;

import ahea.hackerton.nurikabe.crawler.domain.Authority;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Authority entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
