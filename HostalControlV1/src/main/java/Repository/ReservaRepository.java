/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository;

import Config.MongoConnection;
import Model.Reserva;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gt;
import static com.mongodb.client.model.Filters.lt;

public class ReservaRepository {

    private final MongoCollection<Document> collection;

    public ReservaRepository() {
        MongoDatabase database = MongoConnection.getDatabase();
        this.collection = database.getCollection("reservas");
    }

    public void guardar(Reserva reserva) {
        Document documento = new Document("habitacionId", reserva.getHabitacionId())
                .append("numeroHabitacion", reserva.getNumeroHabitacion())
                .append("nombreHuesped", reserva.getNombreHuesped())
                .append("documentoHuesped", reserva.getDocumentoHuesped())
                .append("telefonoHuesped", reserva.getTelefonoHuesped())
                .append("fechaIngreso", reserva.getFechaIngreso())
                .append("fechaSalida", reserva.getFechaSalida());

        collection.insertOne(documento);
    }

    public boolean existeReservaEnRango(String habitacionId, String fechaIngreso, String fechaSalida) {
        Document encontrada = collection.find(
                and(
                        eq("habitacionId", habitacionId),
                        lt("fechaIngreso", fechaSalida),
                        gt("fechaSalida", fechaIngreso)
                )
        ).first();

        return encontrada != null;
    }

    public List<Reserva> listar() {
        List<Reserva> reservas = new ArrayList<>();

        for (Document doc : collection.find()) {
            Reserva reserva = new Reserva();

            reserva.setId(doc.getObjectId("_id").toString());
            reserva.setHabitacionId(doc.getString("habitacionId"));
            reserva.setNumeroHabitacion(doc.getInteger("numeroHabitacion"));
            reserva.setNombreHuesped(doc.getString("nombreHuesped"));
            reserva.setDocumentoHuesped(doc.getString("documentoHuesped"));
            reserva.setTelefonoHuesped(doc.getString("telefonoHuesped"));
            reserva.setFechaIngreso(doc.getString("fechaIngreso"));
            reserva.setFechaSalida(doc.getString("fechaSalida"));

            reservas.add(reserva);
        }

        return reservas;
    }
}