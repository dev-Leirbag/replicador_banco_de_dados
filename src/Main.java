import database.model.TB_REPLICACAO_PROCESSO;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        TB_REPLICACAO_PROCESSO tbReplicacaoProcesso = new TB_REPLICACAO_PROCESSO();
        tbReplicacaoProcesso.setId(1L);
        tbReplicacaoProcesso.setProcesso("Completo");
        tbReplicacaoProcesso.setDescricao("Processa todas as tabelas");
        tbReplicacaoProcesso.setHabilitado(true);
        System.out.println(tbReplicacaoProcesso);
    }
}