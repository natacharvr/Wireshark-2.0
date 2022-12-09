JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = Wireshark2.java Trame.java Tcp.java SelecteurFichier.java Ipv4.java Http.java Graph.java CoucheApplication.java CoucheReseau.java CoucheTransport.java Analyseur.java Affichage.java


default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class