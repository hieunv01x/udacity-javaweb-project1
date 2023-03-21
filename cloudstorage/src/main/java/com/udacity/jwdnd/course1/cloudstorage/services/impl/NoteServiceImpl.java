package com.udacity.jwdnd.course1.cloudstorage.services.impl;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.repository.NoteRepository;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;

    public NoteServiceImpl(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @Override
    public Note getByNoteId(String noteId) {
        return noteRepository.getNoteById(noteId);
    }

    @Override
    public List<Note> getNoteByUserId(int userId) {
        return noteRepository.getNoteByUserId(userId);
    }

    @Override
    public boolean saveNote(Note note) {
        return noteRepository.saveNote(note);
    }

    @Override
    public boolean updateNote(Note note) {
        return noteRepository.updateNote(note);
    }

    @Override
    public boolean deleteNote(int noteId) {
        return noteRepository.deleteNote(noteId);
    }

}
