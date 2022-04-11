package com.example.myshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myshop.database.BancoSQLite;
import com.example.myshop.modelos.Cliente;
import com.example.myshop.modelos.Vendedor;

public class LoginActivity extends AppCompatActivity {

    Button Btn_Entrar_Login, Btn_Registrar_Login, Btn_EsqueciSenha_Login;
    ImageButton ImgBtn_Voltar_Login;
    EditText Edt_Email_Login, Edt_Senha_Login;
    TextView Txt_Erro_Login;
    RadioButton Rdo_Cliente_Login, Rdo_Vendedor_Login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        procuraIDs();
        eventoClique();
    }

    private void procuraIDs() {
        ImgBtn_Voltar_Login = findViewById(R.id.imgbtn_Voltar_Login);
        Txt_Erro_Login = findViewById(R.id.txt_Erro_Login);
        Edt_Email_Login = findViewById(R.id.edt_Email_Login);
        Edt_Senha_Login = findViewById(R.id.edt_Senha_Login);
        Btn_EsqueciSenha_Login = findViewById(R.id.btn_EsqueciSenha_Login);
        Rdo_Cliente_Login = findViewById(R.id.rdo_Cliente_Login);
        Rdo_Vendedor_Login = findViewById(R.id.rdo_Vendedor_Login);
        Btn_Entrar_Login = findViewById(R.id.btn_Entrar_Login);
        Btn_Registrar_Login = findViewById(R.id.btn_Registrar_Login);
    }

    private void eventoClique() {
        Btn_Entrar_Login.setOnClickListener(view -> verificaClienteVendedor());
        Btn_Registrar_Login.setOnClickListener(view -> carregaTela(CadastroUsuarioActivity.class));
        ImgBtn_Voltar_Login.setOnClickListener(view -> carregaTela(PrincipalActivity.class));
        Btn_EsqueciSenha_Login.setOnClickListener(view -> carregaTela(EsqueceuSenhaActivity.class));
    }

    private void carregaTela(Class classe){
        Intent it = new Intent(getApplicationContext(), classe);
        startActivity(it);
    }

    // Verifica se os campos foram preenchidos corretamente de acordo com o banco de dados
    // Também, verifica se o usuário ao menos existe no banco
    // Também, de acordo com a opção do radio, verifica em qual banco vai ser feita as verificações acima
    private void verificaClienteVendedor(){
        BancoSQLite db = new BancoSQLite(this);
        String opcao_usuario;
        if (Rdo_Cliente_Login.isChecked()){
            try {
                Cliente cliente = db.selecionarCliente(Edt_Email_Login.getText().toString());

                if(cliente.getSenha().equals(Edt_Senha_Login.getText().toString())){
                    opcao_usuario = Rdo_Cliente_Login.getText().toString();
                    carregaTelaComInfoUser(PerfilActivity.class, opcao_usuario, cliente.getId());

                } else{
                    Txt_Erro_Login.setText("Email ou senha incorretos!");
                }
            }catch (Exception e){
                Toast.makeText(this, "Cliente não cadastrado!", Toast.LENGTH_SHORT).show();
            }
        } else if(Rdo_Vendedor_Login.isChecked()){
            try {
                Vendedor vendedor = db.selecionarVendedor(Edt_Email_Login.getText().toString());

                if(vendedor.getSenha().equals(Edt_Senha_Login.getText().toString())){
                    opcao_usuario = Rdo_Vendedor_Login.getText().toString();
                    carregaTelaComInfoUser(PerfilActivity.class, opcao_usuario, vendedor.getId());
                } else{
                    Txt_Erro_Login.setText("Email ou senha incorretos!");
                }
            }catch (Exception e){
                Toast.makeText(this, "Vendedor não cadastrado!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Manda o id e tipo de usuario que está logando para a tela de perfil
    public void carregaTelaComInfoUser(Class cls, String opc, int id){
        Intent mandaOpcao = new Intent(getApplicationContext(), cls);
        Bundle infos = new Bundle();

        infos.putString("tipo_usuario", opc);
        infos.putInt("id_usuario", id);
        mandaOpcao.putExtras(infos);
        startActivity(mandaOpcao);
    }
}