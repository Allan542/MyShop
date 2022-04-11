package com.example.myshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myshop.database.BancoSQLite;
import com.example.myshop.modelos.Produto;


public class CadastroProdutoActivity extends AppCompatActivity {

    Button Btn_Registrar_CadastroProduto;
    ImageButton ImgBtn_Voltar_CadastroProduto;
    Spinner Spnr_Categorias_CadastroProduto, Spnr_SelecionaFoto_CadastroProduto;
    EditText Edt_Codigo_CadastroProduto, Edt_Nome_CadastroProduto, Edt_Quantidade_CadastroProduto,
            Edt_Descricao_CadastroProduto, Edt_Marca_CadastroProduto, Edt_PrecoCusto_CadastroProduto, Edt_PrecoVenda_CadastroProduto;
    ImageView Img_FotoProduto_CadastroProduto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_produto);

        procuraIDs();
        eventoClique();
        adaptaSpinner();

    }

    private void procuraIDs() {
        ImgBtn_Voltar_CadastroProduto = findViewById(R.id.imgbtn_Voltar_CadastroProduto);
        Spnr_Categorias_CadastroProduto = findViewById(R.id.spnr_Categoria_CadastroProduto);
        Edt_Codigo_CadastroProduto = findViewById(R.id.edt_Codigo_CadastroProduto);
        Edt_Nome_CadastroProduto = findViewById(R.id.edt_NomeProduto_CadastroProduto);
        Edt_Quantidade_CadastroProduto = findViewById(R.id.edt_Quantidade_CadastroProduto);
        Edt_Descricao_CadastroProduto = findViewById(R.id.edt_Descricao_CadastroProduto);
        Edt_Marca_CadastroProduto = findViewById(R.id.edt_Marca_CadastroProduto);
        Edt_PrecoCusto_CadastroProduto = findViewById(R.id.edt_PrecoCusto_CadastroProduto);
        Edt_PrecoVenda_CadastroProduto = findViewById(R.id.edt_PrecoVenda_CadastroProduto);
        Img_FotoProduto_CadastroProduto = findViewById(R.id.img_FotoProduto_CadastroProduto);
        Spnr_SelecionaFoto_CadastroProduto = findViewById(R.id.spnr_SelecionaFoto_CadastroProduto);
        Btn_Registrar_CadastroProduto = findViewById(R.id.btn_Registrar_CadastroProduto);
    }

    // Antes de cadastrar o produto, verifica se os campos foram preenchidos
    // Não irá cadastrar se os campos com cláusula UNIQUE forem preenchidos com valores que já existem
    private void eventoClique() {
        Btn_Registrar_CadastroProduto.setOnClickListener(view -> {
            BancoSQLite db = new BancoSQLite(this);
            boolean vazio = verificaVazio();
            if (vazio) Toast.makeText(this, "Não possível cadastrar o produto, pois há campo(s) vazio(s)!", Toast.LENGTH_SHORT).show();
            else {
                if (db.inserirProduto(new Produto(
                        Integer.parseInt(Edt_Codigo_CadastroProduto.getText().toString()),
                        Spnr_Categorias_CadastroProduto.getSelectedItem().toString(),
                        Edt_Nome_CadastroProduto.getText().toString(),
                        Integer.parseInt(Edt_Quantidade_CadastroProduto.getText().toString()),
                        Edt_Descricao_CadastroProduto.getText().toString(),
                        Edt_Marca_CadastroProduto.getText().toString(),
                        Double.parseDouble(Edt_PrecoCusto_CadastroProduto.getText().toString()),
                        Double.parseDouble(Edt_PrecoVenda_CadastroProduto.getText().toString()),
                        selecionaImagem()
                ))) {
                    Toast.makeText(this, "Produto Cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                } else Toast.makeText(this, "Não foi possível realizar o cadastro do produto!", Toast.LENGTH_SHORT).show();
            }
        });
        ImgBtn_Voltar_CadastroProduto.setOnClickListener(view -> carregaTela(PrincipalActivity.class));
        Spnr_SelecionaFoto_CadastroProduto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Img_FotoProduto_CadastroProduto.setImageResource(selecionaImagem());
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void carregaTela(Class classe){
        Intent it = new Intent(getApplicationContext(), classe);
        startActivity(it);
    }

    // Adapta uma textview nos spinners para receber estilos
    private void adaptaSpinner() {
        ArrayAdapter<CharSequence> adaptador = ArrayAdapter.createFromResource(this, R.array.categorias, R.layout.spinner_estilo_texto);
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spnr_Categorias_CadastroProduto.setAdapter(adaptador);

        ArrayAdapter<CharSequence> adaptador2 = ArrayAdapter.createFromResource(this, R.array.imagens, R.layout.spinner_estilo_texto2);
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spnr_SelecionaFoto_CadastroProduto.setAdapter(adaptador2);
    }

    // Seleciona imagem para ser mostrada na foto ao lado do spinner de opção de fotos
    private int selecionaImagem(){
        String imagem = Spnr_SelecionaFoto_CadastroProduto.getSelectedItem().toString();
        int id_imagem = 0;
        if(imagem.equals("Celular 1")) id_imagem = R.drawable.celular2;
        else if(imagem.equals("Celular 2")) id_imagem = R.drawable.celular3;
        else if(imagem.equals("Celular 3")) id_imagem = R.drawable.celular4;
        else if(imagem.equals("Calça 1")) id_imagem = R.drawable.calcajeansfemvictory;
        else if(imagem.equals("Calça 2")) id_imagem = R.drawable.calca2;
        else if(imagem.equals("Calça 3")) id_imagem = R.drawable.calca3;
        else if(imagem.equals("Camera")) id_imagem = R.drawable.camera;
        else if(imagem.equals("Anel")) id_imagem = R.drawable.anel;
        else id_imagem = R.drawable.ic_baseline_add_photo_alternate_24;
        return id_imagem;
    }

    // Verifica se os campos foram preenchidos
    private boolean verificaVazio(){
        return Edt_Codigo_CadastroProduto.getText().toString().isEmpty() ||
                Edt_Nome_CadastroProduto.getText().toString().isEmpty() ||
                Edt_Quantidade_CadastroProduto.getText().toString().isEmpty() ||
                Edt_Descricao_CadastroProduto.getText().toString().isEmpty() ||
                Edt_Marca_CadastroProduto.getText().toString().isEmpty() ||
                Edt_PrecoCusto_CadastroProduto.getText().toString().isEmpty() ||
                Edt_PrecoVenda_CadastroProduto.getText().toString().isEmpty();
    }
}