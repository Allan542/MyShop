package com.example.myshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class EsqueceuSenhaActivity extends AppCompatActivity {

    ImageButton ImgBtn_Voltar_EsqueceuSenha;
    Button Btn_Redefinir_EsqueceuSenha;
    EditText Edt_Email_EsqueceuSenha, Edt_SenhaNova_EsqueceuSenha, Edt_SenhaNovamente_EsqueceuSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_esqueceu_senha);

        procuraIDs();
        eventoClique();
    }

    private void procuraIDs() {
        Btn_Redefinir_EsqueceuSenha = findViewById(R.id.btn_Redefinir_EsqueceuSenha);
        ImgBtn_Voltar_EsqueceuSenha = findViewById(R.id.imgbtn_Voltar_EsqueceuSenha);
        Edt_Email_EsqueceuSenha = findViewById(R.id.edt_Email_EsqueceuSenha);
        Edt_SenhaNova_EsqueceuSenha = findViewById(R.id.edt_SenhaNova_EsqueceuSenha);
        Edt_SenhaNovamente_EsqueceuSenha = findViewById(R.id.edt_SenhaNovamente_EsqueceuSenha);
    }

    private void eventoClique() {
        Btn_Redefinir_EsqueceuSenha.setOnClickListener(view -> {
            Toast.makeText(this, "Senha redefinida com sucesso!", Toast.LENGTH_SHORT).show();
            carregaTela(LoginActivity.class);
        });
        ImgBtn_Voltar_EsqueceuSenha.setOnClickListener(view -> carregaTela(LoginActivity.class));
    }

    private void carregaTela(Class classe){
        Intent it = new Intent(getApplicationContext(), classe);
        startActivity(it);
    }
}