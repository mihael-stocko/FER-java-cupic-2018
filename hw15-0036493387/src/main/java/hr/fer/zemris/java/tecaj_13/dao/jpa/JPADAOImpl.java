package hr.fer.zemris.java.tecaj_13.dao.jpa;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import hr.fer.zemris.java.tecaj_13.dao.DAO;
import hr.fer.zemris.java.tecaj_13.dao.DAOException;
import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;
import hr.fer.zemris.java.tecaj_13.web.servlets.Util;

/**
 * This is a concrete implementation of the DAO interface. It uses JPA for communication
 * with the database.
 * 
 * @author Mihael Stočko
 *
 */
public class JPADAOImpl implements DAO {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BlogEntry getBlogEntry(Long id) throws DAOException {
		BlogEntry blogEntry = JPAEMProvider.getEntityManager().find(BlogEntry.class, id);
		return blogEntry;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public BlogUser getBlogUser(String nick) throws DAOException {
		List<BlogUser> user = (List<BlogUser>)JPAEMProvider.getEntityManager()
				.createQuery("select u from BlogUser as u where u.nick=:n")
				.setParameter("n", nick).getResultList();
		
		if(user.size() == 0) {
			return null;
		} else if(user.size() == 1) {
			return user.get(0);
		}
		
		throw new DAOException("More than one user with the same nick exist.");
	}
	
	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BlogEntry> getBlogEntries(String nick) throws DAOException {
		List<BlogEntry> entries = (List<BlogEntry>)JPAEMProvider.getEntityManager()
				.createQuery("select e from BlogEntry as e where e.creator.nick=:nick")
				.setParameter("nick", nick).getResultList();
		return entries;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BlogUser> getAuthors() throws DAOException {
		List<BlogUser> users = (List<BlogUser>)JPAEMProvider.getEntityManager()
				.createQuery("select u from BlogUser as u").getResultList();
		
		return users;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addComment(BlogEntry blogEntry, String email, String message) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		
		BlogComment blogComment = new BlogComment();
		blogComment.setUsersEMail(email);
		blogComment.setPostedOn(new Date());
		blogComment.setMessage(message);
		blogComment.setBlogEntry(blogEntry);
		
		List<BlogComment> comments = blogEntry.getComments();
		comments.add(blogComment);
		em.persist(blogComment);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addEntry(BlogUser user, String title, String content) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		
		BlogEntry blogentry = new BlogEntry();
		blogentry.setTitle(title);
		blogentry.setText(content);
		blogentry.setLastModifiedAt(new Date());
		blogentry.setCreatedAt(new Date());
		blogentry.setComments(new ArrayList<>());
		blogentry.setCreator(user);
		
		List<BlogEntry> entries = user.getEntries();
		entries.add(blogentry);
		
		em.persist(blogentry);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateEntry(Long id, String title, String content) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		
		em.createQuery("update BlogEntry set title = :title, text = :text where id = :id")
			.setParameter("title", title).setParameter("text", content).setParameter("id", id).executeUpdate();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addUser(String firstName, String lastName, String email, String nick, String password)
			throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		
		BlogUser blogUser = new BlogUser();
		blogUser.setFirstName(firstName);
		blogUser.setLastName(lastName);
		blogUser.setEmail(email);
		blogUser.setNick(nick);
		blogUser.setPasswordHash(Util.getDigest(password));
		
		em.persist(blogUser);
	}
}