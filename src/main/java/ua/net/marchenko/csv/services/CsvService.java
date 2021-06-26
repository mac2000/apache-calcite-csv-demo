package ua.net.marchenko.csv.services;

import org.springframework.stereotype.Service;
import ua.net.marchenko.csv.configurations.ModelConfiguration;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CsvService {
    private final ModelConfiguration modelConfiguration;

    public CsvService(ModelConfiguration modelConfiguration) {
        this.modelConfiguration = modelConfiguration;
    }

    public List<Map<String, Object>> query(String query) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:calcite:model=inline:" + modelConfiguration.getModel());
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        ResultSetMetaData metaData = resultSet.getMetaData();

        List<Map<String, Object>> rows = new ArrayList<>();
        while (resultSet.next()) {
            Map<String, Object> row = new HashMap<>();
            for(var column = 1; column <= metaData.getColumnCount(); column++) {
                row.put(metaData.getColumnName(column), resultSet.getObject(column));
            }
            rows.add(row);
        }

        resultSet.close();
        statement.close();
        connection.close();

        return rows;
    }
}
