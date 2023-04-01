import org.Conta;
import org.Dinheiro;
import org.Entrada;
import org.Moeda;
import org.Saida;
import org.SistemaBancario;

public class TestHelper {

    private SistemaBancario sBancarioHelper;

    public TestHelper() {
        this.sBancarioHelper = new SistemaBancario();
    }

    public Conta criarContaZeradaParaTeste(String nomeBanco, Moeda moeda, String nomeAgencia, String nomeTitular) {
        return sBancarioHelper
                .criarBanco(nomeBanco, moeda)
                .criarAgencia(nomeAgencia)
                .criarConta(nomeTitular);
    }

    public Conta criarContaComDinheiroParaTeste(String nomeBanco, Moeda moeda, String nomeAgencia, String nomeTitular, int inteiro, int fracionado) {
        Conta conta = criarContaZeradaParaTeste(nomeBanco, moeda, nomeAgencia, nomeTitular);
        adicionaEntradaNaConta(conta, moeda, inteiro, fracionado);
        return conta;
    }

    public void adicionaEntradaNaConta(Conta conta, Moeda moeda, int inteiro, int fracionado) {
        Entrada deposito = new Entrada(conta, new Dinheiro(moeda, inteiro, fracionado));
        conta.adicionarTransacao(deposito); // adiciona depósito
    }

    public void adicionaSaidaNaConta(Conta conta, Moeda moeda, int inteiro, int fracionado) {
        Saida saque = new Saida(conta, new Dinheiro(moeda, inteiro, fracionado));
        conta.adicionarTransacao(saque); // adiciona saída
    }

}
