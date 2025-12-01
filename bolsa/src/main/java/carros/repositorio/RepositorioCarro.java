package carros.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import carros.modelo.*;

@NoRepositoryBean
public interface RepositorioCarro extends JpaRepository<Carro, Long>{

	Optional<Carro> findByUsuarioId(Long usuarioId);
}
