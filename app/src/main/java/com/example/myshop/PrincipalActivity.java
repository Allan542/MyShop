package com.example.myshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.myshop.database.BancoSQLite;
import com.example.myshop.modelos.Produto;

import java.text.DecimalFormat;


public class PrincipalActivity extends AppCompatActivity {

    ImageButton ImgBtn_Perfil_Principal, ImgBtn_Vendedor_Principal, ImgBtn_Menu_Principal, ImgBtn_Carrinho_Principal, ImgBtn_Suporte_Principal,
            ImgBtn_Pesquisa_Principal;
    ImageButton ImgBtn_Carrossel_Principal, ImgBtn_Produto1_Principal, ImgBtn_Produto2_Principal, ImgBtn_Produto3_Principal, ImgBtn_Produto4_Principal;
    EditText Edt_BuscaProduto_Principal;
    TextView Txt_Titulo_Principal, Txt_NomeProduto1_Principal, Txt_PrecoProduto1_Principal, Txt_NomeProduto2_Principal,
            Txt_PrecoProduto2_Principal, Txt_NomeProduto3_Principal, Txt_PrecoProduto3_Principal, Txt_NomeProduto4_Principal, Txt_PrecoProduto4_Principal;
    int foto_carrossel = 0;
    int clique_pesquisa = 1;
    int[] id_produto = {1, 2, 3, 4};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        procuraIDs();
        carregaProduto(ImgBtn_Produto1_Principal, Txt_NomeProduto1_Principal, Txt_PrecoProduto1_Principal, 0);
        carregaProduto(ImgBtn_Produto2_Principal, Txt_NomeProduto2_Principal, Txt_PrecoProduto2_Principal, 1);
        carregaProduto(ImgBtn_Produto3_Principal, Txt_NomeProduto3_Principal, Txt_PrecoProduto3_Principal, 2);
        carregaProduto(ImgBtn_Produto4_Principal, Txt_NomeProduto4_Principal, Txt_PrecoProduto4_Principal, 3);
        eventoClique();
        Tempo();
    }

    private void procuraIDs() {
        // Barra inferior
        ImgBtn_Perfil_Principal = findViewById(R.id.imgbtn_Perfil_Principal);
        ImgBtn_Vendedor_Principal = findViewById(R.id.imgbtn_Vendedor_Principal);
        ImgBtn_Menu_Principal = findViewById(R.id.imgbtn_Menu_Principal);
        ImgBtn_Carrinho_Principal = findViewById(R.id.imgbtn_Carrinho_Principal);
        ImgBtn_Suporte_Principal = findViewById(R.id.imgbtn_Suporte_Principal);

        // Barra superior e carrossel
        ImgBtn_Pesquisa_Principal = findViewById(R.id.imgbtn_Pesquisa_Principal);
        Edt_BuscaProduto_Principal = findViewById(R.id.edt_BuscaProduto_Principal);
        Txt_Titulo_Principal = findViewById(R.id.txt_Titulo_Principal);
        ImgBtn_Carrossel_Principal = findViewById(R.id.imgbtn_Carrossel_Principal);

        // Produto 1
        ImgBtn_Produto1_Principal = findViewById(R.id.imgbtn_Produto1_Principal);
        Txt_NomeProduto1_Principal = findViewById(R.id.txt_NomeProduto1_Principal);
        Txt_PrecoProduto1_Principal = findViewById(R.id.txt_PrecoProduto1_Principal);
        // Produto 2
        ImgBtn_Produto2_Principal = findViewById(R.id.imgbtn_Produto2_Principal);
        Txt_NomeProduto2_Principal = findViewById(R.id.txt_NomeProduto2_Principal);
        Txt_PrecoProduto2_Principal = findViewById(R.id.txt_PrecoProduto2_Principal);
        // Produto 3
        ImgBtn_Produto3_Principal = findViewById(R.id.imgbtn_Produto3_Principal);
        Txt_NomeProduto3_Principal = findViewById(R.id.txt_NomeProduto3_Principal);
        Txt_PrecoProduto3_Principal = findViewById(R.id.txt_PrecoProduto3_Principal);
        // Produto 4
        ImgBtn_Produto4_Principal = findViewById(R.id.imgbtn_Produto4_Principal);
        Txt_NomeProduto4_Principal = findViewById(R.id.txt_NomeProduto4_Principal);
        Txt_PrecoProduto4_Principal = findViewById(R.id.txt_PrecoProduto4_Principal);
    }

    private void eventoClique() {
        ImgBtn_Vendedor_Principal.setOnClickListener(view -> carregaTela(CadastroProdutoActivity.class));
        ImgBtn_Carrinho_Principal.setOnClickListener(view -> carregaTela(CarrinhoActivity.class));
        ImgBtn_Menu_Principal.setOnClickListener(view -> carregaTela(CategoriasActivity.class));
        ImgBtn_Perfil_Principal.setOnClickListener(view -> carregaTela(LoginActivity.class));
        ImgBtn_Suporte_Principal.setOnClickListener(view -> carregaTela(SuporteActivity.class));
        ImgBtn_Pesquisa_Principal.setOnClickListener(view -> {
            if (clique_pesquisa == 1) {
                Edt_BuscaProduto_Principal.setVisibility(EditText.VISIBLE);
                Txt_Titulo_Principal.setVisibility(TextView.INVISIBLE);
                clique_pesquisa = 2;
            }
            else if (clique_pesquisa == 2){
                String busca_por_nome = Edt_BuscaProduto_Principal.getText().toString();
                Edt_BuscaProduto_Principal.setVisibility(EditText.INVISIBLE);
                Txt_Titulo_Principal.setVisibility(TextView.VISIBLE);
                clique_pesquisa = 1;
                fazPesquisa(ProdutoEncontradoActivity.class, busca_por_nome);
            }
        });
        ImgBtn_Produto1_Principal.setOnClickListener(view -> carregaTelaComProduto(ProdutoSelecionadoActivity.class, id_produto[0]));
        ImgBtn_Produto2_Principal.setOnClickListener(view -> carregaTelaComProduto(ProdutoSelecionadoActivity.class, id_produto[1]));
        ImgBtn_Produto3_Principal.setOnClickListener(view -> carregaTelaComProduto(ProdutoSelecionadoActivity.class, id_produto[2]));
        ImgBtn_Produto4_Principal.setOnClickListener(view -> carregaTelaComProduto(ProdutoSelecionadoActivity.class, id_produto[3]));
    }

    private void carregaTela(Class classe){
        Intent it = new Intent(getApplicationContext(), classe);
        startActivity(it);
    }

    // Método que faz a mudança de foto do carrossel
    private void mudaImagemCarrossel(int x){
        if(x == 0) ImgBtn_Carrossel_Principal.setImageResource(R.drawable.camera);
        else if(x == 1) ImgBtn_Carrossel_Principal.setImageResource(R.drawable.calca2);
        else if(x == 2) ImgBtn_Carrossel_Principal.setImageResource(R.drawable.celular3);
        else if(x == 3) ImgBtn_Carrossel_Principal.setImageResource(R.drawable.celular);
        Tempo();
    }

    // Passa a imagem do carrossel com um delay de 2 segundos
    private void Tempo(){
        int delay_ms = 2000; //2000ms = 2s
        new Handler().postDelayed(()->{
            mudaImagemCarrossel(foto_carrossel++);
            if (foto_carrossel == 4) foto_carrossel = 0;}, delay_ms);
    }

    // Carrega o produto que foi cadastrado no banco em um dos 4 slots
    private void carregaProduto(ImageButton imgbtn, TextView txtnome, TextView txtpreco, int id){
        DecimalFormat df = new DecimalFormat("#####.00");
        BancoSQLite db = new BancoSQLite(this);
        String preco;

        try {
            Produto produto = db.selecionarProduto(id_produto[id]);
            imgbtn.setImageResource(produto.getImagem());
            txtnome.setText(produto.getNomeproduto());
            preco = "R$" + df.format(produto.getPrecoVenda());
            txtpreco.setText(preco);
        } catch(Exception e) {
            Log.d("Exceção", "" + e);
        }
    }

    // Manda o id do produto selecionado para a tela ProdutoSelecionado
    private void carregaTelaComProduto(Class cls, int id){
        Intent mandaConteudo = new Intent(getApplicationContext(), cls);
        Bundle infos = new Bundle();
        infos.putInt("id_produto", id);
        mandaConteudo.putExtras(infos);
        startActivity(mandaConteudo);
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