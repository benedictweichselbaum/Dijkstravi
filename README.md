# Dijkstravi

# Scenebuilder
https://gluonhq.com/products/scene-builder/

# Video zur Installation von JavaFX in IntelliJ --> Unabdingbar zum Programmieren
https://www.youtube.com/watch?v=qn2tbftFjno
Die module-Datei ist bereits im Repository. Das müsst ihr also nicht machen!

# Link zur Aufgabe
http://www.ba-horb.de/~pl/Programmieren/Projekt_Dijkstra.pdf

# Hinweise:
Das XML mit dem Autobahnnetz ist über die Overpass API entstanden.
Man kann es sich selbst über den "Overpass Turbo" holen.
Dafür muss man folgenden Query-Code auf dem seitlichen Feld ausführen:

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

Im Gegensatz zum Namen dauert das aber seine Zeit. Also Geduld.
Wenn er fertig ist auf "Export" clicken und die .osm herunterladen.
