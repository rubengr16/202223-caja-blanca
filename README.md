# Ejecutar Todos los test
mvn test
# Ejecutar test de los métodos con tag add
mvn test -Dgroups="add"
# Ejecutar los test de una clase, por ejemplo la clase AddTest
mvn test -Dtest=AddTest test