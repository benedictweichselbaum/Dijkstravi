# Dijkstravi

# Scenebuilder
https://gluonhq.com/products/scene-builder/

# Video zur Installation von JavaFX in IntelliJ --> Unabdingbar zum Programmieren
https://www.youtube.com/watch?v=qn2tbftFjno
Die module-Datei ist bereits im Repository. Das müsst ihr also nicht machen!

# Link zur Aufgabe
http://www.ba-horb.de/~pl/Programmieren/Projekt_Dijkstra.pdf

# Ausführbare .jar erstellen

### Einmalig
- File -> Project Structure (Str + Alt + Umschalt + S)
- Project Settings -> Artifacts
- auf + klicken
- JAR -> From modules with dependencies
- Main als Main-Class auswählen
- OK

Optional
- Name vergeben
- Output directory anpassen

### Zum Erstellen
- Build -> Build Artifacts
- Richtiges auswählen -> Build

fertig, .jar liegt jetzt im Output-Verzeichnis


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
Wenn er fertig ist auf "Export" klicken und die .osm herunterladen. </br>

# Vorgehensweise
- [X] Datenstruktur erstellen </br>
- [X] OSM einlesen und in Graph übertragen </br>
- [ ] Algorithmen implementieren.</br>
- [ ] Routenausgabe implementieren</br>
- [X] Graphische Implementierung (Algorithmus ist fertig. Die Nutzung muss natürlich noch implementiert werden.)

# Aufgaben
- [X] Hilfsstruktur für mehrere Start- und Zielknoten 
- [ ] Karte zoombar machen -> "Mitscrollen" vermeiden, Drag & Drop einbauen
- [ ] Zeitmessung

# „Probleme“
- [ ] Was mit mehreren Namen bei einer Ausfahrt machen? (z.B. Kreuz München-West;München-Lochhausen)
- [ ] Dijkstra und A* werfen Exception bei gleichem Start und Ziel (evtl. gezielt abfangen) -> Bedenken, ob bei Zwischenzielen Kreise möglich sein sollen

# Ideen
- [ ] Zwischenziele
- [ ] mehrere Ziele in bester Reihenfolge (evtl. mit Endziel)
