package url.shortener.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import url.shortener.model.Url;

@Repository
public interface UrlRepo extends JpaRepository<Url, Long>{

	public Url findByAlias(String alias);
	
	@Modifying
	@Query("update Url u set u.numberOfUses = u.numberOfUses +1 where id = ?1")
	public void addUse(Long id);
	
	@Query(value = "select * from url order by number_of_uses desc limit 10", nativeQuery = true)
	public List<Url> mostAccessed();
}
