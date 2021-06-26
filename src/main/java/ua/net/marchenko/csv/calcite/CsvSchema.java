package ua.net.marchenko.csv.calcite;

import org.apache.calcite.schema.Table;
import org.apache.calcite.schema.impl.AbstractSchema;
import org.apache.calcite.util.Sources;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CsvSchema extends AbstractSchema {
    private final File directoryFile;
    private Map<String, Table> tableMap;

    public CsvSchema(File directoryFile) {
        this.directoryFile = directoryFile;
    }

    @Override
    public Map<String, Table> getTableMap() {
        if (tableMap != null) {
            return  tableMap;
        }

        File[] files = directoryFile.listFiles((dir, name) -> name.endsWith(".csv"));
        tableMap = new HashMap<>();
        for (File file : files) {
            try {
                tableMap.put(file.getName().replaceAll(".csv", "").toUpperCase(), new CsvTable(Sources.of(file)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return tableMap;
    }
}
