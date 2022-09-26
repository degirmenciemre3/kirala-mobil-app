package com.flakysob.kiralaapp;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private TextInputLayout txtInputEposta, txtInputParola;
    private MaterialButton btnGirisYap, btnKayitOl;
    private TextView sifremiUnuttum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();
        registerEventHandlers();

    }

    public void updateUI(FirebaseUser user) {
        View v = new View(MainActivity.this);
        if (user != null) {
            /*
            Snackbar snackbar = Snackbar.make(v, user.getEmail() + " giriş yaptı", Snackbar.LENGTH_LONG);
            snackbar.show();*/
            Intent intent = new Intent(this, AnaEkranActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);

        } /*else {

            Snackbar snackbar = Snackbar.make(v, "Henüz giriş yapılmadı", Snackbar.LENGTH_LONG);
            snackbar.show();
        }*/
    }
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }



    private void initComponents(){
        mAuth = FirebaseAuth.getInstance();
        txtInputEposta = (TextInputLayout) findViewById(R.id.txtInputEposta);
        txtInputParola = (TextInputLayout) findViewById(R.id.txtInputParola);
        btnKayitOl = (MaterialButton) findViewById(R.id.btnDogrulama);
        btnGirisYap = (MaterialButton) findViewById(R.id.btnGirisYap);
        sifremiUnuttum = (TextView) findViewById(R.id.txtSifremiUnuttum);
    }

    private void registerEventHandlers(){
        btnKayitOl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(MainActivity.this,KayitOlActivity.class);
               startActivity(intent);
            }
        });


        btnGirisYap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String eposta = txtInputEposta.getEditText().getText().toString();
                String sifre = txtInputParola.getEditText().getText().toString();
                mAuth.signInWithEmailAndPassword(eposta, sifre).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        }
                        else
                        {
                            Snackbar snackbar = Snackbar.make(v, "Giriş başarısız.", Snackbar.LENGTH_LONG);
                            snackbar.show();
                            updateUI(null);
                        }
                    }
                });
            }
        });
        sifremiUnuttum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,SifremiUnuttumActivity.class);
                startActivity(intent);
            }
        });

    }

}