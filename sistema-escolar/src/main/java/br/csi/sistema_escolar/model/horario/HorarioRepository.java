package br.csi.sistema_escolar.model.horario;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface HorarioRepository extends JpaRepository<Horario, Long> {
    public Horario findHorarioByUuid(UUID uuid);
    public void deleteHorarioByUuid(UUID uuid);
}
