package com.flakysob.kiralaapp;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.flakysob.kiralaapp.DAO.IKategoriDAO;
import com.flakysob.kiralaapp.DAO.IUrunDAO;
import com.flakysob.kiralaapp.DAO.IUserDAO;
import com.flakysob.kiralaapp.Database.AppDatabase;
import com.flakysob.kiralaapp.Entity.KategoriEntity;
import com.flakysob.kiralaapp.Entity.UrunEntity;
import com.flakysob.kiralaapp.Entity.UserEntity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class IlanEkleActivity extends AppCompatActivity {

    private EditText ilanbaslik,ilanfiyat,ilanaciklama;
    private Spinner kategoriSpinner;
    private ImageView ilanresim;
    private Uri resim;
    List<KategoriEntity> kategoriList;
    private AppDatabase appDatabase;
    private IUserDAO userDAO;
    private IKategoriDAO kategoriDAO;
    private IUrunDAO urunDAO;
    private FirebaseAuth mAuth;
    private TextView tvKategoriID;
    private Button btnIlanEkle;
    LayoutInflater layoutInflater;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ilan_ekle);
        initComponents();
        registerEventHandlers();

        kategoriList = kategoriDAO.loadAllKategori();
        KategoriSpinnerAdapter spinnerAdapter = new KategoriSpinnerAdapter(this,R.layout.spinner_kategori,kategoriList);
        kategoriSpinner.setAdapter(spinnerAdapter);
    }

    public void initComponents(){
        ilanbaslik = (EditText) findViewById(R.id.ilanbaslik);
        ilanfiyat = (EditText) findViewById(R.id.ilanfiyat);
        ilanaciklama = (EditText) findViewById(R.id.ilanaciklama);
        kategoriSpinner = (Spinner) findViewById(R.id.spinnerkategori);
        ilanresim = (ImageView) findViewById(R.id.ilanimg);
        appDatabase = AppDatabase.getAppDatabase(IlanEkleActivity.this);
        kategoriDAO = appDatabase.getKategoriDAO();
        userDAO = appDatabase.getUserDAO();
        urunDAO = appDatabase.getUrunDAO();
        mAuth = FirebaseAuth.getInstance();
        layoutInflater = LayoutInflater.from(IlanEkleActivity.this);
        View rowView = layoutInflater.inflate(R.layout.spinner_kategori,null,true);
        tvKategoriID = (TextView) rowView.findViewById(R.id.kategoriID);
        btnIlanEkle = (Button) findViewById(R.id.btnIlanEkle);

    }

    public void registerEventHandlers(){
        btnIlanEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.w("deneme", "kategoriID öncesi");
                String kategoriID = tvKategoriID.getText().toString();
                Log.w("deneme", "kategoriID: "+kategoriID);
                UrunEntity ilan = new UrunEntity();
                ilan.setAciklama(ilanaciklama.getText().toString());
                ilan.setFiyat(Integer.parseInt(ilanfiyat.getText().toString()));
                ilan.setKategoriId(Long.parseLong(kategoriID));
                ilan.setUrunBaslik(ilanbaslik.getText().toString());
                ilan.setKiralandiMi(0);
                FirebaseUser user = mAuth.getCurrentUser();
                UserEntity kullanici = new UserEntity();
                kullanici = userDAO.loadUserByEposta(user.getEmail());
                ilan.setUserId(kullanici.getId());

                try {
                    urunDAO.insertUrun(ilan);
                    Log.w("deneme", "Urun Ekleme Başarılı: "+ilan.getUrunBaslik()+ " "+ ilan.getAciklama() + " "+ ilan.getFiyat());
                }catch (Exception e)
                {
                    Log.w("deneme", "Urun Ekleme Başarısız: ", e);
                }
            }
        });
    }


    /*
    public void ekleUrun(){
        UrunEntity urunEkle = new UrunEntity();

        urunEkle.setUrunBaslik(ilanBasligi.getText().toString());

        urunEkle.setAciklama(ilanAciklama.getText().toString());

        String price = ilanFiyat.getText().toString();
        int fiyat = Integer.parseInt(price);
        urunEkle.setFiyat(fiyat);

        urunEkle.setAciklama(ilanAciklama.getText().toString());
    }

    public void getirSpinner(){

    } */
}