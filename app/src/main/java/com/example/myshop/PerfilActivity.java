package com.example.myshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myshop.database.BancoSQLite;
import com.example.myshop.modelos.Cliente;
import com.example.myshop.modelos.Vendedor;

public class PerfilActivity extends AppCompatActivity {

    ImageButton ImgBtn_Home_Perfil, ImgBtn_Vendedor_Perfil, ImgBtn_Menu_Perfil, ImgBtn_Carrinho_Perfil, ImgBtn_Suporte_Perfil, ImgBtn_Sair_Perfil;
    Button Btn_AtualizarDados_Perfil, Btn_ProdutosCadastrados_Perfil, Btn_TornarPremium_Perfil;
    TextView Txt_Nome_Perfil, Txt_Email_Perfil, Txt_Documento_Perfil, Txt_Telefone_Perfil;
    EditText Edt_AtualizaEmail_Perfil, Edt_AtualizaTelefone_Perfil, Edt_AtualizaCep_Perfil, Edt_AtualizaEndereco_Perfil,
            Edt_AtualizaBairro_Perfil, Edt_AtualizaCidade_Perfil, Edt_AtualizaEstado_Perfil;
    String opcao_usuario;
    int id_usuario;
    int num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        procuraIDs();
        recebeOpcao();
        eventoClique();
        carregaInformacoes();
    }

    private void recebeOpcao() {
        Intent it = getIntent();
        Bundle infos = it.getExtras();

        opcao_usuario = infos.getString("tipo_usuario");
        id_usuario = infos.getInt("id_usuario");
    }

    private void procuraIDs() {
        ImgBtn_Home_Perfil = findViewById(R.id.imgbtn_Home_Perfil);
        ImgBtn_Vendedor_Perfil = findViewById(R.id.imgbtn_Vendedor_Perfil);
        ImgBtn_Menu_Perfil = findViewById(R.id.imgbtn_Menu_Perfil);
        ImgBtn_Carrinho_Perfil = findViewById(R.id.imgbtn_Carrinho_Perfil);
        ImgBtn_Suporte_Perfil = findViewById(R.id.imgbtn_Suporte_Perfil);
        ImgBtn_Sair_Perfil = findViewById(R.id.imgbtn_Sair_Perfil);
        Btn_AtualizarDados_Perfil = findViewById(R.id.btn_AtualizarDados_Perfil);
        Btn_ProdutosCadastrados_Perfil = findViewById(R.id.btn_ProdutosCadastrados_Perfil);
        Txt_Nome_Perfil = findViewById(R.id.txt_Nome_Perfil);
        Txt_Email_Perfil = findViewById(R.id.txt_Email_Perfil);
        Txt_Telefone_Perfil = findViewById(R.id.txt_Telefone_Perfil);
        Txt_Documento_Perfil = findViewById(R.id.txt_Documento_Perfil);
        Edt_AtualizaEmail_Perfil = findViewById(R.id.edt_AtualizaEmail_Perfil);
        Edt_AtualizaTelefone_Perfil = findViewById(R.id.edt_AtualizaTelefone_Perfil);
        Edt_AtualizaCep_Perfil = findViewById(R.id.edt_AtualizaCep_Perfil);
        Edt_AtualizaEndereco_Perfil = findViewById(R.id.edt_AtualizaEndereco_Perfil);
        Edt_AtualizaBairro_Perfil = findViewById(R.id.edt_AtualizaBairro_Perfil);
        Edt_AtualizaCidade_Perfil = findViewById(R.id.edt_AtualizaCidade_Perfil);
        Edt_AtualizaEstado_Perfil = findViewById(R.id.edt_AtualizaEstado_Perfil);
        Btn_TornarPremium_Perfil = findViewById(R.id.btn_TornarPremium_Perfil);
    }

    private void eventoClique() {
        ImgBtn_Home_Perfil.setOnClickListener(view -> carregaTela(PrincipalActivity.class));
        ImgBtn_Vendedor_Perfil.setOnClickListener(view -> carregaTela(CadastroProdutoActivity.class));
        ImgBtn_Carrinho_Perfil.setOnClickListener(view -> carregaTela(CarrinhoActivity.class));
        ImgBtn_Menu_Perfil.setOnClickListener(view -> carregaTela(CategoriasActivity.class));
        ImgBtn_Suporte_Perfil.setOnClickListener(view -> carregaTela(SuporteActivity.class));
        ImgBtn_Sair_Perfil.setOnClickListener(view -> carregaTela(LoginActivity.class));
        Btn_AtualizarDados_Perfil.setOnClickListener(view -> Toast.makeText(this, "Dados Atualizado com sucesso!", Toast.LENGTH_SHORT).show());
        Btn_ProdutosCadastrados_Perfil.setOnClickListener(view -> carregaTelaComInfoUser(ProdutosCadastradosActivity.class, opcao_usuario, id_usuario));
        Btn_TornarPremium_Perfil.setOnClickListener(view -> carregaTelaComInfoUser(PlanoContratoActivity.class, opcao_usuario, id_usuario));
    }

    private void carregaTela(Class classe){
        Intent it = new Intent(getApplicationContext(), classe);
        startActivity(it);
    }

    private void carregaInformacoes(){
        BancoSQLite db = new BancoSQLite(this);
        recebeOpcao();

        // Carregar as informações do usuário logado na tela
        if (opcao_usuario.equals("Cliente")){
            Cliente cliente = db.selecionarInformacoesCliente(String.valueOf(id_usuario));
            Txt_Nome_Perfil.setText(cliente.getNome());
            Txt_Email_Perfil.setText(cliente.getEmail());
            Txt_Documento_Perfil.setText(cliente.getDocumento());
            Txt_Telefone_Perfil.setText(cliente.getTelefone());

            Edt_AtualizaEmail_Perfil.setText(cliente.getEmail());
            Edt_AtualizaTelefone_Perfil.setText(cliente.getTelefone());
            Edt_AtualizaCep_Perfil.setText(cliente.getCep());
            Edt_AtualizaEndereco_Perfil.setText(cliente.getEndereco());
            Edt_AtualizaBairro_Perfil.setText(cliente.getBairro());
            Edt_AtualizaCidade_Perfil.setText(cliente.getCidade());
            Edt_AtualizaEstado_Perfil.setText(cliente.getEstado());

            Btn_ProdutosCadastrados_Perfil.setVisibility(Button.INVISIBLE);
            Btn_TornarPremium_Perfil.setVisibility(Button.INVISIBLE);
        } else{
            Vendedor vendedor = db.selecionarInformacoesVendedor(String.valueOf(id_usuario));
            Txt_Nome_Perfil.setText(vendedor.getNome());
            Txt_Email_Perfil.setText(vendedor.getEmail());
            Txt_Documento_Perfil.setText(vendedor.getDocumento());
            Txt_Telefone_Perfil.setText(vendedor.getTelefone());

            Edt_AtualizaEmail_Perfil.setText(vendedor.getEmail());
            Edt_AtualizaTelefone_Perfil.setText(vendedor.getTelefone());
            Edt_AtualizaCep_Perfil.setText(vendedor.getCep());
            Edt_AtualizaEndereco_Perfil.setText(vendedor.getEndereco());
            Edt_AtualizaBairro_Perfil.setText(vendedor.getBairro());
            Edt_AtualizaCidade_Perfil.setText(vendedor.getCidade());
            Edt_AtualizaEstado_Perfil.setText(vendedor.getEstado());

            Tempo();
        }
    }

    private void Tempo(){
        int delay_ms = 1000; //1000ms = 1s
        new Handler().postDelayed(()->{
            piscaBotao(num++);
            if (num > 1) num = 0;
        }, delay_ms);

    }

    private void piscaBotao(int x){
        if(x == 0) Btn_TornarPremium_Perfil.setVisibility(Button.INVISIBLE);
        else Btn_TornarPremium_Perfil.setVisibility(Button.VISIBLE);
        Tempo();
    }

    public void carregaTelaComInfoUser(Class cls, String opc, int id){
        Intent mandaOpcao = new Intent(getApplicationContext(), cls);
        Bundle infos = new Bundle();

        infos.putString("tipo_usuario", opc);
        infos.putInt("id_usuario", id);
        mandaOpcao.putExtras(infos);
        startActivity(mandaOpcao);
    }
}