package com.example.myshop;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myshop.database.BancoSQLite;
import com.example.myshop.modelos.Produto;

public class CarrinhoVazioActivity extends AppCompatActivity {

    ImageButton ImgBtn_Home_CarrinhoVazio, ImgBtn_Vendedor_CarrinhoVazio, ImgBtn_Menu_CarrinhoVazio, ImgBtn_Perfil_CarrinhoVazio,
            ImgBtn_Suporte_CarrinhoVazio, ImgBtn_Pesquisa_CarrinhoVazio;
    TextView Txt_Titulo_CarrinhoVazio;
    EditText Edt_BuscaProduto_CarrinhoVazio;
    int clique_pesquisa = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho_vazio);

        procuraIDs();
        eventoClique();
    }

    private void procuraIDs() {
        // Barra inferior
        ImgBtn_Home_CarrinhoVazio = findViewById(R.id.imgbtn_Home_CarrinhoVazio);
        ImgBtn_Vendedor_CarrinhoVazio = findViewById(R.id.imgbtn_Vendedor_CarrinhoVazio);
        ImgBtn_Menu_CarrinhoVazio = findViewById(R.id.imgbtn_Menu_CarrinhoVazio);
        ImgBtn_Perfil_CarrinhoVazio = findViewById(R.id.imgbtn_Perfil_CarrinhoVazio);
        ImgBtn_Suporte_CarrinhoVazio = findViewById(R.id.imgbtn_Suporte_CarrinhoVazio);

        // Barra superior
        Edt_BuscaProduto_CarrinhoVazio = findViewById(R.id.edt_BuscaProduto_CarrinhoVazio);
        ImgBtn_Pesquisa_CarrinhoVazio = findViewById(R.id.imgbtn_Pesquisa_CarrinhoVazio);
        Txt_Titulo_CarrinhoVazio = findViewById(R.id.txt_Titulo_CarrinhoVazio);
    }

    private void eventoClique() {
        ImgBtn_Home_CarrinhoVazio.setOnClickListener(view -> carregaTela(PrincipalActivity.class));
        ImgBtn_Vendedor_CarrinhoVazio.setOnClickListener(view -> carregaTela(CadastroProdutoActivity.class));
        ImgBtn_Menu_CarrinhoVazio.setOnClickListener(view -> carregaTela(CategoriasActivity.class));
        ImgBtn_Perfil_CarrinhoVazio.setOnClickListener(view -> carregaTela(LoginActivity.class));
        ImgBtn_Suporte_CarrinhoVazio.setOnClickListener(view -> carregaTela(SuporteActivity.class));
        ImgBtn_Pesquisa_CarrinhoVazio.setOnClickListener(view -> {
            if (clique_pesquisa == 1) {
                Edt_BuscaProduto_CarrinhoVazio.setVisibility(EditText.VISIBLE);
                Txt_Titulo_CarrinhoVazio.setVisibility(TextView.INVISIBLE);
                clique_pesquisa = 2;
            }
            else if (clique_pesquisa == 2){
                String busca_por_nome = Edt_BuscaProduto_CarrinhoVazio.getText().toString();
                Edt_BuscaProduto_CarrinhoVazio.setVisibility(EditText.INVISIBLE);
                Txt_Titulo_CarrinhoVazio.setVisibility(TextView.VISIBLE);
                clique_pesquisa = 1;
                fazPesquisa(ProdutoEncontradoActivity.class, busca_por_nome);
            }
        });
    }

    private void carregaTela(Class classe){
        Intent it = new Intent(getApplicationContext(), classe);
        startActivity(it);
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