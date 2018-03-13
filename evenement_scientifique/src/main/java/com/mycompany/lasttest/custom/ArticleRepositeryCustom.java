package com.mycompany.lasttest.custom;

import com.mycompany.lasttest.bean.Article;
import com.mycompany.lasttest.bean.Auteur;
import com.mycompany.lasttest.bean.Event;
import com.mycompany.lasttest.bean.Inscrit;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.Query;

public interface ArticleRepositeryCustom {

    List<Article> rechercher(String titre, String description, String etat, Date dateMin, Date dateMax, Long eventId, List<String> tags);

    void accorder(Event event, List<Article> articles, List<Inscrit> rapporteurs);

    List<Article> findByIds(List<Long> ids);
    
    List<Article> findByPaiement(int payed, List<Article> articles);
    
    void saveArticle(Article article, List<Auteur> auteurs);
    
}
