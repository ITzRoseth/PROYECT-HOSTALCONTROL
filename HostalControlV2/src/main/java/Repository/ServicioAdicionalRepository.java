
package Repository;

import Config.MongoConnection;
import Model.ServicioAdicional;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class ServicioAdicionalRepository {

    private final MongoCollection<Document> collection;

    public ServicioAdicionalRepository() {
        MongoDatabase database = MongoConnection.getDatabase();
        this.collection = database.getCollection("servicios_adicionales");
    }

    public void guardar(ServicioAdicional servicio) {
        Document documento = new Document("estadiaId", servicio.getEstadiaId())
                .append("habitacionId", servicio.getHabitacionId())
                .append("numeroHabitacion", servicio.getNumeroHabitacion())
                .append("nombreServicio", servicio.getNombreServicio())
                .append("cantidad", servicio.getCantidad())
                .append("valorUnitario", servicio.getValorUnitario())
                .append("subtotal", servicio.getSubtotal())
                .append("fechaRegistro", servicio.getFechaRegistro());

        collection.insertOne(documento);
    }

    public List<ServicioAdicional> listar() {
        List<ServicioAdicional> servicios = new ArrayList<>();

        for (Document doc : collection.find()) {
            ServicioAdicional servicio = convertirDocumento(doc);
            servicios.add(servicio);
        }

        return servicios;
    }

    public List<ServicioAdicional> listarPorEstadia(String estadiaId) {
        List<ServicioAdicional> servicios = new ArrayList<>();

        for (Document doc : collection.find(eq("estadiaId", estadiaId))) {
            ServicioAdicional servicio = convertirDocumento(doc);
            servicios.add(servicio);
        }

        return servicios;
    }

    private ServicioAdicional convertirDocumento(Document doc) {
        ServicioAdicional servicio = new ServicioAdicional();

        servicio.setId(doc.getObjectId("_id").toString());
        servicio.setEstadiaId(doc.getString("estadiaId"));
        servicio.setHabitacionId(doc.getString("habitacionId"));
        servicio.setNumeroHabitacion(doc.getInteger("numeroHabitacion"));
        servicio.setNombreServicio(doc.getString("nombreServicio"));
        servicio.setCantidad(doc.getInteger("cantidad"));
        servicio.setValorUnitario(doc.getDouble("valorUnitario"));
        servicio.setSubtotal(doc.getDouble("subtotal"));
        servicio.setFechaRegistro(doc.getString("fechaRegistro"));

        return servicio;
    }
}