package SistemaDeCadastros;

public class Endereco {
    private String numero;
    private String rua;
    private String cidade;

    public Endereco(String rua, String numero, String cidade) {
        this.rua = rua;
        this.numero = numero;
        this.cidade = cidade;
    }

    @Override
    public String toString() {
        return rua + ", " + numero + ", " + cidade;
    }
}
