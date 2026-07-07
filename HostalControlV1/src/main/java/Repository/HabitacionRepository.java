package Repository;


import Config.MongoConnection;
import Model.EstadoHabitacion;
import Model.Habitacion;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import com.mongodb.client.model.Updates;
import org.bson.types.ObjectId;


import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class HabitacionRepository {

    private final MongoCollection<Document> collection;

    public HabitacionRepository() {
        MongoDatabase database = MongoConnection.getDatabase();
        this.collection = database.getCollection("habitaciones");
    }

    public void guardar(Habitacion habitacion) {
        Document documento = new Document("numero", habitacion.getNumero())
                .append("tipo", habitacion.getTipo())
                .append("tarifa", habitacion.getTarifa())
                .append("estado", habitacion.getEstado().name());

        collection.insertOne(documento);
    }

    public boolean existeNumero(int numero) {
        Document encontrado = collection.find(eq("numero", numero)).first();
        return encontrado != null;
    }

    public List<Habitacion> listar() {
        List<Habitacion> habitaciones = new ArrayList<>();

        for (Document doc : collection.find()) {
            Habitacion habitacion = new Habitacion();

            habitacion.setId(doc.getObjectId("_id").toString());
            habitacion.setNumero(doc.getInteger("numero"));
            habitacion.setTipo(doc.getString("tipo"));
            habitacion.setTarifa(doc.getDouble("tarifa"));
            habitacion.setEstado(EstadoHabitacion.valueOf(doc.getString("estado")));

            habitaciones.add(habitacion);
        }

        return habitaciones;
    }
    public void actualizarEstado(String idHabitacion, EstadoHabitacion nuevoEstado) {
    collection.updateOne(
            eq("_id", new ObjectId(idHabitacion)),
            Updates.set("estado", nuevoEstado.name())
    );
}
}