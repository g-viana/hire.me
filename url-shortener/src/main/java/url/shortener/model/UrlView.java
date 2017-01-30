package url.shortener.model;

/** Using Jackson's JsonView to make sure that the most accessed urls only show relevant information, instead
 * of everything*/
public class UrlView {

	public interface MostAccessed {}
	public interface Simple {}
	public interface UrlAndStatistics extends Simple {}
}
