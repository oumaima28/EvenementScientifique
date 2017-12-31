package com.mycompany.lasttest.custom;

import com.mycompany.lasttest.bean.Inscrit;

public interface InscritRepositeryCustom {

	void customTest(Inscrit inscrit);
        Boolean confirmEmail(String email, String emailConfirmation);
        int sendEmail(Inscrit inscrit, String password);
        Boolean checkValidityOfEmail(String email);
}
