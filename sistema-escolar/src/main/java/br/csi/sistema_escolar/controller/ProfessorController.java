package br.csi.sistema_escolar.controller;

import br.csi.sistema_escolar.model.professor.Professor;
import br.csi.sistema_escolar.service.ProfessorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/professores")
@Tag(name = "Professores", description = "Path relacionado a operações de professores")
public class ProfessorController {

    private final ProfessorService service;

    public ProfessorController(ProfessorService service) {
        this.service = service;
    }

    @GetMapping("/listar")
    @Operation(summary = "Listar todos os professores", description = "Retorna uma lista de todos os professores registrados.")
    public List<Professor> listarProfessores() {
        return this.service.listarProfessores();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar professor por ID", description = "Retorna um professor correspondente ao ID fornecido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Professor encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Professor.class))),
            @ApiResponse(responseCode = "404", description = "Professor não encontrado", content = @Content)
    })
    public ResponseEntity<Professor> BuscarProfessorPorID(@PathVariable Long id) {
        Professor professor = this.service.getProfessor(id);
        return professor != null ? ResponseEntity.ok(professor) : ResponseEntity.notFound().build();
    }

    @GetMapping("/uuid/{uuid}")
    @Operation(summary = "Buscar professor por UUID", description = "Retorna um professor correspondente ao UUID fornecido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Professor encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Professor.class))),
            @ApiResponse(responseCode = "404", description = "Professor não encontrado", content = @Content)
    })
    public ResponseEntity<Professor> BuscarProfessorPorUUID(@PathVariable String uuid) {
        Professor professor = this.service.getProfessorUUID(uuid);
        return professor != null ? ResponseEntity.ok(professor) : ResponseEntity.notFound().build();
    }

    @PostMapping("/salvar")
    @Operation(summary = "Criar um novo professor", description = "Cria um novo professor e o adiciona à lista")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Professor criado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Professor.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos", content = @Content)
    })
    public ResponseEntity<Professor> salvarProfessor(@RequestBody @Valid Professor professor, UriComponentsBuilder uriBuilder) {
        this.service.salvarProfessor(professor);
        URI uri = uriBuilder.path("/professores/{uuid}").buildAndExpand(professor.getUuid()).toUri();
        return ResponseEntity.created(uri).body(professor);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um professor existente por ID", description = "Atualiza as informações de um professor existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Professor atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Professor não encontrado", content = @Content)
    })
    public ResponseEntity<Void> atualizarProfessorPorID(@PathVariable Long id, @RequestBody Professor professor) {
        Professor professorExistente = this.service.getProfessor(id);
        if (professorExistente != null) {
            professor.setId_professor(id);
            this.service.atualizarProfessor(professor);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/uuid/{uuid}")
    @Operation(summary = "Atualizar um professor existente por UUID", description = "Atualiza as informações de um professor com base no UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Professor atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Professor não encontrado", content = @Content)
    })
    public ResponseEntity<Void> atualizarProfessorPorUUID(@PathVariable String uuid, @RequestBody Professor professor) {
        // Validar UUID antes de processar
        try {
            UUID uuidFormatado = UUID.fromString(uuid);
            Professor professorExistente = this.service.getProfessorUUID(uuid);
            if (professorExistente != null) {
                professor.setUuid(uuidFormatado);
                this.service.atualizarUuidProfessor(uuidFormatado, professor);
                return ResponseEntity.ok().build();
            }
        } catch (IllegalArgumentException e) {
            // UUID inválido
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar um professor por ID", description = "Remove um professor pelo seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Professor deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Professor não encontrado", content = @Content)
    })
    public ResponseEntity<Void> deletarProfessorPorID(@PathVariable Long id) {
        Professor professorExistente = this.service.getProfessor(id);
        if (professorExistente != null) {
            this.service.excluirProfessor(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/uuid/{uuid}")
    @Operation(summary = "Deletar um professor por UUID", description = "Remove um professor pelo seu UUID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Professor deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Professor não encontrado", content = @Content)
    })
    public ResponseEntity<Void> deletarProfessorPorUUID(@PathVariable String uuid) {
        try {
            UUID uuidFormatado = UUID.fromString(uuid);
            Professor professorExistente = this.service.getProfessorUUID(uuid);
            if (professorExistente != null) {
                this.service.deletarUuidProfessor(String.valueOf(uuidFormatado));
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
