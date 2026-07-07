/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository;


import Config.MongoConnection;
import Model.EstadoEstadia;
import Model.Estadia;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class EstadiaRepository {

    private final MongoCollection<Document> collection;

    public EstadiaRepository() {
        MongoDatabase database = MongoConnection.getDatabase();
        this.collection = database.getCollection("estadias");
    }

    public void guardar(Estadia estadia) {
        Document documento = new Document("habitacionId", estadia.getHabitacionId())
                .append("numeroHabitacion", estadia.getNumeroHabitacion())
                .append("nombreHuesped", estadia.getNombreHuesped())
                .append("documentoHuesped", estadia.getDocumentoHuesped())
                .append("telefonoHuesped", estadia.getTelefonoHuesped())
                .append("fechaHoraIngreso", estadia.getFechaHoraIngreso())
                .append("estado", estadia.getEstado().name());

        collection.insertOne(documento);
    }

    public List<Estadia> listar() {
        List<Estadia> estadias = new ArrayList<>();

        for (Document doc : collection.find()) {
            Estadia estadia = new Estadia();

            estadia.setId(doc.getObjectId("_id").toString());
            estadia.setHabitacionId(doc.getString("habitacionId"));
            estadia.setNumeroHabitacion(doc.getInteger("numeroHabitacion"));
            estadia.setNombreHuesped(doc.getString("nombreHuesped"));
            estadia.setDocumentoHuesped(doc.getString("documentoHuesped"));
            estadia.setTelefonoHuesped(doc.getString("telefonoHuesped"));
            estadia.setFechaHoraIngreso(doc.getString("fechaHoraIngreso"));
            estadia.setEstado(EstadoEstadia.valueOf(doc.getString("estado")));

            estadias.add(estadia);
        }

        return estadias;
    }
}