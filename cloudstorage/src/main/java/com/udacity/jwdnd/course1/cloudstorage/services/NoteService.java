package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;

import java.util.List;

public interface NoteService {
    Note getByNoteId(String noteId);

    List<Note> getNoteByUserId(int userId);

    boolean saveNote(Note note);

    boolean updateNote(Note note);

    boolean deleteNote(int noteId);
}