package com.example.myshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

public class PlanoContratoActivity extends AppCompatActivity {

    Button Btn_GeraContrato_PlanoContrato;
    ImageButton ImgBtn_Voltar_PlanoContrato;
    RadioButton Rdo_AceitaTermos_PlanoContrato;
    int id_usuario;
    String opcao_usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plano_contrato);

        procuraIDs();
        recebeOpcao();
        eventoClique();

    }

    private void procuraIDs() {
        Btn_GeraContrato_PlanoContrato = findViewById(R.id.btn_GeraContrato_PlanoContrato);
        ImgBtn_Voltar_PlanoContrato = findViewById(R.id.imgbtn_Voltar_PlanoContrato);
        Rdo_AceitaTermos_PlanoContrato = findViewById(R.id.rdo_AceitaTermos_PlanoContrato);
    }

    private void eventoClique() {
        // Se o radio não estiver marcado, o botão não passará para a próxima tela
        Btn_GeraContrato_PlanoContrato.setOnClickListener(view -> {
            if(Rdo_AceitaTermos_PlanoContrato.isChecked()) carregaTelaComInfoUser(PagarPlanoPremiumActivity.class, opcao_usuario, id_usuario);
            else Toast.makeText(this, "Aceite os termos do contrato antes de prosseguir!", Toast.LENGTH_SHORT).show();
        });
        ImgBtn_Voltar_PlanoContrato.setOnClickListener(view -> carregaTelaComInfoUser(PerfilActivity.class, opcao_usuario, id_usuario));
    }

    // Manda as informações do usuário logado para a tela PagarPlanoPremium
    public void carregaTelaComInfoUser(Class cls, String opc, int id){
        Intent mandaOpcao = new Intent(getApplicationContext(), cls);
        Bundle infos = new Bundle();

        infos.putString("tipo_usuario", opc);
        infos.putInt("id_usuario", id);
        mandaOpcao.putExtras(infos);
        startActivity(mandaOpcao);
    }

    // Recebe as informações do usuário logado da tela Perfil
    private void recebeOpcao() {
        Intent it = getIntent();
        Bundle infos = it.getExtras();

        opcao_usuario = infos.getString("tipo_usuario");
        id_usuario = infos.getInt("id_usuario");
    }
}