package Proyecto.model;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;

public class Exel {
    String[] nombresColumnas = {"Usuario", "Contraseña", "Nombre", "Correo", "Teléfono", "Dirección"};

    public static void agregarClientesExel(){
        String txtFilePath = "src/main/resources/persistencia/clientes.txt";
        String excelFilePath = "src/main/resources/persistencia/datos.xlsx";
        String[] nombresColumnas = {"Usuario", "Contraseña", "Nombre", "Correo", "Teléfono", "Dirección"};

        try {
            FileInputStream fis = new FileInputStream(txtFilePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));

            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Datos");

            Row firstRow = sheet.createRow(0);
            for (int i = 0; i < nombresColumnas.length; i++) {
                Cell cell = firstRow.createCell(i);
                cell.setCellValue(nombresColumnas[i]);
            }

            String line;
            int rowNum = 1;
            while ((line = br.readLine()) != null) {
                Row row = sheet.createRow(rowNum++);
                String[] data = line.split(",");

                int cellNum = 0;
                for (String datum : data) {
                    Cell cell = row.createCell(cellNum++);
                    cell.setCellValue(datum.isEmpty() ? null : datum);
                }
            }

            FileOutputStream fos = new FileOutputStream(excelFilePath);
            workbook.write(fos);

            fos.close();
            br.close();
            System.out.println("Se han transferido datos de txt a Excel con éxito.");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
