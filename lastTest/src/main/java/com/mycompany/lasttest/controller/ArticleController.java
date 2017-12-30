package com.mycompany.lasttest.controller;

import java.util.ArrayList;
import java.util.List;

import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.mycompany.lasttest.bean.Article;
import com.mycompany.lasttest.repositery.ArticleRepositery;

@Scope(value = "session")
@Component(value = "articleController")
@ELBeanName(value = "articleController")
public class ArticleController {

	private Article article;
	private List<Article> articles;
	
	@Autowired
	private ArticleRepositery articleRepositery;

	
	public Article getArticle() {
		if(article == null) {
			article = new Article();
		}
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public List<Article> getArticles() {
		if(articles == null) {
			articles = new ArrayList<>();
		}
		return articles;
	}

	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}
	
	
}
