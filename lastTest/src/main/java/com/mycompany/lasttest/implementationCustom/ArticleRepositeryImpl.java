package com.mycompany.lasttest.implementationCustom;

import com.mycompany.lasttest.bean.Article;
import com.mycompany.lasttest.bean.Auteur;
import com.mycompany.lasttest.bean.Event;
import com.mycompany.lasttest.bean.Inscrit;
import com.mycompany.lasttest.bean.Paiement;
import com.mycompany.lasttest.bean.Role;
import com.mycompany.lasttest.custom.ArticleRepositeryCustom;
import com.mycompany.lasttest.repositery.ArticleRepositery;
import com.mycompany.lasttest.repositery.EventRepositery;
import com.mycompany.lasttest.repositery.InscritRepositery;
import com.mycompany.lasttest.repositery.PaiementRepositery;
import com.mycompany.lasttest.repositery.RoleRepositery;
import com.mycompany.lasttest.util.SearchUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;

public class ArticleRepositeryImpl implements ArticleRepositeryCustom {

    @Autowired
    private EventRepositery eventRepositery;
    @Autowired
    private RoleRepositery roleRepositery;
    @Autowired
    private ArticleRepositery articleRepositery;
    @Autowired
    private PaiementRepositery paiementRepositery;
    @Autowired
    private InscritRepositery inscritRepositery;

//    @Override
//    public List<Article> rechercher(String titre, String description, String etat, Date dateMin, Date dateMax, List<String> tags) {
////        LdapQuery query = query();
////        String base = "";
////        String filter = "";
////        SearchControls searchCtls = new SearchControls();
////        searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
////        searchCtls.setReturningAttributes(null);
////        Properties properties = new Properties();
////        DirContext dirContext = new InitialDirContext();
////        properties.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
////        properties.put(Context.PROVIDER_URL, "com.sun.jndi.ldap.LdapCtxFactory");
////        return ldapTemplate.search(base, searchCtls);
//        return null;
//    }
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Article> rechercher(String titre, String description, String etat, Date dateMin, Date dateMax, Long eventId, List<String> tags) {
        String query = "Select a From Article a WHERE a.event.id = '" + eventId + "'";
        int found = 0;
        if (titre != null && !titre.equals("")) {
            query += " AND a.titre = '" + titre + "'";
        }
        if (description != null && !description.equals("")) {
            query += " AND a.description = '" + description + "'";
        }
        if (etat != null && !etat.equals("--SELECT--")) {
            query += " AND a.etat = '" + etat + "'";
        }
        if (dateMin != null) {
            query += " AND a.dateEnvoie >= '" + SearchUtil.convertToSqlDate(dateMin) + "'";
        }
        if (dateMax != null) {
            query += " AND a.dateEnvoie <= '" + SearchUtil.convertToSqlDate(dateMax) + "'";
        }
        if (tags != null) {
            for (String tag : tags) {
                List<Long> eventIds = new ArrayList<>();
                eventIds.addAll(eventRepositery.rechercherEventForTag(tag));
                if (eventIds != null) {
                    System.out.println("ha les ids" + eventIds);
                    System.out.println("ha l id tl event" + eventId);
                    if (eventIds.size() == 1) {
                        query += " AND a.event.id = '" + eventIds.get(0) + "'";
                    } else {
                        query += " AND";
                        for (int i = 0; i < eventIds.size() - 1; i++) {
                            query += " a.event.id = '" + eventIds.get(i) + "' OR";
                        }
                        query += " a.event.id = '" + eventIds.get(eventIds.size() - 1) + "'";
                    }
                }
//            if (found == 1) {
//                System.out.println("dkhl lfound");
//                query += " AND a.event.id + '" + eventId + "";
//            }
            }
        }
        System.out.println("ha lquery" + query);
        return em.createQuery(query).getResultList();
    }

    @Override
    public void accorder(Event event, List<Article> articles, List<Inscrit> rapporteurs) {
//        List <Role> roles = new ArrayList<>();
        System.out.println("3iw");
        for (Inscrit rapporteur : rapporteurs) {
            Role role = roleRepositery.findRapporteurByEventAndInscrit(event.getId(), rapporteur.getLogin());
            System.out.println("ha role" + role);
            for (Article article : articles) {
                articleRepositery.saveInArticleRoles(role.getId(), article.getId());
                articleRepositery.saveInRoleArticles(role.getId(), article.getId());
            }
//            roles.add(role);
        }
    }

    @Override
    public List<Article> findByIds(List<Long> ids) {
        String query = "Select a from Article a where ";
        for (int i = 0; i < ids.size() - 1; i++) {
            query += "a.id = '" + ids.get(i) + "' or ";
        }
        query += "a.id = '" + ids.get(ids.size() - 1) + "'";
        return em.createQuery(query).getResultList();
    }

    @Override
    public List<Article> findByPaiement(int payed, List<Article> articlesR) {
        System.out.println("ha es articles" + articlesR);
        System.out.println("ha payed" + payed);
        if (payed != 2) {
            System.out.println("1");
            List<String> logins = new ArrayList<>();
            for (Article article : articlesR) {
                System.out.println("2");
                String log = paiementRepositery.findLoginByPaiementAndEventAndInscrit(payed, articlesR.get(0).getEvent().getId(), article.getInscrit().getLogin());
                System.out.println("login" + log);
                System.out.println("3");
                if (log != null) {
                    logins.add(log);
                }
                System.out.println("4");
            }
            System.out.println("5");
            System.out.println("logins" + logins);
            List<Article> articles = new ArrayList<>();
            for (String login : logins) {
                articles.add(articleRepositery.findByEventAndInscrit(articlesR.get(0).getEvent().getId(), login));
                System.out.println("3iw");
            }
            System.out.println("articles" + articles);
            return articles;
        }

        return articlesR;
    }

    @Override
    public void saveArticle(Article article, List<Auteur> auteurs) {
        Inscrit inscrit = inscritRepositery.connectedInscrit();
        System.out.println("3");
        if (inscrit != null) {
            article.setInscrit(inscrit);
        }
        article.setEtat("Waiting");
        article.setDateEnvoie(new Date());
        Paiement paiement = new Paiement();
        paiement.setEvent(article.getEvent());
        paiement.setInscrit(inscrit);
        paiement.setPayed(0);
        System.out.println("4");
        paiementRepositery.save(paiement);
        System.out.println("5");
        articleRepositery.save(article);
        System.out.println("6");
        List<Article> loadedArticles = articleRepositery.findByTitre(article.getTitre());
        System.out.println("7");
        if (auteurs != null) {
            for (Auteur auteur : auteurs) {
                articleRepositery.saveEmails(loadedArticles.get(0).getId(), auteur.getEmail());
                articleRepositery.saveAutr_Article(loadedArticles.get(0).getId(), auteur.getCin());
            }
        }
        articleRepositery.saveEmails(loadedArticles.get(0).getId(), inscritRepositery.connectedInscrit().getEmail());
        articleRepositery.saveAutr_Article(loadedArticles.get(0).getId(), inscritRepositery.connectedInscrit().getCin());
        System.out.println("8");
        System.out.println("9");
    }

}
