package br.csi.sistema_escolar.service;

import br.csi.sistema_escolar.model.administrador.DadosAdministrador;
import br.csi.sistema_escolar.model.administrador.Administrador;
import br.csi.sistema_escolar.model.administrador.AdministradorRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AdministradorService {

    private final AdministradorRepository repository;

    public AdministradorService(AdministradorRepository repository) {
        this.repository = repository;
    }

    public void salvar(Administrador administrador) {
        administrador.setSenha(new BCryptPasswordEncoder().encode(administrador.getSenha()));
        this.repository.save(administrador);
    }

    public List<Administrador> listar() {
        return this.repository.findAll();
    }

    public Optional<Administrador> getAdministradorUUID(String uuid) {
        UUID uuidFormatado = UUID.fromString(uuid);
        return Optional.ofNullable(this.repository.findAdministradorByUuid(uuidFormatado));
    }

    public void atualizarUUID(Administrador administrador) {
        Optional<Administrador> optionalAdministrador = Optional.ofNullable(this.repository.findAdministradorByUuid(administrador.getUuid()));
        if (optionalAdministrador.isPresent()) {
            Administrador a = optionalAdministrador.get();
            System.out.println(a.getData_cadastro());
            System.out.println(a.getNome_administrador());
            a.setNome_administrador(administrador.getNome_administrador());
            a.setId_administrador(Long.valueOf(administrador.getEmail()));
            a.setSenha(new BCryptPasswordEncoder().encode(administrador.getSenha()));
            this.repository.save(a);
        } else {
            throw new RuntimeException("Administrador não encontrado");
        }
    }

    public void deletarUUID(String uuid) {
        this.repository.deleteAdministradorByUuid(UUID.fromString(uuid));
    }

    public DadosAdministrador findAdministrador(Long id){
        Administrador administrador = this.repository.getReferenceById(id);
        return new DadosAdministrador(administrador);
    }

    public List<DadosAdministrador> findAllAdministradores(){
        return this.repository.findAll().stream().map(DadosAdministrador::new).toList();
    }
}
