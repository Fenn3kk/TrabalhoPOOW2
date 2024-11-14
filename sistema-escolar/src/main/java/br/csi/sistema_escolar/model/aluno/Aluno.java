package br.csi.sistema_escolar.model.aluno;

import br.csi.sistema_escolar.model.turma.Turma;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "Aluno")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Schema(description = "Entidade que representa um aluno no sistema")
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID do aluno", example = "1")
    @Column(nullable = false)
    @NonNull
    @NotNull
    private Long id_aluno;

    @UuidGenerator
    @Column(nullable = false, unique = true)
    @NonNull
    @NotNull
    private UUID uuid;

    @Column(nullable = false)
    @NonNull
    @NotNull
    @Schema(description = "Nome do aluno", example = "Nicolas")
    private String nome_aluno;

    @Column(nullable = false)
    @NonNull
    @NotNull
    @Schema(description = "Matrícula do aluno", example = "2024XXXX")
    private String matricula_aluno;

    @Column(nullable = false)
    @NonNull
    @NotNull
    @Schema(description = "Email do aluno", example = "nicolas@example.com")
    private String email_aluno;
}
