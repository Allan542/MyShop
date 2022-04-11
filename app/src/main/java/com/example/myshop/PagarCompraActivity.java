package com.example.myshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myshop.database.BancoSQLite;
import com.example.myshop.modelos.Produto;

public class PagarCompraActivity extends AppCompatActivity {

    Button Btn_Pagar_PagarCompra;
    ImageButton ImgBtn_Home_PagarCompra, ImgBtn_Vendedor_PagarCompra, ImgBtn_Menu_PagarCompra, ImgBtn_Perfil_PagarCompra,
            ImgBtn_Suporte_PagarCompra, ImgBtn_Voltar_PagarCompra,
            ImgBtn_Pesquisa_PagarCompra, ImgBtn_SelecionaCartao_PagarCompra, ImgBtn_SelecionaBoleto_PagarCompra;
    TextView Txt_Titulo_PagarCompra, Txt_NomeProduto_PagarCompra;
    ImageView Img_Produto_PagarCompra;
    EditText Edt_BuscaProduto_PagarCompra;
    ProgressBar PgrsBar_Carregar_PagarCompra;
    int clique_pesquisa = 1;
    int id_produto, progresso = 0;
    String opcao_pagamento = "Nenhuma"; // Opção de pagamento que será passada para a tela CompraFinalizada

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagar_compra);

        procuraIDs();
        eventoClique();
        recebeProdutoId();
        carregaProduto();
    }

    private void procuraIDs() {
        // Barra inferior
        ImgBtn_Home_PagarCompra = findViewById(R.id.imgbtn_Home_PagarCompra);
        ImgBtn_Vendedor_PagarCompra = findViewById(R.id.imgbtn_Vendedor_PagarCompra);
        ImgBtn_Menu_PagarCompra = findViewById(R.id.imgbtn_Menu_PagarCompra);
        ImgBtn_Perfil_PagarCompra = findViewById(R.id.imgbtn_Perfil_PagarCompra);
        ImgBtn_Suporte_PagarCompra = findViewById(R.id.imgbtn_Suporte_PagarCompra);

        //Barra superior
        ImgBtn_Voltar_PagarCompra = findViewById(R.id.imgbtn_Voltar_PagarCompra);
        ImgBtn_Pesquisa_PagarCompra = findViewById(R.id.imgbtn_Pesquisa_PagarCompra);
        Edt_BuscaProduto_PagarCompra = findViewById(R.id.edt_BuscaProduto_PagarCompra);
        Txt_Titulo_PagarCompra = findViewById(R.id.txt_Titulo_PagarCompra);

        // Conteúdo
        Img_Produto_PagarCompra = findViewById(R.id.img_Produto_PagarCompra);
        Txt_NomeProduto_PagarCompra = findViewById(R.id.txt_NomeProduto_PagarCompra);
        ImgBtn_SelecionaCartao_PagarCompra = findViewById(R.id.imgbtn_SelecionaCartao_PagarCompra);
        ImgBtn_SelecionaBoleto_PagarCompra = findViewById(R.id.imgbtn_SelecionaBoleto_PagarCompra);
        Btn_Pagar_PagarCompra = findViewById(R.id.btn_Pagar_PagarCompra);

        // Barra de progresso que só aparecerá quando clicar no botão de pagar
        PgrsBar_Carregar_PagarCompra = findViewById(R.id.pgrsbar_Carregar_PagarCompra);
    }

    private void eventoClique() {
        ImgBtn_Home_PagarCompra.setOnClickListener(view -> carregaTela(PrincipalActivity.class));
        ImgBtn_Vendedor_PagarCompra.setOnClickListener(view -> carregaTela(CadastroProdutoActivity.class));
        ImgBtn_Menu_PagarCompra.setOnClickListener(view -> carregaTela(CategoriasActivity.class));
        ImgBtn_Perfil_PagarCompra.setOnClickListener(view -> carregaTela(LoginActivity.class));
        ImgBtn_Suporte_PagarCompra.setOnClickListener(view -> carregaTela(SuporteActivity.class));
        ImgBtn_SelecionaCartao_PagarCompra.setOnClickListener(view -> {
            ImgBtn_SelecionaCartao_PagarCompra.setBackgroundColor(getResources().getColor(R.color.barra));
            ImgBtn_SelecionaBoleto_PagarCompra.setBackgroundColor(getResources().getColor(R.color.transparente));
            opcao_pagamento = "Cartão";
        });
        ImgBtn_SelecionaBoleto_PagarCompra.setOnClickListener(view -> {
            ImgBtn_SelecionaBoleto_PagarCompra.setBackgroundColor(getResources().getColor(R.color.barra));
            ImgBtn_SelecionaCartao_PagarCompra.setBackgroundColor(getResources().getColor(R.color.transparente));
            opcao_pagamento = "Boleto";
        });
        ImgBtn_Pesquisa_PagarCompra.setOnClickListener(view -> {
            if (clique_pesquisa == 1) {
                Edt_BuscaProduto_PagarCompra.setVisibility(EditText.VISIBLE);
                Txt_Titulo_PagarCompra.setVisibility(TextView.INVISIBLE);
                clique_pesquisa = 2;
            }
            else if (clique_pesquisa == 2){
                String busca_por_nome = Edt_BuscaProduto_PagarCompra.getText().toString();
                Edt_BuscaProduto_PagarCompra.setVisibility(EditText.INVISIBLE);
                Txt_Titulo_PagarCompra.setVisibility(TextView.VISIBLE);
                clique_pesquisa = 1;
                fazPesquisa(ProdutoEncontradoActivity.class, busca_por_nome);
            }
        });
        Btn_Pagar_PagarCompra.setOnClickListener(view -> {
            if(opcao_pagamento.equals("Nenhuma")) Toast.makeText(this, "Por favor, selecione uma opção de pagamento", Toast.LENGTH_SHORT).show();
            else {
                Btn_Pagar_PagarCompra.setEnabled(false);
                ImgBtn_SelecionaBoleto_PagarCompra.setEnabled(false);
                ImgBtn_SelecionaCartao_PagarCompra.setEnabled(false);
                Tempo();
            }
        });
        ImgBtn_Voltar_PagarCompra.setOnClickListener(view -> carregaTela(CarrinhoActivity.class));
    }

    private void carregaTela(Class classe){
        Intent it = new Intent(getApplicationContext(), classe);
        startActivity(it);
    }

    // Recebe o id do produto escolhido na tela Carrinho
    private void recebeProdutoId(){
        Intent receberProduto = getIntent();
        Bundle infos = receberProduto.getExtras();
        id_produto = infos.getInt("id_compra");
    }

    // Carrega o nome e a foto do produto escolhido pelo id
    private void carregaProduto(){
        BancoSQLite db = new BancoSQLite(this);
        try {
            Produto produto = db.selecionaProdutoSelecionado(id_produto);
            Img_Produto_PagarCompra.setImageResource(produto.getImagem());
            Txt_NomeProduto_PagarCompra.setText(produto.getNomeproduto());
        } catch (Exception e) {
            Log.d("Erro", "Não carregou o produto");
        }
    }

    // Manda o id e a opção de pagamento para a tela CompraFinalizada
    private void carregaTelaComProduto(Class cls, int id){
        Intent mandaProduto = new Intent(getApplicationContext(), cls);
        Bundle infos = new Bundle();
        infos.putInt("id_compra", id);
        infos.putString("opcao_pagamento", opcao_pagamento);
        mandaProduto.putExtras(infos);
        startActivity(mandaProduto);
    }

    // Verifica a busca preenchida no campo de busca no banco de dados, para mandar para a tela com o produto encontrado
    // ou para a tela dizendo que não foi encontrado. Também, manda o valor da busca e o tipo dela para as telas que forem colocadas como argumento
    private void fazPesquisa(Class classe, String nomeproduto){
        Intent busca;
        Bundle infos = new Bundle();
        BancoSQLite db = new BancoSQLite(this);

        try{
            Produto p = db.selecionaProdutoPorNome(nomeproduto);
            busca = new Intent(getApplicationContext(), classe);
        } catch (Exception e){
            busca = new Intent(getApplicationContext(), ProdutoNaoEncontradoActivity.class);
        }
        infos.putString("busca", nomeproduto);
        infos.putString("tipo_busca", "Busca por Nome");
        busca.putExtras(infos);
        startActivity(busca);
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
        PgrsBar_Carregar_PagarCompra.setBackgroundColor(getResources().getColor(R.color.fundo_progress));
        PgrsBar_Carregar_PagarCompra.setVisibility(ProgressBar.VISIBLE);
        PgrsBar_Carregar_PagarCompra.setMax(5);
        PgrsBar_Carregar_PagarCompra.setProgress(progresso);
        if(progresso < 4) {
            progresso++;
            Tempo();
        }
        else{
            PgrsBar_Carregar_PagarCompra.setVisibility(ProgressBar.INVISIBLE);
            Toast.makeText(this, "Pagamento aprovado com sucesso!", Toast.LENGTH_SHORT).show();
            carregaTelaComProduto(CompraFinalizadaActivity.class, id_produto);
        }
    }
}