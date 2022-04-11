package com.example.myshop.modelos;


public class Produto implements Cloneable {
    private int id;
    private int codigodebarras;
    private String categoria;
    private String nomeproduto;
    private int quantidade;
    private String descricao;
    private String marca;
    private double precocusto;
    private double precovenda;
    private int imagem;


    public Produto() {
    }

    public Produto(int id, double precovenda, int imagem){
        this.id = id;
        this.precovenda = precovenda;
        this.imagem = imagem;
    }

    public Produto(int id, String nomeproduto, double precovenda, int imagem){
        this.id = id;
        this.nomeproduto = nomeproduto;
        this.precovenda = precovenda;
        this.imagem = imagem;
    }

    public Produto(int id, String nomeproduto, double precovenda, String descricao, int imagem){
        this.id = id;
        this.nomeproduto = nomeproduto;
        this.precovenda = precovenda;
        this.descricao = descricao;
        this.imagem = imagem;
    }

    public Produto(int codigodebarras, String categoria, String nomeproduto, int quantidade, String descricao,
                   String marca, double precocusto, double precovenda, int imagem) {
        this.codigodebarras = codigodebarras;
        this.categoria = categoria;
        this.nomeproduto = nomeproduto;
        this.quantidade = quantidade;
        this.descricao = descricao;
        this.marca = marca;
        this.precocusto = precocusto;
        this.precovenda = precovenda;
        this.imagem = imagem;
    }

    public Produto(int id, int codigodebarras, String categoria, String nomeproduto, int quantidade,
                   String marca, double precocusto, double precovenda, int imagem) {
        this.id = id;
        this.codigodebarras = codigodebarras;
        this.categoria = categoria;
        this.nomeproduto = nomeproduto;
        this.quantidade = quantidade;
        this.marca = marca;
        this.precocusto = precocusto;
        this.precovenda = precovenda;
        this.imagem = imagem;
    }

    public Produto(Produto p) {
        this.id = p.id;
        this.codigodebarras = p.codigodebarras;
        this.categoria = p.categoria;
        this.nomeproduto = p.nomeproduto;
        this.quantidade = p.quantidade;
        this.descricao = p.descricao;
        this.marca = p.marca;
        this.precocusto = p.precocusto;
        this.precovenda = p.precovenda;
        this.imagem = p.imagem;
    }

    public int getId(){
        return this.id;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getCodigodebarras(){
        return this.codigodebarras;
    }

    public void setCodigodebarras(int codigodebarras){
        this.codigodebarras = codigodebarras;
    }

    public String getCategoria(){
        return this.categoria;
    }

    public void setCategoria(String categoria){
        this.categoria = categoria;
    }

    public String getNomeproduto(){
        return this.nomeproduto;
    }

    public void setNomeproduto(String nomeproduto){
        this.nomeproduto = nomeproduto;
    }

    public int getQuantidade(){
        return this.quantidade;
    }

    public void setQuantidade(int quantidade){
        this.quantidade = quantidade;
    }

    public String getDescricao(){
        return this.descricao;
    }

    public void setDescricao(String descricao){
        this.descricao = descricao;
    }

    public String getMarca(){
        return this.marca;
    }

    public void setMarca(String marca){
        this.marca = marca;
    }

    public double getPrecoCusto(){
        return this.precocusto;
    }

    public void setPrecoCusto(double precocusto){
        this.precocusto = precocusto;
    }

    public double getPrecoVenda(){
        return this.precovenda;
    }

    public void setPrecoVenda(double precovenda){
        this.precovenda = precovenda;
    }

    public int getImagem(){
        return this.imagem;
    }

    public void setImagem(int imagem){
        this.imagem = imagem;
    }

    @Override
    public Object clone() {
        Produto clone = new Produto(this);
        return clone;
    }
}

