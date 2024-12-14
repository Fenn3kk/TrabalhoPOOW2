package br.csi.sistema_escolar.service;

import br.csi.sistema_escolar.model.administrador.Administrador;
import br.csi.sistema_escolar.model.administrador.AdministradorRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoService implements UserDetailsService {

    private final AdministradorRepository repository;

    public AutenticacaoService(AdministradorRepository repository){
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Administrador administrador = this.repository.findAdministradorByEmail(email);
        if(administrador == null) {
            throw new UsernameNotFoundException("Usuário ou senha incorretos");
        } else {
            String permissao = administrador.getPermissao();

            UserDetails user = User.withUsername(administrador.getEmail())
                    .password(administrador.getSenha())
                    .authorities(new SimpleGrantedAuthority(permissao))
                    .build();
            return user;
        }
    }

}

