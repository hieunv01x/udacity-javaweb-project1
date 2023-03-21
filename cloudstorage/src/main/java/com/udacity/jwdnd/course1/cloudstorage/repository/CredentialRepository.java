package com.udacity.jwdnd.course1.cloudstorage.repository;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialRepository {

    @Select("SELECT * FROM CREDENTIALS WHERE credentialId = #{credentialId}")
    Credential getCredentialById(String credentialId);

    @Select("SELECT * FROM CREDENTIALS WHERE userId = #{userId}")
    List<Credential> getCredentialByUserId(int userId);

    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userid) VALUES(" +
            "#{url}, #{username}, #{key}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    boolean saveCredential(Credential credential);

    @Update("UPDATE CREDENTIALS SET url = #{url}, username = #{username},key = #{key}, password = #{password} " +
            "WHERE userid = #{userId} AND credentialId = #{credentialId}")
    boolean updateCredential(Credential credential);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialId = #{credentialId}")
    boolean deleteCredential(int credentialId);
}