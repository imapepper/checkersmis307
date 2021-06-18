# checkersmis307

Checkers game for MIS 307 at Iowa State University.

## To run:
1. cd to repo directory
2. Use unix shell to run `find . -name "*.java" | xargs javac -cp javax.json-1.1.0-M1.jar`
3. `jar cfm checkers.jar ./resources/META-INF/MANIFEST.MF -C src/ .`
4. Copy resources/img and resources/sound to checkers.jar/resources
5. Copy javax.json-1.1.0-M1.jar to checkers.jar/
6. `java -jar ./checkers.jar`
