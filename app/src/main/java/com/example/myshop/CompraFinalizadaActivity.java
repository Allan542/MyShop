package com.example.myshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myshop.database.BancoSQLite;
import com.example.myshop.modelos.Produto;

import java.text.DecimalFormat;

public class CompraFinalizadaActivity extends AppCompatActivity {

    ImageButton ImgBtn_Home_CompraFinalizada, ImgBtn_Vendedor_CompraFinalizada, ImgBtn_Menu_CompraFinalizada,
            ImgBtn_Carrinho_CompraFinalizada, ImgBtn_Perfil_CompraFinalizada, ImgBtn_Suporte_CompraFinalizada, ImgBtn_Pesquisa_CompraFinalizada;
    TextView Txt_Titulo_CompraFinalizada, Txt_Produto_CompraFinalizada, Txt_Descricao_CompraFinalizada;
    EditText Edt_BuscaProduto_CompraFinalizada;
    ImageView Img_Produto_CompraFinalizada;
    int clique_pesquisa = 1;
    int id_produto;
    String opcao_pagamento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compra_finalizada);

        procuraIDs();
        recebeProduto();
        carregaInformacoesPedido();
        eventoClique();
    }

    private void procuraIDs() {
        // Barra inferior
        ImgBtn_Home_CompraFinalizada = findViewById(R.id.imgbtn_Home_CompraFinalizada);
        ImgBtn_Vendedor_CompraFinalizada = findViewById(R.id.imgbtn_Vendedor_CompraFinalizada);
        ImgBtn_Menu_CompraFinalizada = findViewById(R.id.imgbtn_Menu_CompraFinalizada);
        ImgBtn_Carrinho_CompraFinalizada = findViewById(R.id.imgbtn_Carrinho_CompraFinalizada);
        ImgBtn_Perfil_CompraFinalizada = findViewById(R.id.imgbtn_Perfil_CompraFinalizada);
        ImgBtn_Suporte_CompraFinalizada = findViewById(R.id.imgbtn_Suporte_CompraFinalizada);

        // Barra Superior
        Edt_BuscaProduto_CompraFinalizada = findViewById(R.id.edt_BuscaProduto_CompraFinalizada);
        ImgBtn_Pesquisa_CompraFinalizada = findViewById(R.id.imgbtn_Pesquisa_CompraFinalizada);
        Txt_Titulo_CompraFinalizada = findViewById(R.id.txt_Titulo_CompraFinalizada);

        // Conteúdo
        Txt_Produto_CompraFinalizada = findViewById(R.id.txt_NomeProduto_CompraFinalizada);
        Txt_Descricao_CompraFinalizada = findViewById(R.id.txt_Descricao_CompraFinalizada);
        Img_Produto_CompraFinalizada = findViewById(R.id.img_Produto_CompraFinalizada);
    }

    private void eventoClique() {
        ImgBtn_Home_CompraFinalizada.setOnClickListener(view -> carregaTela(PrincipalActivity.class));
        ImgBtn_Vendedor_CompraFinalizada.setOnClickListener(view -> carregaTela(CadastroProdutoActivity.class));
        ImgBtn_Carrinho_CompraFinalizada.setOnClickListener(view -> carregaTela(CarrinhoActivity.class));
        ImgBtn_Menu_CompraFinalizada.setOnClickListener(view -> carregaTela(CategoriasActivity.class));
        ImgBtn_Perfil_CompraFinalizada.setOnClickListener(view -> carregaTela(LoginActivity.class));
        ImgBtn_Suporte_CompraFinalizada.setOnClickListener(view -> carregaTela(SuporteActivity.class));
        ImgBtn_Pesquisa_CompraFinalizada.setOnClickListener(view -> {
            if (clique_pesquisa == 1) {
                String busca_por_nome = Edt_BuscaProduto_CompraFinalizada.getText().toString();
                Edt_BuscaProduto_CompraFinalizada.setVisibility(EditText.VISIBLE);
                Txt_Titulo_CompraFinalizada.setVisibility(TextView.INVISIBLE);
                clique_pesquisa = 2;
                fazPesquisa(ProdutoEncontradoActivity.class, busca_por_nome);
            }
            else if (clique_pesquisa == 2){
                Edt_BuscaProduto_CompraFinalizada.setVisibility(EditText.INVISIBLE);
                Txt_Titulo_CompraFinalizada.setVisibility(TextView.VISIBLE);
                clique_pesquisa = 1;
            }
        });
    }

    private void carregaTela(Class classe){
        Intent it = new Intent(getApplicationContext(), classe);
        startActivity(it);
    }

    // Recebe as informações da tela PagarCompra, essas que são o id_produto e a opcao_pagamento
    private void recebeProduto(){
        Intent receberProduto = getIntent();
        Bundle infos = receberProduto.getExtras();
        id_produto = infos.getInt("id_compra");
        opcao_pagamento = infos.getString("opcao_pagamento");
    }

    // Carrega as informações recebidas da tela PagarCompra com os detalhes da tela atual
    private void carregaInformacoesPedido(){
        BancoSQLite db = new BancoSQLite(this);
        DecimalFormat df = new DecimalFormat("#####.00");
        String descricao;
        try {
            Produto produto = db.selecionaProdutoSelecionado(id_produto);
            Img_Produto_CompraFinalizada.setImageResource(produto.getImagem());
            Txt_Produto_CompraFinalizada.setText(produto.getNomeproduto());
            descricao = produto.getDescricao() + "\n" +
                    "Realizado dia 08/04/2022\n"+
                    "Situação: \"Pedido Efetuado\"\n"+
                    "Forma de pagamento: " + opcao_pagamento + "\n" +
                    "Total bruto: R$" + df.format(produto.getPrecoVenda()) + "\n" +
                    "Frete: R$ 0,00\n" +
                    "Desconto: R$0,00";
            Txt_Descricao_CompraFinalizada.setText(descricao);
        } catch (Exception e) {
            Log.d("Erro", "Não carregou o produto");
        }
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
}