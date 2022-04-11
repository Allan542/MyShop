package com.example.myshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

public class PagarPlanoPremiumActivity extends AppCompatActivity {

    ImageButton ImgBtn_Home_PagarPlanoPremium, ImgBtn_Vendedor_PagarPlanoPremium, ImgBtn_Menu_PagarPlanoPremium,
            ImgBtn_Carrinho_PagarPlanoPremium, ImgBtn_Suporte_PagarPlanoPremium, ImgBtn_Voltar_PagarPlanoPremium,
            ImgBtn_SelecionaCartao_PagarPlanoPremium, ImgBtn_SelecionaBoleto_PagarPlanoPremium;
    Button Btn_Pagar_PagarPlanoPremium;
    ProgressBar PgrsBar_Carregar_PagarPlanoPremium;
    int id_usuario, progresso = 0;
    String opcao_usuario, opcao_pagamento = "Nenhuma";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagar_plano_premium);

        procuraIDs();
        recebeOpcao();
        eventoClique();
    }

    private void procuraIDs() {
        // Barra inferior
        ImgBtn_Home_PagarPlanoPremium = findViewById(R.id.imgbtn_Home_PagarPlanoPremium);
        ImgBtn_Vendedor_PagarPlanoPremium = findViewById(R.id.imgbtn_Vendedor_PagarPlanoPremium);
        ImgBtn_Menu_PagarPlanoPremium = findViewById(R.id.imgbtn_Menu_PagarPlanoPremium);
        ImgBtn_Carrinho_PagarPlanoPremium = findViewById(R.id.imgbtn_Carrinho_PagarPlanoPremium);
        ImgBtn_Suporte_PagarPlanoPremium = findViewById(R.id.imgbtn_Suporte_PagarPlanoPremium);

        // Conteúdo
        ImgBtn_Voltar_PagarPlanoPremium = findViewById(R.id.imgbtn_Voltar_PagarPlanoPremium);
        ImgBtn_SelecionaCartao_PagarPlanoPremium = findViewById(R.id.imgbtn_SelecionaCartao_PagarPlanoPremium);
        ImgBtn_SelecionaBoleto_PagarPlanoPremium = findViewById(R.id.imgbtn_SelecionaBoleto_PagarPlanoPremium);
        Btn_Pagar_PagarPlanoPremium = findViewById(R.id.btn_Pagar_PagarPlanoPremium);

        // Conteúdo
        PgrsBar_Carregar_PagarPlanoPremium = findViewById(R.id.pgrsbarCarregarPagarPlanoPremium);
    }

    private void eventoClique() {
        ImgBtn_Home_PagarPlanoPremium.setOnClickListener(view -> carregaTela(PrincipalActivity.class));
        ImgBtn_Vendedor_PagarPlanoPremium.setOnClickListener(view -> carregaTela(CadastroProdutoActivity.class));
        ImgBtn_Carrinho_PagarPlanoPremium.setOnClickListener(view -> carregaTela(CarrinhoActivity.class));
        ImgBtn_Menu_PagarPlanoPremium.setOnClickListener(view -> carregaTela(CategoriasActivity.class));
        ImgBtn_Suporte_PagarPlanoPremium.setOnClickListener(view -> carregaTela(SuporteActivity.class));
        Btn_Pagar_PagarPlanoPremium.setOnClickListener(view -> {
            if(opcao_pagamento.equals("Nenhuma")) Toast.makeText(this, "Por favor, selecione uma opção de pagamento.", Toast.LENGTH_SHORT).show();
            else {
                Btn_Pagar_PagarPlanoPremium.setEnabled(false);
                ImgBtn_SelecionaCartao_PagarPlanoPremium.setEnabled(false);
                ImgBtn_SelecionaBoleto_PagarPlanoPremium.setEnabled(false);
                Tempo();
            }
        });
        ImgBtn_Voltar_PagarPlanoPremium.setOnClickListener(view -> carregaTelaComInfoUser(PlanoContratoActivity.class, opcao_usuario, id_usuario));
        ImgBtn_SelecionaCartao_PagarPlanoPremium.setOnClickListener(view -> {
            ImgBtn_SelecionaCartao_PagarPlanoPremium.setBackgroundColor(getResources().getColor(R.color.barra));
            ImgBtn_SelecionaBoleto_PagarPlanoPremium.setBackgroundColor(getResources().getColor(R.color.transparente));
            opcao_pagamento = "Cartão";
        });
        ImgBtn_SelecionaBoleto_PagarPlanoPremium.setOnClickListener(view -> {
            ImgBtn_SelecionaBoleto_PagarPlanoPremium.setBackgroundColor(getResources().getColor(R.color.barra));
            ImgBtn_SelecionaCartao_PagarPlanoPremium.setBackgroundColor(getResources().getColor(R.color.transparente));
            opcao_pagamento = "Boleto";
        });
    }

    private void carregaTela(Class classe){
        Intent it = new Intent(getApplicationContext(), classe);
        startActivity(it);
    }

    // Recebe as informações do usuario logado da tela PlanoContrato que recebeu do perfil
    private void recebeOpcao() {
        Intent it = getIntent();
        Bundle infos = it.getExtras();

        opcao_usuario = infos.getString("tipo_usuario");
        id_usuario = infos.getInt("id_usuario");
    }

    // Manda as informações do usuário logado de volta ao perfil
    public void carregaTelaComInfoUser(Class cls, String opc, int id){
        Intent mandaOpcao = new Intent(getApplicationContext(), cls);
        Bundle infos = new Bundle();

        infos.putString("tipo_usuario", opc);
        infos.putInt("id_usuario", id);
        mandaOpcao.putExtras(infos);
        startActivity(mandaOpcao);
    }

    // Delay entre os progressos da barra
    private void Tempo(){
        int delay_ms = 1000; //1000ms = 1s
        new Handler().postDelayed(()->{
            aumentaProgresso();
        }, delay_ms);

    }

    // Método que faz a progress bar aparecer. Quando chega no limite, a progress barra passa para a próxima tela com um toast
    private void aumentaProgresso(){
        PgrsBar_Carregar_PagarPlanoPremium.setBackgroundColor(getResources().getColor(R.color.fundo_progress));
        PgrsBar_Carregar_PagarPlanoPremium.setVisibility(ProgressBar.VISIBLE);
        PgrsBar_Carregar_PagarPlanoPremium.setMax(5);
        PgrsBar_Carregar_PagarPlanoPremium.setProgress(progresso);
        if(progresso < 4) {
            progresso++;
            Tempo();
        }
        else{
            PgrsBar_Carregar_PagarPlanoPremium.setVisibility(ProgressBar.INVISIBLE);
            Toast.makeText(this, "Asssinatura aprovada com sucesso!", Toast.LENGTH_SHORT).show();
            carregaTelaComInfoUser(PerfilActivity.class, opcao_usuario, id_usuario);
        }
    }
}