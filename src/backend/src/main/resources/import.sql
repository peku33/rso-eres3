-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: test
-- ------------------------------------------------------
-- Server version	5.7.18-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Dumping data for table `credit`
--
-- ORDER BY:  `id`

LOCK TABLES `credit` WRITE;
/*!40000 ALTER TABLE `credit` DISABLE KEYS */;
/*!40000 ALTER TABLE `credit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `organizational_unit`
--
-- ORDER BY:  `id`

LOCK TABLES `organizational_unit` WRITE;
/*!40000 ALTER TABLE `organizational_unit` DISABLE KEYS */;
INSERT INTO `organizational_unit` VALUES (1,'Inżynieria systemów informatycznych','I.ISI'),(2,'Systemy informacyjno-decyzyjne','I.SID'),(3,'Radiokomunikacja i techniki multimedialne','E.RTM'),(4,'Systemy i sieci telekomunikacyjne','E.SST'),(5,'Teleinformatyka i zarządzanie w telekomunikacji','E.TIZ'),(6,'Elektronika i inżynieria komputerowa','E.EIK'),(7,'Elektronika i informatyka w medycynie','E.EIM');
/*!40000 ALTER TABLE `organizational_unit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `permission`
--
-- ORDER BY:  `name`

LOCK TABLES `permission` WRITE;
/*!40000 ALTER TABLE `permission` DISABLE KEYS */;
INSERT INTO `permission` VALUES ('CreditCreate','User is allowed to create credits'),('CreditDelete','User is allowed to delete credits'),('CreditRead','User is allowed to read credits'),('CreditUpdate','User is allowed to update credits'),('GrantedPermissionCreate','User is allowed to grant permissions'),('GrantedPermissionDelete','User is allowed to delete granted permissions'),('GrantedPermissionRead','User is allowed to read granted permissions'),('OrganizationalUnitCreate','User is allowed to create organizational units'),('OrganizationalUnitDelete','User is allowed to delete organizational units'),('OrganizationalUnitRead','User is allowed to read organizational units'),('PermissionCreate','User is allowed to create new permissions'),('PermissionDelete','User is allowed to delete permissions'),('PermissionRead','User is allowed to read permissions'),('PermissionUpdate','User is allowed to update permissions'),('PersonCreate','User is allowed to register new users'),('PersonDelete','User is allowed to delete users'),('PersonRead','User is allowed to read list of users'),('PersonUpdate','User is allowed to update users'),('SemesterRead','User is allowed to read semesters'),('SpecializationCreate','User is allowed to create specializations'),('SpecializationDelete','User is allowed to delete specializations'),('SpecializationRead','User is allowed to read specializations'),('SpecializationUpdate','User is allowed to update specializations'),('StudentTourCreate','User is allowed to create student tours'),('StudentTourDelete','User is allowed to delete student tours'),('StudentTourRead','User is allowed to read student tours'),('StudentTourSemesterCreate','User is allowed to create student tour semesters'),('StudentTourSemesterDelete','User is allowed to delete student tour semesters'),('StudentTourSemesterRead','User is allowed to read student tour semesters'),('StudentTourSemesterUpdate','User is allowed to update student tour semesters'),('StudentTourUpdate','User is allowed to update student tours'),('SubjectCreate','User is allowed to create subjects'),('SubjectDelete','User is allowed to delete subjects'),('SubjectRead','User is allowed to read subjects'),('SubjectRealizationCreate','User is allowed to create subject realizations'),('SubjectRealizationDelete','User is allowed to delete subject realizations'),('SubjectRealizationRead','User is allowed to read subject realizations'),('SubjectUpdate','User is allowed to update subjects'),('SubjectVersionCreate','User is allowed to create subject versions'),('SubjectVersionDelete','User is allowed to delete subject versions'),('SubjectVersionRead','User is allowed to read subject versions');
/*!40000 ALTER TABLE `permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `person`
--
-- ORDER BY:  `id`

LOCK TABLES `person` WRITE;
/*!40000 ALTER TABLE `person` DISABLE KEYS */;
INSERT INTO `person` VALUES (1,NULL,NULL,'root',NULL,'$2a$10$.DwPvtZHtCJWFXBkMPnu3.gCYHZ4.GPbsdXtHDdDpfkJYBPbTIaCa',NULL,1);
/*!40000 ALTER TABLE `person` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Dumping data for table `granted_permission`
--
-- ORDER BY:  `permission_name`,`person_id`,`unit_id`

LOCK TABLES `granted_permission` WRITE;
/*!40000 ALTER TABLE `granted_permission` DISABLE KEYS */;
INSERT INTO `granted_permission` VALUES ('CreditCreate',1,1),('CreditDelete',1,1),('CreditRead',1,1),('CreditUpdate',1,1),('GrantedPermissionCreate',1,1),('GrantedPermissionDelete',1,1),('GrantedPermissionRead',1,1),('OrganizationalUnitCreate',1,1),('OrganizationalUnitDelete',1,1),('OrganizationalUnitRead',1,1),('PermissionCreate',1,1),('PermissionDelete',1,1),('PermissionRead',1,1),('PermissionUpdate',1,1),('PersonCreate',1,1),('PersonDelete',1,1),('PersonRead',1,1),('PersonUpdate',1,1),('SemesterRead',1,1),('SpecializationCreate',1,1),('SpecializationDelete',1,1),('SpecializationRead',1,1),('SpecializationUpdate',1,1),('StudentTourCreate',1,1),('StudentTourDelete',1,1),('StudentTourRead',1,1),('StudentTourSemesterCreate',1,1),('StudentTourSemesterDelete',1,1),('StudentTourSemesterRead',1,1),('StudentTourSemesterUpdate',1,1),('StudentTourUpdate',1,1),('SubjectCreate',1,1),('SubjectDelete',1,1),('SubjectRead',1,1),('SubjectRealizationCreate',1,1),('SubjectRealizationDelete',1,1),('SubjectRealizationRead',1,1),('SubjectUpdate',1,1),('SubjectVersionCreate',1,1),('SubjectVersionDelete',1,1),('SubjectVersionRead',1,1);
/*!40000 ALTER TABLE `granted_permission` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Dumping data for table `semester`
--
-- ORDER BY:  `id`

LOCK TABLES `semester` WRITE;
/*!40000 ALTER TABLE `semester` DISABLE KEYS */;
/*!40000 ALTER TABLE `semester` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `specialization`
--
-- ORDER BY:  `id`

LOCK TABLES `specialization` WRITE;
/*!40000 ALTER TABLE `specialization` DISABLE KEYS */;
/*!40000 ALTER TABLE `specialization` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `student_tour`
--
-- ORDER BY:  `id`

LOCK TABLES `student_tour` WRITE;
/*!40000 ALTER TABLE `student_tour` DISABLE KEYS */;
/*!40000 ALTER TABLE `student_tour` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `student_tour_semester`
--
-- ORDER BY:  `id`

LOCK TABLES `student_tour_semester` WRITE;
/*!40000 ALTER TABLE `student_tour_semester` DISABLE KEYS */;
/*!40000 ALTER TABLE `student_tour_semester` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `subject`
--
-- ORDER BY:  `id`

LOCK TABLES `subject` WRITE;
/*!40000 ALTER TABLE `subject` DISABLE KEYS */;
/*!40000 ALTER TABLE `subject` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `subject_realization`
--
-- ORDER BY:  `id`

LOCK TABLES `subject_realization` WRITE;
/*!40000 ALTER TABLE `subject_realization` DISABLE KEYS */;
/*!40000 ALTER TABLE `subject_realization` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `subject_version`
--
-- ORDER BY:  `id`

LOCK TABLES `subject_version` WRITE;
/*!40000 ALTER TABLE `subject_version` DISABLE KEYS */;
/*!40000 ALTER TABLE `subject_version` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-06-07  0:55:07
