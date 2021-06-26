package ua.net.marchenko.csv.calcite;

import lombok.SneakyThrows;
import org.apache.calcite.schema.Schema;
import org.apache.calcite.schema.SchemaFactory;
import org.apache.calcite.schema.SchemaPlus;

import java.io.File;
import java.util.Map;

public class CsvSchemaFactory implements SchemaFactory {
    @SneakyThrows
    @Override
    public Schema create(SchemaPlus schemaPlus, String s, Map<String, Object> map) {
        return new CsvSchema(new File(CsvSchemaFactory.class.getResource("/data").toURI()));
    }
}
