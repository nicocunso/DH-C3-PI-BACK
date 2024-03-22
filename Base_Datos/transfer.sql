-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema renta_autos
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema renta_autos
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `renta_autos` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `renta_autos` ;

-- -----------------------------------------------------
-- Table `renta_autos`.`tipo_autos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `renta_autos`.`tipo_autos` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `tipo_auto` VARCHAR(50) NOT NULL,
  `creado` TIMESTAMP NOT NULL,
  `actualizado` TIMESTAMP NOT NULL,
  `creado_por` VARCHAR(20) NOT NULL,
  `actualizado_por` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 11
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `renta_autos`.`autos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `renta_autos`.`autos` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `id_tipo_auto` INT NOT NULL,
  `matricula` VARCHAR(50) NOT NULL,
  `marca` VARCHAR(50) NOT NULL,
  `modelo` VARCHAR(50) NOT NULL,
  `tipo_combustible` VARCHAR(50) NULL DEFAULT NULL,
  `trasmision` VARCHAR(50) NULL DEFAULT NULL,
  `kilometraje` INT NOT NULL,
  `precioXdia` DECIMAL(10,0) NOT NULL,
  `estado` VARCHAR(20) NOT NULL,
  `nro_puertas` INT NOT NULL,
  `nro_maleteros` INT NOT NULL,
  `nro_pasajeros` INT NOT NULL,
  `creado` TIMESTAMP NOT NULL,
  `actualizado` TIMESTAMP NULL DEFAULT NULL,
  `creado_por` VARCHAR(20) NOT NULL,
  `actualizado_por` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `id_tipo_auto` (`id_tipo_auto` ASC) VISIBLE,
  CONSTRAINT `autos_ibfk_1`
    FOREIGN KEY (`id_tipo_auto`)
    REFERENCES `renta_autos`.`tipo_autos` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 11
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `renta_autos`.`tipo_usuarios`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `renta_autos`.`tipo_usuarios` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `tipo_usuario` VARCHAR(20) NOT NULL,
  `descripcion` VARCHAR(50) NULL DEFAULT NULL,
  `creado` TIMESTAMP NOT NULL,
  `actualizado` TIMESTAMP NULL DEFAULT NULL,
  `creado_por` VARCHAR(20) NOT NULL,
  `actualizado_por` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `renta_autos`.`usuarios`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `renta_autos`.`usuarios` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `id_tipo_Usuario` INT NOT NULL,
  `tipo_documento` VARCHAR(3) NOT NULL,
  `numero_documento` VARCHAR(20) NOT NULL,
  `nombres` VARCHAR(50) NOT NULL,
  `apellidos` VARCHAR(50) NULL DEFAULT NULL,
  `licencia_conducir` VARCHAR(50) NOT NULL,
  `direccion` VARCHAR(50) NOT NULL,
  `correo_electronico` VARCHAR(50) NOT NULL,
  `fecha_nacimiento` DATE NOT NULL,
  `creado` TIMESTAMP NOT NULL,
  `actualizado` TIMESTAMP NULL DEFAULT NULL,
  `creado_por` VARCHAR(20) NOT NULL,
  `actualizado_por` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `id_tipo_Usuario` (`id_tipo_Usuario` ASC) VISIBLE,
  CONSTRAINT `usuarios_ibfk_1`
    FOREIGN KEY (`id_tipo_Usuario`)
    REFERENCES `renta_autos`.`tipo_usuarios` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 11
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `renta_autos`.`rentas`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `renta_autos`.`rentas` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `id_usuario` INT NOT NULL,
  `id_auto` INT NOT NULL,
  `fecha_inicio` TIMESTAMP NOT NULL,
  `fecha_entrega` TIMESTAMP NOT NULL,
  `creado` TIMESTAMP NOT NULL,
  `actualizado` TIMESTAMP NULL DEFAULT NULL,
  `creado_por` VARCHAR(20) NOT NULL,
  `actualizado_por` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `id_usuario` (`id_usuario` ASC) VISIBLE,
  INDEX `id_auto` (`id_auto` ASC) VISIBLE,
  CONSTRAINT `rentas_ibfk_1`
    FOREIGN KEY (`id_usuario`)
    REFERENCES `renta_autos`.`usuarios` (`id`),
  CONSTRAINT `rentas_ibfk_2`
    FOREIGN KEY (`id_auto`)
    REFERENCES `renta_autos`.`autos` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 11
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `renta_autos`.`facturas`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `renta_autos`.`facturas` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `id_renta` INT NOT NULL,
  `valor_unitario` DECIMAL(10,0) NOT NULL,
  `valor_impuestos` DECIMAL(10,0) NULL DEFAULT NULL,
  `valor_total` DECIMAL(10,0) NOT NULL,
  `fecha_factura` TIMESTAMP NOT NULL,
  `creado` TIMESTAMP NOT NULL,
  `actualizado` TIMESTAMP NULL DEFAULT NULL,
  `creado_por` VARCHAR(20) NOT NULL,
  `actualizado_por` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `id_renta` (`id_renta` ASC) VISIBLE,
  CONSTRAINT `facturas_ibfk_1`
    FOREIGN KEY (`id_renta`)
    REFERENCES `renta_autos`.`rentas` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 11
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `renta_autos`.`favoritos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `renta_autos`.`favoritos` (
  `id_usuario` INT NOT NULL,
  `id_auto` INT NOT NULL,
  `creado` TIMESTAMP NOT NULL,
  `actualizado` TIMESTAMP NOT NULL,
  `creado_por` VARCHAR(20) NOT NULL,
  `actualizado_por` VARCHAR(20) NOT NULL,
  INDEX `id_usuario` (`id_usuario` ASC) VISIBLE,
  INDEX `id_auto` (`id_auto` ASC) VISIBLE,
  CONSTRAINT `favoritos_ibfk_1`
    FOREIGN KEY (`id_usuario`)
    REFERENCES `renta_autos`.`usuarios` (`id`),
  CONSTRAINT `favoritos_ibfk_2`
    FOREIGN KEY (`id_auto`)
    REFERENCES `renta_autos`.`autos` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `renta_autos`.`imagenes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `renta_autos`.`imagenes` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `id_auto` INT NOT NULL,
  `url` VARCHAR(400) NOT NULL,
  `creado` TIMESTAMP NOT NULL,
  `actualizado` TIMESTAMP NOT NULL,
  `creado_por` VARCHAR(20) NOT NULL,
  `actualizado_por` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `id_auto` (`id_auto` ASC) VISIBLE,
  CONSTRAINT `imagenes_ibfk_1`
    FOREIGN KEY (`id_auto`)
    REFERENCES `renta_autos`.`autos` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 11
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `renta_autos`.`pagos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `renta_autos`.`pagos` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `id_factura` INT NOT NULL,
  `valor_pago` DECIMAL(10,0) NOT NULL,
  `forma_pago` VARCHAR(20) NOT NULL,
  `estado_pago` VARCHAR(20) NOT NULL,
  `creado` TIMESTAMP NOT NULL,
  `actualizado` TIMESTAMP NULL DEFAULT NULL,
  `creado_por` VARCHAR(20) NOT NULL,
  `actualizado_por` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `id_factura` (`id_factura` ASC) VISIBLE,
  CONSTRAINT `pagos_ibfk_1`
    FOREIGN KEY (`id_factura`)
    REFERENCES `renta_autos`.`facturas` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 45
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
