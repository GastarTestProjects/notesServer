package ru.xe72.notes.db;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.xe72.notes.entity.Tag;

import java.util.List;

@Repository
public interface TagsRepository extends CrudRepository<Tag, String> {
    List<Tag> findAll(Sort sort);

    Tag findByName(String name);
}
