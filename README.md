# Dijkstravi

# Scenebuilder
https://gluonhq.com/products/scene-builder/

# Video zur Installation von JavaFX in IntelliJ --> Unabdingbar zum Programmieren
https://www.youtube.com/watch?v=qn2tbftFjno
Die module-Datei ist bereits im Repository. Das müsst ihr also nicht machen!

# Link zur Aufgabe
http://www.ba-horb.de/~pl/Programmieren/Projekt_Dijkstra.pdf

# Hinweise:
Das XML mit dem Autobahnnetz ist über die Overpass API entstanden.</br>
Man kann es sich selbst über den "Overpass Turbo" holen.</br>
https://overpass-turbo.eu </br>
Dafür muss man folgenden Query-Code auf dem seitlichen Feld ausführen:</br>

<pre><code>
  [out:xml][timeout:1000];
// fetch area “germany” to search in
{{geocodeArea:germany}}->.searchArea;
// gather results
(
  // query part for: “highway=motorway”
  node["highway"="motorway"](area.searchArea);
  way["highway"="motorway"](area.searchArea);
  relation["highway"="motorway"](area.searchArea);
  node["highway"="motorway_link"](area.searchArea);
  way["highway"="motorway_link"](area.searchArea);
  relation["highway"="motorway_link"](area.searchArea);
);
out body;
>;
out skel qt;
</code></pre>

Im Gegensatz zum Namen dauert das aber seine Zeit. Also Geduld.</br>
Wenn er fertig ist auf "Export" clicken und die .osm herunterladen. </br>

# Vorgehensweise
[] Datenstruktur erstellen
[] OSM einlesen und in Graph übertragen
[] Algorithmen implementieren.
[] Routenausgabe implementieren
[] Graphische Implementierung
