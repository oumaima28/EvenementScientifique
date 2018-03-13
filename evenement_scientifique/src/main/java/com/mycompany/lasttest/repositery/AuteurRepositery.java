package com.mycompany.lasttest.repositery;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mycompany.lasttest.bean.Auteur;
import com.mycompany.lasttest.custom.AuteurRepositeryCustom;
import java.util.List;

public interface AuteurRepositery extends JpaRepository<Auteur, String>, AuteurRepositeryCustom{

    Auteur findByEmail(String email);
}
