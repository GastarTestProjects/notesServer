package ru.xe72.notes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import ru.xe72.notes.db.NotesRepository;
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

@RestController
public class MainController {

  @Autowired NotesRepository notesRepository;

  @Autowired TagsRepository tagsRepository;

  @GetMapping("notes")
  public List<Note> getNotes(
      @RequestParam(value = "version", required = false) Long version,
      @RequestParam(value = "filter", required = false) String filter,
      @RequestParam(value = "sortCol", required = false) String sortCol,
      @RequestParam(value = "sortAsc", required = false) Boolean sortAsc) {
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
      result = notesRepository.findByTitleIn(filter, sort);
    }
    return result;
  }

  @GetMapping("notes/{id}")
  public Optional<Note> getNote(@PathVariable("id") Long id) {
    return notesRepository.findById(id);
  }

  @PostMapping("notes")
  public Long addNote(@RequestBody @Valid Note note) {
    return notesRepository.save(prepareNoteForSave(note)).getId();
  }

  @PostMapping("notesBatch")
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

  @GetMapping("tags")
  public List<Tag> getTags() {
    //        return Arrays.asList(new Tag("tag1"), new Tag("tag2"));
    return tagsRepository.findAll(Sort.by(Sort.Order.asc("name")));
  }

  @GetMapping("tags/{name}")
  public Optional<Tag> getTag(@PathVariable("name") String name) {
    return tagsRepository.findById(name.toLowerCase());
  }

  @PostMapping("tags")
  public Tag addTag(@RequestBody Tag tag) {
    return tagsRepository.save(tag);
  }

  @Transactional
  @DeleteMapping("notes")
  public void deleteNotes(@RequestBody List<Long> ids) {
    notesRepository.deleteByIdIn(ids);
  }
}
