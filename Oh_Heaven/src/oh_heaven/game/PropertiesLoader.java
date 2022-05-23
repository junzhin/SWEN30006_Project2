package oh_heaven.game;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

public class PropertiesLoader {

    
    public static final String DEFAULT_DIRECTORY_PATH = "properties/";

    /**load properties file, based on code in projet 1 */
    public static Properties loadPropertiesFile(String propertiesFile) {
        if (propertiesFile == null) {
            try (InputStream input = new FileInputStream( DEFAULT_DIRECTORY_PATH + "runmode.properties")) {

                Properties prop = new Properties();

                // load a properties file
                prop.load(input);

                propertiesFile = DEFAULT_DIRECTORY_PATH + prop.getProperty("current_mode");
                System.out.println(propertiesFile);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        try (InputStream input = new FileInputStream(propertiesFile)) {

            Properties prop = new Properties();

            // load a properties file
            prop.load(input);

            return prop;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /*load the player types from the property file as the arraylist */
    public static ArrayList<String> loadPlayerTypes(Properties properties) {
        ArrayList<String> playerTypes = new ArrayList<>();
        for (int i=0;i<4;i++) {
            String propertyPlayerType = properties.getProperty("players."+i);
            playerTypes.add(propertyPlayerType);
        }
        return playerTypes;
    }


}
