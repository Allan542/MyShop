package com.example.myshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myshop.database.BancoSQLite;
import com.example.myshop.modelos.Produto;
import com.example.myshop.modelos.Vendedor;

import java.text.DecimalFormat;

public class ProdutoSelecionadoActivity extends AppCompatActivity {

    ImageButton ImgBtn_Home_ProdutoSelecionado, ImgBtn_Menu_ProdutoSelecionado, ImgBtn_Vendedor_ProdutoSelecionado, ImgBtn_Carrinho_ProdutoSelecionado,
            ImgBtn_Perfil_ProdutoSelecionado, ImgBtn_Suporte_ProdutoSelecionado, ImgBtn_Voltar_ProdutoSelecionado,
    ImgBtn_Pesquisa_ProdutoSelecionado;
    EditText Edt_BuscaProduto_ProdutoSelecionado;
    TextView Txt_Titulo_ProdutoSelecionado, Txt_Nome_ProdutoSelecionado, Txt_Descricao_ProdutoSelecionado, Txt_DescricaoVendedor_ProdutoSelecionado;
    ImageView Img_FotoProduto_ProdutoSelecionado;
    Button Btn_AdicionarCarrinho_ProdutoSelecionado;
    int clique_pesquisa = 1;
    int id_produto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto_selecionado);

        procuraIDs();
        eventoClique();
        carregaProdutoSelecionado();
    }


    private void carregaTela(Class classe){
        Intent it = new Intent(getApplicationContext(), classe);
        startActivity(it);
    }

    private void procuraIDs(){
        // Barra inferior
        ImgBtn_Home_ProdutoSelecionado = findViewById(R.id.imgbtn_Home_ProdutoSelecionado);
        ImgBtn_Vendedor_ProdutoSelecionado = findViewById(R.id.imgbtn_Vendedor_ProdutoSelecionado);
        ImgBtn_Menu_ProdutoSelecionado = findViewById(R.id.imgbtn_Menu_ProdutoSelecionado);
        ImgBtn_Carrinho_ProdutoSelecionado = findViewById(R.id.imgbtn_Carrinho_ProdutoSelecionado);
        ImgBtn_Perfil_ProdutoSelecionado = findViewById(R.id.imgbtn_Perfil_ProdutoSelecionado);
        ImgBtn_Suporte_ProdutoSelecionado = findViewById(R.id.imgbtn_Suporte_ProdutoSelecionado);

        // Barra superior
        ImgBtn_Voltar_ProdutoSelecionado = findViewById(R.id.imgbtn_Voltar_ProdutoSelecionado);
        ImgBtn_Pesquisa_ProdutoSelecionado = findViewById(R.id.imgbtn_Pesquisa_ProdutoSelecionado);
        Edt_BuscaProduto_ProdutoSelecionado = findViewById(R.id.edt_Busca_ProdutoSelecionado);
        Txt_Titulo_ProdutoSelecionado = findViewById(R.id.txt_Titulo_ProdutoSelecionado);

        // Conteúdo
        Txt_Nome_ProdutoSelecionado = findViewById(R.id.txt_Nome_ProdutoSelecionado);
        Txt_Descricao_ProdutoSelecionado = findViewById(R.id.txt_Descricao_ProdutoSelecionado);
        Txt_DescricaoVendedor_ProdutoSelecionado = findViewById(R.id.txt_DescricaoVendedor_ProdutoSelecionado);
        Img_FotoProduto_ProdutoSelecionado = findViewById(R.id.img_FotoProduto_ProdutoSelecionado);
        Btn_AdicionarCarrinho_ProdutoSelecionado = findViewById(R.id.btn_AdicionarCarrinho_ProdutoSelecionado);
    }

    private void eventoClique() {
        ImgBtn_Home_ProdutoSelecionado.setOnClickListener(view -> carregaTela(PrincipalActivity.class));
        ImgBtn_Vendedor_ProdutoSelecionado.setOnClickListener(view -> carregaTela(CadastroProdutoActivity.class));
        ImgBtn_Carrinho_ProdutoSelecionado.setOnClickListener(view -> carregaTela(CarrinhoActivity.class));
        ImgBtn_Menu_ProdutoSelecionado.setOnClickListener(view -> carregaTela(CategoriasActivity.class));
        ImgBtn_Perfil_ProdutoSelecionado.setOnClickListener(view -> carregaTela(LoginActivity.class));
        ImgBtn_Suporte_ProdutoSelecionado.setOnClickListener(view -> carregaTela(SuporteActivity.class));
        ImgBtn_Voltar_ProdutoSelecionado.setOnClickListener(view -> carregaTela(PrincipalActivity.class));
        ImgBtn_Pesquisa_ProdutoSelecionado.setOnClickListener(view -> {
            if (clique_pesquisa == 1) {
                Edt_BuscaProduto_ProdutoSelecionado.setVisibility(EditText.VISIBLE);
                Txt_Titulo_ProdutoSelecionado.setVisibility(TextView.INVISIBLE);
                clique_pesquisa = 2;
            } else if (clique_pesquisa == 2) {
                String busca_por_nome = Edt_BuscaProduto_ProdutoSelecionado.getText().toString();
                Edt_BuscaProduto_ProdutoSelecionado.setVisibility(EditText.INVISIBLE);
                Txt_Titulo_ProdutoSelecionado.setVisibility(TextView.VISIBLE);
                clique_pesquisa = 1;
                fazPesquisa(ProdutoEncontradoActivity.class, busca_por_nome);
            }
        });
        Btn_AdicionarCarrinho_ProdutoSelecionado.setOnClickListener(view -> carregaTela(CarrinhoActivity.class));
    }

    // Recebe o id do produto que foi selecionado na tela Principal ou na tela ProdutoEncontrado
    private int carregaProdutoId(){
        Intent it = getIntent();
        Bundle infos = it.getExtras();
        int id = infos.getInt("id_produto");
        return id;
    }

    // Carrega as informações do produto selecionado pelo id e as informações do vendedor
    private void carregaProdutoSelecionado(){
        id_produto = carregaProdutoId();
        String descricao;

        BancoSQLite db = new BancoSQLite(this);
        DecimalFormat df = new DecimalFormat("#####.00");

        try {
            Produto produto = db.selecionaProdutoSelecionado(id_produto);
            Vendedor vendedor = db.selecionarInformacoesVendedor("1");

            // Carrega produto
            Img_FotoProduto_ProdutoSelecionado.setImageResource(produto.getImagem());
            Txt_Nome_ProdutoSelecionado.setText(produto.getNomeproduto());
            descricao = "Preço: R$" + df.format(produto.getPrecoVenda()) + "\n" + produto.getDescricao();
            Txt_Descricao_ProdutoSelecionado.setText(descricao);

            // Carrega vendedor
            descricao = "Informações sobre o vendedor:\n" +
                    "Nome do vendedor: " + vendedor.getNome() + "\n" +
                    "Email do vendedor: " + vendedor.getEmail() + "\n" +
                    "Telefone do vendedor: " + vendedor.getTelefone() + "\n" +
                    "CEP do vendedor" + vendedor.getCep() + "\n" +
                    "Endereço do vendedor: " + vendedor.getEndereco() + "\n" +
                    "Bairro: " + vendedor.getBairro() + "\n" +
                    "Cidade: "+ vendedor.getCidade() + ", Estado: " + vendedor.getEstado() + "\n" +
                    "Vende desde 08/04/2022";
            Txt_DescricaoVendedor_ProdutoSelecionado.setText(descricao);
        }catch (Exception e){
            Toast.makeText(this, "ERRO! Não foi possível carregar o produto selecionado.", Toast.LENGTH_SHORT).show();
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