package url.shortener.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import url.shortener.model.Url;

@Repository
public interface UrlRepo extends JpaRepository<Url, Long>{

	@Query("from Url where custom is true and shortUrl = ?1")
	public Url findWithCustomAlias(String customAlias);
}
