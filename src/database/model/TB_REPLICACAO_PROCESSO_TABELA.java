package database.model;

public class TB_REPLICACAO_PROCESSO_TABELA {
    private Long id;
    private Long processo_id;
    private String tabela_origem;
    private String tabela_destino;
    private Integer ordem;
    private Boolean habilitado;
    private String ds_where;

    public TB_REPLICACAO_PROCESSO_TABELA() {
    }

    public TB_REPLICACAO_PROCESSO_TABELA(Long id, Long processo_id, String tabela_origem, String tabela_destino, Integer ordem, boolean habilitado, String ds_where) {
        this.id = id;
        this.processo_id = processo_id;
        this.tabela_origem = tabela_origem;
        this.tabela_destino = tabela_destino;
        this.ordem = ordem;
        this.habilitado = habilitado;
        this.ds_where = ds_where;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProcesso_id() {
        return processo_id;
    }

    public void setProcesso_id(Long processo_id) {
        this.processo_id = processo_id;
    }

    public String getTabela_origem() {
        return tabela_origem;
    }

    public void setTabela_origem(String tabela_origem) {
        this.tabela_origem = tabela_origem;
    }

    public String getTabela_destino() {
        return tabela_destino;
    }

    public void setTabela_destino(String tabela_destino) {
        this.tabela_destino = tabela_destino;
    }

    public Integer getOrdem() {
        return ordem;
    }

    public void setOrdem(Integer ordem) {
        this.ordem = ordem;
    }

    public boolean isHabilitado() {
        return habilitado;
    }

    public void setHabilitado(boolean habilitado) {
        this.habilitado = habilitado;
    }

    public String getDs_where() {
        return ds_where;
    }

    public void setDs_where(String ds_where) {
        this.ds_where = ds_where;
    }
}
