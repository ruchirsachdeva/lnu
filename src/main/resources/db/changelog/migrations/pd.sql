-- phpMyAdmin SQL Dump
-- version 4.7.7
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Oct 04, 2018 at 10:43 AM
-- Server version: 5.6.38
-- PHP Version: 7.2.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

--
-- Database: `pd_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `medicine`
--

CREATE TABLE `medicine` (
  `medicineID` int(11) NOT NULL,
  `name` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `medicine`
--

INSERT INTO `medicine` (`medicineID`, `name`) VALUES
  (1, 'Medicine 1'),
  (2, 'Medicine 2');

-- --------------------------------------------------------

--
-- Table structure for table `note`
--

CREATE TABLE `note` (
  `noteID` int(11) NOT NULL,
  `Test_Session_IDtest_session` int(11) NOT NULL,
  `note` longtext NOT NULL,
  `User_IDmed` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `note`
--

INSERT INTO `note` (`noteID`, `Test_Session_IDtest_session`, `note`, `User_IDmed`) VALUES
  (1, 1, 'Well this is interesting.', 2),
  (2, 1, 'This seams a bit weird.', 1);

-- --------------------------------------------------------

--
-- Table structure for table `organization`
--

CREATE TABLE `organization` (
  `organizationID` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `organization`
--

INSERT INTO `organization` (`organizationID`, `name`) VALUES
  (1, 'Hospital'),
  (2, 'LNU University');

-- --------------------------------------------------------

--
-- Table structure for table `role`
--

CREATE TABLE `role` (
  `roleID` int(11) NOT NULL,
  `name` varchar(45) NOT NULL,
  `type` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `role`
--

INSERT INTO `role` (`roleID`, `name`, `type`) VALUES
  (1, 'patient', '1'),
  (2, 'physician', '2'),
  (3, 'researcher', '3'),
  (4, 'junior researcher', '3');

-- --------------------------------------------------------

--
-- Table structure for table `test`
--

CREATE TABLE `test` (
  `testID` int(11) NOT NULL,
  `dateTime` datetime NOT NULL,
  `Therapy_IDtherapy` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `test`
--

INSERT INTO `test` (`testID`, `dateTime`, `Therapy_IDtherapy`) VALUES
  (1, '2009-12-01 18:00:00', 1),
  (2, '2009-12-02 18:00:00', 1),
  (3, '2009-12-02 18:00:00', 2);

-- --------------------------------------------------------

--
-- Table structure for table `test_session`
--

CREATE TABLE `test_session` (
  `test_SessionID` int(11) NOT NULL,
  `test_type` int(11) NOT NULL,
  `Test_IDtest` int(11) NOT NULL,
  `DataURL` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `test_session`
--

INSERT INTO `test_session` (`test_SessionID`, `test_type`, `Test_IDtest`, `DataURL`) VALUES
  (1, 1, 1, 'data1'),
  (2, 2, 1, 'data2'),
  (3, 1, 2, 'data3'),
  (4, 2, 2, 'data4'),
  (5, 1, 3, 'data5'),
  (6, 2, 3, 'data6');

-- --------------------------------------------------------

--
-- Table structure for table `therapy`
--

CREATE TABLE `therapy` (
  `therapyID` int(11) NOT NULL,
  `User_IDpatient` int(11) NOT NULL,
  `User_IDmed` int(11) NOT NULL,
  `TherapyList_IDtherapylist` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `therapy`
--

INSERT INTO `therapy` (`therapyID`, `User_IDpatient`, `User_IDmed`, `TherapyList_IDtherapylist`) VALUES
  (1, 3, 1, 1),
  (2, 4, 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `therapy_list`
--

CREATE TABLE `therapy_list` (
  `therapy_listID` int(11) NOT NULL,
  `name` varchar(45) NOT NULL,
  `Medicine_IDmedicine` int(11) NOT NULL,
  `Dosage` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `therapy_list`
--

INSERT INTO `therapy_list` (`therapy_listID`, `name`, `Medicine_IDmedicine`, `Dosage`) VALUES
  (1, 'Therapy trials with Medicine 1', 1, '400 ml');

-- --------------------------------------------------------

--
-- Table structure for table `Car`
--

CREATE TABLE `user` (
  `userID` int(11) NOT NULL,
  `username` varchar(45) NOT NULL,
  `email` varchar(255) NOT NULL,
  `Role_IDrole` int(11) NOT NULL,
  `organization` int(11) NOT NULL,
  `Lat` float DEFAULT NULL,
  `Long` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Car`
--

