-- phpMyAdmin SQL Dump
-- version 3.3.9
-- http://www.phpmyadmin.net
--
-- Serveur: localhost
-- Généré le : Mer 09 Février 2011 à 15:40
-- Version du serveur: 5.1.54
-- Version de PHP: 5.3.5

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de données: `sims`
--

-- --------------------------------------------------------

--
-- Structure de la table `chemin`
--

CREATE TABLE IF NOT EXISTS `chemin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `depart` text NOT NULL,
  `arrivee` text NOT NULL,
  `date` bigint(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=10 ;

--
-- Contenu de la table `chemin`
--

INSERT INTO `chemin` (`id`, `depart`, `arrivee`, `date`) VALUES
(1, 'charpenne', 'Part Dieu', 0),
(2, 'Belcour', 'INSA de Lyon', 0),
(3, 'aaa', 'aaaa', 0),
(5, 'xx', 'xx', 2011),
(6, 'tes1', 'tes2', 0),
(7, 'vv', 'vv', 1297983600000),
(8, 'marche', 'marche', 1297897200000),
(9, 'depart1', 'arrivee', 1298588400000);
