package br.csi.sistema_escolar.model.administrador;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AdministradorRepository extends JpaRepository<Administrador, Long> {
    public Administrador findAdministradorByUuid(UUID uuid);
    public void deleteAdministradorByUuid(UUID uuid);
}
