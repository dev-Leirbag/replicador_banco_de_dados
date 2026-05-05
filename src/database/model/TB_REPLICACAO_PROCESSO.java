package database.model;

public class TB_REPLICACAO_PROCESSO {

    private Long id;
    private String processo;
    private String descricao;
    private Boolean habilitado;

    public TB_REPLICACAO_PROCESSO() {
    }

    public TB_REPLICACAO_PROCESSO(Long id, String processo, String descricao, Boolean habilitado) {
        this.id = id;
        this.processo = processo;
        this.descricao = descricao;
        this.habilitado = habilitado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProcesso() {
        return processo;
    }

    public void setProcesso(String processo) {
        this.processo = processo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean getHabilitado() {
        return habilitado;
    }

    public void setHabilitado(Boolean habilitado) {
        this.habilitado = habilitado;
    }

    @Override
    public String toString() {
        return "TB_REPLICACAO_PROCESSO{" +
                "id=" + id +
                ", processo='" + processo + '\'' +
                ", descricao='" + descricao + '\'' +
                ", habilitado=" + habilitado +
                '}';
    }
}
