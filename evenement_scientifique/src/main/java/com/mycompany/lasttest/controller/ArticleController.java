package com.mycompany.lasttest.controller;

import java.util.ArrayList;
import java.util.List;

import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.mycompany.lasttest.bean.Article;
import com.mycompany.lasttest.bean.Auteur;
import com.mycompany.lasttest.bean.Event;
import com.mycompany.lasttest.bean.Inscrit;
import com.mycompany.lasttest.bean.Revision;
import com.mycompany.lasttest.bean.Role;
import com.mycompany.lasttest.repositery.ArticleRepositery;
import com.mycompany.lasttest.repositery.AuteurRepositery;
import com.mycompany.lasttest.repositery.EventRepositery;
import com.mycompany.lasttest.repositery.InscritRepositery;
import com.mycompany.lasttest.repositery.PaiementRepositery;
import com.mycompany.lasttest.repositery.RevisionRepositery;
import com.mycompany.lasttest.repositery.RoleRepositery;
import com.mycompany.lasttest.util.JsfUtil;
import com.mycompany.lasttest.util.ServerConfigUtil;
import com.mycompany.lasttest.util.SessionUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.StringJoiner;
import java.util.Vector;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

@Scope(value = "session")
@Component(value = "articleController")
@ELBeanName(value = "articleController")
public class ArticleController {

    private Article article;
    private Article selectedArticle;
    private List<Article> articles;

    private List<Revision> revisions;
    private List<Article> articlesOfEvent;
    private List<Revision> revisionOfArticle;

    //Attribut recherche article
    private String titreArticleRecherche;
    private String descriptionArticleRecherche;
    private String etatArticleRecherche;
    private Date dateEnvoieArticleMin;
    private Date dateEnvoieArticleMax;
    public List<String> availableTags;
    public List<String> choosedTags;
    public int paiementRecherchee;

    //AccorderList View
    private List<Article> articlesOfAccorderView;
    private List<Article> articlesToAccordToRapporteur;
    private List<Vector> selected;
    private List<Vector> selectedRapporteurs;
    private List<Inscrit> rapporteurs;
    private List<Inscrit> rapporteursToAccordToArticles;
    private String stylesheet = "";
    //Paiement
    private String etatPaiement;
    private Event selectedEvent;
    //Juger article
    private List<Article> articlesAjuger;
    private Article articleAjuger;
    private Revision revisionAJuger;

    //Send article view
    private Auteur auteur;
    private Article selectedA = new Article();
    private Auteur auteur1;
    private UploadedFile uploadedFile;
    protected List<Auteur> auteurs = new ArrayList<Auteur>();
    protected List<String> emails = new ArrayList<String>();
    protected List<String> cins = new ArrayList<String>();
    private boolean save = false;
    private boolean edit = true;
    private String cin;
    private String nom;
    private String prenom;
    private String description;
    private String email;
    private String titre;
    private String fileName;
    private String articlDescription;
    private StreamedContent myArticle;
    private DefaultStreamedContent download;

    @Autowired
    private ArticleRepositery articleRepositery;
    @Autowired
    private RevisionRepositery revisionRepositery;
    @Autowired
    private InscritRepositery inscritRepositery;
    @Autowired
    private RoleRepositery roleRepositery;
    @Autowired
    private PaiementRepositery paiementRepositery;
    @Autowired
    private AuteurRepositery auteurRepositery;
    @Autowired
    private EventRepositery eventRepositery;

    public void addDatatoForm(Auteur selectedAuteur) {
        if (selectedAuteur != null) {
            nom = selectedAuteur.getNom();
            prenom = selectedAuteur.getPrenom();
            cin = selectedAuteur.getCin();
            email = selectedAuteur.getEmail();
            description = selectedAuteur.getDescription();
        }
    }

    public String distinctValue() {
        if (emails.contains(email)) {
            JsfUtil.addErrorMessage("Cet email existe deja ! ", "email");
        }
        if (cins.contains(cin)) {
            JsfUtil.addErrorMessage("Ce CIN existe deja ", "cin");
        }
        return null;
    }

