package ru.xe72.notes.db;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.xe72.notes.entity.Note;

import java.util.List;

@Repository
public interface NotesRepository extends CrudRepository<Note, Long> {
    List<Note> findAll(Sort sort);

    List<Note> findAllByTitleIn(String title, Sort sort);

    void deleteByIdIn(List<Long> ids);
}
