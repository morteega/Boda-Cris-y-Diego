package com.boda.diegoycris.services;

import com.boda.diegoycris.models.Rsvp;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Service
public class RsvpCsvService {

    private static final String CSV_PATH = "respuestas-rsvp.csv";
    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private static final ZoneId MADRID = ZoneId.of("Europe/Madrid");

    public synchronized void guardarRsvp(Rsvp rsvp) {
        File archivo = new File(CSV_PATH);
        boolean archivoNuevo = !archivo.exists();
        long id = siguienteId(archivo);
        String fecha = Instant.ofEpochMilli(rsvp.getUpdatedAt()).atZone(MADRID).format(FORMATO);

        try (FileWriter writer = new FileWriter(archivo, true)) {

            if (archivoNuevo) {
                writer.append("id,nombre,asistencia,intolerancias,fecha\n");
            }

            writer.append(String.valueOf(id)).append(",");
            writer.append(escaparCsv(rsvp.getFullName())).append(",");
            writer.append(String.valueOf(rsvp.getAssist())).append(",");
            writer.append(escaparCsv(rsvp.getIntolerances())).append(",");
            writer.append(fecha).append("\n");

        } catch (IOException e) {
            throw new RuntimeException("No se pudo guardar el RSVP en el CSV", e);
        }
    }

    private String escaparCsv(String texto) {
        if (texto == null) {
            return "";
        }

        String limpio = texto.replace("\"", "\"\"");

        if (limpio.contains(",") || limpio.contains("\"") || limpio.contains("\n")) {
            return "\"" + limpio + "\"";
        }

        return limpio;
    }
    // id = líneas actuales del CSV (cabecera + filas). Persiste aunque Render reinicie.
    private long siguienteId(File archivo) {
        if (!archivo.exists()) {
            return 1;
        }
        try (var lineas = Files.lines(archivo.toPath())) {
            return lineas.count();
        } catch (IOException e) {
            throw new RuntimeException("No se pudo leer el CSV", e);
        }
    }
}
