package Steps;

import Servicos.AluguelService;
import Utils.DateUtils;
import cucumber.api.DataTable;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.E;
import cucumber.api.java.pt.Entao;
import cucumber.api.java.pt.Quando;
import entidades.Filme;
import entidades.NotaAlugel;
import entidades.TiposAluguel;
import org.junit.Assert;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Map;

public class steps {

    private Filme filme;
    private AluguelService aluguel = new AluguelService();
    private NotaAlugel nota;
    private String erro;
    private TiposAluguel tipoAluguel;

    @Dado("^um filme com estoque de (\\d+) unidades$")
    public void umFilmeComEstoqueDeUnidades(int arg0) {
        filme = new Filme();
        filme.setEstoque(arg0);
    }



    @E("^que o preco do aluguel seja R\\$ (\\d+)$")
    public void queOPrecoDoAluguelSejaR$(int arg0) {
        filme.setAluguel(arg0);
    }

    @Dado("^um filme$")
    public void um_filme(DataTable table) throws Throwable {
        Map<String, String> map = table.asMap(String.class, String.class);
        filme = new Filme();
        filme.setEstoque(Integer.parseInt(map.get("estoque")));
        filme.setAluguel(Integer.parseInt(map.get("preco")));
        String tipo = map.get("tipo");
        tipoAluguel = tipo.equals("semanal")? TiposAluguel.SEMANAL: tipo.equals("extendido")? TiposAluguel.EXTENDIDO: TiposAluguel.COMUM;
    }

    @Quando("^alugar$")
    public void alugar() {
        try {
            nota = aluguel.alugar(filme, tipoAluguel);
        }catch (RuntimeException e) {
            erro = e.getMessage();
        }
    }

    @Entao("^o preco do aluguel sera R\\$ (\\d+)$")
    public void oPrecoDoAluguelSeraR$(int arg0) {

        Assert.assertEquals(arg0, nota.getPreco());
    }


    @E("^o estoque do filme sera (\\d+) unidades$")
    public void oEstoqueDoFilmeSeraUnidades(int arg0) {
    }

    @Entao("^nao sera possivel por falta de estoque$")
    public void naoSeraPossivelPorFaltaDeEstoque() {
        Assert.assertEquals("Filme sem estoque", erro);
    }

    @E("^que o tipo de seja (.*)$")
    public void queOTipoDeSejaExtendido(String tipo) {
        tipoAluguel = tipo.equals("semanal")? TiposAluguel.SEMANAL: tipo.equals("extendido")? TiposAluguel.EXTENDIDO: TiposAluguel.COMUM;
    }

    @E("^a data de entrega sera em (\\d+) dias?$")
    public void aDataDeEntregaSeraEmDias(int arg0) {
        Date dataEsperada = DateUtils.obterDataDiferencaDias(arg0);
        Date dataReal = nota.getDataEntrega();

        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        Assert.assertEquals(format.format(dataEsperada), format.format(dataReal));
    }

    @E("^a pontuacao recebida sera de (\\d+) pontos$")
    public void aPontuacaoRecebidaSeraDePontos(int arg0) {
        Assert.assertEquals(arg0, nota.getPontuacao());
    }
}
