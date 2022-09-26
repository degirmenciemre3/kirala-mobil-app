package com.flakysob.kiralaapp.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.flakysob.kiralaapp.Entity.UrunEntity;
import com.flakysob.kiralaapp.Entity.UserEntity;

import java.util.List;

@Dao
public interface IUrunDAO {
    @Insert
    void insertUrun(UrunEntity urun);

    @Update
    void updateUrun(UrunEntity urun);

    @Delete
    void deleteUrun(UrunEntity urun);

    @Query("SELECT * FROM uruntbl WHERE kiralandi_mi=0")
    List<UrunEntity> loadNotKiralaUruns();

    @Query("SELECT * FROM uruntbl WHERE kategori_id=:bolumId")
    List<UrunEntity> loadUrunByKategori(long bolumId);

    @Query("SELECT * FROM uruntbl")
    List<UrunEntity> loadAllUruns();

    @Query("SELECT * FROM uruntbl WHERE id=:id")
    UrunEntity loadUrunById(long id);
}
