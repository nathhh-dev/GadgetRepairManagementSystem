-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Oct 06, 2026 at 12:39 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `gadgetrepairdb`
--

-- --------------------------------------------------------

--
-- Table structure for table `accounts`
--

CREATE TABLE `accounts` (
  `account_id` int(11) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` varchar(20) NOT NULL,
  `is_active` tinyint(1) NOT NULL DEFAULT 1,
  `security_question` varchar(255) DEFAULT NULL,
  `security_answer` varchar(255) DEFAULT NULL,
  `is_first_login` tinyint(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `accounts`
--

INSERT INTO `accounts` (`account_id`, `first_name`, `last_name`, `username`, `password`, `role`, `is_active`, `security_question`, `security_answer`, `is_first_login`) VALUES
(1, '', '', 'admin', 'Jjaudian@123', 'Admin', 1, 'What city were you born in?', 'CDO', 0),
(2, '', '', 'jojos', 'gwapoko', 'Technician', 1, NULL, NULL, 1),
(4, '', '', 'mathi', '12345', 'Staff', 0, NULL, NULL, 1),
(6, '', '', 'jojoss', '12345', 'Staff', 0, NULL, NULL, 1),
(7, '', '', 'testtech', '12345', 'Technician', 1, NULL, NULL, 1),
(8, '', '', 'Jonathan', '12345', 'Technician', 1, NULL, NULL, 1),
(9, '', '', 'Khaleed', '54321', 'Staff', 1, NULL, NULL, 1),
(10, '', '', 'Jo', '2006', 'Technician', 1, NULL, NULL, 1),
(11, '', '', 'Nath', '2005', 'Staff', 1, NULL, NULL, 1),
(12, '', '', 'Tan', '1999', 'Admin', 1, NULL, NULL, 1),
(13, '', '', 'TANTAN', 'lolo', 'Technician', 1, NULL, NULL, 1),
(14, '', '', 'ezequel', '12345', 'Staff', 1, NULL, NULL, 1),
(15, '', '', 'Grock', '54321', 'Technician', 1, NULL, NULL, 1),
(16, '', '', 'Hilda', '9090', 'Staff', 1, NULL, NULL, 1),
(17, '', '', 'Ashton', '9090', 'Staff', 1, NULL, NULL, 1),
(18, '', '', 'Kiel', '9091', 'Technician', 1, NULL, NULL, 1),
(21, '', '', 'Loki', 'Loki@2026', 'Staff', 1, 'What is your favorite color?', 'Blue', 0),
(22, '', '', 'Thor', 'Lokiisalegend@20', 'Technician', 1, 'What is your favorite food?', 'Lechon', 0),
(23, '', '', 'Lukas', 'Jjaudian@123', 'Technician', 1, 'What is your favorite movie?', 'Avengers', 0),
(25, '', '', 'SSNA', 'Mommyshark@20', 'Staff', 1, 'What is the name of your first pet?', 'Shark', 0),
(26, '', '', 'Saberlvl4', 'Jjaudian@20', 'Technician', 1, 'What is your favorite color?', 'red', 0),
(27, '', '', 'PAQUITO', 'Baboyramo@20', 'Staff', 1, 'What is the name of your first pet?', 'BABOY', 0),
(28, '', '', 'SABER', 'gwapoko@1234', 'Staff', 1, 'What is the name of your first pet?', 'rat', 0),
(31, 'Nath', 'Tan', 'tani', '12345', 'Staff', 1, NULL, NULL, 1),
(32, 'Nath', 'Tan', 'nata', '12345', 'Technician', 1, NULL, NULL, 1),
(37, 'Nath', 'Tan', 'Tani', '12345', 'Staff', 1, NULL, NULL, 1),
(38, 'Jo', 'Jau', 'Jojo', 'Jjaudian@20', 'Admin', 1, 'What is your favorite color?', 'Red', 0),
(39, 'Tenma', 'Matzukaze', 'Tenma', 'Jjaudian@20', 'Technician', 1, 'What is your favorite color?', 'Red', 0);

-- --------------------------------------------------------

--
-- Table structure for table `customers`
--

CREATE TABLE `customers` (
  `customer_id` int(11) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `contact_number` varchar(20) NOT NULL,
  `is_active` tinyint(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `customers`
--

INSERT INTO `customers` (`customer_id`, `first_name`, `last_name`, `contact_number`, `is_active`) VALUES
(1, 'jojo', 'jau', '09265696498', 1),
(2, 'loj', 'awa', '0999921212', 1),
(3, 'mathilda', 'flask', '3453454353', 1),
(4, 'princess', 'pitpitiw', '121211212', 1),
(5, 'floryn', 'mlbwew', '12453424', 1),
(6, 'novaria', 'sling', '356654645', 1),
(7, '121', '121', '12121', 1),
(8, 'JO', 'NATHAN', '090934324', 1),
(9, 'TJ', 'Relampagos', '092657894', 1),
(10, 'Fubuki', 'Shirou', '092656976498', 1),
(11, 'PAQUITO', 'ROAM', '09767721212', 1),
(12, 'rene', 'baterbonia', '09224244243424', 1);

-- --------------------------------------------------------

--
-- Table structure for table `gadgets`
--

CREATE TABLE `gadgets` (
  `gadget_id` int(11) NOT NULL,
  `customer_id` int(11) NOT NULL,
  `gadget_type` varchar(50) NOT NULL,
  `brand_model` varchar(100) NOT NULL,
  `problem` varchar(255) NOT NULL,
  `is_active` tinyint(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `gadgets`
--

INSERT INTO `gadgets` (`gadget_id`, `customer_id`, `gadget_type`, `brand_model`, `problem`, `is_active`) VALUES
(1, 1, 'Cp', 'POCO 7', 'Screen problem, busdak', 1),
(2, 4, 'Cp', 'Iphone', 'RAM', 1),
(3, 5, 'PC', 'MSI', 'MONITOR', 1),
(4, 1, 'LAPTOP', 'ACER', 'MEMORY RAM', 1),
(5, 4, 'cp', 'Iphone 20', 'No power', 1),
(6, 1, 'CP', 'Infinix', 'Screen', 1),
(7, 2, 'ip', 'samsung', 'lcd', 1),
(8, 9, 'CP', 'Iphone', 'Screen', 1),
(9, 9, 'CP', 'Samsung', 'LCD', 1),
(10, 10, 'CP', 'Iphone 20 Pro max', 'Screen', 1),
(11, 12, 'CP', 'vivo iQoo z11 turpo pro', 'gos tats', 1),
(12, 3, 'CP', 'TECHNOS', 'BACKPANEL', 1);

-- --------------------------------------------------------

--
-- Table structure for table `repairs`
--

CREATE TABLE `repairs` (
  `repair_id` int(11) NOT NULL,
  `gadget_id` int(11) NOT NULL,
  `technician_id` int(11) DEFAULT NULL,
  `repair_status` enum('Pending','In Progress','Completed') DEFAULT 'Pending',
  `repair_description` varchar(255) NOT NULL,
  `date_brought` date NOT NULL,
  `date_returned` date DEFAULT NULL,
  `repair_cost` decimal(10,2) DEFAULT 0.00,
  `is_archived` tinyint(1) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `repairs`
--

INSERT INTO `repairs` (`repair_id`, `gadget_id`, `technician_id`, `repair_status`, `repair_description`, `date_brought`, `date_returned`, `repair_cost`, `is_archived`) VALUES
(1, 1, 1, 'Completed', 'Screen problem, busdak', '2026-09-29', '2026-10-04', 5000.00, 1),
(2, 4, 1, 'Completed', 'MEMORY RAM', '2026-09-29', '2026-10-01', 10000.00, 1),
(3, 5, 2, 'Pending', 'No power', '2026-09-29', NULL, 0.00, 0),
(4, 6, 2, 'Pending', 'Screen', '2026-09-29', NULL, 0.00, 0),
(5, 8, 6, 'Completed', 'Screen', '2026-09-30', '2026-10-01', 500.00, 0),
(6, 9, 6, 'Completed', 'LCD', '2026-09-30', '2026-10-02', 5000.00, 1),
(7, 10, 7, 'Completed', 'Screen', '2026-10-02', NULL, 0.00, 1),
(8, 1, 8, 'Pending', 'Screen problem, busdak', '2026-10-04', NULL, 0.00, 0);

-- --------------------------------------------------------

--
-- Table structure for table `technicians`
--

CREATE TABLE `technicians` (
  `technician_id` int(11) NOT NULL,
  `account_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `technicians`
--

INSERT INTO `technicians` (`technician_id`, `account_id`) VALUES
(1, 7),
(2, 8),
(3, 10),
(4, 13),
(5, 15),
(6, 18),
(7, 22),
(8, 23),
(9, 26);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `user_id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` enum('Admin','Staff','Technician') NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `is_active` tinyint(1) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `accounts`
--
ALTER TABLE `accounts`
  ADD PRIMARY KEY (`account_id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- Indexes for table `customers`
--
ALTER TABLE `customers`
  ADD PRIMARY KEY (`customer_id`);

--
-- Indexes for table `gadgets`
--
ALTER TABLE `gadgets`
  ADD PRIMARY KEY (`gadget_id`),
  ADD KEY `customer_id` (`customer_id`);

--
-- Indexes for table `repairs`
--
ALTER TABLE `repairs`
  ADD PRIMARY KEY (`repair_id`),
  ADD KEY `gadget_id` (`gadget_id`),
  ADD KEY `technician_id` (`technician_id`);

--
-- Indexes for table `technicians`
--
ALTER TABLE `technicians`
  ADD PRIMARY KEY (`technician_id`),
  ADD KEY `fk_technician_account` (`account_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `accounts`
--
ALTER TABLE `accounts`
  MODIFY `account_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=40;

--
-- AUTO_INCREMENT for table `customers`
--
ALTER TABLE `customers`
  MODIFY `customer_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `gadgets`
--
ALTER TABLE `gadgets`
  MODIFY `gadget_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `repairs`
--
ALTER TABLE `repairs`
  MODIFY `repair_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `technicians`
--
ALTER TABLE `technicians`
  MODIFY `technician_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `gadgets`
--
ALTER TABLE `gadgets`
  ADD CONSTRAINT `gadgets_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`customer_id`) ON UPDATE CASCADE;

--
-- Constraints for table `repairs`
--
ALTER TABLE `repairs`
  ADD CONSTRAINT `repairs_ibfk_1` FOREIGN KEY (`gadget_id`) REFERENCES `gadgets` (`gadget_id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `repairs_ibfk_2` FOREIGN KEY (`technician_id`) REFERENCES `technicians` (`technician_id`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Constraints for table `technicians`
--
ALTER TABLE `technicians`
  ADD CONSTRAINT `fk_technician_account` FOREIGN KEY (`account_id`) REFERENCES `accounts` (`account_id`) ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
