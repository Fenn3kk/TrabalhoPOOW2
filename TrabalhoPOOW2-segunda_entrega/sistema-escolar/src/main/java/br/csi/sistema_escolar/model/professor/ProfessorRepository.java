package br.csi.sistema_escolar.model.professor;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {
    public Professor findProfessorByUuid(UUID uuid);
    public void deleteProfessorByUuid(UUID uuid);
}
