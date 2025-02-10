-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 10-02-2025 a las 12:42:00
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `tarea4dwesalejandropelayobd`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `credenciales`
--

CREATE TABLE `credenciales` (
  `id` bigint(20) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `usuario` varchar(255) DEFAULT NULL,
  `id_persona` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `credenciales`
--

INSERT INTO `credenciales` (`id`, `password`, `usuario`, `id_persona`) VALUES
(1, 'admin', 'admin', 0),
(3, 'pelayovv20.', 'pelayovv20', 3),
(4, 'alejandromg73.', 'alejandromg73', 4);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ejemplares`
--

CREATE TABLE `ejemplares` (
  `id` bigint(20) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `idplanta` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `ejemplares`
--

INSERT INTO `ejemplares` (`id`, `nombre`, `idplanta`) VALUES
(9, 'ALCORNOQUE_9', 28),
(10, 'CALADIO_10', 23),
(11, 'FILODENDRO_11', 27),
(12, 'CADAVER_12', 20),
(13, 'HIEDRA_13', 25),
(14, 'JUNQUILLO_14', 22),
(15, 'ROBLE_15', 24),
(16, 'ROSA_16', 21),
(17, 'TULIPAN_17', 26),
(18, 'ALCORNOQUE_18', 28),
(19, 'ALCORNOQUE_19', 28),
(20, 'CALADIO_20', 23),
(21, 'CALADIO_21', 23),
(22, 'FILODENDRO_22', 27),
(23, 'HIEDRA_23', 25),
(24, 'HIEDRA_24', 25),
(25, 'JUNQUILLO_25', 22),
(26, 'JUNQUILLO_26', 22),
(27, 'JUNQUILLO_27', 22),
(28, 'JUNQUILLO_28', 22),
(29, 'TULIPAN_29', 26),
(30, 'ALCORNOQUE_30', 28),
(31, 'CALADIO_31', 23),
(32, 'FILODENDRO_32', 27),
(33, 'FILODENDRO_33', 27),
(34, 'CADAVER_34', 20),
(35, 'HIEDRA_35', 25),
(36, 'ROBLE_36', 24),
(37, 'ROSA_37', 21),
(38, 'HIEDRA_38', 25),
(39, 'ALCORNOQUE_39', 28),
(40, 'ROBLE_40', 24),
(41, 'TULIPAN_41', 26),
(42, 'FILODENDRO_42', 27);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `mensajes`
--

CREATE TABLE `mensajes` (
  `id` bigint(20) NOT NULL,
  `fecha_hora` datetime(6) NOT NULL,
  `mensaje` varchar(500) NOT NULL,
  `id_ejemplar` bigint(20) NOT NULL,
  `id_persona` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `mensajes`
--

INSERT INTO `mensajes` (`id`, `fecha_hora`, `mensaje`, `id_ejemplar`, `id_persona`) VALUES
(7, '2025-02-10 12:32:38.000000', 'Añadido el ejemplar ALCORNOQUE_9', 9, 0),
(8, '2025-02-10 12:32:44.000000', 'Añadido el ejemplar CALADIO_10', 10, 0),
(9, '2025-02-10 12:32:51.000000', 'Añadido el ejemplar FILODENDRO_11', 11, 0),
(10, '2025-02-10 12:32:58.000000', 'Añadido el ejemplar CADAVER_12', 12, 0),
(11, '2025-02-10 12:33:02.000000', 'Añadido el ejemplar HIEDRA_13', 13, 0),
(12, '2025-02-10 12:33:06.000000', 'Añadido el ejemplar JUNQUILLO_14', 14, 0),
(13, '2025-02-10 12:33:10.000000', 'Añadido el ejemplar ROBLE_15', 15, 0),
(14, '2025-02-10 12:33:13.000000', 'Añadido el ejemplar ROSA_16', 16, 0),
(16, '2025-02-10 12:33:39.000000', 'Añadido el ejemplar ALCORNOQUE_18', 18, 0),
(18, '2025-02-10 12:33:49.000000', 'Añadido el ejemplar CALADIO_20', 20, 0),
(20, '2025-02-10 12:33:57.000000', 'Añadido el ejemplar FILODENDRO_22', 22, 0),
(21, '2025-02-10 12:34:02.000000', 'Añadido el ejemplar HIEDRA_23', 23, 0),
(23, '2025-02-10 12:34:10.000000', 'Añadido el ejemplar JUNQUILLO_25', 25, 0),
(24, '2025-02-10 12:34:14.000000', 'Añadido el ejemplar JUNQUILLO_26', 26, 0),
(27, '2025-02-10 12:34:32.000000', 'Añadido el ejemplar TULIPAN_29', 29, 0),
(28, '2025-02-10 12:37:31.000000', 'Añadido el ejemplar ALCORNOQUE_30', 30, 3),
(29, '2025-02-10 12:37:39.000000', 'Añadido el ejemplar CALADIO_31', 31, 3),
(30, '2025-02-10 12:37:43.000000', 'Añadido el ejemplar FILODENDRO_32', 32, 3),
(31, '2025-02-10 12:37:48.000000', 'Añadido el ejemplar FILODENDRO_33', 33, 3),
(32, '2025-02-10 12:37:54.000000', 'Añadido el ejemplar CADAVER_34', 34, 3),
(33, '2025-02-10 12:37:59.000000', 'Añadido el ejemplar HIEDRA_35', 35, 3),
(34, '2025-02-10 12:38:05.000000', 'Añadido el ejemplar ROBLE_36', 36, 3),
(35, '2025-02-10 12:38:07.000000', 'Añadido el ejemplar ROSA_37', 37, 3),
(36, '2025-02-10 12:39:07.000000', 'Añadido el ejemplar HIEDRA_38', 38, 4),
(37, '2025-02-10 12:39:11.000000', 'Añadido el ejemplar ALCORNOQUE_39', 39, 4),
(38, '2025-02-10 12:39:13.000000', 'Añadido el ejemplar ROBLE_40', 40, 4),
(39, '2025-02-10 12:39:17.000000', 'Añadido el ejemplar TULIPAN_41', 41, 4),
(40, '2025-02-10 12:39:23.000000', 'Añadido el ejemplar FILODENDRO_42', 42, 4);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `personas`
--

CREATE TABLE `personas` (
  `id` bigint(20) NOT NULL,
  `email` varchar(255) NOT NULL,
  `nombre` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `personas`
--

INSERT INTO `personas` (`id`, `email`, `nombre`) VALUES
(0, 'admin@admin.es', 'admin'),
(3, 'pelayovv20@educastur.es', 'pelayo'),
(4, 'alejandromg73@educastur.es', 'alejandro');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `plantas`
--

CREATE TABLE `plantas` (
  `id` bigint(20) NOT NULL,
  `codigo` varchar(255) DEFAULT NULL,
  `nombre_cientifico` varchar(255) DEFAULT NULL,
  `nombre_comun` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `plantas`
--

INSERT INTO `plantas` (`id`, `codigo`, `nombre_cientifico`, `nombre_comun`) VALUES
(20, 'CADAVER', 'Rafflesia arnoldii', 'Flor Cadáver'),
(21, 'ROSA', 'Rosae', 'Rosa'),
(22, 'JUNQUILLO', 'Narcissus jonquilla', 'Junquillo'),
(23, 'CALADIO', 'Caladium', 'Caladio'),
(24, 'ROBLE', 'Quercus robur', 'Roble'),
(25, 'HIEDRA', 'Hedera helix', 'Hiedra'),
(26, 'TULIPAN', 'Tulipa spp', 'Tulipán'),
(27, 'FILODENDRO', 'Philodendron', 'Filodendro'),
(28, 'ALCORNOQUE', 'Quercus suber', 'Alcornoque');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `credenciales`
--
ALTER TABLE `credenciales`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKgl50fmouks2ue8s9yclvv059j` (`usuario`),
  ADD UNIQUE KEY `UKkj0bakygq84a8uwy2avcihxqi` (`id_persona`);

--
-- Indices de la tabla `ejemplares`
--
ALTER TABLE `ejemplares`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKclleiwyydddhkx72v38u6uw0l` (`idplanta`);

--
-- Indices de la tabla `mensajes`
--
ALTER TABLE `mensajes`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKidbx1mhngh3c3ry5bqisftxbv` (`id_ejemplar`),
  ADD KEY `FK2e6au5w562m7skcvx9jckiba6` (`id_persona`);

--
-- Indices de la tabla `personas`
--
ALTER TABLE `personas`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKlrw7flsg11d8nhgyvueqtnp8e` (`email`);

--
-- Indices de la tabla `plantas`
--
ALTER TABLE `plantas`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKbqo6lbeads0ifdh6dohhfhryp` (`codigo`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `credenciales`
--
ALTER TABLE `credenciales`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT de la tabla `ejemplares`
--
ALTER TABLE `ejemplares`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=43;

--
-- AUTO_INCREMENT de la tabla `mensajes`
--
ALTER TABLE `mensajes`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=41;

--
-- AUTO_INCREMENT de la tabla `personas`
--
ALTER TABLE `personas`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT de la tabla `plantas`
--
ALTER TABLE `plantas`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=29;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `credenciales`
--
ALTER TABLE `credenciales`
  ADD CONSTRAINT `FKgntr1s6h8ryu511tqjln50yp2` FOREIGN KEY (`id_persona`) REFERENCES `personas` (`id`);

--
-- Filtros para la tabla `ejemplares`
--
ALTER TABLE `ejemplares`
  ADD CONSTRAINT `FKclleiwyydddhkx72v38u6uw0l` FOREIGN KEY (`idplanta`) REFERENCES `plantas` (`id`);

--
-- Filtros para la tabla `mensajes`
--
ALTER TABLE `mensajes`
  ADD CONSTRAINT `FK2e6au5w562m7skcvx9jckiba6` FOREIGN KEY (`id_persona`) REFERENCES `personas` (`id`),
  ADD CONSTRAINT `FKidbx1mhngh3c3ry5bqisftxbv` FOREIGN KEY (`id_ejemplar`) REFERENCES `ejemplares` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
