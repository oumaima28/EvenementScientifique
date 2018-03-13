-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Feb 08, 2018 at 10:08 PM
-- Server version: 5.6.17
-- PHP Version: 5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `evenement_scientifique`
--

-- --------------------------------------------------------

--
-- Table structure for table `article`
--

CREATE TABLE IF NOT EXISTS `article` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `date_envoie` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `etat` varchar(255) DEFAULT NULL,
  `titre` varchar(255) DEFAULT NULL,
  `event_id` bigint(20) DEFAULT NULL,
  `inscrit_login` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKde5c812g0or6q5gkk7qu79tj1` (`event_id`),
  KEY `FKkjjk7am9coasrw1jp1f2i5hjt` (`inscrit_login`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=11 ;

--
-- Dumping data for table `article`
--

INSERT INTO `article` (`id`, `date_envoie`, `description`, `etat`, `titre`, `event_id`, `inscrit_login`) VALUES
(1, '2018-02-08 15:11:57', 'description de l''article et du blabla', 'Waiting', 'os', 1, 'okarin'),
(2, '2018-02-08 15:32:22', 'description', 'Waiting', 'embarque', 1, 'ghita'),
(3, '2018-02-08 15:48:33', 'texte', 'Waiting', 'article', 1, 'mikasa'),
(5, '2018-02-08 17:39:06', 'description test bbb', 'Waiting', 'test', 2, 'mikasa'),
(6, '2018-02-08 17:45:27', 'random text', 'Waiting', 'amine', 2, 'amine'),
(7, '2018-02-08 18:27:47', 'test', 'Waiting', 'data', 3, 'okarin'),
(8, '2018-02-08 18:37:58', 'testing some agorithms', 'Not accepted', 'bigdata algo', 3, 'ghita'),
(9, '2018-02-08 18:50:38', 'descroption bout the algorithms used in my article', 'Accepted', 'machine algo', 5, 'oumaima'),
(10, '2018-02-08 18:57:54', 'description of article on bigdata', 'Not accepted', 'data article', 4, 'oumaima');

-- --------------------------------------------------------

--
-- Table structure for table `article_auteurs`
--

CREATE TABLE IF NOT EXISTS `article_auteurs` (
  `article_id` bigint(20) NOT NULL,
  `auteurs_cin` varchar(255) NOT NULL,
  KEY `FKpoe61dy5oylr6g70s94hsgyxs` (`auteurs_cin`),
  KEY `FKi2mly8eqks09c1i3cexk4s08u` (`article_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `article_auteurs`
--

INSERT INTO `article_auteurs` (`article_id`, `auteurs_cin`) VALUES
(1, 'EE1475869'),
(1, 'RR149852'),
(2, 'AA478523'),
(3, 'EE1456327'),
(5, 'EE1456327'),
(6, 'EE1111111'),
(7, 'RR149852'),
(8, 'AA478523'),
(9, 'EE98989898'),
(10, 'EE98989898');

-- --------------------------------------------------------

--
-- Table structure for table `article_emails`
--

