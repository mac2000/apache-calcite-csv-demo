package ua.net.marchenko.csv.calcite;

import org.apache.calcite.linq4j.Enumerator;
import org.apache.calcite.util.Source;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;

public class CsvEnumerator<E> implements Enumerator<E> {
    private E current;

    private BufferedReader br;

    public CsvEnumerator(Source source) {
        try {
            br = new BufferedReader(source.reader());
            br.readLine(); // read first line to skip header row
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public E current() {
        return current;
    }

    @Override
    public boolean moveNext() {
        try {
            String line = br.readLine();
            if(line == null){
                return false;
            }

            current = (E) Arrays.stream(line.split(",")).map(cell -> cell.matches("\\d+") ? Long.valueOf(cell) : cell).toArray();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void reset() {

    }

    @Override
    public void close() {

    }
}
