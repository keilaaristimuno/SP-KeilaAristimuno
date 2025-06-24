
package utils;
import models.Medicamento;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException; 
import java.util.List;

public class InformeVencimientos {
    public static void generarInforme(List<Medicamento> medicamentos, String rutaArchivo) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivo))) {
            bw.write("Listado de medicamentos próximos a vencer\n");
            bw.write("------------------------------------------\n");

            if (medicamentos.isEmpty()) {
                bw.write("No hay medicamentos por vencer en los próximos 30 días.\n");
            } else {
                for (Medicamento med : medicamentos) {
                    bw.write(med.toString());
                    bw.newLine();
                }
            }

            System.out.println("Informe generado correctamente en: " + rutaArchivo);
        } catch (IOException e) {
            System.err.println("Error al generar el informe: " + e.getMessage());
        }
    }
}
