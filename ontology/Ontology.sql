DROP database if exists ontology;
CREATE database ontology;
use ontology;

CREATE TABLE `diseases`
(
  `disease_id` varchar(255) PRIMARY KEY,
  `disease_name` varchar(255),
  `description` varchar(255),
  `records_count` int,
  `has_symptom_count` int,
  `has_treatment_count` int
);

CREATE TABLE `treatment`
(
  `treatment_id` varchar(255) PRIMARY KEY,
  `description` varchar(255),
  `type` varchar(255)
);

CREATE TABLE `symptoms`
(
  `symptom_id` varchar(255) PRIMARY KEY,
  `description` varchar(255)
);

CREATE TABLE `disease_symptoms`
(
  `disease_id` varchar(255),
  `symptom_id` varchar(255),
  `count` int
);

CREATE TABLE `disease_treatments`
(
  `disease_id` varchar(255),
  `treatment_id` varchar(255),
  `count` int
);

CREATE TABLE `anatomy`
(
  `anatomy_id` varchar(255) PRIMARY KEY,
  `description` varchar(255)
);

CREATE TABLE `disease_anatomy`
(
  `disease_id` varchar(255),
  `anatomy_id` varchar(255)
);

ALTER TABLE `disease_symptoms` ADD FOREIGN KEY (`disease_id`) REFERENCES `diseases` (`disease_id`);

ALTER TABLE `disease_symptoms` ADD FOREIGN KEY (`symptom_id`) REFERENCES `symptoms` (`symptom_id`);

ALTER TABLE `disease_treatments` ADD FOREIGN KEY (`disease_id`) REFERENCES `diseases` (`disease_id`);

ALTER TABLE `disease_treatments` ADD FOREIGN KEY (`treatment_id`) REFERENCES `treatment` (`treatment_id`);

ALTER TABLE `disease_anatomy` ADD FOREIGN KEY (`disease_id`) REFERENCES `diseases` (`disease_id`);

ALTER TABLE `disease_anatomy` ADD FOREIGN KEY (`anatomy_id`) REFERENCES `anatomy` (`anatomy_id`);
