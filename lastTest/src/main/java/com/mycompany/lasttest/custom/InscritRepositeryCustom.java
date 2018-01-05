package com.mycompany.lasttest.custom;

import com.mycompany.lasttest.bean.Inscrit;
import java.util.List;

public interface InscritRepositeryCustom {

    Boolean confirmEmail(String email, String emailConfirmation);

    int sendEmail(Inscrit inscrit, String password);

    Boolean checkValidityOfEmail(String email);

    List<Inscrit> findByEmails(List<String> emails);

    Object[] seConnecter(String login, String pswrd);

    Inscrit cloneInscrit(Inscrit inscrit);
}
