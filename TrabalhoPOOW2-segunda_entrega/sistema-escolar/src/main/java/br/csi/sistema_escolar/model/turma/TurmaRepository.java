package br.csi.sistema_escolar.model.turma;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface TurmaRepository extends JpaRepository<Turma, Long> {
    public Turma findTurmaByUuid(UUID uuid);
    public void deleteTurmaByUuid(UUID uuid);
}
