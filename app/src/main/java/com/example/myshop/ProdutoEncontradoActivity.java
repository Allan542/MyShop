package com.example.myshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.myshop.database.BancoSQLite;
import com.example.myshop.modelos.Produto;

import java.text.DecimalFormat;


public class ProdutoEncontradoActivity extends AppCompatActivity {

    ImageButton ImgBtn_Home_ProdutoEncontrado, ImgBtn_Vendedor_ProdutoEncontrado, ImgBtn_Menu_ProdutoEncontrado,
            ImgBtn_Carrinho_ProdutoEncontrado, ImgBtn_Perfil_ProdutoEncontrado, ImgBtn_Suporte_ProdutoEncontrado, ImgBtn_Voltar_ProdutoEncontrado,
            ImgBtn_Pesquisa_ProdutoEncontrado, ImgBtn_FotoProduto_ProdutoEncontrado, ImgBtn_AdicionarCarrinho_ProdutoEncontrado;
    EditText Edt_BuscaProduto_ProdutoEncontrado;
    TextView Txt_Titulo_ProdutoEncontrado, Txt_Preco_ProdutoEncontrado;
    int clique_pesquisa = 1;
    String busca, tipo_busca;
    int id_produto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto_encontrado);

        procuraIDs();
        recebePesquisa();
        pesquisaNaTela();
        eventoClique();

    }

    private void procuraIDs() {
        // Barra inferior
        ImgBtn_Home_ProdutoEncontrado = findViewById(R.id.imgbtn_Home_ProdutoEncontrado);
        ImgBtn_Vendedor_ProdutoEncontrado = findViewById(R.id.imgbtn_Vendedor_ProdutoEncontrado);
        ImgBtn_Menu_ProdutoEncontrado = findViewById(R.id.imgbtn_Menu_ProdutoEncontrado);
        ImgBtn_Carrinho_ProdutoEncontrado = findViewById(R.id.imgbtn_Carrinho_ProdutoEncontrado);
        ImgBtn_Perfil_ProdutoEncontrado = findViewById(R.id.imgbtn_Perfil_ProdutoEncontrado);
        ImgBtn_Suporte_ProdutoEncontrado = findViewById(R.id.imgbtn_Suporte_ProdutoEncontrado);

        // Barra superior
        ImgBtn_Voltar_ProdutoEncontrado = findViewById(R.id.imgbtn_Voltar_ProdutoEncontrado);
        ImgBtn_Pesquisa_ProdutoEncontrado = findViewById(R.id.imgbtn_Pesquisa_ProdutoEncontrado);
        Edt_BuscaProduto_ProdutoEncontrado = findViewById(R.id.edt_BuscaProduto_ProdutoEncontrado);
        Txt_Titulo_ProdutoEncontrado = findViewById(R.id.txt_Titulo_ProdutoEncontrado);

        // Conteúdo
        ImgBtn_FotoProduto_ProdutoEncontrado = findViewById(R.id.imgbtn_FotoProduto_ProdutoEncontrado);
        ImgBtn_AdicionarCarrinho_ProdutoEncontrado = findViewById(R.id.imgbtn_AdicionarCarrinho_ProdutoEncontrado);
        Txt_Preco_ProdutoEncontrado = findViewById(R.id.txt_Preco_ProdutoEncontrado);
    }

    private void eventoClique() {
        ImgBtn_Home_ProdutoEncontrado.setOnClickListener(view -> carregaTela(PrincipalActivity.class));
        ImgBtn_Vendedor_ProdutoEncontrado.setOnClickListener(view -> carregaTela(CadastroProdutoActivity.class));
        ImgBtn_Carrinho_ProdutoEncontrado.setOnClickListener(view -> carregaTela(CarrinhoActivity.class));
        ImgBtn_Menu_ProdutoEncontrado.setOnClickListener(view -> carregaTela(CategoriasActivity.class));
        ImgBtn_Perfil_ProdutoEncontrado.setOnClickListener(view -> carregaTela(LoginActivity.class));
        ImgBtn_Suporte_ProdutoEncontrado.setOnClickListener(view -> carregaTela(SuporteActivity.class));
        ImgBtn_Voltar_ProdutoEncontrado.setOnClickListener(view -> carregaTela(PrincipalActivity.class));
        ImgBtn_Pesquisa_ProdutoEncontrado.setOnClickListener(view -> {
            if (clique_pesquisa == 1) {
                Edt_BuscaProduto_ProdutoEncontrado.setVisibility(EditText.VISIBLE);
                Txt_Titulo_ProdutoEncontrado.setVisibility(TextView.INVISIBLE);
                clique_pesquisa = 2;
            }
            else if (clique_pesquisa == 2){
                String busca_por_nome = Edt_BuscaProduto_ProdutoEncontrado.getText().toString();
                Edt_BuscaProduto_ProdutoEncontrado.setVisibility(EditText.INVISIBLE);
                Txt_Titulo_ProdutoEncontrado.setVisibility(TextView.VISIBLE);
                clique_pesquisa = 1;
                fazPesquisa(ProdutoEncontradoActivity.class, busca_por_nome);
            }
        });
        ImgBtn_FotoProduto_ProdutoEncontrado.setOnClickListener(view -> {
            carregaTelaComProduto(ProdutoSelecionadoActivity.class, id_produto);
        });
        ImgBtn_AdicionarCarrinho_ProdutoEncontrado.setOnClickListener(view -> carregaTela(CarrinhoActivity.class));
    }

    private void carregaTela(Class classe){
        Intent it = new Intent(getApplicationContext(), classe);
        startActivity(it);
    }

    // Recebe a busca feita em qualquer tela com botão e campo de pesquisa para mostrar no título
    private void recebePesquisa(){
        Intent recebeBusca = getIntent();
        Bundle infos = recebeBusca.getExtras();
        busca = infos.getString("busca");
        tipo_busca = infos.getString("tipo_busca");
        Txt_Titulo_ProdutoEncontrado.setText(busca);
    }

    // Carrega as informações do produto encontrado na busca
    private void pesquisaNaTela() {
        BancoSQLite db = new BancoSQLite(this);
        DecimalFormat df = new DecimalFormat("#####.00");
        Produto produto;
        if (tipo_busca.equals("Categoria")) produto = db.selecionaProdutoPorCategoria(busca);
        else produto = db.selecionaProdutoPorNome(busca);
        ImgBtn_FotoProduto_ProdutoEncontrado.setImageResource(produto.getImagem());
        String preco = "R$" + df.format(produto.getPrecoVenda());
        Txt_Preco_ProdutoEncontrado.setText(preco);
        id_produto = produto.getId();
    }

    // Manda o id do produto para a tela ProdutoSelecionado
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