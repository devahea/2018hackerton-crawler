package ahea.hackerton.nurikabe.crawler.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Puzzle.
 */
@Entity
@Table(name = "puzzle")
public class Puzzle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhi_size")
    private Integer size;

    @Column(name = "source")
    private String source;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSize() {
        return size;
    }

    public Puzzle size(Integer size) {
        this.size = size;
        return this;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getSource() {
        return source;
    }

    public Puzzle source(String source) {
        this.source = source;
        return this;
    }

    public void setSource(String source) {
        this.source = source;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Puzzle puzzle = (Puzzle) o;
        if (puzzle.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), puzzle.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Puzzle{" +
            "id=" + getId() +
            ", size=" + getSize() +
            ", source='" + getSource() + "'" +
            "}";
    }
}
