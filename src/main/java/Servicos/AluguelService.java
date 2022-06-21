package Servicos;

import Utils.DateUtils;
import entidades.Filme;
import entidades.NotaAlugel;
import entidades.TiposAluguel;


public class AluguelService {
    public NotaAlugel alugar(Filme filme, TiposAluguel tipo) {
        if (filme.getEstoque() == 0) throw new RuntimeException("Filme sem estoque");

        NotaAlugel nota = new NotaAlugel();
        switch (tipo){
            case COMUM:
                nota.setPreco(filme.getAluguel());
                nota.setDataEntrega(DateUtils.obterDataDiferencaDias(1));
                nota.setPontuacao(1);
            break;
            case EXTENDIDO:
                nota.setPreco(filme.getAluguel() * 2);
                nota.setDataEntrega(DateUtils.obterDataDiferencaDias(3));
                nota.setPontuacao(2);
                break;
            case SEMANAL:
                nota.setPreco(filme.getAluguel() * 3);
                nota.setDataEntrega(DateUtils.obterDataDiferencaDias(7));
                nota.setPontuacao(3);
        }

        filme.setEstoque(filme.getEstoque() -1);
        return nota;
    }

}
