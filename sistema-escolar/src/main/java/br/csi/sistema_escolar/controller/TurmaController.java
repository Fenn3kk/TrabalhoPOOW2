package br.csi.sistema_escolar.controller;

import br.csi.sistema_escolar.model.aluno.Aluno;
import br.csi.sistema_escolar.model.turma.Turma;
import br.csi.sistema_escolar.service.TurmaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/turmas")
@Tag(name = "Turmas", description = "Path relacionado a operações de turmas")
public class TurmaController {

    private final TurmaService service;

    public TurmaController(TurmaService service) {
        this.service = service;
    }

    @GetMapping("/listar")
    @Operation(summary = "Listar todas as turmas", description = "Retorna uma lista de todas as turmas registradas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Turmas encontradas",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Turma.class))),
            @ApiResponse(responseCode = "404", description = "Turmas não encontradas", content = @Content)
    })
    public List<Turma> listarTurmas() {
        return this.service.listarTurmas();
    }

    @GetMapping("/buscar/id/{id}")
    @Operation(summary = "Buscar turma por ID", description = "Retorna uma turma correspondente ao ID fornecido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Turma encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Turma.class))),
            @ApiResponse(responseCode = "404", description = "Turma não encontrada", content = @Content)
    })
    public ResponseEntity<Turma> buscarTurmaPorID(@PathVariable Long id) {
        Turma turma = this.service.getTurma(id);
        return turma != null ? ResponseEntity.ok(turma) : ResponseEntity.notFound().build();
    }

    @GetMapping("/buscar/uuid/{uuid}")
    @Operation(summary = "Buscar turma por UUID", description = "Retorna uma turma correspondente ao UUID fornecido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Turma encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Turma.class))),
            @ApiResponse(responseCode = "404", description = "Turma não encontrada", content = @Content)
    })
    public ResponseEntity<Turma>buscarTurmaPorUUID(@PathVariable String uuid) {
        Turma turma = this.service.getTurmaByUUID(uuid);
        return turma != null ? ResponseEntity.ok(turma) : ResponseEntity.notFound().build();
    }

    @PostMapping("/salvar")
    @Operation(summary = "Criar uma nova turma", description = "Cria uma nova turma e a adiciona à lista")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Turma criada com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Turma.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos", content = @Content)
    })
    public ResponseEntity<Turma> salvarTurma(@RequestBody Turma turma) {
        this.service.salvarTurma(turma);
        URI uri = URI.create("/turmas/" + turma.getUuid());
        return ResponseEntity.created(uri).body(turma);
    }

    @PutMapping("/atualizar/id/{id}")
    @Operation(summary = "Atualizar uma turma existente por ID", description = "Atualiza as informações de uma turma existente com base no ID fornecido.")
    public ResponseEntity<Void> atualizarTurmaPorID(@PathVariable Long id, @RequestBody Turma turma) {
        Turma turmaExistente = this.service.getTurma(id);
        if (turmaExistente != null) {
            turma.setId_turma(id);
            this.service.atualizarTurma(turma);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/atualizar/uuid/{uuid}")
    @Operation(summary = "Atualizar uma turma existente por UUID", description = "Atualiza as informações de uma turma existente com base no UUID fornecido.")
    public ResponseEntity<Void> atualizarTurmaPorUUID(@PathVariable String uuid, @RequestBody Turma turma) {
        try {
            UUID uuidFormatado = UUID.fromString(uuid);
            Turma turmaExistente = this.service.getTurmaByUUID(uuid);
            if (turmaExistente != null) {
                turma.setUuid(uuidFormatado);
                this.service.atualizarUuidTurma(uuidFormatado, turma);
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/listar/alunos/turma/{turmaUUID}")
    @Operation(summary = "Listar todas os alunos de uma turma", description = "Retorna uma lista de todos os alunos em uma turma.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Alunos na turma encontrados",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Turma.class))),
            @ApiResponse(responseCode = "404", description = "Alunos ou turma não encontrados", content = @Content)
    })
    public ResponseEntity<List<Aluno>> listarAlunosTurma(@PathVariable UUID turmaUUID) {
        List<Aluno> alunos = this.service.listarAlunosTurma(turmaUUID);
        if (!alunos.isEmpty()) {
            return ResponseEntity.ok(alunos);
        }
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/adicionar/aluno/{alunoUUID}/turma/{turmaUUID}")
    @Operation(summary = "Adicionar um aluno a uma turma", description = "Adiciona um aluno a uma turma específica.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Aluno adicionado na turma"),
            @ApiResponse(responseCode = "404", description = "Aluno ou turma não encontrado(s)", content = @Content)
    })
    public ResponseEntity<Turma> adicionarAlunoTurma(@PathVariable UUID turmaUUID, @PathVariable UUID alunoUUID) {
        Turma turmaAtualizada = this.service.adicionarAlunoTurma(turmaUUID, alunoUUID);
        return turmaAtualizada != null ? ResponseEntity.ok(turmaAtualizada) : ResponseEntity.notFound().build();
    }

    @PutMapping("/remover/aluno/{alunoUUID}/turma/{turmaUUID}")
    @Operation(summary = "Remover um aluno de uma turma", description = "Remove um aluno de uma turma específica.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Aluno removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Aluno ou turma não encontrado(s)", content = @Content)
    })
    public ResponseEntity<Turma> removerAlunoTurma(@PathVariable UUID turmaUUID, @PathVariable UUID alunoUUID) {
        Turma turmaAtualizada = this.service.removerAlunoTurma(turmaUUID, alunoUUID);
        return turmaAtualizada != null ? ResponseEntity.ok(turmaAtualizada) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/deletar/id/{id}")
    @Operation(summary = "Deletar uma turma por ID", description = "Remove uma turma pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Turma deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Turma não encontrada", content = @Content)
    })
    public ResponseEntity<Void> deletarTurmaPorID(@PathVariable Long id) {
        Turma turmaExistente = this.service.getTurma(id);
        if (turmaExistente != null) {
            this.service.excluirTurma(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/deletar/uuid/{uuid}")
    @Operation(summary = "Deletar uma turma por UUID", description = "Deleta uma turma baseada no UUID fornecido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Turma deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Turma não encontrada", content = @Content)
    })
    public ResponseEntity<Void> deletarTurmaPorUUID(@PathVariable String uuid) {
        Turma turma = this.service.getTurmaByUUID(uuid);
        if (turma != null) {
            this.service.deletarTurmaByUUID(uuid);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
