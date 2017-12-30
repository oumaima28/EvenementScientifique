package com.mycompany.lasttest.repositery;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mycompany.lasttest.bean.Revision;

public interface RevisionRepositery extends JpaRepository<Revision, Long>{

}
