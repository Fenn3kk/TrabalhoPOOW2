package br.csi.sistema_escolar.controller;

import br.csi.sistema_escolar.model.administrador.Administrador;
import br.csi.sistema_escolar.model.administrador.DadosAdministrador;
import br.csi.sistema_escolar.service.AdministradorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/administrador")
@Tag(name = "Administradores", description = "Path relacionado a operações de administradores")
public class AdministradorController {

    private final AdministradorService service;

    public AdministradorController(AdministradorService service) {
        this.service = service;
    }

    @Operation(summary = "Listar todos os administradores", description = "Retorna uma lista de todos os administradores cadastrados.")
    @GetMapping("/listar")
    public List<Administrador> listar() {
        List<Administrador> administradores = this.service.listar();
        return administradores.stream()
                .map(Administrador::new)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Listar os dados específicos dos administradores", description = "Retorna uma lista de dados específicos dos administradores cadastrados.")
    @GetMapping("/listarDA")
    public List<DadosAdministrador> listarDA() {
        return this.service.findAllAdministradores();
    }

    @Operation(summary = "Buscar administrador por UUID", description = "Retorna um administrador específico pelo seu UUID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Administrador encontrado"),
            @ApiResponse(responseCode = "404", description = "Administrador não encontrado")
    })
    @GetMapping("/{uuid}")
    public ResponseEntity<Administrador> buscar(
            @Parameter(description = "UUID do administrador a ser buscado", required = true)
            @PathVariable String uuid) {
        Optional<Administrador> administrador = this.service.getAdministradorUUID(uuid);
        return administrador.map(a -> ResponseEntity.ok(new Administrador(a)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Salvar novo administrador", description = "Cadastra um novo administrador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Administrador cadastrado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Administrador.class))),
            @ApiResponse(responseCode = "400", description = "Erro na validação dos dados")
    })
    @PostMapping
    @Transactional
    public ResponseEntity<Administrador> salvar(@RequestBody @Valid Administrador administrador, UriComponentsBuilder uriBuilder) {
        this.service.salvar(administrador);
        URI uri = uriBuilder.path("/administrador/{uuid}").buildAndExpand(administrador.getUuid()).toUri();
        return ResponseEntity.created(uri).body(new Administrador(administrador));
    }

    @Operation(summary = "Atualizar administrador", description = "Atualiza as informações de um administrador existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Administrador atualizado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Administrador.class))),
            @ApiResponse(responseCode = "404", description = "Administrador não encontrado")
    })
    @PutMapping
    public ResponseEntity<Administrador> atualizarUUID(@RequestBody @Valid Administrador administrador) {
        this.service.atualizarUUID(administrador);
        return ResponseEntity.ok(new Administrador(administrador));
    }

    @Operation(summary = "Deletar administrador", description = "Remove um administrador pelo seu UUID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Administrador deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Administrador não encontrado")
    })
    @Transactional
    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deletar(
            @Parameter(description = "UUID do administrador a ser deletado", required = true)
            @PathVariable String uuid) {
        this.service.deletarUUID(uuid);
        return ResponseEntity.noContent().build();
    }
}
