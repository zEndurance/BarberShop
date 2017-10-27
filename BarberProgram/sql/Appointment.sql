-- --------------------------------------------------------
-- Host:                         dbprojects.eecs.qmul.ac.uk
-- Server version:               5.0.95 - Source distribution
-- Server OS:                    redhat-linux-gnu
-- HeidiSQL Version:             9.4.0.5125
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for mm335
CREATE DATABASE IF NOT EXISTS `epiz_20932514_bbs118` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `epiz_20932514_bbs118`;

-- Dumping structure for table mm335.appointments
CREATE TABLE IF NOT EXISTS `appointments` (
  `ID` int(6) unsigned NOT NULL auto_increment,
  `userID` int(6) default NULL,
  `Day` varchar(30) NOT NULL,
  `Date` date NOT NULL,
  `t9` varchar(120) NOT NULL,
  `t10` varchar(120) NOT NULL,
  `t11` varchar(120) NOT NULL,
  `t12` varchar(120) NOT NULL,
  `t13` varchar(120) NOT NULL,
  `t14` varchar(120) NOT NULL,
  `t15` varchar(120) NOT NULL,
  `t16` varchar(120) NOT NULL,
  `t17` varchar(120) NOT NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

-- Dumping data for table mm335.appointments: 2 rows
/*!40000 ALTER TABLE `appointments` DISABLE KEYS */;
INSERT INTO `appointments` (`ID`, `userID`, `Day`, `Date`, `t9`, `t10`, `t11`, `t12`, `t13`, `t14`, `t15`, `t16`, `t17`) VALUES
	(8, 2, 'Monday', '2017-10-20', '1, James, Back and Sides, 077718213123, guy1.jpg, 15.50', '0', '0', '0', '0', '0', '0', '0', '0'),
	(9, 2, 'Saturday', '2017-10-21', '1, Klare, Long Cut, 45353453, girl4.jpg, 50.0', '0', '0', '0', '0', '0', '0', '0', '0');
/*!40000 ALTER TABLE `appointments` ENABLE KEYS */;

-- Dumping structure for table mm335.login
CREATE TABLE IF NOT EXISTS `login` (
  `ID` int(6) unsigned NOT NULL auto_increment,
  `Username` varchar(30) NOT NULL,
  `Password` varchar(30) NOT NULL,
  `Email` text,
  `Name` varchar(50) default NULL,
  `ECName` varchar(50) NOT NULL,
  `ECNum` varchar(45) NOT NULL,
  `Mobile` varchar(12) NOT NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

-- Dumping data for table mm335.login: 2 rows
/*!40000 ALTER TABLE `login` DISABLE KEYS */;
INSERT INTO `login` (`ID`, `Username`, `Password`, `Email`, `Name`, `ECName`, `ECNum`, `Mobile`) VALUES
	(1, 'raj', 'raj', 'raj@barbershop.com', '', '', '', ''),
	(2, 't', '', 'sadeqrahman@barbershop.com', 'Sadeq Rahman', 'Mikdhad Miah', '07744557782', '07766432134');
/*!40000 ALTER TABLE `login` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