CREATE TABLE IF NOT EXISTS `article_emails` (
  `article_id` bigint(20) NOT NULL,
  `emails` varchar(255) DEFAULT NULL,
  KEY `FKpllnde1i5ela0shxs3o7jlf6j` (`article_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `article_emails`
--

INSERT INTO `article_emails` (`article_id`, `emails`) VALUES
(1, 'hindbzd@gmail.com'),
(1, 'okarin.okabe.steinsgate@gmail.com'),
(2, 'ghitaanwar95@gmail.com'),
(3, 'mikasaa.chan.28@gmail.com'),
(5, 'mikasaa.chan.28@gmail.com'),
(6, 'amine.arrama@gmail.com'),
(7, 'okarin.okabe.steinsgate@gmail.com'),
(8, 'ghitaanwar95@gmail.com'),
(9, 'oumaima28b@gmail.com'),
(10, 'oumaima28b@gmail.com');

-- --------------------------------------------------------

--
-- Table structure for table `article_roles`
--

CREATE TABLE IF NOT EXISTS `article_roles` (
  `article_id` bigint(20) NOT NULL,
  `roles_id` bigint(20) NOT NULL,
  KEY `FK5a2yjsacrxvtjyy08bd5m1rk0` (`roles_id`),
  KEY `FK8778vabdsxo5ymmjonwq45wyi` (`article_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `article_roles`
--

INSERT INTO `article_roles` (`article_id`, `roles_id`) VALUES
(1, 2),
(1, 3),
(6, 9),
(5, 9),
(10, 15),
(9, 20);

-- --------------------------------------------------------

--
-- Table structure for table `auteur`
--

CREATE TABLE IF NOT EXISTS `auteur` (
  `cin` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `nom` varchar(255) DEFAULT NULL,
  `prenom` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`cin`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `auteur`
--

INSERT INTO `auteur` (`cin`, `description`, `email`, `nom`, `prenom`) VALUES
('AA478523', 'Sleepy head', 'ghitaanwar95@gmail.com', 'Anwar', 'Ghita'),
('EE1111111', 'bo9al', 'amine.arrama@gmail.com', 'Arrama', 'Amine'),
('EE1456327', 'Anime Character', 'mikasaa.chan.28@gmail.com', 'Ackerman', 'Mikasa'),
('EE1475869', 'Etudiante Master semlalia', 'hindbzd@gmail.com', 'Bouzidi', 'hind'),
('EE98989898', 'Etudiante Master Sem', 'oumaima28b@gmail.com', 'Oumaima', 'Bousqaoui'),
('RR149852', 'Inventor and creator', 'okarin.okabe.steinsgate@gmail.com', 'Okabe', 'Okarin');

-- --------------------------------------------------------

--
-- Table structure for table `auteur_articles`
--

CREATE TABLE IF NOT EXISTS `auteur_articles` (
  `auteur_cin` varchar(255) NOT NULL,
  `articles_id` bigint(20) NOT NULL,
  KEY `FKq1pucyjkxyirky3y4k0i84umb` (`articles_id`),
  KEY `FKn8ksk0qkkvlnwgn0be9f48xs0` (`auteur_cin`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `event`
--

CREATE TABLE IF NOT EXISTS `event` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `adresse` varchar(255) DEFAULT NULL,
  `date_debut` date DEFAULT NULL,
  `date_fin` date DEFAULT NULL,
  `date_limite_envoie_article` date DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `heure_debut` time DEFAULT NULL,
  `montant` double DEFAULT NULL,
  `nom` varchar(255) DEFAULT NULL,
  `pays` varchar(255) DEFAULT NULL,
  `rib_compte` varchar(255) DEFAULT NULL,
  `ville` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=8 ;

--
-- Dumping data for table `event`
--

INSERT INTO `event` (`id`, `adresse`, `date_debut`, `date_fin`, `date_limite_envoie_article`, `description`, `heure_debut`, `montant`, `nom`, `pays`, `rib_compte`, `ville`) VALUES
(1, 'ENSA', '2018-03-03', '2018-03-06', '2018-02-25', 'Evenement sur les OS Embarques et les OS d''internet d''objets', '10:00:00', 3000, 'OS Embarque', 'Morroco', '12345777', 'Tetouan'),
(2, 'adresse bbb 3 ad', '2018-04-03', '2018-04-06', '2018-03-30', 'evenement sur Iot et ses applications', '09:00:00', 2500, 'IoT Applications', 'France', 'EE124536', 'Paris'),
(3, 'badia daoudiyat', '2018-05-05', '2018-05-07', '2018-04-30', 'evenement sur les algortihme d''analyse des bigData', '10:00:00', 18000, 'BigData', 'Morroco', 'EE369752', 'Marrakech'),
(4, ' adresse blabla 99', '2019-01-01', '2019-01-03', '2018-12-01', 'evenement sur les methodes de mining des data', '09:30:00', 2200, 'data mining', 'America', '123468', 'New York'),
(5, 'adresse ttt N 20', '2018-05-05', '2018-05-07', '2018-04-30', 'evenemenet sur les algorithme du machine learning en chaine logistique', '09:00:00', 3400, 'Machine learning', 'Morroco', '425936', 'Rabat'),
(6, 'adresse bla bla', '2018-05-05', '2018-05-09', '2018-04-20', 'evenemenent sur la therorie d''Einstein et ses possibilite', '10:00:00', 2200, 'therorie de relativite', 'Morroco', '2581030', 'Casablanca'),
(7, 'adresse somewhere', '2018-07-07', '2018-07-11', '2018-06-20', 'methode mathematique d''analyse des systemes', '09:00:00', 4500, 'methode d''analyse', 'America', '8587212', 'Washinton');

-- --------------------------------------------------------

--
-- Table structure for table `event_inscrits`
--

CREATE TABLE IF NOT EXISTS `event_inscrits` (
  `event_id` bigint(20) NOT NULL,
  `inscrits_login` varchar(255) NOT NULL,
  KEY `FK9ty7vrx031bnughh9oumwena9` (`inscrits_login`),
  KEY `FKde3kpq4pso58d63ltkr4fof4b` (`event_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `event_tags`
--

CREATE TABLE IF NOT EXISTS `event_tags` (
  `event_id` bigint(20) NOT NULL,
  `tags` varchar(255) DEFAULT NULL,
  KEY `FKhjeq0v70jnlh0glmfoatbqy0b` (`event_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `event_tags`
--

INSERT INTO `event_tags` (`event_id`, `tags`) VALUES
(1, 'Social Engineering'),
(1, 'Informatics'),
(2, 'Electronics'),
(2, 'Informatics'),
(2, 'Robotics'),
(3, 'BigData'),
(3, 'Informatics'),
(4, 'BigData'),
(4, 'Informatics'),
(5, 'Artificial Intelligence'),
(6, 'Physiques'),
(6, 'Mathematics'),
(7, 'Physiques'),
(7, 'Mathematics');

-- --------------------------------------------------------

--
-- Table structure for table `inscrit`
--

CREATE TABLE IF NOT EXISTS `inscrit` (
  `login` varchar(255) NOT NULL,
  `bloqued` int(11) NOT NULL,
  `cin` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `nom` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `prenom` varchar(255) DEFAULT NULL,
  `tel` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`login`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `inscrit`
--

INSERT INTO `inscrit` (`login`, `bloqued`, `cin`, `description`, `email`, `nom`, `password`, `prenom`, `tel`) VALUES
('amine', 0, 'EE1111111', 'bo9al', 'amine.arrama@gmail.com', 'Arrama', '198f5987f277c05090008fc9a451c34a0b328c4b727d8315334481820e868957', 'Amine', '0789543614'),
('fatima', 0, 'EE2232354', 'Wkala flil', 'fatimaac391@gmail.com', 'AitCheikh', '10c932ece8048a7386160bf1463a4db09c26613801369bd5d1d00a7ed6c001dd', 'Fatima', '0678951234'),
('ghita', 0, 'AA478523', 'Sleepy head', 'ghitaanwar95@gmail.com', 'Anwar', 'bffeefa2e67dece7d63f2770176743c73d65f2e273d40493febb5ba447d1bcac', 'Ghita', '0678951411'),
('mikasa', 0, 'EE1456327', 'Anime Character', 'mikasaa.chan.28@gmail.com', 'Ackerman', '15601f727ee139c2ced68cff02e96de612c7dc8587420cc68c7a97a01465241c', 'Mikasa', '0652458978'),
('okarin', 0, 'RR149852', 'Inventor and creator', 'okarin.okabe.steinsgate@gmail.com', 'Okabe', 'fb61a281ed528310bbf33896044ecb68cb84b60bc2efcefdcf329818477d579e', 'Okarin', '0648752111'),
('oumaima', 0, 'EE98989898', 'Etudiante Master Sem', 'oumaima28b@gmail.com', 'Oumaima', '83d0ad57a647e8de258825dc26452c9aa255c66655eab1da43b860be0a0a4bdb', 'Bousqaoui', '0655555555'),
('zhor', 0, 'CC78211144', 'n3asa', 'Azlal.zhoraz@gmail.com', 'Azlal', 'df7aafcce1a1c3e2d75c324101c5068caa161c30922d84ba6e1af37cf9b467c0', 'Zhor', '0677777725');

-- --------------------------------------------------------

--
-- Table structure for table `inscrit_events`
--

CREATE TABLE IF NOT EXISTS `inscrit_events` (
  `inscrit_login` varchar(255) NOT NULL,
  `events_id` bigint(20) NOT NULL,
  KEY `FKgk3x1tnt1d9o4xhinimgwc70h` (`events_id`),
  KEY `FK25eu0jyf7od5g3oj1dgl4mlj2` (`inscrit_login`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `inscrit_events`
--

INSERT INTO `inscrit_events` (`inscrit_login`, `events_id`) VALUES
('okarin', 1),
('mikasa', 1),
('oumaima', 4),
('amine', 4),
('fatima', 4),
('ghita', 4),
('ghita', 2),
('mikasa', 2),
('amine', 2),
('okarin', 3),
('ghita', 3);

-- --------------------------------------------------------

--
-- Table structure for table `paiement`
--

CREATE TABLE IF NOT EXISTS `paiement` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `payed` int(11) NOT NULL,
  `event_id` bigint(20) DEFAULT NULL,
  `inscrit_login` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKsgk28qr1xnfpmyim1av90bsow` (`event_id`),
  KEY `FK6wrjebw2708m0hg59s1rxn4no` (`inscrit_login`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=12 ;

--
-- Dumping data for table `paiement`
--

INSERT INTO `paiement` (`id`, `payed`, `event_id`, `inscrit_login`) VALUES
(1, 0, 1, 'okarin'),
(2, 0, 1, 'ghita'),
(3, 0, 1, 'mikasa'),
(5, 0, 2, 'mikasa'),
(6, 0, 2, 'amine'),
(7, 0, 3, 'okarin'),
(8, 0, 3, 'ghita'),
(9, 0, 4, 'oumaima'),
(10, 0, 5, 'oumaima'),
(11, 0, 4, 'oumaima');

-- --------------------------------------------------------

--
-- Table structure for table `revision`
--

CREATE TABLE IF NOT EXISTS `revision` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `experience_level` int(11) NOT NULL,
  `remarque_pour_auteur` varchar(255) DEFAULT NULL,
  `remarque_pour_organisateur` varchar(255) DEFAULT NULL,
  `article_id` bigint(20) DEFAULT NULL,
  `rapporteur_login` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKb1yqk43vfs68bm1k20534eoq5` (`article_id`),
  KEY `FKfw3up04cfhqrs9gj1pv5x1t0e` (`rapporteur_login`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

--
-- Dumping data for table `revision`
--

INSERT INTO `revision` (`id`, `experience_level`, `remarque_pour_auteur`, `remarque_pour_organisateur`, `article_id`, `rapporteur_login`) VALUES
(1, 3, 'badly written a lot of changes are needed', 'moyen article', 5, 'oumaima'),
(3, 3, 'passable mais change la structure de l''article', 'moyen, article interessant bien ecrit', 10, 'mikasa'),
(5, 3, 'remarque auteur', 'remarque orga', 9, 'fatima');

-- --------------------------------------------------------

--
-- Table structure for table `role`
--

CREATE TABLE IF NOT EXISTS `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role` int(11) NOT NULL,
  `verified_rapporteur` bit(1) DEFAULT NULL,
  `event_id` bigint(20) DEFAULT NULL,
  `inscrit_login` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKsf37ehciemr9uliju68ntw1xc` (`event_id`),
  KEY `FKbjrbkxrlq6ifhgufy42o3gbfv` (`inscrit_login`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=30 ;

--
-- Dumping data for table `role`
--

INSERT INTO `role` (`id`, `role`, `verified_rapporteur`, `event_id`, `inscrit_login`) VALUES
(1, 1, NULL, 1, 'oumaima'),
(2, 2, b'1', 1, 'amine'),
(3, 2, b'1', 1, 'fatima'),
(4, 2, b'1', 1, 'zhor'),
(5, 3, NULL, 1, 'okarin'),
(6, 3, NULL, 1, 'ghita'),
(7, 3, NULL, 1, 'mikasa'),
(8, 1, NULL, 2, 'okarin'),
(9, 2, b'1', 2, 'oumaima'),
(10, 1, NULL, 3, 'mikasa'),
(11, 2, b'0', 3, 'oumaima'),
(12, 2, b'0', 3, 'amine'),
(13, 2, b'0', 3, 'fatima'),
(14, 1, NULL, 4, 'okarin'),
(15, 2, b'1', 4, 'mikasa'),
(17, 3, NULL, 2, 'mikasa'),
(18, 3, NULL, 2, 'amine'),
(19, 1, NULL, 5, 'amine'),
(20, 2, b'1', 5, 'fatima'),
(21, 3, NULL, 3, 'okarin'),
(22, 3, NULL, 3, 'ghita'),
(24, 3, NULL, 5, 'oumaima'),
(25, 3, NULL, 4, 'oumaima'),
(26, 1, NULL, 6, 'ghita'),
(27, 2, b'0', 6, 'okarin'),
(28, 1, NULL, 7, 'okarin'),
(29, 2, b'0', 7, 'mikasa');

-- --------------------------------------------------------

--
-- Table structure for table `role_articles`
--

CREATE TABLE IF NOT EXISTS `role_articles` (
  `role_id` bigint(20) NOT NULL,
  `articles_id` bigint(20) NOT NULL,
  KEY `FKdbd3a1udmyh52r2nm0wmmohka` (`articles_id`),
  KEY `FKhlltws0t4v0wll4nk3ql7iotu` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `role_articles`
--

INSERT INTO `role_articles` (`role_id`, `articles_id`) VALUES
(2, 1),
(3, 1),
(9, 6),
(9, 5),
(15, 10),
(20, 9);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `article`
--
ALTER TABLE `article`
  ADD CONSTRAINT `FKde5c812g0or6q5gkk7qu79tj1` FOREIGN KEY (`event_id`) REFERENCES `event` (`id`),
  ADD CONSTRAINT `FKkjjk7am9coasrw1jp1f2i5hjt` FOREIGN KEY (`inscrit_login`) REFERENCES `inscrit` (`login`);

--
-- Constraints for table `article_auteurs`
--
ALTER TABLE `article_auteurs`
  ADD CONSTRAINT `FKi2mly8eqks09c1i3cexk4s08u` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`),
  ADD CONSTRAINT `FKpoe61dy5oylr6g70s94hsgyxs` FOREIGN KEY (`auteurs_cin`) REFERENCES `auteur` (`cin`);

--
-- Constraints for table `article_emails`
--
ALTER TABLE `article_emails`
  ADD CONSTRAINT `FKpllnde1i5ela0shxs3o7jlf6j` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`);

--
-- Constraints for table `article_roles`
--
ALTER TABLE `article_roles`
  ADD CONSTRAINT `FK8778vabdsxo5ymmjonwq45wyi` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`),
  ADD CONSTRAINT `FK5a2yjsacrxvtjyy08bd5m1rk0` FOREIGN KEY (`roles_id`) REFERENCES `role` (`id`);

--
-- Constraints for table `auteur_articles`
--
ALTER TABLE `auteur_articles`
  ADD CONSTRAINT `FKn8ksk0qkkvlnwgn0be9f48xs0` FOREIGN KEY (`auteur_cin`) REFERENCES `auteur` (`cin`),
  ADD CONSTRAINT `FKq1pucyjkxyirky3y4k0i84umb` FOREIGN KEY (`articles_id`) REFERENCES `article` (`id`);

--
-- Constraints for table `event_inscrits`
--
ALTER TABLE `event_inscrits`
  ADD CONSTRAINT `FKde3kpq4pso58d63ltkr4fof4b` FOREIGN KEY (`event_id`) REFERENCES `event` (`id`),
  ADD CONSTRAINT `FK9ty7vrx031bnughh9oumwena9` FOREIGN KEY (`inscrits_login`) REFERENCES `inscrit` (`login`);

--
-- Constraints for table `event_tags`
--
ALTER TABLE `event_tags`
  ADD CONSTRAINT `FKhjeq0v70jnlh0glmfoatbqy0b` FOREIGN KEY (`event_id`) REFERENCES `event` (`id`);

--
-- Constraints for table `inscrit_events`
--
ALTER TABLE `inscrit_events`
  ADD CONSTRAINT `FK25eu0jyf7od5g3oj1dgl4mlj2` FOREIGN KEY (`inscrit_login`) REFERENCES `inscrit` (`login`),
  ADD CONSTRAINT `FKgk3x1tnt1d9o4xhinimgwc70h` FOREIGN KEY (`events_id`) REFERENCES `event` (`id`);

--
-- Constraints for table `paiement`
--
ALTER TABLE `paiement`
  ADD CONSTRAINT `FK6wrjebw2708m0hg59s1rxn4no` FOREIGN KEY (`inscrit_login`) REFERENCES `inscrit` (`login`),
  ADD CONSTRAINT `FKsgk28qr1xnfpmyim1av90bsow` FOREIGN KEY (`event_id`) REFERENCES `event` (`id`);

--
-- Constraints for table `revision`
--
ALTER TABLE `revision`
  ADD CONSTRAINT `FKfw3up04cfhqrs9gj1pv5x1t0e` FOREIGN KEY (`rapporteur_login`) REFERENCES `inscrit` (`login`),
  ADD CONSTRAINT `FKb1yqk43vfs68bm1k20534eoq5` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`);

--
-- Constraints for table `role`
--
ALTER TABLE `role`
  ADD CONSTRAINT `FKbjrbkxrlq6ifhgufy42o3gbfv` FOREIGN KEY (`inscrit_login`) REFERENCES `inscrit` (`login`),
  ADD CONSTRAINT `FKsf37ehciemr9uliju68ntw1xc` FOREIGN KEY (`event_id`) REFERENCES `event` (`id`);

--
-- Constraints for table `role_articles`
--
ALTER TABLE `role_articles`
  ADD CONSTRAINT `FKhlltws0t4v0wll4nk3ql7iotu` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  ADD CONSTRAINT `FKdbd3a1udmyh52r2nm0wmmohka` FOREIGN KEY (`articles_id`) REFERENCES `article` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
