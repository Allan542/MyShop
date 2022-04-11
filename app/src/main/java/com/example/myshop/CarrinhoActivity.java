package com.example.myshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myshop.database.BancoSQLite;
import com.example.myshop.modelos.Cliente;
import com.example.myshop.modelos.Produto;

import java.text.DecimalFormat;
import java.util.Random;

public class CarrinhoActivity extends AppCompatActivity {

    Random random = new Random();

    ImageButton ImgBtn_Home_Carrinho, ImgBtn_Vendedor_Carrinho, ImgBtn_Menu_Carrinho, ImgBtn_Perfil_Carrinho, ImgBtn_Suporte_Carrinho, ImgBtn_Pesquisa_Carrinho;
    Button Btn_ContinuarCompra_Carrinho;
    TextView Txt_Titulo_Carrinho, Txt_PrecoProduto_Carrinho, Txt_NomeProduto_Carrinho, Txt_DescricaoProduto_Carrinho, Txt_EnderecoCliente_Carrinho;
    ImageView Img_FotoProduto_Carrinho;
    EditText Edt_BuscaProduto_Carrinho;
    int clique_pesquisa = 1;
    int id_random =  random.nextInt(4) + 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho);

        procuraIDs();
        carregaProdutoSelecionado();
        eventoClique();

    }

    private void procuraIDs() {
        // Barra inferior
        ImgBtn_Home_Carrinho = findViewById(R.id.imgbtn_Home_Carrinho);
        ImgBtn_Vendedor_Carrinho = findViewById(R.id.imgbtn_Vendedor_Carrinho);
        ImgBtn_Menu_Carrinho = findViewById(R.id.imgbtn_Menu_Carrinho);
        ImgBtn_Perfil_Carrinho = findViewById(R.id.imgbtn_Perfil_Carrinho);
        ImgBtn_Suporte_Carrinho = findViewById(R.id.imgbtn_Suporte_Carrinho);

        // Barra superior
        Edt_BuscaProduto_Carrinho = findViewById(R.id.edt_BuscaProduto_Carrinho);
        ImgBtn_Pesquisa_Carrinho = findViewById(R.id.imgbtn_Pesquisa_Carrinho);
        Txt_Titulo_Carrinho = findViewById(R.id.txt_Titulo_Carrinho);

        // Conteúdo
        Txt_NomeProduto_Carrinho = findViewById(R.id.txt_NomeProduto_Carrinho);
        Txt_PrecoProduto_Carrinho = findViewById(R.id.txt_PrecoProduto_Carrinho);
        Txt_DescricaoProduto_Carrinho = findViewById(R.id.txt_DescricaoProduto_Carrinho);
        Txt_EnderecoCliente_Carrinho = findViewById(R.id.txt_EnderecoCliente_Carrinho);
        Img_FotoProduto_Carrinho = findViewById(R.id.img_FotoProduto_Carrinho);
        Btn_ContinuarCompra_Carrinho = findViewById(R.id.btn_ContinuarCompra_Carrinho);
    }

    private void eventoClique() {
        ImgBtn_Home_Carrinho.setOnClickListener(view -> carregaTela(PrincipalActivity.class));
        ImgBtn_Vendedor_Carrinho.setOnClickListener(view -> carregaTela(CadastroProdutoActivity.class));
        ImgBtn_Menu_Carrinho.setOnClickListener(view -> carregaTela(CategoriasActivity.class));
        ImgBtn_Perfil_Carrinho.setOnClickListener(view -> carregaTela(LoginActivity.class));
        ImgBtn_Suporte_Carrinho.setOnClickListener(view -> carregaTela(SuporteActivity.class));
        Btn_ContinuarCompra_Carrinho.setOnClickListener(view -> carregaTelaComProduto(PagarCompraActivity.class, id_random));
        ImgBtn_Pesquisa_Carrinho.setOnClickListener(view -> {
            if (clique_pesquisa == 1) {
                Edt_BuscaProduto_Carrinho.setVisibility(EditText.VISIBLE);
                Txt_Titulo_Carrinho.setVisibility(TextView.INVISIBLE);
                clique_pesquisa = 2;
            }
            else if (clique_pesquisa == 2){
                String busca_por_nome = Edt_BuscaProduto_Carrinho.getText().toString();
                Edt_BuscaProduto_Carrinho.setVisibility(EditText.INVISIBLE);
                Txt_Titulo_Carrinho.setVisibility(TextView.VISIBLE);
                clique_pesquisa = 1;
                fazPesquisa(ProdutoEncontradoActivity.class, busca_por_nome);
            }
        });
    }

    private void carregaTela(Class classe){
        Intent it = new Intent(getApplicationContext(), classe);
        startActivity(it);
    }

    private void carregaProdutoSelecionado(){
        BancoSQLite db = new BancoSQLite(this);
        DecimalFormat df = new DecimalFormat("#####.00");
        String preco, endereco;
        try {
            Produto produto = db.selecionaProdutoSelecionado(id_random);
            Cliente cliente = db.selecionarInformacoesCliente("1");
            Img_FotoProduto_Carrinho.setImageResource(produto.getImagem());
            Txt_NomeProduto_Carrinho.setText(produto.getNomeproduto());
            preco = "R$" + df.format(produto.getPrecoVenda());
            Txt_PrecoProduto_Carrinho.setText(preco);
            Txt_DescricaoProduto_Carrinho.setText(produto.getDescricao());
            endereco = "Endereço de Entrega\n" +
                    "CEP: " + cliente.getCep() + "\n" +
                    "Endereço: " + cliente.getEndereco() + "\n" +
                    "Bairro: " + cliente.getBairro() + "\n" +
                    "Cidade: " + cliente.getCidade() + "\n" +
                    "Estado: " + cliente.getEstado();
            Txt_EnderecoCliente_Carrinho.setText(endereco);
        } catch (Exception e){
            carregaTela(CarrinhoVazioActivity.class);
        }
    }

    private void carregaTelaComProduto(Class cls, int id){
        Intent mandaProduto = new Intent(getApplicationContext(), cls);
        Bundle infos = new Bundle();
        infos.putInt("id_compra", id);
        mandaProduto.putExtras(infos);
        startActivity(mandaProduto);
    }

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