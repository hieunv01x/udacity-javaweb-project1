package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.common.Message;
import com.udacity.jwdnd.course1.cloudstorage.dto.NoteDto;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/note")
public class NoteController {

    private final NoteService noteService;

    private final UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @GetMapping()
    public String viewNote() {
        return "redirect:/home";
    }

    public void createNote(NoteDto noteDto, int userId, RedirectAttributes redirectAttributes) {
        Note note = convertToNote(noteDto.getNoteTitle(), noteDto.getNoteDescription(), userId);
        if (!noteService.saveNote(note)) {
            redirectAttributes.addFlashAttribute("message", Message.CREATE_NOTE_ERROR.toString());
        } else {
            redirectAttributes.addFlashAttribute("message", Message.CREATE_NOTE_SUCCESS.toString());
        }
    }

    @PostMapping()
    public String updateNote(@ModelAttribute("note") NoteDto noteDto, RedirectAttributes redirectAttributes) {
        if (noteDto == null) {
            redirectAttributes.addFlashAttribute("message", Message.CREATE_NOTE_ERROR_EMPTY.toString());
            return "redirect:/home";
        }
        Integer userId;
        try {
            userId = userService.getUserId();
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", Message.USER_NOT_FOUND.toString());
            return "redirect:/login";
        }
        if (noteDto.getNoteId() != 0) {
            Note note = convertToNote(noteDto.getNoteTitle(), noteDto.getNoteDescription(), userId);
            note.setNoteId(noteDto.getNoteId());
            if (!noteService.updateNote(note)) {
                redirectAttributes.addFlashAttribute("message", Message.UPDATE_NOTE_ERROR.toString());
            } else {
                redirectAttributes.addFlashAttribute("message", Message.UPDATE_NOTE_SUCCESS.toString());
            }
        } else {
            createNote(noteDto, userId, redirectAttributes);
        }
        return "redirect:/home";
    }

    @GetMapping("/delete")
    public String deleteNote(@RequestParam("noteId") int noteId, RedirectAttributes redirectAttributes) {
        if (!noteService.deleteNote(noteId)) {
            redirectAttributes.addFlashAttribute("message", Message.DELETE_NOTE_ERROR.toString());
        } else {
            redirectAttributes.addFlashAttribute("message", Message.DELETE_NOTE_SUCCESS.toString());
        }
        return "redirect:/home";
    }

    public Note convertToNote(String noteTitle, String noteDescription, int userId) {
        Note note = new Note();
        note.setNoteTitle(noteTitle);
        note.setNoteDescription(noteDescription);
        note.setUserId(userId);
        return note;
    }
}