INSERT INTO `user` (`userID`, `username`, `email`, `Role_IDrole`, `organization`, `Lat`, `Long`) VALUES
  (1, 'doc', 'doc@hospital.com', 2, 1, NULL, NULL),
  (2, 'researcher', 'res@uni.se', 3, 2, NULL, NULL),
  (3, 'patient1', 'x@gmail.com', 1, 1, 59.6567, 16.6709),
  (4, 'patient2', 'y@happyemail.com', 1, 1, 57.3365, 12.5164);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `medicine`
--
ALTER TABLE `medicine`
  ADD PRIMARY KEY (`medicineID`);

--
-- Indexes for table `note`
--
ALTER TABLE `note`
  ADD PRIMARY KEY (`noteID`),
  ADD KEY `fk_Test_SessionID_idx` (`Test_Session_IDtest_session`),
  ADD KEY `fk_UserID_idx` (`User_IDmed`);

--
-- Indexes for table `organization`
--
ALTER TABLE `organization`
  ADD PRIMARY KEY (`organizationID`);

--
-- Indexes for table `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`roleID`);

--
-- Indexes for table `test`
--
ALTER TABLE `test`
  ADD PRIMARY KEY (`testID`),
  ADD KEY `fk_TherapyID_idx` (`Therapy_IDtherapy`);

--
-- Indexes for table `test_session`
--
ALTER TABLE `test_session`
  ADD PRIMARY KEY (`test_SessionID`),
  ADD KEY `fk_TestID_idx` (`Test_IDtest`);

--
-- Indexes for table `therapy`
--
ALTER TABLE `therapy`
  ADD PRIMARY KEY (`therapyID`),
  ADD KEY `fk_UserID_Patient_idx` (`User_IDpatient`),
  ADD KEY `fk_UserID_medic_idx` (`User_IDmed`),
  ADD KEY `fk_Therapy_ListID_idx` (`TherapyList_IDtherapylist`);

--
-- Indexes for table `therapy_list`
--
ALTER TABLE `therapy_list`
  ADD PRIMARY KEY (`therapy_listID`),
  ADD KEY `fk_medicineID_idx` (`Medicine_IDmedicine`);

--
-- Indexes for table `Car`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`userID`),
  ADD UNIQUE KEY `username_UNIQUE` (`username`),
  ADD KEY `roleID_idx` (`Role_IDrole`),
  ADD KEY `fk_User_Organization_idx` (`organization`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `medicine`
--
ALTER TABLE `medicine`
  MODIFY `medicineID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `note`
--
ALTER TABLE `note`
  MODIFY `noteID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `organization`
--
ALTER TABLE `organization`
  MODIFY `organizationID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `role`
--
ALTER TABLE `role`
  MODIFY `roleID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `test`
--
ALTER TABLE `test`
  MODIFY `testID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `test_session`
--
ALTER TABLE `test_session`
  MODIFY `test_SessionID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `therapy`
--
ALTER TABLE `therapy`
  MODIFY `therapyID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `therapy_list`
--
ALTER TABLE `therapy_list`
  MODIFY `therapy_listID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `Car`
--
ALTER TABLE `user`
  MODIFY `userID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `note`
--
ALTER TABLE `note`
  ADD CONSTRAINT `fk_Test_SessionID` FOREIGN KEY (`Test_Session_IDtest_session`) REFERENCES `test_session` (`test_SessionID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_UserID` FOREIGN KEY (`User_IDmed`) REFERENCES `user` (`userID`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `test`
--
ALTER TABLE `test`
  ADD CONSTRAINT `fk_TherapyID` FOREIGN KEY (`Therapy_IDtherapy`) REFERENCES `therapy` (`therapyID`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `test_session`
--
ALTER TABLE `test_session`
  ADD CONSTRAINT `fk_TestID` FOREIGN KEY (`Test_IDtest`) REFERENCES `test` (`testID`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `therapy`
--
ALTER TABLE `therapy`
  ADD CONSTRAINT `fk_Therapy_ListID` FOREIGN KEY (`TherapyList_IDtherapylist`) REFERENCES `therapy_list` (`therapy_listID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_UserID_Patient` FOREIGN KEY (`User_IDpatient`) REFERENCES `user` (`userID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_UserID_medic` FOREIGN KEY (`User_IDmed`) REFERENCES `user` (`userID`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `therapy_list`
--
ALTER TABLE `therapy_list`
  ADD CONSTRAINT `fk_MedicineID` FOREIGN KEY (`Medicine_IDmedicine`) REFERENCES `medicine` (`medicineID`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `Car`
--
ALTER TABLE `user`
  ADD CONSTRAINT `fk_User_Organization` FOREIGN KEY (`organization`) REFERENCES `organization` (`OrganizationID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_User_Role` FOREIGN KEY (`Role_IDrole`) REFERENCES `role` (`roleID`) ON DELETE NO ACTION ON UPDATE NO ACTION;