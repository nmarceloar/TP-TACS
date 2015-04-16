
UTN FRBA - TACS 2015 - 1C - GRUPO 1  



Build: 

    mvn -X clean package

Run: 

    mvn appengine:devserver  
    
Deploy al appengine: 

 	mvn appengine:update 

Ayuda: 

    mvn help:describe -D plugin=appengine