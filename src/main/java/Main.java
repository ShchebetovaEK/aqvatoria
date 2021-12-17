import by.tms.aqvatoria.exception.AqvatoriaThreadException;
import by.tms.aqvatoria.reader.impl.DataReaderImpl;

public class Main {
    public static void main(String[] args) {

        DataReaderImpl dataReader = new DataReaderImpl();
        try {
            dataReader.readLines("data/ship.txt");
        } catch (AqvatoriaThreadException e) {
            e.printStackTrace();
        }


    }

}
