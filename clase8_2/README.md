# Clase 8 - 2

Ejercicio 1 

Reutilice la clase PoligonoIrreg elaborada en la clase 6, pero ahora almacene los vértices del polígono en un objeto ArrayList (véase https://www.geeksforgeeks.org/list-interface-java-examples/?ref=gcse ). 

Se debe disponer de los métodos anadeVertice para agregar un nuevo vértice al polígono, es decir un objeto Coordenada, y sobrescribir el método toString( ) para imprimir el conjunto de vértices que componen al polígono. 

Dentro de este método debe hacer uso de un bucle for-each para imprimir los elementos de ArrayList ( for( : ){ … }). 

Pruebe su clase PoligonoIrreg al imprimir un objeto recién creado, después agregue tres vértices con valores de coordenadas aleatorias comprendidos entre +100.000 y -100.000 y vuelva a imprimir el objeto (la idea es que puedan existir valores positivos y/o negativos con enteros y decimales distintos de cero).