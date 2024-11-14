package br.csi.sistema_escolar.model.administrador;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "Administrador")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Schema(description = "Entidade que representa um administrador no sistema")
public class Administrador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID do administrador", example = "1")
    @Column(nullable = false)
    @NonNull
    @NotNull
    private Long id_administrador;

    @UuidGenerator
    @Column(nullable = false, unique = true)
    @NonNull
    @NotNull
    private UUID uuid;

    @Column(nullable = false)
    @Schema(description = "Nome do administrador", example = "Felipe")
    @NonNull
    @NotNull
    private String nome_administrador;

    @Column(nullable = false)
    @NonNull
    @NotNull
    private String senha_administrador;
}
