package br.csi.sistema_escolar.model.administrador;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "Administrador")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Schema(description = "Entidade que representa um administrador(usuário) do sistema")
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

    @NonNull
    @NotNull
    @Column(nullable = false, length = 100, unique = true)
    @Email(message = "O email fornecido está incorreto")
    @Schema(description = "Email do usuario", example = "exemplo@exemplo.com")
    private String email;

    @NonNull
    @NotNull
    @Column(nullable = false, length = 255)
    @Size(max = 255, message = "O tamanho da senha do usuário não pode ter mais de 255 caracteres")
    @Schema(description = "Senha do usuario", example = "1234")
    private String senha;

    @NonNull
    @Column(nullable = false)
    @CreationTimestamp
    @Schema(description = "Data do cadastro do usuario", example = "2024-12-09T12:00:00")
    private LocalDateTime data_cadastro;

    @NonNull
    @NotNull
    @Column(nullable = false)
    @Size(max = 20, message = "O tamanho da permissão do usuário não pode ter mais de 20 caracteres")
    @Schema(description = "Permissão do administrador", example = "ROLE_ADMIN")
    private String permissao;

    public Administrador(Administrador administrador) {
        this.id_administrador = id_administrador;
        this.uuid = uuid;
        this.nome_administrador = nome_administrador;
        this.email = email;
        this.senha = senha;
        this.data_cadastro = data_cadastro;
        this.permissao = permissao;
    }
}
