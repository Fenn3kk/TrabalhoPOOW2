package br.csi.sistema_escolar.service;

import br.csi.sistema_escolar.model.aluno.Aluno;
import br.csi.sistema_escolar.model.aluno.AlunoRepository;
import br.csi.sistema_escolar.model.turma.Turma;
import br.csi.sistema_escolar.model.turma.TurmaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TurmaService {

    private final TurmaRepository turmaRepository;
    private AlunoRepository alunoRepository;

    public TurmaService(TurmaRepository turmaRepository) {
        this.turmaRepository = turmaRepository;
    }

    public void salvarTurma(Turma turma) {
        this.turmaRepository.save(turma);
    }

    public List<Turma> listarTurmas() {
        return this.turmaRepository.findAll();
    }

    public Turma getTurma(Long id) {
        return this.turmaRepository.findById(id).orElse(null);
    }

    public void excluirTurma(Long id) {
        this.turmaRepository.deleteById(id);
    }

    public void atualizarTurma(Turma turma) {
        Turma turmaExistente = this.getTurma(turma.getId_turma());
        if (turmaExistente != null) {
            turmaExistente.setNome_turma(turma.getNome_turma());
            turmaExistente.setAlunos(turma.getAlunos());
            this.turmaRepository.save(turmaExistente);
        }
    }

    public void atualizarUuidTurma(UUID uuid, Turma turma) {
        Turma turmaExistente = this.turmaRepository.findTurmaByUuid(uuid);
        if (turmaExistente != null) {
            turmaExistente.setNome_turma(turma.getNome_turma());
            turmaExistente.setAlunos(turma.getAlunos());
            this.turmaRepository.save(turmaExistente);
        }
    }

    public Turma getTurmaByUUID(String uuid) {
        try {
            UUID uuidFormatado = UUID.fromString(uuid); // Validação do UUID
            return this.turmaRepository.findTurmaByUuid(uuidFormatado);
        } catch (IllegalArgumentException e) {
            return null; // Caso o UUID seja inválido
        }
    }

    public void deletarTurmaByUUID(String uuid) {
        try {
            UUID uuidFormatado = UUID.fromString(uuid); // Validação do UUID
            this.turmaRepository.deleteTurmaByUuid(uuidFormatado);
        } catch (IllegalArgumentException e) {
            // Se UUID for inválido, você pode lançar uma exceção ou apenas não fazer nada
        }
    }

    public Turma adicionarAlunoNaTurma(UUID turmaUUID, UUID alunoUUID) {
        Turma turma = turmaRepository.findTurmaByUuid(turmaUUID);
        Aluno aluno = alunoRepository.findAlunoByUuid(alunoUUID);

        if (turma != null && aluno != null) {
            if (!turma.getAlunos().contains(aluno)) {
                turma.getAlunos().add(aluno);
                return turmaRepository.save(turma);
            }
        }
        return null;
    }

    public Turma removerAlunoDaTurma(UUID turmaUUID, UUID alunoUUID) {
        Turma turma = turmaRepository.findTurmaByUuid(turmaUUID);
        Aluno aluno = alunoRepository.findAlunoByUuid(alunoUUID);

        if (turma != null && aluno != null) {
            if (turma.getAlunos().contains(aluno)) {
                turma.getAlunos().remove(aluno);
                return turmaRepository.save(turma);
            }
        }
        return null;
    }
}
