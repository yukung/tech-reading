package jp.jbug.example.seam.action;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import jp.jbug.example.seam.model.BlogEntry;

import org.jboss.seam.annotations.Destroy;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.annotations.datamodel.DataModelSelection;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.log.Log;

@Stateful
@Name("blogListAction")
public class BlogListAction implements Serializable, IBlogList {

	private static final long serialVersionUID = 1L;

	@Logger
	private Log log;

	@PersistenceContext(type = PersistenceContextType.EXTENDED)
	private EntityManager em;

	@DataModel(value = "blogEntries")
	private List<BlogEntry> blogEntries;

	@DataModelSelection
	@Out(required = false)
	private BlogEntry blogEntry;

	@Factory
	@Observer("blogUpdated")
	@Override
	public String getBlogEntries() {
		blogEntries = em.createQuery(
				"select b from BlogEntry b order by b.blogDate DESC")
				.getResultList();
		return "/BlogEntryList.xhtml";
	}

	@Override
	public String deleteBlogEntry() {
		BlogEntry cancelBlogEntry = em.find(BlogEntry.class,
				blogEntry.getBlogEntryId());
		if (cancelBlogEntry != null) {
			em.remove(cancelBlogEntry);
		}
		getBlogEntries();
		FacesMessages.instance().add("削除しました");
		log.info("ブログエントリを削除しました。");
		return "/BlogEntryList.xhtml";
	}

	@Remove
	@Destroy
	@Override
	public void destroy() {
		log.info(this + " は破棄されました。");
	}

}