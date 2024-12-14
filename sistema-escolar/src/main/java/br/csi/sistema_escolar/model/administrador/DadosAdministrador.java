package br.csi.sistema_escolar.model.administrador;

public record DadosAdministrador(Long id, String email, String permissao) {

    public DadosAdministrador(Administrador administrador) { this(administrador.getId(), administrador.getEmail(),
                administrador.getPermissao());
    }
}
