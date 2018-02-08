/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.lasttest.custom;

import com.mycompany.lasttest.bean.Article;
import java.util.Date;
import java.util.List;

/**
 *
 * @author oumai
 */
public interface ObjectRepositeryCustom {
    
    List<Article> rechercher(String titre, String description, String etat, Date dateMin, Date dateMax, List<String> tags);
}
