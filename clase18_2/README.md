
# Clase X - 2

Servidor HTTP el código fuente https://youtu.be/xlZQ0bGHYVE 

Comentar su código fuente de acuerdo con la explicación del video. 

Ejercicio 1 ¿Qué cree que sucedería con los tiempos de respuesta promedio del servidor si en la línea: server.setExecutor(Executors.newFixedThreadPool(8)); se cambia el ocho por un uno? 

Compruebe si tiene razón apoyándose en la siguiente sugerencia: Agregue un sleep de 5 segundos en la tarea que realiza el servidor y tome los tiempos que demora el comando curl con ayuda del comando time, al ejecutarse simultáneamente en dos terminales con el pool de 8 threads vs el pool de 1 thread. 

Envíe por teams la captura de pantalla donde se observe la salida del comando time en las dos terminales y una explicación de lo que está sucediendo.


INSTRUCCIONES

En la linea 73, cambias el 8 por el 1

Seguido abres dos terminales y pones

time curl -X POST http://localhost:8080/task -d "2,3,4"

en ambas terminales, una vez que lo tengas asi, colocas todo para ejecutar uno y rapidamente ejecutar el otro

Haces lo mismo pero ahora cambiando el 8 por el 1 y ya tomas las fotos
