
package interfaces;


public interface ISerializableCsv {
    String toCSV();
    ISerializableCsv fromCSV(String line);
}
