package com.mycompany.lasttest.repositery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mycompany.lasttest.bean.Article;
import com.mycompany.lasttest.bean.Event;
import com.mycompany.lasttest.bean.Inscrit;
import com.mycompany.lasttest.custom.ArticleRepositeryCustom;
import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

public interface ArticleRepositery extends JpaRepository<Article, Long>, ArticleRepositeryCustom {

    Article findByInscritAndEvent(Inscrit inscrit, Event event);

    List<Article> findByEvent(Event event);

    List<Article> findByInscrit(Inscrit inscrit);

    @Modifying
    @Query(value = "delete from article_auteurs where article_id = ?1", nativeQuery = true)
    @Transactional
    public void deleteArticle_Auteurs(Long articleId);

    @Modifying
    @Query(value = "delete from auteur_articles where article_id = ?1", nativeQuery = true)
    @Transactional
    public void deleteAuteur_Articles(Long articleId);

    public List<Article> findByTitre(String titre);

    public List<Article> findByDescription(String description);

    @Modifying
    @Query(value = "insert into role_articles (role_id,articles_id) VALUES (:role,:article)", nativeQuery = true)
    @Transactional
    public void saveInRoleArticles(@Param("role") Long roleId, @Param("article") Long articleId);

    @Modifying
    @Query(value = "insert into article_roles (article_id,roles_id) VALUES (:article,:role)", nativeQuery = true)
    @Transactional
    public void saveInArticleRoles(@Param("role") Long roleId, @Param("article") Long articleId);

    @Query(value = "select e.articles_id from role_articles e where e.role_id= ?1", nativeQuery = true)
    public List<Long> findArticlesAjuger(Long roleId);

    @Query("SELECT a FROM #{#entityName} a WHERE a.event.id = ?1 and a.inscrit.login = ?2")
    public Article findByEventAndInscrit(Long eventId, String login);

    @Modifying
    @Query(value = "insert into article_emails (article_id,emails) VALUES (:id,:email)", nativeQuery = true)
    @Transactional
    public void saveEmails(@Param("id") Long articleId, @Param("email") String email);

    @Modifying
    @Query(value = "insert into article_auteurs (article_id,auteurs_cin) VALUES (:articleId,:auteurId)", nativeQuery = true)
    @Transactional
    public void saveAutr_Article(@Param("articleId") Long articleId, @Param("auteurId") String cin);

    Article findByEventAndInscrit(Event event, Inscrit inscrit);
    
    @Query(value = "select e.emails from article_emails e where e.article_id = ?1", nativeQuery = true)
    public List<String> findEmailsByArticle(Long articleId);
}
