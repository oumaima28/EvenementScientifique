package com.mycompany.lasttest.controller;

import java.util.ArrayList;
import java.util.List;

import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.mycompany.lasttest.bean.Revision;
import com.mycompany.lasttest.repositery.RevisionRepositery;

@Scope(value = "session")
@Component(value = "revisionController")
@ELBeanName(value = "revisionController")
public class RevisionController {

	private Revision revision;
	private List<Revision> revisions;
	
	@Autowired
	private RevisionRepositery revisionRepositery;

	public Revision getRevision() {
		if(revision == null) {
			revision = new Revision();
		}
		return revision;
	}

	public void setRevision(Revision revision) {
		this.revision = revision;
	}

	public List<Revision> getRevisions() {
		if(revisions == null) {
			revisions = new ArrayList<>();
		}
		return revisions;
	}

	public void setRevisions(List<Revision> revisions) {
		this.revisions = revisions;
	}
	
}
