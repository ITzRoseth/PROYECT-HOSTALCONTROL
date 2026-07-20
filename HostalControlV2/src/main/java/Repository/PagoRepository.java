/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository;


import Config.MongoConnection;
import Model.MetodoPago;
import Model.Pago;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class PagoRepository {

    private final MongoCollection<Document> collection;

    public PagoRepository() {
        MongoDatabase database = MongoConnection.getDatabase();
        this.collection = database.getCollection("pagos");
    }

    public void guardar(Pago pago) {
        Document documento = new Document("estadiaId", pago.getEstadiaId())
                .append("habitacionId", pago.getHabitacionId())
                .append("numeroHabitacion", pago.getNumeroHabitacion())
                .append("nombreHuesped", pago.getNombreHuesped())
                .append("documentoHuesped", pago.getDocumentoHuesped())
                .append("fechaIngreso", pago.getFechaIngreso())
                .append("fechaSalida", pago.getFechaSalida())
                .append("diasHospedaje", pago.getDiasHospedaje())
                .append("subtotalHabitacion", pago.getSubtotalHabitacion())
                .append("totalServicios", pago.getTotalServicios())
                .append("totalPagado", pago.getTotalPagado())
                .append("metodoPago", pago.getMetodoPago().name())
                .append("fechaPago", pago.getFechaPago());

        collection.insertOne(documento);
    }

    public List<Pago> listar() {
        List<Pago> pagos = new ArrayList<>();

        for (Document doc : collection.find()) {
            Pago pago = new Pago();

            pago.setId(doc.getObjectId("_id").toString());
            pago.setEstadiaId(doc.getString("estadiaId"));
            pago.setHabitacionId(doc.getString("habitacionId"));
            pago.setNumeroHabitacion(doc.getInteger("numeroHabitacion"));
            pago.setNombreHuesped(doc.getString("nombreHuesped"));
            pago.setDocumentoHuesped(doc.getString("documentoHuesped"));
            pago.setFechaIngreso(doc.getString("fechaIngreso"));
            pago.setFechaSalida(doc.getString("fechaSalida"));
            pago.setDiasHospedaje(doc.getLong("diasHospedaje"));
            pago.setSubtotalHabitacion(doc.getDouble("subtotalHabitacion"));
            pago.setTotalServicios(doc.getDouble("totalServicios"));
            pago.setTotalPagado(doc.getDouble("totalPagado"));
            pago.setMetodoPago(MetodoPago.valueOf(doc.getString("metodoPago")));
            pago.setFechaPago(doc.getString("fechaPago"));

            pagos.add(pago);
        }

        return pagos;
    }
}
