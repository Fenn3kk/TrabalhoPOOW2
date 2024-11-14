package br.csi.sistema_escolar.model.disciplina;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface DisciplinaRepository extends JpaRepository<Disciplina, Long> {
    public Disciplina findDisciplinaByUuid(UUID uuid);
    public void deleteDisciplinaByUuid(UUID uuid);
}
