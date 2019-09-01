package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/*
Class that provides the autobahn picture. Is needed because in the JAR-File the normal "new File(//relativ Path)" does
not work.
 */
public class PictureGetter {

    public File getImage () throws IOException {
        Path temp = Files.createTempFile("picChange-", "ext");
        Files.copy(this.getClass().getResourceAsStream("autobahnnetz_DE.png"), temp,
                StandardCopyOption.REPLACE_EXISTING);
        FileInputStream fileInputStream = new FileInputStream(temp.toFile());
        byte[] bytes = new byte[fileInputStream.available()];
        fileInputStream.read(bytes);
        fileInputStream.close();
        File image = new File("changingImg.png");
        FileOutputStream outputStream = new FileOutputStream(image);
        outputStream.write(bytes);
        outputStream.flush();
        outputStream.close();
        return image;
    }
}
