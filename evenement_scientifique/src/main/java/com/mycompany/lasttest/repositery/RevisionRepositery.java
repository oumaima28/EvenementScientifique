package com.mycompany.lasttest.repositery;

import com.mycompany.lasttest.bean.Article;
import com.mycompany.lasttest.bean.Event;
import com.mycompany.lasttest.bean.Inscrit;
import org.springframework.data.jpa.repository.JpaRepository;

import com.mycompany.lasttest.bean.Revision;
import com.mycompany.lasttest.custom.RevisionRepositeryCustom;
import java.util.List;

public interface RevisionRepositery extends JpaRepository<Revision, Long>, RevisionRepositeryCustom {

    List<Revision> findByArticle(Article article);

    List<Revision> findByArticleAndRapporteur(Article article, Inscrit rapporteur);
    
    Revision findByRapporteurAndArticle(Inscrit rapporteur, Article article);

}
