Getting Started
These are instructions to create the ontology schema and dump data into it.

Prerequisites
1. MySQL Workbench Installed
2. CSV file outputted by Metamap.

Steps
1. Open the MySQL Workbench and execute Ontology.sql. This will create all the tables. Now we will add data using below steps.
2. Execute the csv_parsing_to_table.sql script. Change the location path of csv in the script before execution. This will put data of csv as it is in patientsinfo table.
3. Execute the complete_ontology_insertion_prc.sql script to create the stored procedure for insertion from patientsinfo to Ontology tables.
4. Call the stored procedure using the statement: CALL `ontology`.`ontology_insertion_prc`();

Enjoy!!
