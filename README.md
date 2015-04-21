UTN FRBA - TACS 2015 - 1C - GRUPO 1  


Build: 

    mvn -e -DskipTests clean package


Test: 

	mvn -X test 

Run: 

    mvn -e -DskipTests appengine:devserver  
    

Ayuda: 

    mvn help:describe -D plugin=appengine