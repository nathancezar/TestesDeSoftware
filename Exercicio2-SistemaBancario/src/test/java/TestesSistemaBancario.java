
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.Agencia;
import org.Banco;
import org.Conta;
import org.Dinheiro;
import org.EstadosDeOperacao;
import org.Moeda;
import org.Operacao;
import org.SistemaBancario;
import org.ValorMonetario;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestesSistemaBancario {

    private SistemaBancario sBancarioBefore;
    private Agencia agenciaBefore;
    private Banco bancoBefore;
    private TestHelper testHelper = new TestHelper();

    @BeforeEach
    public void setUp() {
        sBancarioBefore = new SistemaBancario();
        bancoBefore = sBancarioBefore.criarBanco("Banco A", Moeda.BRL);
        agenciaBefore = bancoBefore.criarAgencia("Agência A");
        agenciaBefore.criarConta("Titular");
    }

    @AfterEach
    public void tearDown() {
        sBancarioBefore = null;
        bancoBefore = null;
        agenciaBefore = null;
    }

    /* ----- Implicit Setup ----- */

    // implicit setup
    @Test
    public void testSistBancarioObterBancos() {
        // Fixure setup

        // Exercise SUT
        List<Banco> bancos = sBancarioBefore.obterBancos();

        // Result verification
        assertEquals(1, bancos.size());
        assertEquals("Banco A", bancos.get(0).obterNome());
        // Fixture teardown
    }

    // implicit fixture setup
    @Test
    public void testCriarBanco() {
        // Fixure setup
        // Exercise SUT
        Banco banco = sBancarioBefore.criarBanco("Banco B", Moeda.BRL);
        // Result verification
        assertEquals("Banco B", banco.obterNome());
        // Fixture teardown
    }

    // implicit fixture setup
    @Test
    public void testCriarAgencia() {
        // Fixure setup
        // Exercise SUT
        Agencia agencia = bancoBefore.criarAgencia("Agência A");
        // Result verification
        assertEquals("Agência A", agencia.obterNome());
        // Fixture teardown
    }

    // implicit fixture setup
    @Test
    public void testCriarConta() {
        // Fixure setup
        // Exercise SUT
        Conta conta = agenciaBefore.criarConta("Titular");
        // Result verification
        assertEquals("Titular", conta.obterTitular());
        // Fixture teardown
    }

    // implicit fixture setup
    @Test
    public void testObterIdentificador() {
        // Fixure setup
        // Exercise SUT
        String identificador = agenciaBefore.obterIdentificador();
        // Result verification
        assertEquals("001", identificador);
        // Fixture teardown
    }

    // implicit fixture setup
    @Test
    public void testObterNome() {
        // Fixure setup
        // Exercise SUT
        String nome = agenciaBefore.obterNome();
        // Result verification
        assertEquals("Agência A", nome);
        // Fixture teardown
    }

    // implicit fixture setup
    @Test
    public void testObterBanco() {
        // Fixure setup
        // Exercise SUT
        Banco banco = agenciaBefore.obterBanco();
        // Result verification
        assertEquals(banco, banco);
        // Fixture teardown
    }

    // implicit setup
    @Test
    public void testValorMonetarioEquals() {
        // Fixure setup
        Dinheiro dinheiro1 = new Dinheiro(Moeda.BRL, 100, 0);
        Dinheiro dinheiro2 = new Dinheiro(Moeda.BRL, 100, 0);

        // Exercise SUT
        boolean equals = dinheiro1.equals(dinheiro2);

        // Result verification
        assertTrue(equals);
        // Fixture teardown
    }

    // implicit setup
    @Test
    public void testValorMonetarioEqualsMoedaDiferente() {
        // Fixure setup
        Dinheiro dinheiro1 = new Dinheiro(Moeda.BRL, 100, 0);
        Dinheiro dinheiro2 = new Dinheiro(Moeda.USD, 100, 0);

        // Exercise SUT
        boolean equals = dinheiro1.equals(dinheiro2);

        // Result verification
        assertFalse(equals);
        // Fixture teardown
    }

    /* ----- Inline Setup ----- */

    // in-line fixture setup
    @Test
    public void testDinheiroObterMoeda() {
        // Fixure setup
        Dinheiro dinheiro = new Dinheiro(Moeda.BRL, 0, 0);
        // Exercise SUT
        Moeda moeda = dinheiro.obterMoeda();
        // Result verification
        assertEquals(Moeda.BRL, moeda);
        // Fixture teardown
    }

    // in-line fixture setup
    @Test
    public void testDinheiroToStringZero() {
        // Fixure setup
        Dinheiro dinheiro = new Dinheiro(Moeda.BRL, 0, 0);
        // Exercise SUT
        String formatado = dinheiro.toString();
        // Result verification
        assertEquals("0,00", formatado);
        // Fixture teardown
    }

    // in-line fixture setup
    @Test
    public void testDinheiroToStringComValor() {
        // Fixure setup
        Dinheiro dinheiro = new Dinheiro(Moeda.BRL, 10, 10);
        // Exercise SUT
        String formatado = dinheiro.toString();
        // Result verification
        assertEquals("10,10 BRL", formatado);
        // Fixture teardown
    }

    // in-line fixture setup
    @Test
    public void testMoedaObterSimbolo() {
        // Fixure setup
        Moeda moeda = Moeda.BRL;
        // Exercise SUT
        String simbolo = moeda.obterSimbolo();
        // Result verification
        assertEquals("R$", simbolo);
        // Fixture teardown
    }

    /* ----- Delegated Setup ----- */

    // Delegated Setup
    @Test
    public void testCalcularSaldo() {
        // Fixure setup
        Conta conta = testHelper.criarContaComDinheiroParaTeste("Banco A", Moeda.BRL, "Agência A", "Titular", 100, 0);
        testHelper.adicionaSaidaNaConta(conta, Moeda.BRL, 50, 0);// adiciona saque

        // Exercise SUT
        ValorMonetario saldoAtual = conta.calcularSaldo();

        // Result verification
        assertEquals(new Dinheiro(Moeda.BRL, 50, 0), saldoAtual.obterQuantia());
        // Fixture teardown
    }

    // Delegated Setup
    @Test
    public void testSistBancarioOperacaoDepositar() {
        // Fixure setup
        Conta conta = testHelper.criarContaZeradaParaTeste("Banco A", Moeda.BRL, "Agência A", "Titular");

        // Exercise SUT
        Operacao deposito = sBancarioBefore.depositar(conta, new Dinheiro(Moeda.BRL, 100, 0));

        // Result verification
        assertEquals(EstadosDeOperacao.SUCESSO, deposito.obterEstado());
        assertEquals(new Dinheiro(Moeda.BRL, 100, 0), conta.calcularSaldo().obterQuantia());
        // Fixture teardown
    }

    // Delegated Setup
    @Test
    public void testSistemaBancarioOperacaoDepositarMoedaInvalida() {
        // Fixure setup
        Conta conta = testHelper.criarContaZeradaParaTeste("Banco A", Moeda.BRL, "Agência A", "Titular");

        // Exercise SUT
        Operacao deposito = sBancarioBefore.depositar(conta, new Dinheiro(Moeda.USD, 100, 0));

        // Result verification
        assertEquals(EstadosDeOperacao.MOEDA_INVALIDA, deposito.obterEstado());
        assertEquals(new Dinheiro(Moeda.BRL, 0, 0), conta.calcularSaldo().obterQuantia());
        // Fixture teardown
    }

    // Delegated Setup
    @Test
    public void testSistemaBancarioOperacaoSacarMoedaInvalida() {
        // Fixure setup
        Conta conta = testHelper.criarContaZeradaParaTeste("Banco A", Moeda.BRL, "Agência A", "Titular");

        // Exercise SUT
        Operacao saque = sBancarioBefore.sacar(conta, new Dinheiro(Moeda.USD, 100, 0));

        // Result verification
        assertEquals(EstadosDeOperacao.MOEDA_INVALIDA, saque.obterEstado());
        assertEquals(new Dinheiro(Moeda.BRL, 0, 0), conta.calcularSaldo().obterQuantia());
        // Fixture teardown
    }

    // Delegated Setup
    @Test
    public void testSistBancarioOperacaoSacar() {
        // Fixure setup
        Conta conta = testHelper.criarContaComDinheiroParaTeste("Banco A", Moeda.BRL, "Agência A", "Titular", 100, 0);

        // Exercise SUT
        Operacao saque = sBancarioBefore.sacar(conta, new Dinheiro(Moeda.BRL, 50, 0));

        // Result verification
        assertEquals(EstadosDeOperacao.SUCESSO, saque.obterEstado());
        assertEquals(new Dinheiro(Moeda.BRL, 50, 0), conta.calcularSaldo().obterQuantia());
        // Fixture teardown
    }

    // Delegated Setup
    @Test
    public void testSistBancarioOperacaoSacarMoedaInvalida() {
        // Fixure setup
        Conta conta = testHelper.criarContaComDinheiroParaTeste("Banco A", Moeda.BRL, "Agência A", "Titular", 100, 0);

        // Exercise SUT
        Operacao saque = sBancarioBefore.sacar(conta, new Dinheiro(Moeda.USD, 50, 0));

        // Result verification
        assertEquals(EstadosDeOperacao.MOEDA_INVALIDA, saque.obterEstado());
        assertEquals(new Dinheiro(Moeda.BRL, 100, 0), conta.calcularSaldo().obterQuantia());
        // Fixture teardown
    }

    // Delegated Setup
    @Test
    public void testSistBancarioOperacaoSacarSaldoInsuficiente() {
        // Fixure setup
        Conta conta = testHelper.criarContaZeradaParaTeste("Banco A", Moeda.BRL, "Agência A", "Titular");

        // Exercise SUT
        Operacao saque = sBancarioBefore.sacar(conta, new Dinheiro(Moeda.BRL, 150, 0));

        // Result verification
        assertEquals(EstadosDeOperacao.SALDO_INSUFICIENTE, saque.obterEstado());
        // Fixture teardown
    }

    // Delegated Setup
    @Test
    public void testSistBancarioOperacaoTransferir() {
        // Fixure setup
        Conta contaOrigem = testHelper.criarContaComDinheiroParaTeste("Banco A", Moeda.BRL, "Agência A", "Titular", 100,
                0);
        Conta contaDestino = testHelper.criarContaZeradaParaTeste("Banco B", Moeda.BRL, "Agência B", "Titular B");

        // Exercise SUT
        Operacao transferencia = sBancarioBefore.transferir(contaOrigem, contaDestino, new Dinheiro(Moeda.BRL, 50, 0));

        // Result verification
        assertEquals(EstadosDeOperacao.SUCESSO, transferencia.obterEstado());
        assertEquals("+50,00 BRL", contaOrigem.calcularSaldo().formatado());
        assertEquals("+50,00 BRL", contaDestino.calcularSaldo().formatado());
        // Fixture teardown
    }

    // Delegated Setup
    @Test
    public void testSistemaBancarioTransferirMoedaInvalida() {
        // Fixure setup
        Conta contaOrigem = testHelper.criarContaComDinheiroParaTeste("Banco A", Moeda.BRL, "Agência A", "Titular", 100,
                0);
        Conta contaDestino = testHelper.criarContaZeradaParaTeste("Banco B", Moeda.USD, "Agência B", "Titular B");

        // Exercise SUT
        Operacao transferencia = sBancarioBefore.transferir(contaOrigem, contaDestino, new Dinheiro(Moeda.BRL, 50, 0));

        // Result verification
        assertEquals(EstadosDeOperacao.MOEDA_INVALIDA, transferencia.obterEstado());
        assertEquals("+100,00 BRL", contaOrigem.calcularSaldo().formatado());
        assertEquals("0,00", contaDestino.calcularSaldo().formatado());
        // Fixture teardown
    }

}
