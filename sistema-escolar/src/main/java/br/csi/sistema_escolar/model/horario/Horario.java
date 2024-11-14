package br.csi.sistema_escolar.model.horario;

import br.csi.sistema_escolar.model.disciplina.Disciplina;
import br.csi.sistema_escolar.model.professor.Professor;
import br.csi.sistema_escolar.model.turma.Turma;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "Horario", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"id_turma", "id_professor", "id_disciplina"})
})
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Schema(description = "Entidade que representa um horário de aula no sistema")
public class Horario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID do horário", example = "1")
    @Column(nullable = false)
    @NonNull
    @NotNull
    private Long id_horario;

    @UuidGenerator
    @Column(nullable = false, unique = true)
    @NonNull
    @NotNull
    private UUID uuid;

    @ManyToOne
    @JoinColumn(name = "id_turma", nullable = false)
    @NonNull
    @NotNull
    @Schema(description = "A turma a que esse horário pertence")
    private Turma turma;

    @ManyToOne
    @JoinColumn(name = "id_professor", nullable = false)
    @NonNull
    @NotNull
    @Schema(description = "Que professor leciona nesse horário")
    private Professor professor;

    @ManyToOne
    @JoinColumn(name = "id_disciplina", nullable = false)
    @NonNull
    @NotNull
    @Schema(description = "Que disciplina é lecionada nesse horário")
    private Disciplina disciplina;

    @Column(nullable = false)
    @NonNull
    @NotNull
    @Schema(description = "O dia a que esse horário pertence")
    private String dia_horario;

    @Column(nullable = false)
    @NonNull
    @NotNull
    @Schema(description = "Horario em que a disciplina é lecionada")
    private String hora_horario;
}
