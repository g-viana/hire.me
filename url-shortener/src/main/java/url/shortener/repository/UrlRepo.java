package url.shortener.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import url.shortener.model.Url;

@Repository
public interface UrlRepo extends JpaRepository<Url, Long>{

	public Url findByAlias(String alias);
}
