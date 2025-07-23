package SistemaDeCadastros;

public class Pet {
    private String nome;
    private TipoPet tipo;
    private SexoPet sexo;
    private Endereco endereco;
    private int idade;
    private int peso;
    private String raca;

    public static final String NAO_INFORMADO = "NÃO INFORMADO";
    public static final int NAO_INFORMADO_IDADE = -1;
    public static final int NAO_INFORMADO_PESO = -1;

    public Pet(String nome, TipoPet tipo, SexoPet sexo, Endereco endereco, int idade, int peso, String raca) {
        this.nome = nome;
        this.tipo = tipo;
        this.sexo = sexo;
        this.endereco = endereco;
        this.idade = idade;
        this.peso = peso;
        this.raca = raca;
    }

    public enum TipoPet {
        CACHORRO, GATO
    }

    public enum SexoPet{
        MACHO, FEMEA
    }

    public String getNome() {
        return nome;
    }

    public TipoPet getTipo() {
        return tipo;
    }

    public SexoPet getSexo() {
        return sexo;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public int getIdade() {
        return idade;
    }

    public int getPeso() {
        return peso;
    }

    public String getRaca() {
        return raca;
    }
}
