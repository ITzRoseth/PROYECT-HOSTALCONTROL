/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository;

import Config.MongoConnection;
import Model.MantenimientoHabitacion;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class MantenimientoRepository {

    private final MongoCollection<Document> collection;

    public MantenimientoRepository() {
        MongoDatabase database = MongoConnection.getDatabase();
        this.collection = database.getCollection("mantenimientos");
    }

    public void guardar(MantenimientoHabitacion mantenimiento) {
        Document documento = new Document("habitacionId", mantenimiento.getHabitacionId())
                .append("numeroHabitacion", mantenimiento.getNumeroHabitacion())
                .append("tipoHabitacion", mantenimiento.getTipoHabitacion())
                .append("estadoAnterior", mantenimiento.getEstadoAnterior())
                .append("observacion", mantenimiento.getObservacion())
                .append("fechaRegistro", mantenimiento.getFechaRegistro());

        collection.insertOne(documento);
    }

    public List<MantenimientoHabitacion> listar() {
        List<MantenimientoHabitacion> mantenimientos = new ArrayList<>();

        for (Document doc : collection.find()) {
            MantenimientoHabitacion mantenimiento = new MantenimientoHabitacion();

            mantenimiento.setId(doc.getObjectId("_id").toString());
            mantenimiento.setHabitacionId(doc.getString("habitacionId"));
            mantenimiento.setNumeroHabitacion(doc.getInteger("numeroHabitacion"));
            mantenimiento.setTipoHabitacion(doc.getString("tipoHabitacion"));
            mantenimiento.setEstadoAnterior(doc.getString("estadoAnterior"));
            mantenimiento.setObservacion(doc.getString("observacion"));
            mantenimiento.setFechaRegistro(doc.getString("fechaRegistro"));

            mantenimientos.add(mantenimiento);
        }

        return mantenimientos;
    }
}