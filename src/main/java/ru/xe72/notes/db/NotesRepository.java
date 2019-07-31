package ru.xe72.notes.db;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.xe72.notes.entity.Note;

import java.util.List;

@Repository
public interface NotesRepository extends CrudRepository<Note, Long> {
    List<Note> findAll(Sort sort);

    List<Note> findByTitleIn(String title, Sort sort);

    List<Note> findByVersionGreaterThan(Long version);

    void deleteByIdIn(List<Long> ids);
}
