package ru.xe72.notes.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.xe72.notes.controllers.MainController;
import ru.xe72.notes.entity.Note;
import ru.xe72.notes.entity.Tag;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * Контроллер для rest api
 */
@RestController
@RequestMapping("api")
public class MainEndpoint {

  @Autowired
  MainController controller;

  @GetMapping("notes")
  public List<Note> getNotes(
      @RequestParam(value = "version", required = false) Long version,
      @RequestParam(value = "filter", required = false) String filter,
      @RequestParam(value = "sortCol", required = false) String sortCol,
      @RequestParam(value = "sortAsc", required = false) Boolean sortAsc) {
    return controller.getNotes(version, filter, sortCol, sortAsc);
  }

  @GetMapping("notes/{id}")
  public Optional<Note> getNote(@PathVariable("id") Long id) {
    return controller.getNote(id);
  }

  @PostMapping("notes")
  public Long addNote(@RequestBody @Valid Note note) {
    return controller.addNote(note);
  }

  @PostMapping("notesBatch")
  public Iterable<Long> addNotes(@RequestBody @Valid List<Note> notes) {
    return controller.addNotes(notes);
  }

  @GetMapping("tags")
  public List<Tag> getTags() {
    return controller.getTags();
  }

  @Transactional
  @DeleteMapping("notes")
  public void deleteNotes(@RequestBody List<Long> ids) {
    controller.deleteNotes(ids);
  }
}
