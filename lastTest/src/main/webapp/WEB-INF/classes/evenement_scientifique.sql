-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jan 05, 2018 at 01:47 PM
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
  `etat` varchar(255) DEFAULT NULL,
  `file` tinyblob,
  `titre` varchar(255) DEFAULT NULL,
  `event_id` bigint(20) DEFAULT NULL,
  `inscrit_login` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKde5c812g0or6q5gkk7qu79tj1` (`event_id`),
  KEY `FKkjjk7am9coasrw1jp1f2i5hjt` (`inscrit_login`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

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

-- --------------------------------------------------------

--
-- Table structure for table `article_emails`
--

CREATE TABLE IF NOT EXISTS `article_emails` (
  `article_id` bigint(20) NOT NULL,
  `emails` varchar(255) DEFAULT NULL,
  KEY `FKpllnde1i5ela0shxs3o7jlf6j` (`article_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `auteur`
--

CREATE TABLE IF NOT EXISTS `auteur` (
  `cin` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `nom` varchar(255) DEFAULT NULL,
  `prenom` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`cin`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

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
  `montant` double DEFAULT NULL,
  `nom` varchar(255) DEFAULT NULL,
  `pays` varchar(255) DEFAULT NULL,
  `ville` varchar(255) DEFAULT NULL,
  `heure_debut` time DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `event`
--

INSERT INTO `event` (`id`, `adresse`, `date_debut`, `date_fin`, `date_limite_envoie_article`, `description`, `montant`, `nom`, `pays`, `ville`, `heure_debut`) VALUES
(2, 'adresse', '2018-03-05', '2018-05-05', '2018-02-05', 'a conference to better your coding skills', 500, 'sem 2018', 'Morroco', 'Marrakech', '10:00:00'),
(3, 'adressa', '2019-01-01', '2019-01-03', '2019-12-20', 'reuninon class', 100, 'sir 2019', 'America', 'New York', '22:00:00');

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
(2, 'Social Engineering'),
(2, 'Electronics'),
(2, 'Robotics'),
(3, 'Social Engineering'),
(3, 'Informatics'),
(3, 'Artificial Intelligence');

-- --------------------------------------------------------

--
-- Table structure for table `inscrit`
--

CREATE TABLE IF NOT EXISTS `inscrit` (
  `login` varchar(255) NOT NULL,
  `bloqued` int(11) NOT NULL,
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

INSERT INTO `inscrit` (`login`, `bloqued`, `description`, `email`, `nom`, `password`, `prenom`, `tel`) VALUES
('aa', 0, NULL, NULL, NULL, 'www', NULL, NULL),
('batima', 0, 'lwez', 'fatimaac391@gmail.com', 'batima', 'a200c24afabb3a21fa9a289874501f2bead88657acc43d4fdad727d334c2d5ce', 'batimation', '02582'),
('oumaima', 0, 'sss', 'oumaima28b@gmail.com', 'oumaima', '83d0ad57a647e8de258825dc26452c9aa255c66655eab1da43b860be0a0a4bdb', 'oumaima', '00000');

-- --------------------------------------------------------

--
-- Table structure for table `paiement`
--

CREATE TABLE IF NOT EXISTS `paiement` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `auteur_login` varchar(255) DEFAULT NULL,
  `event_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKc1vad6gtmvt4j1x9550ocbbk8` (`auteur_login`),
  KEY `FKsgk28qr1xnfpmyim1av90bsow` (`event_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

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
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `role`
--

CREATE TABLE IF NOT EXISTS `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role` int(11) NOT NULL,
  `event_id` bigint(20) DEFAULT NULL,
  `inscrit_login` varchar(255) DEFAULT NULL,
  `verified_rapporteur` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKsf37ehciemr9uliju68ntw1xc` (`event_id`),
  KEY `FKbjrbkxrlq6ifhgufy42o3gbfv` (`inscrit_login`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=8 ;

--
-- Dumping data for table `role`
--

INSERT INTO `role` (`id`, `role`, `event_id`, `inscrit_login`, `verified_rapporteur`) VALUES
(4, 1, 2, 'oumaima', NULL),
(5, 2, 2, 'batima', b'0'),
(6, 1, 3, 'oumaima', NULL),
(7, 2, 3, 'batima', b'0');

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
-- Constraints for table `auteur_articles`
--
ALTER TABLE `auteur_articles`
  ADD CONSTRAINT `FKn8ksk0qkkvlnwgn0be9f48xs0` FOREIGN KEY (`auteur_cin`) REFERENCES `auteur` (`cin`),
  ADD CONSTRAINT `FKq1pucyjkxyirky3y4k0i84umb` FOREIGN KEY (`articles_id`) REFERENCES `article` (`id`);

--
-- Constraints for table `event_tags`
--
ALTER TABLE `event_tags`
  ADD CONSTRAINT `FKhjeq0v70jnlh0glmfoatbqy0b` FOREIGN KEY (`event_id`) REFERENCES `event` (`id`);

--
-- Constraints for table `paiement`
--
ALTER TABLE `paiement`
  ADD CONSTRAINT `FKc1vad6gtmvt4j1x9550ocbbk8` FOREIGN KEY (`auteur_login`) REFERENCES `inscrit` (`login`),
  ADD CONSTRAINT `FKsgk28qr1xnfpmyim1av90bsow` FOREIGN KEY (`event_id`) REFERENCES `event` (`id`);

--
-- Constraints for table `revision`
--
ALTER TABLE `revision`
  ADD CONSTRAINT `FKb1yqk43vfs68bm1k20534eoq5` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`),
  ADD CONSTRAINT `FKfw3up04cfhqrs9gj1pv5x1t0e` FOREIGN KEY (`rapporteur_login`) REFERENCES `inscrit` (`login`);

--
-- Constraints for table `role`
--
ALTER TABLE `role`
  ADD CONSTRAINT `FKbjrbkxrlq6ifhgufy42o3gbfv` FOREIGN KEY (`inscrit_login`) REFERENCES `inscrit` (`login`),
  ADD CONSTRAINT `FKsf37ehciemr9uliju68ntw1xc` FOREIGN KEY (`event_id`) REFERENCES `event` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
