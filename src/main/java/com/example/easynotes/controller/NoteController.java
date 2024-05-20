package com.example.easynotes.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.easynotes.exception.ResourceNotFoundException;
import com.example.easynotes.model.Note;
import com.example.easynotes.repository.NoteRepository;

@RestController
@RequestMapping("/api")
public class NoteController {
	@Autowired
	private NoteRepository noteRepository;
	
	private Logger logger = LoggerFactory.getLogger(NoteController.class);

	@GetMapping("/notes")
	public List<Note> getAllNotes() {
		logger.info("starting getAllNotes API");
		return noteRepository.findAll();
	}

	@PostMapping("/notes")
	public Note createNote(@RequestBody Note note) {
		logger.info("starting createNote API");
		return noteRepository.save(note);
	}

	@GetMapping("/notes/{id}")
	public Note getNoteById(@PathVariable(value = "id") Long noteId) {
		logger.info("starting getNoteById API");
		return noteRepository.findById(noteId).orElseThrow(() -> new ResourceNotFoundException("Note", "id", noteId));
	}

	@PutMapping("/notes/{id}")
	public Note updateNote(@PathVariable(value = "id") Long noteId,@RequestBody Note noteDetails) {
		logger.info("starting updateNote API");
		Note note = noteRepository.findById(noteId)
				.orElseThrow(() -> new ResourceNotFoundException("Note", "id", noteId));
		note.setTitle(noteDetails.getTitle());
		note.setContent(noteDetails.getContent());
		Note updatedNote = noteRepository.save(note);
		return updatedNote;
	}

	@DeleteMapping("/notes/{id}")
	public ResponseEntity<?> deleteNote(@PathVariable(value = "id") Long noteId) {
		logger.info("starting deleteNote API");
		Note note = noteRepository.findById(noteId)
				.orElseThrow(() -> new ResourceNotFoundException("Note", "id", noteId));
		noteRepository.delete(note);
		return ResponseEntity.ok().build();
	}
}
