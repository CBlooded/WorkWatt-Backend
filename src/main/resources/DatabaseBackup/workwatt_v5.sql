-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Cze 01, 2025 at 02:30 AM
-- Wersja serwera: 10.4.32-MariaDB
-- Wersja PHP: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `workwatt`
--
CREATE DATABASE IF NOT EXISTS `workwatt` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `workwatt`;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `activation_host`
--

DROP TABLE IF EXISTS `activation_host`;
CREATE TABLE `activation_host` (
  `id` varchar(36) NOT NULL,
  `user_id` varchar(36) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `activation_host`
--

INSERT INTO `activation_host` (`id`, `user_id`) VALUES
('eee987ed-3593-47eb-9ac5-b8bafb5db590', 'c760850a-634b-4cb6-9a88-52f629c567a2');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `computer`
--

DROP TABLE IF EXISTS `computer`;
CREATE TABLE `computer` (
  `id` int(11) NOT NULL,
  `name` varchar(250) NOT NULL,
  `user_id` varchar(36) DEFAULT NULL,
  `consumption` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `computer`
--

INSERT INTO `computer` (`id`, `name`, `user_id`, `consumption`) VALUES
(1, 'PC-Alice-01', '11111111-aaaa-bbbb-cccc-111111111111', 125.5),
(2, 'PC-Bob-02', '22222222-bbbb-cccc-dddd-222222222222', 110.3),
(3, 'PC-Charlie-03', '33333333-cccc-dddd-eeee-333333333333', 98.7),
(4, 'PC-Wda-04', 'c760850a-634b-4cb6-9a88-52f629c567a2', 135.2),
(5, 'PC-Shared-05', NULL, 120);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `hierarchy`
--

DROP TABLE IF EXISTS `hierarchy`;
CREATE TABLE `hierarchy` (
  `id` int(11) NOT NULL,
  `supervisor_id` varchar(36) NOT NULL,
  `subordinate_id` varchar(36) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `hierarchy`
--

INSERT INTO `hierarchy` (`id`, `supervisor_id`, `subordinate_id`) VALUES
(1, '33333333-cccc-dddd-eeee-333333333333', '11111111-aaaa-bbbb-cccc-111111111111'),
(2, '33333333-cccc-dddd-eeee-333333333333', '22222222-bbbb-cccc-dddd-222222222222'),
(3, '33333333-cccc-dddd-eeee-333333333333', 'c760850a-634b-4cb6-9a88-52f629c567a2'),
(4, '11111111-aaaa-bbbb-cccc-111111111111', '22222222-bbbb-cccc-dddd-222222222222'),
(5, 'c760850a-634b-4cb6-9a88-52f629c567a2', '22222222-bbbb-cccc-dddd-222222222222');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `usage_history`
--

DROP TABLE IF EXISTS `usage_history`;
CREATE TABLE `usage_history` (
  `id` int(11) NOT NULL,
  `user_id` varchar(36) NOT NULL,
  `start` datetime NOT NULL,
  `stop` datetime DEFAULT NULL,
  `computer_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `usage_history`
--

INSERT INTO `usage_history` (`id`, `user_id`, `start`, `stop`, `computer_id`) VALUES
(41, '11111111-aaaa-bbbb-cccc-111111111111', '2025-05-01 08:00:00', '2025-05-01 10:15:00', 1),
(42, '22222222-bbbb-cccc-dddd-222222222222', '2025-05-01 09:00:00', '2025-05-01 11:30:00', 2),
(43, '33333333-cccc-dddd-eeee-333333333333', '2025-05-01 12:00:00', NULL, 3),
(44, 'c760850a-634b-4cb6-9a88-52f629c567a2', '2025-05-02 07:45:00', '2025-05-02 09:00:00', 1),
(45, '11111111-aaaa-bbbb-cccc-111111111111', '2025-05-03 10:00:00', '2025-05-03 11:00:00', 2),
(46, '22222222-bbbb-cccc-dddd-222222222222', '2025-05-04 14:00:00', '2025-05-04 15:45:00', 4),
(47, '33333333-cccc-dddd-eeee-333333333333', '2025-05-04 16:00:00', NULL, 5),
(48, 'c760850a-634b-4cb6-9a88-52f629c567a2', '2025-05-05 08:30:00', '2025-05-05 09:30:00', 1),
(49, '11111111-aaaa-bbbb-cccc-111111111111', '2025-05-06 11:00:00', '2025-05-06 12:00:00', 3),
(50, '22222222-bbbb-cccc-dddd-222222222222', '2025-05-06 13:00:00', NULL, 2),
(51, '33333333-cccc-dddd-eeee-333333333333', '2025-05-07 09:00:00', '2025-05-07 10:00:00', 4),
(52, 'c760850a-634b-4cb6-9a88-52f629c567a2', '2025-05-08 15:30:00', '2025-05-08 17:00:00', 2),
(53, '11111111-aaaa-bbbb-cccc-111111111111', '2025-05-09 10:15:00', NULL, 5),
(54, '22222222-bbbb-cccc-dddd-222222222222', '2025-05-10 07:00:00', '2025-05-10 08:30:00', 1),
(55, '33333333-cccc-dddd-eeee-333333333333', '2025-05-11 14:00:00', '2025-05-11 16:00:00', 3),
(56, 'c760850a-634b-4cb6-9a88-52f629c567a2', '2025-05-12 08:00:00', NULL, 4),
(57, '11111111-aaaa-bbbb-cccc-111111111111', '2025-05-13 09:30:00', '2025-05-13 10:45:00', 2),
(58, '22222222-bbbb-cccc-dddd-222222222222', '2025-05-13 11:00:00', '2025-05-13 12:15:00', 3),
(59, '33333333-cccc-dddd-eeee-333333333333', '2025-05-14 08:00:00', NULL, 5),
(60, 'c760850a-634b-4cb6-9a88-52f629c567a2', '2025-05-15 10:00:00', '2025-05-15 11:30:00', 1);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` varchar(36) NOT NULL,
  `email` varchar(250) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` int(11) NOT NULL,
  `first_name` varchar(250) NOT NULL,
  `last_name` varchar(250) NOT NULL,
  `enabled` tinyint(1) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `email`, `password`, `role`, `first_name`, `last_name`, `enabled`) VALUES
('11111111-aaaa-bbbb-cccc-111111111111', 'alice@example.com', '$2a$10$abcdehashedpass1', 1, 'Alice', 'Kowalska', 1),
('22222222-bbbb-cccc-dddd-222222222222', 'bob@example.com', '$2a$10$abcdehashedpass2', 2, 'Bob', 'Nowak', 1),
('33333333-cccc-dddd-eeee-333333333333', 'charlie@example.com', '$2a$10$abcdehashedpass3', 0, 'Charlie', 'Wiśniewski', 0),
('c760850a-634b-4cb6-9a88-52f629c567a2', 'vokkiyipso@gufum.com', '$2a$10$yZfxx2geAYzpog7m51Js6eXxBdVFbfrsOHdSKLKASIiCla5hJJySC', 2, 'wda', 'wda', 1);

--
-- Indeksy dla zrzutów tabel
--

--
-- Indeksy dla tabeli `activation_host`
--
ALTER TABLE `activation_host`
  ADD KEY `fk_acitvationHost_user` (`user_id`);

--
-- Indeksy dla tabeli `computer`
--
ALTER TABLE `computer`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_computer_users` (`user_id`);

--
-- Indeksy dla tabeli `hierarchy`
--
ALTER TABLE `hierarchy`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_hierarchy_supervisor` (`supervisor_id`),
  ADD KEY `fk_hierarchy_subordinate` (`subordinate_id`);

--
-- Indeksy dla tabeli `usage_history`
--
ALTER TABLE `usage_history`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_user_usage_history` (`user_id`),
  ADD KEY `fk_computer_usage_history` (`computer_id`) USING BTREE;

--
-- Indeksy dla tabeli `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `computer`
--
ALTER TABLE `computer`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `hierarchy`
--
ALTER TABLE `hierarchy`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `usage_history`
--
ALTER TABLE `usage_history`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=61;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `activation_host`
--
ALTER TABLE `activation_host`
  ADD CONSTRAINT `fk_acitvationHost_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `computer`
--
ALTER TABLE `computer`
  ADD CONSTRAINT `computer_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `hierarchy`
--
ALTER TABLE `hierarchy`
  ADD CONSTRAINT `hierarchy_ibfk_1` FOREIGN KEY (`supervisor_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `hierarchy_ibfk_2` FOREIGN KEY (`subordinate_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `usage_history`
--
ALTER TABLE `usage_history`
  ADD CONSTRAINT `usage_history_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `usage_history_ibfk_2` FOREIGN KEY (`computer_id`) REFERENCES `computer` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
