package ru.xe72.notes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import ru.xe72.notes.db.NotesRepository;
import ru.xe72.notes.db.NotesSearch;
import ru.xe72.notes.db.TagsRepository;
import ru.xe72.notes.entity.Note;
import ru.xe72.notes.entity.Tag;
import ru.xe72.notes.utils.NU;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class MainController {

  @Autowired NotesRepository notesRepository;

  @Autowired TagsRepository tagsRepository;

  @Autowired NotesSearch notesSearchRepository;

  public List<Note> getNotes(Long version, String filter, String sortCol, Boolean sortAsc) {
    List<Note> result;

    Sort sort =
        Sort.by(
            Boolean.TRUE.equals(sortAsc) ? Sort.Direction.ASC : Sort.Direction.DESC,
            NU.nvlOrEmpty(sortCol, "createDate"));
    if (version != null) {
      result = notesRepository.findByVersionGreaterThan(version);
    } else if (StringUtils.isEmpty(filter)) {
      result = notesRepository.findAll(sort);
    } else {
      //   /   result = notesRepository.findByTitleContainingIgnoreCase(filter, sort);
      result = notesSearchRepository.searchNotes(filter);
    }
    return result;
  }

  public Optional<Note> getNote(@PathVariable("id") Long id) {
    return notesRepository.findById(id);
  }

  public Long addNote(@RequestBody @Valid Note note) {
    return notesRepository.save(prepareNoteForSave(note)).getId();
  }

  public Iterable<Long> addNotes(@RequestBody @Valid List<Note> notes) {
    return StreamSupport.stream(
            notesRepository
                .saveAll(notes.stream().map(this::prepareNoteForSave).collect(Collectors.toList()))
                .spliterator(),
            false)
        .map(Note::getId)
        .collect(Collectors.toList());
  }

  private Note prepareNoteForSave(Note note) {
    // TODO: Можно ли сделать декларативно?
    if (note.getCreateDate() == null) {
      note.setCreateDate(new Date());
    }

    // TODO: Если пришло с мобильного клиента, дату не перезатирать. Продумать как
    note.setModifyDate(new Date());

    ArrayList<Tag> newTags = new ArrayList<>();
    if (note.getTags() != null) {
      tagsRepository
          .saveAll(
              note.getTags().stream()
                  .filter(t -> !StringUtils.isEmpty(t.getName()))
                  .map(
                      t -> {
                        t.setName(t.getName().toLowerCase());
                        return t;
                      })
                  .collect(Collectors.toList()))
          .forEach(newTags::add);
      note.setTags(newTags);
    }

    return note;
  }

  public List<Tag> getTags() {
    return tagsRepository.findAll(Sort.by(Sort.Order.asc("name")));
  }

  public void deleteNotes(@RequestBody List<Long> ids) {
    notesRepository.deleteByIdIn(ids);
  }
}
