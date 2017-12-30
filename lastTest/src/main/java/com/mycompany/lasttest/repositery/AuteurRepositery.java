package com.mycompany.lasttest.repositery;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mycompany.lasttest.bean.Auteur;

public interface AuteurRepositery extends JpaRepository<Auteur, String>{

}
