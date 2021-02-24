#"make" para compilarlo
#"java TSP -op fichero.tsp" para ejecutarlo
#"make op_test" siendo op=fb|av|pd|rp para ejecutar las 
#	pruebas con la opción correspondiente
#"make all_test" para ejecutar las pruebas con todas las pruebas
#"make clean" para eliminar el ejecutable y archivos objeto.
### ahora hace la prueba con solo con a4, puedes añadir la asignación de FILE2 en FILE para ejecutar todas.
JFLAGS = -g
JC = javac
JVM = java
FILE = a4 
FILE2 = a4 a11 a15 a16 gr17 fri26 dantzig42 att48
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java
CLASSES = \
	TSP.java \
	routeTSP.java
MAIN = TSP 

default: classes

classes: $(CLASSES:.java=.class)

all_test: fb_test av_test pd_test rp_test

%_test: $(MAIN).class
	for number in $(FILE) ; do \
		$(JVM) $(MAIN) -$* $${number}.tsp ; \
	done

clean:
	$(RM) *.class
