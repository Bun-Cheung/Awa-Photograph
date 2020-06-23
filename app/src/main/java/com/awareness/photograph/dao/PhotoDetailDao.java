package com.awareness.photograph.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.awareness.photograph.entity.PhotoDetail;

import java.util.List;

@Dao
public interface PhotoDetailDao {
    @Query("SELECT * FROM photodetail")
    List<PhotoDetail> getAll();

    @Insert
    void insertAll(List<PhotoDetail> photoDetailList);

    @Insert
    void insert(PhotoDetail photoDetail);

    @Update
    void update(PhotoDetail photoDetail);

    @Delete
    void delete(PhotoDetail photoDetail);

    @Query("DELETE FROM photodetail")
    void deleteAll();
}
