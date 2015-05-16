1. Installer Java jdk1.6.0\_23
> Ajouter une nouvelle variable dans "Variables d'environnement"  JAVA\_HOME = C:\Program Files\Java\jdk1.6.0\_23

2. Désarchiver Eclipse-jee-helios-win32.zip

3. Désarchiver apache-tomcat-6.0.32-windows-x86.zip (http://tomcat.apache.org/download-60.cgi)

4. Télécharger les jars pour JSF1.2:
> jsf-api/jsf-impl/jstl-api-1.2/jstl-api-1.2/jstl-impl-1.2
> > Dependencies: commons-beanutils.jar/commons-digester.jar/commons-collections.jar/commons-logging.jar/common-annotations

5. Configurer Eclipse:

> a. Modifier "Installed JREs":
> Window->Installed JREs->Add->Standard VM->C:\Program Files\Java\jdk1.6.0\_23
> > puis cocher celui-ci en tant que JRE par défaut


> b. Installer JBoss tools:
> Help->Install->Work with->add (Name: JBoss tools/ Location: http://download.jboss.org/jbosstools/updates/development)->OK
> > ->Cocher "Web and Java EE Development"->Next->Cocher "I accept the terms of the license agreements"


> e. Installer SVN plug-in. Référence au Wiki du projet

> c. Configurer le serveur
> File->New->Other->Server->Apache->Tomcat v6.0 Server->Next->Browse: D:\Projet\_Covoiturage\apache-tomcat-6.0.32
> > ->Finish

> Dans l'onglet "Server" Double clique sur Tomcat v6.0->Server Locations: Cocher "Use Tomcat installation" / Deploy path: D:\Projet\_Covoiturage\apache-tomcat-6.0.32\webapps
> > ->Sauvgarder(Ctrl+s)


> d. Créer librairie pour JSF 1.2
> Windows->Java->Build Path->User Libraies->New->Name: Sun JSF 1.2 lib->Add JARs (les jars pour JSF2.0)

> e. Créer projet JSF 1.2 pour la première fois
> File->New->New Dynamic Web Project:
> > Name: Todo
> > Target runtime: Apache Tomcat v6.0
> > Dynamic web module version: 2.5
> > Configuration: JavaServer Faces v1.2 Project

> ->Next->Cocher "Generate web.xml deployment descriptor"->Next->Choisir "Sun JSF 1.2 lib" / URL Mapping Patterns: /faces/**,**.jsf,
  * faces, **.xhtml->finish**

6. Installer mysql-5.5.9