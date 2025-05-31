-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Czas generowania: 31 Maj 2025, 18:13
-- Wersja serwera: 10.4.27-MariaDB
-- Wersja PHP: 8.0.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Baza danych: `workwatt`
--
CREATE DATABASE IF NOT EXISTS `workwatt` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `workwatt`;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `computer`
--

DROP TABLE IF EXISTS `computer`;
CREATE TABLE `computer` (
  `id` int(11) NOT NULL,
  `name` varchar(250) NOT NULL,
  `user_id` varchar(36) NOT NULL,
  `consumption` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

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
-- Zrzut danych tabeli `users`
--

INSERT INTO `users` (`id`, `email`, `password`, `role`, `first_name`, `last_name`, `enabled`) VALUES
('256a8dd8-7f1a-4dcc-b124-3e7e4d0f8e20', 'email@example.com', '$2a$10$d5Kp/WKeHmUreF6NKUJoR.OoCjsyvvi4k6p9YL/s0gsBxzzfI4CNu', 0, 'fName', 'lName', 1);

--
-- Indeksy dla zrzutów tabel
--

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
-- AUTO_INCREMENT dla zrzuconych tabel
--

--
-- AUTO_INCREMENT dla tabeli `computer`
--
ALTER TABLE `computer`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT dla tabeli `hierarchy`
--
ALTER TABLE `hierarchy`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT dla tabeli `usage_history`
--
ALTER TABLE `usage_history`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- Ograniczenia dla zrzutów tabel
--

--
-- Ograniczenia dla tabeli `computer`
--
ALTER TABLE `computer`
  ADD CONSTRAINT `computer_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ograniczenia dla tabeli `hierarchy`
--
ALTER TABLE `hierarchy`
  ADD CONSTRAINT `hierarchy_ibfk_1` FOREIGN KEY (`supervisor_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `hierarchy_ibfk_2` FOREIGN KEY (`subordinate_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ograniczenia dla tabeli `usage_history`
--
ALTER TABLE `usage_history`
  ADD CONSTRAINT `usage_history_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `usage_history_ibfk_2` FOREIGN KEY (`computer_id`) REFERENCES `computer` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
