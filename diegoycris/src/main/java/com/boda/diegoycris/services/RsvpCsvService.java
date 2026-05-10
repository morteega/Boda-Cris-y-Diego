package com.boda.diegoycris.services;

import com.boda.diegoycris.models.Rsvp;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Service
public class RsvpCsvService {

    private static final String CSV_PATH = "respuestas-rsvp.csv";

    public void guardarRsvp(Rsvp rsvp) {
        File archivo = new File(CSV_PATH);
        boolean archivoNuevo = !archivo.exists();

        try (FileWriter writer = new FileWriter(archivo, true)) {

            if (archivoNuevo) {
                writer.append("id,nombre,asistencia,intolerancias,fecha\n");
            }

            writer.append(String.valueOf(rsvp.getId())).append(",");
            writer.append(escaparCsv(rsvp.getFullName())).append(",");
            writer.append(String.valueOf(rsvp.getAssist())).append(",");
            writer.append(escaparCsv(rsvp.getIntolerances())).append(",");
            writer.append(String.valueOf(rsvp.getUpdatedAt())).append("\n");

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
}
