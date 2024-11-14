package br.csi.sistema_escolar.model.aluno;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {

    public Aluno findAlunoByUuid(UUID uuid);
    public void deleteAlunoByUuid(UUID uuid);
}
