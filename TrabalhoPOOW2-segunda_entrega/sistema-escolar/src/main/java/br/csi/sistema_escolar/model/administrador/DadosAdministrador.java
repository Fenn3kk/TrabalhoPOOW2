package br.csi.sistema_escolar.model.administrador;

public record DadosAdministrador(Long id, String email, String permissao) {

    public DadosAdministrador(Administrador administrador) { this(administrador.getId_administrador(), administrador.getEmail(),
                administrador.getPermissao());
    }
}
