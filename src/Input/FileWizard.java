package Engine;

import java.io.File;

public class FileWizard {

    // To get the dynamic file name

    public static String getabspath() {

        File currentDir = new File("");
        String dirpath = currentDir.getAbsolutePath();
        return dirpath;

    }

    public static void main(String[] args){

        Engine.FileWizard nv = new Engine.FileWizard();
        nv.getabspath();

    }
}
