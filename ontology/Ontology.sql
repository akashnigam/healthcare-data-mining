CREATE TABLE `diseases`
(
  `disease_id` int PRIMARY KEY,
  `disease_name` varchar(255),
  `description` varchar(255),
  `records_count` int,
  `has_symptom_count` int,
  `has_treatment_count` int
);

CREATE TABLE `treatment`
(
  `treatment_id` int PRIMARY KEY,
  `description` varchar(255),
  `type` varchar(255)
);

CREATE TABLE `symptoms`
(
  `symptom_id` int PRIMARY KEY,
  `description` varchar(255)
);

CREATE TABLE `disease_symptoms`
(
  `disease_id` int,
  `symptom_id` int,
  `count` int
);

CREATE TABLE `disease_treatments`
(
  `disease_id` int,
  `treatment_id` int,
  `count` int
);

CREATE TABLE `anatomy`
(
  `anatomy_id` int PRIMARY KEY,
  `description` varchar(255)
);

CREATE TABLE `disease_anatomy`
(
  `disease_id` int,
  `anatomy_id` int
);

ALTER TABLE `disease_symptoms` ADD FOREIGN KEY (`disease_id`) REFERENCES `diseases` (`disease_id`);

ALTER TABLE `disease_symptoms` ADD FOREIGN KEY (`symptom_id`) REFERENCES `treatment` (`treatment_id`);

ALTER TABLE `disease_treatments` ADD FOREIGN KEY (`disease_id`) REFERENCES `diseases` (`disease_id`);

ALTER TABLE `disease_treatments` ADD FOREIGN KEY (`treatment_id`) REFERENCES `treatment` (`treatment_id`);

ALTER TABLE `disease_anatomy` ADD FOREIGN KEY (`disease_id`) REFERENCES `diseases` (`disease_id`);

ALTER TABLE `disease_anatomy` ADD FOREIGN KEY (`anatomy_id`) REFERENCES `anatomy` (`anatomy_id`);
