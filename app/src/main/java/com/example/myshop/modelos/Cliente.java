package com.example.myshop.modelos;

public class Cliente implements Cloneable {
    private int id;
    private String nome;
    private String email;
    private String documento;
    private String telefone;
    private String senha;
    private String cep;
    private String endereco;
    private String bairro;
    private String cidade;
    private String estado;

    public Cliente(){}


    // Construtor para login
    public Cliente(int id, String email, String senha){
        this.id = id;
        this.email = email;
        this.senha = senha;
    }

    // Construtor para exibir no perfil
    public Cliente (String nome, String email, String documento, String telefone, String senha,
                    String cep, String endereco, String bairro, String cidade, String estado){
        this.nome = nome;
        this.email = email;
        this.documento = documento;
        this.telefone = telefone;
        this.senha = senha;
        this.cep = cep;
        this.endereco = endereco;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
    }

    // Construtor de inserção
    public Cliente (int id, String nome, String email, String documento, String telefone,
                    String cep, String endereco, String bairro, String cidade, String estado){
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.documento = documento;
        this.telefone = telefone;
        this.cep = cep;
        this.endereco = endereco;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
    }


    public Cliente(Cliente c){
        this.id = c.id;
        this.nome = c.nome;
        this.email = c.email;
        this.documento = c.documento;
        this.telefone = c.telefone;
        this.senha = c.senha;
        this.cep = c.cep;
        this.endereco = c.endereco;
        this.bairro = c.bairro;
        this.cidade = c.cidade;
        this.estado = c.estado;
    }

    public int getId(){
        return this.id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getNome(){
        return this.nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public String getEmail(){
        return this.email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getDocumento(){
        return this.documento;
    }

    public void setDocumento(String documento){
        this.documento = documento;
    }

    public String getTelefone(){
        return this.telefone;
    }

    public void setTelefone(String telefone){
        this.telefone = telefone;
    }

    public String getSenha(){
        return this.senha;
    }

    public void setSenha(String senha){
        this.senha = senha;
    }

    public String getCep(){
        return this.cep;
    }

    public void setCep(String cep){
        this.cep = cep;
    }

    public String getEndereco(){
        return this.endereco;
    }

    public void setEndereco(String endereco){
        this.endereco = endereco;
    }

    public String getBairro(){
        return this.bairro;
    }

    public void setBairro(String bairro){
        this.bairro = bairro;
    }

    public String getCidade(){
        return this.cidade;
    }

    public void setCidade(String cidade){
        this.cidade = cidade;
    }

    public String getEstado(){
        return this.estado;
    }

    public void setEstado(String estado){
        this.estado = estado;
    }

    @Override
    public Object clone(){
        Cliente clone = new Cliente(this);
        return clone;
    }
}
