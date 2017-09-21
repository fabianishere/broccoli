package nl.tudelft.broccoli.defpro;

import nl.tu.delft.defpro.api.APIProvider;
import nl.tu.delft.defpro.api.IDefProAPI;

public class MyConfig {
    private IDefProAPI config;

    public MyConfig() {
        System.out.println("Test");
        config = APIProvider.getAPI("C:/Users/Christov/Documents/TI/SEM/broccoli/broccoli-defpro/src/main" +
                "/java/nl/tudelft/broccoli/defpro/configs.config_0.txt");
    }
}
