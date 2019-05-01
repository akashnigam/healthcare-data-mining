# Search Interface backend deployment 👨‍⚕️

For starting the backEnd of the search interface, open this folder as Maven Project in eclipse or Intellij. 

Once the dependencies are loaded, Start the MySql server and change the database details in the file **healthcare-data-mining/searchInterface/src/main/resources/application.properties**
to the local database created in the Ontology creation. 

Now run the above **searchInterface/src/main/java/com/swm/searchInterface/SearchInterfaceApplication.java** as Application file 
which starts web service with url, http://localhost:8080/ which will be used by frontend.

once the service is started the log will look as below
![Log file adter starting the backend server](https://github.com/akashnigam/healthcare-data-mining/blob/master/searchInterface/backEnd_Log.jpg)


# Search Interface frontend deployment: 

For starting frontend server, go to the folder **/healthcare-data-mining/searchInterface/src/main/resources/front-end/app-project** 
in terminal and run the command ng serve which will start the service with url http://localhost:4200/. 

once the front end service is started the log will look as below
![Log file adter starting the front end service](https://github.com/akashnigam/healthcare-data-mining/blob/master/searchInterface/fron_end_log.jpg)
