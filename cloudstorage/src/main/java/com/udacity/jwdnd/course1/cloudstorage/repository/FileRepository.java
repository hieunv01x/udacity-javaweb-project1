package com.udacity.jwdnd.course1.cloudstorage.repository;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileRepository {

    @Select("SELECT * FROM FILES WHERE fileId = #{fileId}")
    File getFileById(int fileId);

    @Select("SELECT * FROM FILES WHERE userId = #{userId} and fileName = #{fileName}")
    File getFileByFileName(int userId, String fileName);

    @Select("SELECT * FROM FILES WHERE userId = #{userId}")
    List<File> getFileByUserId(int userId);

    @Insert("INSERT INTO FILES (fileName, contentType, fileSize, userId, fileData) VALUES(" +
            "#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    boolean saveFile(File file);

    @Delete("DELETE FROM FILES WHERE fileId = #{fileId}")
    boolean deleteFile(int fileId);
}