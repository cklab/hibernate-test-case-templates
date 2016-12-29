/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.envers.bugs;

import entities.Book;
import entities.PageReview;
import entities.Page;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;
import org.hibernate.envers.AuditReader;
import org.junit.Test;
import static org.junit.Assert.*;

import javax.persistence.EntityManager;
import java.util.LinkedList;
import java.util.List;

/**
 * This template demonstrates how to develop a test case for Hibernate Envers, using
 * its built-in unit test framework.
 */
public class EnversUnitTestCase extends AbstractEnversTestCase {

	// Add your entities here.
	@Override
	protected Class[] getAnnotatedClasses() {
		return new Class[] {
				PageReview.class,
				Page.class,
				Book.class
		};
	}

	// If you use *.hbm.xml mappings, instead of annotations, add the mappings here.
	@Override
	protected String[] getMappings() {
		return new String[] {
//				"Foo.hbm.xml",
//				"Bar.hbm.xml"
		};
	}
	// If those mappings reside somewhere other than resources/org/hibernate/test, change this.
	@Override
	protected String getBaseForMappings() {
		return "org/hibernate/test/";
	}

	// Add in any settings that are specific to your test.  See resources/hibernate.properties for the defaults.
	@Override
	protected void configure(Configuration configuration) {
		super.configure( configuration );

		configuration.setProperty( AvailableSettings.SHOW_SQL, Boolean.TRUE.toString() );
		configuration.setProperty( AvailableSettings.FORMAT_SQL, Boolean.TRUE.toString() );
		//configuration.setProperty( AvailableSettings.GENERATE_STATISTICS, "true" );
	}

	@Test
	public void testHHH11364WithoutPage() throws Exception {
		AuditReader reader = getAuditReader();

		List<PageReview> pageReviews = new LinkedList<>();
		pageReviews.add(new PageReview("comment without a page"));

		Book book = new Book();
		book.setTornPages(pageReviews);


		book = save(book);
		Book auditedBook = reader.find(Book.class, book.getId(), 1);

		assertNotNull(auditedBook);
		assertEquals("I should have a PageReview but it's missing!", 1, auditedBook.getTornPages().size());

	}

	@Test
	public void testHHH11364WithPage() throws Exception {
		AuditReader reader = getAuditReader();

		Page page = new Page();
		page.setText("ABC");

		// creates REV 1
		page = save(page);

		List<PageReview> pageReviews = new LinkedList<>();
		pageReviews.add(new PageReview("comment with a page", page));

		Book book = new Book();
		book.setTornPages(pageReviews);

		// creates REV 2
		book = save(book);
		Book auditedBook = reader.find(Book.class, book.getId(), 2);

		assertNotNull(auditedBook);
		assertEquals("I should have a PageReview but it's missing!", 1, auditedBook.getTornPages().size());
	}

	private <T> T save(T entity) {
		EntityManager em = openSession().getEntityManagerFactory().createEntityManager();

		em.getTransaction().begin();
		entity = em.merge(entity);
		em.flush();
		em.getTransaction().commit();
		em.close();
		return entity;
	}
}
