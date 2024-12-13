package br.csi.sistema_escolar.model.disciplina;

import br.csi.sistema_escolar.model.horario.Horario;
import br.csi.sistema_escolar.model.professor.Professor;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "Disciplina")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Schema(description = "Entidade que representa uma disciplina no sistema")
public class Disciplina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID da disciplina", example = "1")
    @Column(nullable = false)
    @NonNull
    @NotNull
    private Long id_disciplina;

    @UuidGenerator
    @Column(nullable = false, unique = true)
    @NonNull
    @NotNull
    private UUID uuid;

    @Column(nullable = false)
    @NonNull
    @NotNull
    @Schema(description = "Nome da disciplina", example = "Matemática")
    private String nome_disciplina;
}
