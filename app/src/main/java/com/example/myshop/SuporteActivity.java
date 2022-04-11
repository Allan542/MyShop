package com.example.myshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.myshop.database.BancoSQLite;
import com.example.myshop.modelos.Produto;

public class SuporteActivity extends AppCompatActivity {

    ImageButton ImgBtn_Home_Suporte, ImgBtn_Vendedor_Suporte, ImgBtn_Menu_Suporte,
            ImgBtn_Carrinho_Suporte, ImgBtn_Perfil_Suporte, ImgBtn_Pesquisa_Suporte;
    TextView Txt_Titulo_Suporte;
    EditText Edt_BuscaProduto_Suporte;
    int clique_pesquisa = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suporte);

        procuraIDs();
        eventoClique();
    }

    private void procuraIDs(){
        // Barra inferior
        ImgBtn_Home_Suporte = findViewById(R.id.imgbtn_Home_Suporte);
        ImgBtn_Vendedor_Suporte = findViewById(R.id.imgbtn_Vendedor_Suporte);
        ImgBtn_Menu_Suporte = findViewById(R.id.imgbtn_Menu_Suporte);
        ImgBtn_Carrinho_Suporte = findViewById(R.id.imgbtn_Carrinho_Suporte);
        ImgBtn_Perfil_Suporte = findViewById(R.id.imgbtn_Perfil_Suporte);

        // Barra superior
        ImgBtn_Pesquisa_Suporte = findViewById(R.id.imgbtn_Pesquisa_Suporte);
        Edt_BuscaProduto_Suporte = findViewById(R.id.edt_BuscaProduto_Suporte);
        Txt_Titulo_Suporte = findViewById(R.id.txt_Titulo_Suporte);
    }

    private void eventoClique(){
        ImgBtn_Home_Suporte.setOnClickListener(view -> carregaTela(PrincipalActivity.class));
        ImgBtn_Vendedor_Suporte.setOnClickListener(view -> carregaTela(CadastroProdutoActivity.class));
        ImgBtn_Menu_Suporte.setOnClickListener(view -> carregaTela(CategoriasActivity.class));
        ImgBtn_Carrinho_Suporte.setOnClickListener(view -> carregaTela(CarrinhoActivity.class));
        ImgBtn_Perfil_Suporte.setOnClickListener(view -> carregaTela(LoginActivity.class));
        ImgBtn_Pesquisa_Suporte.setOnClickListener(view -> {
            if (clique_pesquisa == 1) {
                Edt_BuscaProduto_Suporte.setVisibility(EditText.VISIBLE);
                Txt_Titulo_Suporte.setVisibility(TextView.INVISIBLE);
                clique_pesquisa = 2;
            }
            else if (clique_pesquisa == 2){
                String busca_por_nome = Edt_BuscaProduto_Suporte.getText().toString();
                Edt_BuscaProduto_Suporte.setVisibility(EditText.INVISIBLE);
                Txt_Titulo_Suporte.setVisibility(TextView.VISIBLE);
                clique_pesquisa = 1;
                fazPesquisa(ProdutoEncontradoActivity.class, busca_por_nome);
            }
        });
    }

    public void carregaTela(Class classe){
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