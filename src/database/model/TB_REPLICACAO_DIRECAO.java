package database.model;

public class TB_REPLICACAO_DIRECAO {

    private Long id;
    private String direcao_origem;
    private String direcao_destino;
    private String usuario_origem;
    private String usuario_destino;
    private String senha_origem;
    private String senha_destino;
    private Boolean habilitado;
    private Long processo_id;

    public TB_REPLICACAO_DIRECAO() {
    }

    public TB_REPLICACAO_DIRECAO(Long id, String direcao_origem, String direcao_destino, String usuario_origem, String usuario_destino, String senha_origem, String senha_destino, Boolean habilitado, Long processo_id) {
        this.id = id;
        this.direcao_origem = direcao_origem;
        this.direcao_destino = direcao_destino;
        this.usuario_origem = usuario_origem;
        this.usuario_destino = usuario_destino;
        this.senha_origem = senha_origem;
        this.senha_destino = senha_destino;
        this.habilitado = habilitado;
        this.processo_id = processo_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDirecao_origem() {
        return direcao_origem;
    }

    public void setDirecao_origem(String direcao_origem) {
        this.direcao_origem = direcao_origem;
    }

    public String getDirecao_destino() {
        return direcao_destino;
    }

    public void setDirecao_destino(String direcao_destino) {
        this.direcao_destino = direcao_destino;
    }

    public String getUsuario_origem() {
        return usuario_origem;
    }

    public void setUsuario_origem(String usuario_origem) {
        this.usuario_origem = usuario_origem;
    }

    public String getUsuario_destino() {
        return usuario_destino;
    }

    public void setUsuario_destino(String usuario_destino) {
        this.usuario_destino = usuario_destino;
    }

    public String getSenha_origem() {
        return senha_origem;
    }

    public void setSenha_origem(String senha_origem) {
        this.senha_origem = senha_origem;
    }

    public String getSenha_destino() {
        return senha_destino;
    }

    public void setSenha_destino(String senha_destino) {
        this.senha_destino = senha_destino;
    }

    public Boolean getHabilitado() {
        return habilitado;
    }

    public void setHabilitado(Boolean habilitado) {
        this.habilitado = habilitado;
    }

    public Long getProcesso_id() {
        return processo_id;
    }

    public void setProcesso_id(Long processo_id) {
        this.processo_id = processo_id;
    }
}