    public void preparAuteur() {
        intanceAuteur();
        auteur.setNom(nom);
        auteur.setCin(cin);
        auteur.setPrenom(prenom);
        auteur.setDescription(description);
        auteur.setEmail(email);
    }

    public void addToTable() {
        preparAuteur();
        List<Auteur> allAuteurs = auteurRepositery.findAll();
        if (cins.contains(cin)) {
            JsfUtil.addErrorMessage("Ce CIN existe deja pour un autre auteur ", "cin");
        } else if (emails.contains(email)) {
            JsfUtil.addErrorMessage("Cet email existe deja pour un autre auteur ! ", "email");
        } else if (!allAuteurs.contains(auteur)) {
            emails.add(email);
            auteurs.add(auteur);
            cins.add(cin);
        } else {
            emails.add(email);
        }
    }

    public void detailAuteur(Auteur thisAuteur) {
        intanceAuteur();
        auteur1 = new Auteur();
        auteurRepositery.cloneAuteur(thisAuteur, auteur);
        auteurRepositery.cloneAuteur(thisAuteur, auteur1);
        addDatatoForm(thisAuteur);
        edit = false;
        save = true;
    }

    public void intanceAuteur() {
        auteur = new Auteur();
    }

    public void editAuteur() {
        emails.remove(auteur1.getEmail());
        auteurs.remove(auteur1);
        preparAuteur();
        emails.add(auteur.getEmail());
        auteurs.add(auteur);
        save = false;
        edit = true;
    }

    public void deleteAuteur(Auteur thisAuteur) {
        auteurs.remove(thisAuteur);
    }

    public void upload() {
//        if (uploadedFile != null) {
//            String fileName = uploadedFile.getFileName();
//            FacesMessage message = new FacesMessage("Succesful", fileName + " is uploaded.");
//            FacesContext.getCurrentInstance().addMessage(null, message);
//            selected.setNameOfFile(fileName);
//            ServerConfigUtil.upload(uploadedFile);
//        }
//        else {
//            System.out.println("No file !! ");
//        }
    }

