## **Como se hizo el programa.**
Primeramente identificamos las funcionalidades principales del programa,
seguido programamos cada funcionalidad individualmente sin una iterfaz definida (por consola),
esto se hizo para verificar que las funcionalidades se ejecutaran sin ningun problema.
Esta primera parte del proceso fue probada en un local host.
En la segunda fase del proyecto, decidimos integrar todas las funcionalidades en un mismo
programa, en el cual immplementaríamos la interfaz gráfica.
Las soluciones están hechas sobre UDP, tanto la transacción de mensajes entre apartamentos
como la transacción de mensajes entre portería y los apartamentos.

## **Dificultades a la hora de hacer el programa.**
1. No sabíamos utilizar  SMTP utilizando sockets.
2. La información de apoyo que buscamos (videos/tutoriales) se encontraba desactualizada,
la gran mayoría de tutoriales fueron hechos aproximadamente hace 6 años, lo cual nos
dificultó el desarrollo del proyecto.
3. Debido a que cada integrante se encontraba en un lugar diferente, tuvimos dificultades
entendiendo el funcionamiento del programa, esto es porque emulamos el comportamiento del programa
en un red local.

## **Conclusiones**
1. El conocimiento adquirido con respecto a como implementar este tipo de programas es
ciertamente muy útil, ya que podemos apreciar la cohesión entre la teoría (temas vistos en clase)
y la puesta en práctica de los temas aprendidos.
2. El haber desarrollado un programa que tiene la capacidad de conectarse con otras aplicaciones
nos direcciona muy positivamente a la hora de implementar otros programas, ya que ahora
podríamos añadir funcionalidades nuevas con más potencial.