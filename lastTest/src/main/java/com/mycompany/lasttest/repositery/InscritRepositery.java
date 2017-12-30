package com.mycompany.lasttest.repositery;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mycompany.lasttest.bean.Inscrit;
import com.mycompany.lasttest.custom.InscritRepositeryCustom;

public interface InscritRepositery extends JpaRepository<Inscrit, String>, InscritRepositeryCustom{

	Inscrit findByEmail(String email);
	
	@Query("SELECT i FROM #{#entityName} i WHERE i.bloqued = ?1")
	List<Inscrit> findByBloqued(int bloqued);
}
