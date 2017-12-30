package com.mycompany.lasttest.repositery;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mycompany.lasttest.bean.Paiement;

public interface PaiementRepositery extends JpaRepository<Paiement, Long>{

}
