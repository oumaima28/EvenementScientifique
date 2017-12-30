package com.mycompany.lasttest.implementationCustom;

import com.mycompany.lasttest.bean.Inscrit;
import com.mycompany.lasttest.custom.InscritRepositeryCustom;

public class InscritRepositeryImpl implements InscritRepositeryCustom{

	@Override
	public void customTest(Inscrit inscrit) {
		System.out.println("ha l'inscrit: "+inscrit);
		
	}

}
