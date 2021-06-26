package ua.net.marchenko.csv.calcite;

import org.apache.calcite.DataContext;
import org.apache.calcite.linq4j.AbstractEnumerable;
import org.apache.calcite.linq4j.Enumerable;
import org.apache.calcite.linq4j.Enumerator;
import org.apache.calcite.rel.type.RelDataType;
import org.apache.calcite.rel.type.RelDataTypeFactory;
import org.apache.calcite.schema.ScannableTable;
import org.apache.calcite.schema.impl.AbstractTable;
import org.apache.calcite.sql.type.SqlTypeName;
import org.apache.calcite.util.Pair;
import org.apache.calcite.util.Source;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CsvTable extends AbstractTable implements ScannableTable {
    protected final Source source;
    private final List<String> headers;
    private final List<SqlTypeName> types;

    public CsvTable(Source source) throws IOException {
        this.source = source;

        var reader = new BufferedReader(source.reader());
        headers = Arrays.stream(reader.readLine().split(",")).map(String::toUpperCase).collect(Collectors.toList());
        types = Arrays.stream(reader.readLine().split(",")).map(cell -> cell.matches("\\d+") ? SqlTypeName.BIGINT : SqlTypeName.VARCHAR).collect(Collectors.toList());
    }

    @Override
    public RelDataType getRowType(RelDataTypeFactory relDataTypeFactory) {
        List<RelDataType> types2 = this.types.stream().map(relDataTypeFactory::createSqlType).collect(Collectors.toList());
        return relDataTypeFactory.createStructType(Pair.zip(headers,types2));
    }

    @Override
    public Enumerable<Object[]> scan(DataContext dataContext) {
        return new AbstractEnumerable<>() {
            @Override
            public Enumerator<Object[]> enumerator() {
                return new CsvEnumerator<>(source);
            }
        };
    }
}
