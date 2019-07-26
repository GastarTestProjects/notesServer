package ru.xe72.notes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import ru.xe72.notes.db.NotesRepository;
import ru.xe72.notes.db.TagsRepository;
import ru.xe72.notes.entity.Note;
import ru.xe72.notes.entity.Tag;
import ru.xe72.notes.utils.NU;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class MainController {

    @Autowired
    NotesRepository notesRepository;

    @Autowired
    TagsRepository tagsRepository;

    @GetMapping("notes")
    public List<Note> getNotes(@RequestParam("filter") String filter,
                               @RequestParam("sortCol") String sortCol,
                               @RequestParam("sortAsc") Boolean sortAsc) {
//        Note test = new Note();
//        test.setTitle("test title");
//        test.setText("test text");
//        Note test2 = new Note();
//        test2.setText("test title 2");
//        test2.setText("test text 2");
//        test2.setTags(new HashSet<Tag>(){{add(new Tag("tag1"));add(new Tag("tag2"));}});
//        return Arrays.asList(test, test2);

        List<Note> result;

        Sort sort = Sort.by(sortAsc ? Sort.Direction.ASC : Sort.Direction.DESC,
                NU.nvlOrEmpty(sortCol, "createDate"));
        if (StringUtils.isEmpty(filter)) {
            result = notesRepository.findAll(sort);
        } else {
            result = notesRepository.findAllByTitleIn(filter, sort);
        }
        return result;
    }

    @GetMapping("notes/{id}")
    public Optional<Note> getNote(@PathVariable("id") Long id) {
        return notesRepository.findById(id);
    }

    @PostMapping("notes")
    public Note addNote(@RequestBody Note note) {
        ArrayList<Tag> newTags = new ArrayList<>();
        if (note.getTags() != null) {
            tagsRepository.saveAll(note.getTags().stream()
                    .filter(t -> !StringUtils.isEmpty(t.getName()))
                    .map(t -> {
                        t.setName(t.getName().toLowerCase());
                        return t;
                    }).collect(Collectors.toList())).forEach(newTags::add);
            note.setTags(newTags);
        }

//        note.setTags(note.getTags().stream().map(t -> {
//            Tag res = null;
//            try {
//                res = tagsRepository.save(t);
//            } catch (DataIntegrityViolationException e) {
//                if (e.getCause() instanceof ConstraintViolationException) {
//                    res = tagsRepository.findByName(t.getName());
//                } else {
//                    throw e;
//                }
//            }
//            return res;
//        }).filter(Objects::nonNull).collect(Collectors.toList()));
        return notesRepository.save(note);
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
}
