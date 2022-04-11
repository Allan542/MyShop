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

public class ProdutosCadastradosActivity extends AppCompatActivity {

    ImageButton ImgBtn_Home_ProdutosCadastrados, ImgBtn_Vendedor_ProdutosCadastrados, ImgBtn_Menu_ProdutosCadastrados, ImgBtn_Carrinho_ProdutosCadastrados,
            ImgBtn_Suporte_ProdutosCadastrados, ImgBtn_Voltar_ProdutosCadastrados, ImgBtn_Pesquisa_ProdutosCadastrados;
    TextView Txt_Titulo_ProdutosCadastrados, Txt_InformacoesProduto1_ProdutosCadastrados, Txt_InformacoesProduto2_ProdutosCadastrados,
            Txt_InformacoesProduto3_ProdutosCadastrados, Txt_InformacoesProduto4_ProdutosCadastrados;
    EditText Edt_BuscaProduto_ProdutosCadastrados;
    ImageView Img_Produto1_ProdutosCadastrados, Img_Produto2_ProdutosCadastrados, Img_Produto3_ProdutosCadastrados, Img_Produto4_ProdutosCadastrados;
    int clique_pesquisa = 1;
    int[] id_produto = {1, 2, 3, 4};
    String opcao_usuario;
    int id_usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produtos_cadastrados);

        procuraIDs();
        recebeOpcao();
        carregaProdutoCadastrado(Img_Produto1_ProdutosCadastrados, Txt_InformacoesProduto1_ProdutosCadastrados, 0);
        carregaProdutoCadastrado(Img_Produto2_ProdutosCadastrados, Txt_InformacoesProduto2_ProdutosCadastrados, 1);
        carregaProdutoCadastrado(Img_Produto3_ProdutosCadastrados, Txt_InformacoesProduto3_ProdutosCadastrados, 2);
        carregaProdutoCadastrado(Img_Produto4_ProdutosCadastrados, Txt_InformacoesProduto4_ProdutosCadastrados, 3);
        eventoClique();
    }

    private void procuraIDs(){
        // Barra inferior
        ImgBtn_Home_ProdutosCadastrados = findViewById(R.id.imgbtn_Home_ProdutosCadastrados);
        ImgBtn_Vendedor_ProdutosCadastrados = findViewById(R.id.imgbtn_Vendedor_ProdutosCadastrados);
        ImgBtn_Menu_ProdutosCadastrados = findViewById(R.id.imgbtn_Menu_ProdutosCadastrados);
        ImgBtn_Carrinho_ProdutosCadastrados = findViewById(R.id.imgbtn_Carrinho_ProdutosCadastrados);
        ImgBtn_Suporte_ProdutosCadastrados = findViewById(R.id.imgbtn_Suporte_ProdutosCadastrados);

        // Barra superior
        ImgBtn_Voltar_ProdutosCadastrados = findViewById(R.id.imgbtn_Voltar_ProdutosCadastrados);
        ImgBtn_Pesquisa_ProdutosCadastrados = findViewById(R.id.imgbtn_Pesquisa_ProdutosCadastrados);
        Edt_BuscaProduto_ProdutosCadastrados = findViewById(R.id.edt_BuscaProduto_ProdutosCadastrados);
        Txt_Titulo_ProdutosCadastrados = findViewById(R.id.txt_Titulo_ProdutosCadastrados);

        // Produto 1
        Img_Produto1_ProdutosCadastrados = findViewById(R.id.img_Produto1_ProdutosCadastrados);
        Txt_InformacoesProduto1_ProdutosCadastrados = findViewById(R.id.txt_InformacoesProduto1_ProdutosCadastrados);

        // Produto 2
        Img_Produto2_ProdutosCadastrados = findViewById(R.id.img_Produto2_ProdutosCadastrados);
        Txt_InformacoesProduto2_ProdutosCadastrados = findViewById(R.id.txt_InformacoesProduto2_ProdutosCadastrados);

        // Produto 3
        Img_Produto3_ProdutosCadastrados = findViewById(R.id.img_Produto3_ProdutosCadastrados);
        Txt_InformacoesProduto3_ProdutosCadastrados = findViewById(R.id.txt_InformacoesProduto3_ProdutosCadastrados);

        // Produto 4
        Img_Produto4_ProdutosCadastrados = findViewById(R.id.img_Produto4_ProdutosCadastrados);
        Txt_InformacoesProduto4_ProdutosCadastrados = findViewById(R.id.txt_InformacoesProduto4_ProdutosCadastrados);
    }

    private void eventoClique(){
        ImgBtn_Home_ProdutosCadastrados.setOnClickListener(view -> carregaTela(PrincipalActivity.class));
        ImgBtn_Vendedor_ProdutosCadastrados.setOnClickListener(view -> carregaTela(CadastroProdutoActivity.class));
        ImgBtn_Menu_ProdutosCadastrados.setOnClickListener(view -> carregaTela(CategoriasActivity.class));
        ImgBtn_Carrinho_ProdutosCadastrados.setOnClickListener(view -> carregaTela(CarrinhoActivity.class));
        ImgBtn_Vendedor_ProdutosCadastrados.setOnClickListener(view -> carregaTela(LoginActivity.class));
        ImgBtn_Suporte_ProdutosCadastrados.setOnClickListener(view -> carregaTela(SuporteActivity.class));
        ImgBtn_Voltar_ProdutosCadastrados.setOnClickListener(view -> carregaTelaComInfoUser(PerfilActivity.class, opcao_usuario, id_usuario));
        ImgBtn_Pesquisa_ProdutosCadastrados.setOnClickListener(view -> {
            if (clique_pesquisa == 1) {
                Edt_BuscaProduto_ProdutosCadastrados.setVisibility(EditText.VISIBLE);
                Txt_Titulo_ProdutosCadastrados.setVisibility(TextView.INVISIBLE);
                clique_pesquisa = 2;
            }
            else if (clique_pesquisa == 2){
                String busca_por_nome = Edt_BuscaProduto_ProdutosCadastrados.getText().toString();
                Edt_BuscaProduto_ProdutosCadastrados.setVisibility(EditText.INVISIBLE);
                Txt_Titulo_ProdutosCadastrados.setVisibility(TextView.VISIBLE);
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

    // Carrega os produtos cadastrados no banco de dados
    private void carregaProdutoCadastrado(ImageView img, TextView txtinfo, int id){
        DecimalFormat df = new DecimalFormat("#####.00");
        BancoSQLite db = new BancoSQLite(this);
        String informacoes;

        try {
            Produto produto = db.selecionaProdutosCadastrados(id_produto[id]);
            img.setImageResource(produto.getImagem());
            informacoes = "Código: " + produto.getCodigodebarras() + "\n" +
                    "Categoria: "+ produto.getCategoria() + "\n" +
                    "Nome: " + produto.getNomeproduto() + "\n" +
                    "Quantidade: " + produto.getQuantidade() + "\n" +
                    "Marca: " + produto.getMarca() + "\n" +
                    "Preço de Custo: R$ " + df.format(produto.getPrecoCusto()) + "\n" +
                    "Preço de Venda: R$ " + df.format(produto.getPrecoVenda());
            txtinfo.setText(informacoes);
        } catch(Exception e) {
            Log.d("Exceção", "" + e);
        }
    }

    // Recebe as informações do usuário logado da tela Perfil
    private void recebeOpcao() {
        Intent it = getIntent();
        Bundle infos = it.getExtras();

        opcao_usuario = infos.getString("tipo_usuario");
        id_usuario = infos.getInt("id_usuario");
    }

    // Manda de volta as informações do perfil logado
    public void carregaTelaComInfoUser(Class cls, String opc, int id){
        Intent mandaOpcao = new Intent(getApplicationContext(), cls);
        Bundle infos = new Bundle();

        infos.putString("tipo_usuario", opc);
        infos.putInt("id_usuario", id);
        mandaOpcao.putExtras(infos);
        startActivity(mandaOpcao);
    }
}