package com.mycompany.lasttest.repositery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mycompany.lasttest.bean.Article;

public interface ArticleRepositery extends JpaRepository<Article, Long>{

	
	
}
