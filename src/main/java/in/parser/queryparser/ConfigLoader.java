package in.parser.queryparser;

import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {
    RestrictConfig restrictConfig;

    public ConfigLoader(RestrictConfig restrictConfig){
        this.restrictConfig=restrictConfig;
    }
    public void loadConfig() {
        Properties props = new Properties();

        try (InputStream input = getClass().getClassLoader().getResourceAsStream("restriction-config.properties")) {
            if (input == null) {
                System.out.println("Config file not found in resources!");
                return;
            }
            props.load(input);
            String tables = props.getProperty("restricted.tables");
            if (tables != null) {
                for (String table : tables.split(",")) {
                    restrictConfig.setTableName(table.trim());
                }
            }
            String columns = props.getProperty("restricted.columns");
            if (columns != null) {
                for (String col : columns.split(",")) {
                    String[] parts = col.split("\\.");
                    if (parts.length == 2) {
                        restrictConfig.setColumnName(new TableContext(parts[0].trim(), parts[1].trim(), ""));
                    }
                }
            }
            String prefixTables=props.getProperty("restricted.prefixTables");
            if(prefixTables!=null){
                for(String prefix:prefixTables.split(",")){
                    restrictConfig.setTablePrefixName(prefix.trim());
                }
            }
        }
        catch (Exception e) {
            System.out.println("Error loading config: " + e.getMessage());
        }
    }
}
