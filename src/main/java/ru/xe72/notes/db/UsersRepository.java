package ru.xe72.notes.db;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.xe72.notes.entity.NoteUser;

@Repository
public interface UsersRepository extends CrudRepository<NoteUser, Long> {
}
