# create USER table
CREATE TABLE `myConnection`.`user` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(40) NULL,
  `date_of_birth` DATE NULL,
  `birth_place` VARCHAR(20) NULL,
  `nationality` VARCHAR(25) NULL,
  `permission` INT NULL,
  `username` VARCHAR(5) NULL,
  `password` VARCHAR(256) NULL,
  `bank_account_number` INT NULL,
  `tax_number` INT NULL,
  `degree` VARCHAR(4) NULL,
  `which_semester` INT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC),
  UNIQUE INDEX `usercol_UNIQUE` (`tax_number` ASC),
  UNIQUE INDEX `bank_account_number_UNIQUE` (`bank_account_number` ASC));

# create DOCUMENT table
CREATE TABLE `myConnection`.`document` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `code` VARCHAR(45) NULL,
  `doc_name` VARCHAR(50) NULL,
  `uploaded_date` DATE NULL,
  `name` INT NULL,
  `download_counter` INT NULL,
  `file_size` INT NULL,
  `format` VARCHAR(5) NULL,
  `content` LONGBLOB NULL,
  `belong_to_course` VARCHAR(15) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `code_UNIQUE` (`code` ASC),
  UNIQUE INDEX `doc_name_UNIQUE` (`doc_name` ASC));

# create PRACTICE table
CREATE TABLE `myConnection`.`practice` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `subject_id` INT NULL,
  `teacher` INT NULL,
  `credit_number` INT NULL,
  `has_tasks` TINYINT NULL,
  `how_many_task` INT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC));

# create SUBJECT table
CREATE TABLE `myConnection`.`subject` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(30) NULL,
  `credit_number` INT NULL,
  `lectures_per_week` INT NULL,
  `has_practice` TINYINT NULL,
  `is_necessary` TINYINT NULL,
  `recommended_semester` INT NULL,
  `has_precondition` TINYINT NULL,
  `precondition_subject_id` INT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC));

# create SUBJECT_SEMESTER_TEACHER table
CREATE TABLE `myConnection`.`subject_semester_teacher` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `subject_id` INT NULL,
  `institution_teacher` VARCHAR(40) NULL,
  `starts_in_semester` VARCHAR(10) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC));

# create EXAM table
CREATE TABLE `myConnection`.`exam` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `created_by_name` VARCHAR(40) NULL,
  `which_room` VARCHAR(5) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC));

# create MESSAGE table
CREATE TABLE `myConnection`.`message` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `sender_id` INT NULL,
  `receiver_id` INT NULL,
  `cc_id` INT NULL,
  `content` VARCHAR(2000) NULL,
  `sending_date` TIMESTAMP NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC));

# create SECURITY_PERMISSION table
CREATE TABLE `myConnection`.`security_permission` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `description` VARCHAR(128) NULL,
  `module` VARCHAR(50) NULL,
  `name` VARCHAR(100) NULL,
  `title` VARCHAR(100) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC));

# create SECURITY_GROUP table
CREATE TABLE `myConnection`.`security_group` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `description` VARCHAR(100) NULL,
  `name` VARCHAR(100) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC));

# create DDS table
CREATE TABLE `myConnection`.`dds` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `date_time` TIMESTAMP NULL,
  `durability` INT NULL,
  `how_many_seats` INT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC));


