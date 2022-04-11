package com.example.myshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.myshop.database.BancoSQLite;
import com.example.myshop.modelos.Produto;

public class ProdutoNaoEncontradoActivity extends AppCompatActivity {

    ImageButton ImgBtn_Home_ProdutoNaoEncontrado, ImgBtn_Vendedor_ProdutoNaoEncontrado, ImgBtn_Menu_ProdutoNaoEncontrado,
            ImgBtn_Carrinho_ProdutoNaoEncontrado, ImgBtn_Perfil_ProdutoNaoEncontrado, ImgBtn_Suporte_ProdutoNaoEncontrado, ImgBtn_Voltar_ProdutoNaoEncontrado,
    ImgBtn_Pesquisa_ProdutoNaoEncontrado;
    EditText Edt_BuscaProduto_ProdutoNaoEncontrado;
    TextView Txt_Titulo_ProdutoNaoEncontrado;
    int clique_pesquisa = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto_nao_encontrado);

        procuraIDs();
        recebePesquisa();
        eventoClique();

    }

    private void procuraIDs() {
        // Barra inferior
        ImgBtn_Home_ProdutoNaoEncontrado = findViewById(R.id.imgbtn_Home_ProdutoNaoEncontrado);
        ImgBtn_Vendedor_ProdutoNaoEncontrado = findViewById(R.id.imgbtn_Vendedor_ProdutoNaoEncontrado);
        ImgBtn_Menu_ProdutoNaoEncontrado = findViewById(R.id.imgbtn_Menu_ProdutoNaoEncontrado);
        ImgBtn_Carrinho_ProdutoNaoEncontrado = findViewById(R.id.imgbtn_Carrinho_ProdutoNaoEncontrado);
        ImgBtn_Perfil_ProdutoNaoEncontrado = findViewById(R.id.imgbtn_Perfil_ProdutoNaoEncontrado);
        ImgBtn_Suporte_ProdutoNaoEncontrado = findViewById(R.id.imgbtn_Suporte_ProdutoNaoEncontrado);

        // Barra superior
        ImgBtn_Voltar_ProdutoNaoEncontrado = findViewById(R.id.imgbtn_Voltar_ProdutoNaoEncontrado);
        ImgBtn_Pesquisa_ProdutoNaoEncontrado = findViewById(R.id.imgbtn_Pesquisa_ProdutoNaoEncontrado);
        Edt_BuscaProduto_ProdutoNaoEncontrado = findViewById(R.id.edt_BuscaProduto_ProdutoNaoEncontrado);
        Txt_Titulo_ProdutoNaoEncontrado = findViewById(R.id.txt_Titulo_ProdutoNaoEncontrado);
    }

    private void eventoClique() {
        ImgBtn_Home_ProdutoNaoEncontrado.setOnClickListener(view -> carregaTela(PrincipalActivity.class));
        ImgBtn_Vendedor_ProdutoNaoEncontrado.setOnClickListener(view -> carregaTela(CadastroProdutoActivity.class));
        ImgBtn_Carrinho_ProdutoNaoEncontrado.setOnClickListener(view -> carregaTela(CarrinhoActivity.class));
        ImgBtn_Menu_ProdutoNaoEncontrado.setOnClickListener(view -> carregaTela(CategoriasActivity.class));
        ImgBtn_Perfil_ProdutoNaoEncontrado.setOnClickListener(view -> carregaTela(LoginActivity.class));
        ImgBtn_Suporte_ProdutoNaoEncontrado.setOnClickListener(view -> carregaTela(SuporteActivity.class));
        ImgBtn_Voltar_ProdutoNaoEncontrado.setOnClickListener(view -> carregaTela(PrincipalActivity.class));
        ImgBtn_Pesquisa_ProdutoNaoEncontrado.setOnClickListener(view -> {
            if (clique_pesquisa == 1) {
                Edt_BuscaProduto_ProdutoNaoEncontrado.setVisibility(EditText.VISIBLE);
                Txt_Titulo_ProdutoNaoEncontrado.setVisibility(TextView.INVISIBLE);
                clique_pesquisa = 2;
            } else if (clique_pesquisa == 2) {
                String busca_por_nome = Edt_BuscaProduto_ProdutoNaoEncontrado.getText().toString();
                Edt_BuscaProduto_ProdutoNaoEncontrado.setVisibility(EditText.INVISIBLE);
                Txt_Titulo_ProdutoNaoEncontrado.setVisibility(TextView.VISIBLE);
                clique_pesquisa = 1;
                fazPesquisa(ProdutoEncontradoActivity.class, busca_por_nome);
            }
        });
    }

    public void carregaTela(Class classe){
        Intent it = new Intent(getApplicationContext(), classe);
        startActivity(it);
    }

    // Recebe a busca feita em qualquer tela com botão e campo de pesquisa para mostrar no título
    private void recebePesquisa(){
        Intent recebeBusca = getIntent();
        Bundle infos = recebeBusca.getExtras();
        Txt_Titulo_ProdutoNaoEncontrado.setText(infos.getString("busca"));
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