package br.csi.sistema_escolar.service;

import br.csi.sistema_escolar.model.professor.Professor;
import br.csi.sistema_escolar.model.professor.ProfessorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProfessorService {

    private final ProfessorRepository repository;

    public ProfessorService(ProfessorRepository repository) {
        this.repository = repository;
    }

    public void salvarProfessor(Professor professor) {
        this.repository.save(professor);
    }

    public List<Professor> listarProfessores() {
        return this.repository.findAll();
    }

    public Professor getProfessor(Long id) {
        return this.repository.findById(id).orElse(null);
    }

    public void excluirProfessor(Long id) {
        this.repository.deleteById(id);
    }

    public void atualizarProfessor(Professor professor) {
        Professor professorExistente = this.getProfessor(professor.getId_professor());
        if (professorExistente != null) {
            professorExistente.setNome_professor(professor.getNome_professor());
            professorExistente.setEmail_professor(professor.getEmail_professor());
            professorExistente.setDisciplinas(professor.getDisciplinas());
            this.repository.save(professorExistente);
        }
    }

    public void atualizarUuidProfessor(UUID uuid, Professor professor) {
        Professor professorExistente = this.repository.findProfessorByUuid(uuid);
        if (professorExistente != null) {
            professorExistente.setNome_professor(professor.getNome_professor());
            professorExistente.setEmail_professor(professor.getEmail_professor());
            professorExistente.setDisciplinas(professor.getDisciplinas());
            this.repository.save(professorExistente);
        }
    }

    public Professor getProfessorUUID(String uuid) {
        try {
            UUID uuidFormatado = UUID.fromString(uuid); // Validação do UUID
            return this.repository.findProfessorByUuid(uuidFormatado);
        } catch (IllegalArgumentException e) {
            return null; // Caso o UUID seja inválido
        }
    }

    public void deletarUuidProfessor(String uuid) {
        try {
            UUID uuidFormatado = UUID.fromString(uuid); // Validação do UUID
            this.repository.deleteProfessorByUuid(uuidFormatado);
        } catch (IllegalArgumentException e) {
            // Se UUID for inválido, você pode lançar uma exceção ou apenas não fazer nada
        }
    }
}
