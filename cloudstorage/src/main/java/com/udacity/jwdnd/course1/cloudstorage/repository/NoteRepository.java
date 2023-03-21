package com.udacity.jwdnd.course1.cloudstorage.repository;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteRepository {

    @Select("SELECT * FROM NOTES WHERE noteId = #{noteId}")
    Note getNoteById(String noteId);

    @Select("SELECT * FROM NOTES WHERE userId = #{userId}")
    List<Note> getNoteByUserId(int userId);

    @Insert("INSERT INTO NOTES (noteTitle, noteDescription, userid) VALUES(" +
            "#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    boolean saveNote(Note note);

    @Update("UPDATE NOTES SET noteTitle = #{noteTitle}, noteDescription = #{noteDescription} " +
            "WHERE userId = #{userId} AND noteId = #{noteId}")
    boolean updateNote(Note note);

    @Delete("DELETE FROM NOTES WHERE noteId = #{noteId}")
    boolean deleteNote(int noteId);
}