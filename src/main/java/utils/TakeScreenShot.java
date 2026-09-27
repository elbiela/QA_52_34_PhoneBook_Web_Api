package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TakeScreenShot {
    private static String createFileName(){
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        System.out.println(System.currentTimeMillis());
        Date date = new Date(System.currentTimeMillis());
        System.out.println(date);
        String curDate = formater.format(date);
        System.out.println(curDate);
        String faleName = "src/test/resources/screenshots/screen-"
                + curDate + ".png";
        return faleName;
    }

    public static void takeScreenShot(TakesScreenshot screenshot){
        String fileName = createFileName();
        File screen = screenshot.getScreenshotAs(OutputType.FILE);
        try {
            Files.copy(screen.toPath(), new File(fileName).toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}