    public void save() {
        System.out.println("dkhl");
        if (uploadedFile != null) {
            String fileName = uploadedFile.getFileName();
            FacesMessage message = new FacesMessage("Succesful", fileName + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
//        selectedA.setNameOfFile(fileName);
            ServerConfigUtil.upload(uploadedFile);
        }
        System.out.println("1");
        System.out.println(titre);
        Event event = eventRepositery.findOne(((Event) SessionUtil.getAttribute("event")).getId());
        if (articleRepositery.findByEventAndInscrit(event, inscritRepositery.connectedInscrit()) != null) {
            JsfUtil.addErrorMessage("Vous avez deja envoyé votre article pour participer dans cet evenement, vous ne pouvez envoyé un article qu'une seule fois");
        } else {
            System.out.println("ha levent" + event);
            roleRepositery.createRoleForInscrit(event);
            System.out.println("1");
            auteurRepositery.saveInscrit(inscritRepositery.connectedInscrit());
            if (auteurs != null) {
                for (Auteur myAuteur : auteurs) {
                    auteurRepositery.save(myAuteur);
                }
            }
            selectedA.setTitre(titre);
            selectedA.setEvent(event);
            selectedA.setDescription(articlDescription);
            System.out.println("2");
            articleRepositery.saveArticle(selectedA, auteurs);
            System.out.println("khrj");
            JsfUtil.addSuccessMessage("Vous avez bien envoyé votre article pour participer dans cet evenement veuillez attendre une réponse de confirmation ");
            auteurs = new ArrayList<>();
            auteur = new Auteur();
            titre = null;
            articlDescription = null;
        }
    }

    public void downloadFile(Article article) throws FileNotFoundException {
        System.out.println("titre" + article.getTitre());
        String chemin = "C:\\Users\\oumai\\git\\local_Evenement_Scientifique_Repositery\\lastTest\\src\\main\\webapp\\resources\\articles\\" + article.getTitre() + ".pdf";
        System.out.println(chemin);
        File file = new File(chemin);
        InputStream input = new FileInputStream(file);
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        setDownload(new DefaultStreamedContent(input, externalContext.getMimeType(file.getName()), file.getName()));
        System.out.println("PREP = " + download.getName());
    }

    public void juger(Article article) {
        if (article == null) {
            articleAjuger = new Article();
        }
        articleAjuger = article;
    }

    public void saveRevision() {
        Revision loadedRevision = revisionRepositery.findByRapporteurAndArticle(inscritRepositery.findOne(((Inscrit) SessionUtil.getAttribute("connected")).getLogin()), articleAjuger);
        if (loadedRevision != null) {
            revisionAJuger.setId(loadedRevision.getId());
            revisionRepositery.save(revisionAJuger);
        } else {
            if (revisionAJuger.getExperienceLevel() == 0) {
                JsfUtil.addErrorMessage("Veuillez choisir votre level d'experience dans le domaine", "ex");
            } else {
                revisionAJuger.setRapporteur(inscritRepositery.findOne(((Inscrit) SessionUtil.getAttribute("connected")).getLogin()));
                revisionAJuger.setArticle(articleAjuger);
                System.out.println("ha rev" + revisionAJuger);
                revisionRepositery.save(revisionAJuger);
            }
        }
    }

    public void listRev(Article selectedArticle) {
        Inscrit InscritConnected = ((Inscrit) SessionUtil.getAttribute("connected"));
        Inscrit loadedConnected = inscritRepositery.findOne(InscritConnected.getLogin());
        revisions = revisionRepositery.findByArticle(selectedArticle);
        System.out.println(revisions);
    }

    public void getPaiement(Article selectedArticle) {
        System.out.println("here");
        Inscrit InscritConnected = ((Inscrit) SessionUtil.getAttribute("connected"));
        Inscrit loadedConnected = inscritRepositery.findOne(InscritConnected.getLogin());
        System.out.println("1");
        int found = paiementRepositery.findPayedByInscritAndEvent(loadedConnected.getLogin(), selectedArticle.getEvent().getId());
        System.out.println("2");
        System.out.println("ha found" + found);
        if (selectedEvent == null) {
            selectedEvent = new Event();
        }
        selectedEvent = selectedArticle.getEvent();
        if (found == 1) {
            etatPaiement = "Payer";
        } else if (found == 0) {
            etatPaiement = "Non Payer";
        }
        System.out.println("ha l etat" + etatPaiement);
    }

    public void modifyEtatFromList() {
        System.err.println("1 : " + selectedArticle);
        articleRepositery.save(selectedArticle);
        if (!selectedArticle.getEtat().equals("Waiting")) {
            inscritRepositery.sendEmailOfEtatArticle(selectedArticle);
        }
        System.err.println("2 selected article");
    }

    public void showRevisionOfArticle(Article artcl) throws IOException {
        setRevisionOfArticle(revisionRepositery.findByArticle(artcl));
    }

    public void rechercher() {
        System.out.println("hani");
        System.out.println("ha titre" + titreArticleRecherche);
        Event selectedEvent = (Event) SessionUtil.getAttribute("selectedEventForArticls");
//        articlesOfEvent = articleRepositery.rechercher(titreArticleRecherche, descriptionArticleRecherche, etatArticleRecherche, dateEnvoieArticleMin,
//                dateEnvoieArticleMax, selectedEvent.getId(), choosedTags);
        articlesOfEvent = articleRepositery.findByPaiement(paiementRecherchee, articleRepositery.rechercher(titreArticleRecherche, descriptionArticleRecherche, etatArticleRecherche, dateEnvoieArticleMin,
                dateEnvoieArticleMax, selectedEvent.getId(), choosedTags));
        System.out.println("ha paiement" + paiementRecherchee);
        System.out.println("ha lista" + articlesOfEvent);
        System.out.println("khrj");
    }

    public void addTagsToList() {
        System.out.println("ha lista" + choosedTags);
    }

    public void deleteTagFromList(String tag) {
        choosedTags.remove(tag);
    }

    public String addArticleToAccordedList(Article article) {
        articlesToAccordToRapporteur.add(article);
        return "#026D62";
    }

    public void selectRow(Article article) {
        System.out.println("dkhl");
        if (article != null) {
            if (articlesToAccordToRapporteur == null) {
                articlesToAccordToRapporteur = new ArrayList<>();
            }
            System.out.println("1");
            for (Vector v : selected) {
                System.out.println("2");
                if (v.get(0).equals(article.getTitre())) {
                    System.out.println("3");
                    if (v.get(3).equals("0")) {
                        v.add(1, "#026D62");
                        v.add(2, "glyphicon glyphicon-ok");
                        System.out.println("4");
                        v.add(3, "1");
                        articlesToAccordToRapporteur.add(article);
                    } else {
                        v.add(1, "white");
                        v.add(2, "glyphicon glyphicon-plus");
                        System.out.println("4");
                        v.add(3, "0");
                        articlesToAccordToRapporteur.remove(article);
                    }
                }
            }
        }
        System.out.println(articlesToAccordToRapporteur);
    }

    public String getColor(String titre) {
        if (selected == null) {
            selected = new ArrayList<>();
            for (Article article1 : articlesOfAccorderView) {
                System.out.println("ha lista tles articles:" + articlesOfAccorderView);
                Vector vector = new Vector(4);
                System.out.println("1");
                vector.add(article1.getTitre());
                System.out.println("2");
                vector.add("white");
                vector.add("glyphicon glyphicon-plus");
                vector.add("0");
                System.out.println("3");
                selected.add(vector);
                System.out.println("4");

            }
        }
        for (int i = 0; i < selected.size(); i++) {
            Vector vector = selected.get(i);
            if (vector.get(0).equals(titre)) {
                System.out.println("ha lcolor" + (String) vector.get(1));
                return (String) vector.get(1);
            }
        }
        return "white";
    }

    public String getIcon(String titre) {
        for (int i = 0; i < selected.size(); i++) {
            Vector vector = selected.get(i);
            if (vector.get(0).equals(titre)) {
                System.out.println("ha licon" + (String) vector.get(2));
                return (String) vector.get(2);
            }
        }
        return "glyphicon glyphicon-plus";
    }

    public String getCssStylesheets() {
        for (int i = 0; i < articlesOfAccorderView.size() - 1; i++) {
            stylesheet += "background-color: white;,";
        }
        stylesheet += "background-color: white;";
        return stylesheet;
    }

    public void getRapporteursList() {
        System.out.println("hahowa dkhl");
        Event event = (Event) SessionUtil.getAttribute("selectedEventForAccorderArticles");
        System.out.println("ha eventId" + event.getId());
        rapporteurs = roleRepositery.findRapporteursByEventAndVerified(event.getId());
        System.out.println("ha lista trapporteurs" + rapporteurs);
    }

    public void selectRowRapporteurs(Inscrit inscrit) {
        System.out.println("dkhl");
        if (inscrit != null) {
            if (rapporteursToAccordToArticles == null) {
                rapporteursToAccordToArticles = new ArrayList<>();
            }
            System.out.println("1");
            for (Vector v : selectedRapporteurs) {
                System.out.println("2");
                if (v.get(0).equals(inscrit.getLogin())) {
                    if (v.get(3).equals("0")) {
                        v.add(1, "#026D62");
                        v.add(2, "glyphicon glyphicon-ok");
                        System.out.println("4");
                        v.add(3, "1");
                        rapporteursToAccordToArticles.add(inscrit);
                    } else {
                        v.add(1, "white");
                        v.add(2, "glyphicon glyphicon-plus");
                        System.out.println("4");
                        v.add(3, "0");
                        rapporteursToAccordToArticles.remove(inscrit);
                    }
                }
            }
        }
    }

    public String getColorRapporteurs(String login) {
        if (selectedRapporteurs == null) {
            selectedRapporteurs = new ArrayList<>();
            for (Inscrit inscrit : rapporteurs) {
                System.out.println("ha lista tles rapporteurs:" + rapporteurs);
                Vector vector = new Vector(4);
                System.out.println("1");
                vector.add(inscrit.getLogin());
                System.out.println("2");
                vector.add("white");
                vector.add("glyphicon glyphicon-plus");
                vector.add("0");
                System.out.println("3");
                selectedRapporteurs.add(vector);
                System.out.println("4");

            }
        }
        for (int i = 0; i < selectedRapporteurs.size(); i++) {
            Vector vector = selectedRapporteurs.get(i);
            if (vector.get(0).equals(login)) {
                System.out.println("ha lcolor" + (String) vector.get(1));
                return (String) vector.get(1);
            }
        }
        return "white";
    }

    public String getIconRapporteurs(String login) {
        for (int i = 0; i < selectedRapporteurs.size(); i++) {
            Vector vector = selectedRapporteurs.get(i);
            if (vector.get(0).equals(login)) {
                System.out.println("ha licon" + (String) vector.get(2));
                return (String) vector.get(2);
            }
        }
        return "glyphicon glyphicon-plus";
    }

    public void accorder() {
        if (articlesToAccordToRapporteur != null && rapporteursToAccordToArticles != null) {
            System.out.println("dkhl");
            System.out.println("articles" + articlesToAccordToRapporteur);
            System.out.println("raporteurs" + rapporteursToAccordToArticles);
            System.out.println("event" + (Event) SessionUtil.getAttribute("selectedEventForAccorderArticles"));
            articleRepositery.accorder((Event) SessionUtil.getAttribute("selectedEventForAccorderArticles"), articlesToAccordToRapporteur, rapporteursToAccordToArticles);
        }
    }

    public List<Revision> getRevisions() {
        if (revisions == null) {
            revisions = new ArrayList<Revision>();
        }
        return revisions;
    }

    public void setRevisions(List<Revision> revisions) {
        this.revisions = revisions;
    }

    public List<Article> getArticles() {
            Inscrit inscritConnected = ((Inscrit) SessionUtil.getAttribute("connected"));
            Inscrit loadedConnected = inscritRepositery.findOne(inscritConnected.getLogin());
            articles = articleRepositery.findByInscrit(loadedConnected);
        return articles;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public List<Article> getArticlesOfEvent() {
        if(articlesOfEvent == null){
            Event selectedEvent = (Event) SessionUtil.getAttribute("selectedEventForArticls");
            articlesOfEvent = articleRepositery.findByEvent(selectedEvent);
            System.out.println("get"+articlesOfEvent);
        }
//        else if(articlesOfEvent != articleRepositery.findByEvent((Event) SessionUtil.getAttribute("selectedEventForArticls"))){
//             Event selectedEvent = (Event) SessionUtil.getAttribute("selectedEventForArticls");
//            articlesOfEvent = articleRepositery.findByEvent(selectedEvent);
//        }
        return articlesOfEvent;

    }

    public void setArticlesOfEvent(List<Article> articlesOfEvent) {
        if (articlesOfEvent == null) {
            articlesOfEvent = new ArrayList<Article>();

        }

        this.articlesOfEvent = articlesOfEvent;
    }

    public List<Revision> getRevisionOfArticle() {
        return revisionOfArticle;
    }

    public void setRevisionOfArticle(List<Revision> revisionOfArticle) {
        this.revisionOfArticle = revisionOfArticle;
    }

    public Article getSelectedArticle() {
        return selectedArticle;
    }

    public void setSelectedArticle(Article selectedArticle) {
        this.selectedArticle = selectedArticle;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public String getTitreArticleRecherche() {
        return titreArticleRecherche;
    }

    public void setTitreArticleRecherche(String titreArticleRecherche) {
        this.titreArticleRecherche = titreArticleRecherche;
    }

    public String getDescriptionArticleRecherche() {
        return descriptionArticleRecherche;
    }

    public void setDescriptionArticleRecherche(String descriptionArticleRecherche) {
        this.descriptionArticleRecherche = descriptionArticleRecherche;
    }

    public String getEtatArticleRecherche() {
        return etatArticleRecherche;
    }

    public void setEtatArticleRecherche(String etatArticleRecherche) {
        this.etatArticleRecherche = etatArticleRecherche;
    }

    public Date getDateEnvoieArticleMin() {
        return dateEnvoieArticleMin;
    }

    public void setDateEnvoieArticleMin(Date dateEnvoieArticleMin) {
        this.dateEnvoieArticleMin = dateEnvoieArticleMin;
    }

    public Date getDateEnvoieArticleMax() {
        return dateEnvoieArticleMax;
    }

    public void setDateEnvoieArticleMax(Date dateEnvoieArticleMax) {
        this.dateEnvoieArticleMax = dateEnvoieArticleMax;
    }

    public List<String> getAvailableTags() {
        if (availableTags == null) {
            availableTags = new ArrayList<>();
            availableTags.add("BigData");
            availableTags.add("Social Engineering");
            availableTags.add("Electronics");
            availableTags.add("Physiques");
            availableTags.add("Mathematics");
            availableTags.add("Informatics");
            availableTags.add("Robotics");
            availableTags.add("Artificial Intelligence");
        }
        return availableTags;
    }

    public void setAvailableTags(List<String> availableTags) {
        this.availableTags = availableTags;
    }

    public List<String> getChoosedTags() {
        if (choosedTags == null) {
            choosedTags = new ArrayList<>();
        }
        return choosedTags;
    }

    public void setChoosedTags(List<String> choosedTags) {
        this.choosedTags = choosedTags;
    }

    public List<Article> getArticlesOfAccorderView() {
        if (articlesOfAccorderView == null) {
            articlesOfAccorderView = articleRepositery.findByEvent((Event) SessionUtil.getAttribute("selectedEventForAccorderArticles"));
        }
        return articlesOfAccorderView;
    }

    public void setArticlesOfAccorderView(List<Article> articlesOfAccorderView) {
        this.articlesOfAccorderView = articlesOfAccorderView;
    }

    public List<Article> getArticlesToAccordToRapporteur() {
        if (articlesToAccordToRapporteur == null) {
            articlesToAccordToRapporteur = new ArrayList<>();
        }
        return articlesToAccordToRapporteur;
    }

    public void setArticlesToAccordToRapporteur(List<Article> articlesToAccordToRapporteur) {
        this.articlesToAccordToRapporteur = articlesToAccordToRapporteur;
    }

    public List<Vector> getSelected() {
        if (selected == null) {
            selected = new ArrayList<>();
            for (Article article1 : articlesOfAccorderView) {
                Vector vector = new Vector(2);
                vector.set(0, article1);
                vector.set(1, "white");
                selected.add(vector);

            }
        }
        return selected;
    }

    public void setSelected(List<Vector> selected) {
        this.selected = selected;
    }

    public List<Inscrit> getRapporteurs() {
        if (rapporteurs == null) {
            rapporteurs = new ArrayList<>();
        }
        return rapporteurs;
    }

    public void setRapporteurs(List<Inscrit> rapporteurs) {
        this.rapporteurs = rapporteurs;
    }

    public String getEtatPaiement() {
        return etatPaiement;
    }

    public void setEtatPaiement(String etatPaiement) {
        this.etatPaiement = etatPaiement;
    }

    public Event getSelectedEvent() {
        return selectedEvent;
    }

    public void setSelectedEvent(Event selectedEvent) {
        this.selectedEvent = selectedEvent;
    }

    public List<Article> getArticlesAjuger() {
            articlesAjuger = new ArrayList<>();
            List<Long> ids = new ArrayList<>();
            for (Role role : roleRepositery.findRoleRapporteursByInscritAndVerified((((Inscrit) SessionUtil.getAttribute("connected")).getLogin()))) {
            ids.addAll(articleRepositery.findArticlesAjuger(role.getId()));
        }
            if (!ids.isEmpty()) {
                articlesAjuger.addAll(articleRepositery.findByIds(ids));
            }
        return articlesAjuger;
    }

    public void setArticlesAjuger(List<Article> articlesAjuger) {
        this.articlesAjuger = articlesAjuger;
    }

    public Article getArticleAjuger() {
        if (article == null) {
            articleAjuger = new Article();
        }
        return articleAjuger;
    }

    public void setArticleAjuger(Article articleAjuger) {
        this.articleAjuger = articleAjuger;
    }

    public Revision getRevisionAJuger() {
        if (revisionAJuger == null) {
            revisionAJuger = new Revision();
        }
        return revisionAJuger;
    }

    public void setRevisionAJuger(Revision revisionAJuger) {
        this.revisionAJuger = revisionAJuger;
    }

    public int getPaiementRecherchee() {
        return paiementRecherchee;
    }

    public void setPaiementRecherchee(int paiementRecherchee) {
        this.paiementRecherchee = paiementRecherchee;
    }

    public DefaultStreamedContent getDownload() {
        return download;
    }

    public void setDownload(DefaultStreamedContent download) {
        this.download = download;
    }

    public StreamedContent getMyArticle() {
        return myArticle;
    }

    public void setMyArticle(StreamedContent myArticle) {
        this.myArticle = myArticle;
    }

    public Article getArticle() {
        if (article == null) {
            article = new Article();
        }
        return article;
    }

    public List<String> getEmails() {
        if (emails == null) {
            emails = new ArrayList<>();
        }
        return emails;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }

    public List<String> getCins() {
        if (cins == null) {
            cins = new ArrayList<>();
        }
        return cins;
    }

    public void setCins(List<String> cins) {
        this.cins = cins;
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public ArticleRepositery getArticleRepositery() {
        return articleRepositery;
    }

    public void setArticleRepositery(ArticleRepositery articleRepositery) {
        this.articleRepositery = articleRepositery;
    }

    public Auteur getAuteur() {
        if (auteur == null) {
            auteur = new Auteur();
        }
        return auteur;
    }

    public void setAuteur(Auteur auteur) {
        this.auteur = auteur;
    }

    public Auteur getAuteur1() {
        if (auteur1 == null) {
            auteur1 = new Auteur();
        }
        return auteur1;
    }

    public void setAuteur1(Auteur auteur1) {
        this.auteur1 = auteur1;
    }

    public boolean isSave() {
        return save;
    }

    public void setSave(boolean save) {
        this.save = save;
    }

    public boolean isEdit() {
        return edit;
    }

    public void setEdit(boolean edit) {
        this.edit = edit;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Auteur> getAuteurs() {
        if (auteurs == null) {
            auteurs = new ArrayList<>();
        }
        return auteurs;
    }

    public void setAuteurs(List<Auteur> auteurs) {
        this.auteurs = auteurs;
    }

    public RoleRepositery getRoleRepositery() {
        return roleRepositery;
    }

    public void setRoleRepositery(RoleRepositery roleRepositery) {
        this.roleRepositery = roleRepositery;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public InscritRepositery getInscritRepositery() {
        return inscritRepositery;
    }

    public void setInscritRepositery(InscritRepositery inscritRepositery) {
        this.inscritRepositery = inscritRepositery;
    }

    public String getArticlDescription() {
        return articlDescription;
    }

    public void setArticlDescription(String articlDescription) {
        this.articlDescription = articlDescription;
    }

    public Article getSelectedA() {
        if (selectedA == null) {
            selectedA = new Article();
        }
        return selectedA;
    }

    public void setSelectedA(Article selectedA) {
        this.selectedA = selectedA;
    }

}
