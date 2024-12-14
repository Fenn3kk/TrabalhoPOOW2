package br.csi.sistema_escolar.model.professor;

import br.csi.sistema_escolar.model.disciplina.Disciplina;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "Professor")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Schema(description = "Entidade que representa um professor no sistema")
public class Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID do professor", example = "1")
    @Column(nullable = false)
    private Long id;

    @UuidGenerator
    @Column(nullable = false, unique = true)
    private UUID uuid;

    @ManyToMany
    @JoinTable(
            name = "Professor_disciplina",
            joinColumns = @JoinColumn(name = "id_professor"),
            inverseJoinColumns = @JoinColumn(name = "id_disciplina")
    )
    private Set<Disciplina> disciplinas;

    @Column(nullable = false)
    @NonNull
    @NotNull
    @Schema(description = "Nome do professor", example = "Alencar")
    private String nome;

    @Column(nullable = false)
    @NonNull
    @NotNull
    @Schema(description = "Email do aluno", example = "alencar@example.com")
    private String email;
}
