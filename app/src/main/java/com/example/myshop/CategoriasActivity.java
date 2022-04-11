package com.example.myshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.myshop.database.BancoSQLite;
import com.example.myshop.modelos.Produto;


public class CategoriasActivity extends AppCompatActivity {

    ImageButton ImgBtn_Home_Categorias, ImgBtn_Perfil_Categorias, ImgBtn_Vendedor_Categorias,
            ImgBtn_Carrinho_Categorias, ImgBtn_Suporte_Categorias, ImgBtn_Pesquisa_Categorias;
    Button Btn_CategoriaAcessorios_Categorias, Btn_CategoriaCameras_Categorias, Btn_CategoriaCelulares_Categorias,
            Btn_CategoriaJogos_Categorias, Btn_CategoriaRoupasCalcados_Categorias;
    EditText Edt_BuscaProduto_Categorias;
    TextView Txt_Titulo_Categorias;
    int clique_pesquisa = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorias);

        procuraIDs();
        eventoClique();
    }

    private void procuraIDs() {
        // Barra snferior
        ImgBtn_Home_Categorias = findViewById(R.id.imgbtn_Home_Categorias);
        ImgBtn_Perfil_Categorias = findViewById(R.id.imgbtn_Perfil_Categorias);
        ImgBtn_Vendedor_Categorias = findViewById(R.id.imgbtn_Vendedor_Categorias);
        ImgBtn_Carrinho_Categorias = findViewById(R.id.imgbtn_Carrinho_Categorias);
        ImgBtn_Suporte_Categorias = findViewById(R.id.imgbtn_Suporte_Categorias);

        // Barra superior
        Edt_BuscaProduto_Categorias = findViewById(R.id.edt_BuscaProduto_Categorias);
        ImgBtn_Pesquisa_Categorias = findViewById(R.id.imgbtn_Pesquisa_Categorias);
        Txt_Titulo_Categorias = findViewById(R.id.txt_Titulo_Categorias);

        // Conteúdo
        Btn_CategoriaAcessorios_Categorias = findViewById(R.id.btn_CategoriaAcessorios_Categorias);
        Btn_CategoriaCameras_Categorias = findViewById(R.id.btn_CategoriaCameras_Categorias);
        Btn_CategoriaCelulares_Categorias = findViewById(R.id.btn_CategoriaCelulares_Categorias);
        Btn_CategoriaJogos_Categorias = findViewById(R.id.btn_CategoriaJogos_Categorias);
        Btn_CategoriaRoupasCalcados_Categorias = findViewById(R.id.btn_CategoriaRoupasCalcados_Categorias);
    }

    private void eventoClique() {
        ImgBtn_Home_Categorias.setOnClickListener(view -> carregaTela(PrincipalActivity.class));
        ImgBtn_Vendedor_Categorias.setOnClickListener(view -> carregaTela(CadastroProdutoActivity.class));
        ImgBtn_Carrinho_Categorias.setOnClickListener(view -> carregaTela(CarrinhoActivity.class));
        ImgBtn_Perfil_Categorias.setOnClickListener(view -> carregaTela(LoginActivity.class));
        ImgBtn_Suporte_Categorias.setOnClickListener(view -> carregaTela(SuporteActivity.class));
        ImgBtn_Pesquisa_Categorias.setOnClickListener(view -> {
            if (clique_pesquisa == 1) {
                Edt_BuscaProduto_Categorias.setVisibility(EditText.VISIBLE);
                Txt_Titulo_Categorias.setVisibility(TextView.INVISIBLE);
                clique_pesquisa = 2;
            }
            else if (clique_pesquisa == 2){
                String busca_por_nome = Edt_BuscaProduto_Categorias.getText().toString();
                Edt_BuscaProduto_Categorias.setVisibility(EditText.INVISIBLE);
                Txt_Titulo_Categorias.setVisibility(TextView.VISIBLE);
                clique_pesquisa = 1;
                fazPesquisaPorNome(ProdutoEncontradoActivity.class, busca_por_nome);
            }
        });
        Btn_CategoriaAcessorios_Categorias.setOnClickListener(view -> fazPesquisaPorCategoria(ProdutoEncontradoActivity.class, "Acessórios"));
        Btn_CategoriaCameras_Categorias.setOnClickListener(view -> fazPesquisaPorCategoria(ProdutoEncontradoActivity.class, "Câmeras"));
        Btn_CategoriaCelulares_Categorias.setOnClickListener(view -> fazPesquisaPorCategoria(ProdutoEncontradoActivity.class, "Celulares"));
        Btn_CategoriaJogos_Categorias.setOnClickListener(view -> fazPesquisaPorCategoria(ProdutoEncontradoActivity.class, "Jogos"));
        Btn_CategoriaRoupasCalcados_Categorias.setOnClickListener(view -> fazPesquisaPorCategoria(ProdutoEncontradoActivity.class, "Roupas e Calçados"));
    }

    private void carregaTela(Class classe){
        Intent it = new Intent(getApplicationContext(), classe);
        startActivity(it);
    }

    // Seleciona o produto pela categoria, para mandar para a tela com o produto encontrado ou para a tela
    // dizendo que não foi encontrado. Também, manda o valor da categoria e o tipo dela para as telas que forem colocadas como argumento
    private void fazPesquisaPorCategoria(Class classe, String categoria){
        Intent busca;
        Bundle infos = new Bundle();
        BancoSQLite db = new BancoSQLite(this);

        try{
            Produto p = db.selecionaProdutoPorCategoria(categoria);
            busca = new Intent(getApplicationContext(), classe);
        } catch (Exception e){
            busca = new Intent(getApplicationContext(), ProdutoNaoEncontradoActivity.class);
        }
        infos.putString("busca", categoria);
        infos.putString("tipo_busca", "Categoria");
        busca.putExtras(infos);
        startActivity(busca);
    }

    // Verifica a busca preenchida no campo de busca no banco de dados, para mandar para a tela com o produto encontrado
    // ou para a tela dizendo que não foi encontrado. Também, manda o valor da busca e o tipo dela para as telas que forem colocadas como argumento
    private void fazPesquisaPorNome(Class classe, String nomeproduto){
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