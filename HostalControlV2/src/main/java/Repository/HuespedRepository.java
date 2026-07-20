/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository;


import Config.MongoConnection;
import Model.Huesped;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Updates;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class HuespedRepository {

    private final MongoCollection<Document> collection;
    private final MongoCollection<Document> reservasCollection;
    private final MongoCollection<Document> estadiasCollection;

    public HuespedRepository() {
        MongoDatabase database = MongoConnection.getDatabase();
        this.collection = database.getCollection("huespedes");
        this.reservasCollection = database.getCollection("reservas");
        this.estadiasCollection = database.getCollection("estadias");
    }

    public void guardarOActualizar(Huesped huesped) {
        Document existente = collection.find(eq("documento", huesped.getDocumento())).first();

        if (existente == null) {
            Document documento = new Document("nombre", huesped.getNombre())
                    .append("documento", huesped.getDocumento())
                    .append("telefono", huesped.getTelefono())
                    .append("fechaRegistro", huesped.getFechaRegistro());

            collection.insertOne(documento);
        } else {
            collection.updateOne(
                    eq("documento", huesped.getDocumento()),
                    Updates.combine(
                            Updates.set("nombre", huesped.getNombre()),
                            Updates.set("telefono", huesped.getTelefono()),
                            Updates.set("fechaRegistro", huesped.getFechaRegistro())
                    )
            );
        }
    }

    public Huesped buscarPorDocumento(String documentoBuscado) {
        Document doc = collection.find(eq("documento", documentoBuscado)).first();

        if (doc != null) {
            return convertirDesdeHuespedes(doc);
        }

        doc = estadiasCollection.find(eq("documentoHuesped", documentoBuscado)).first();

        if (doc != null) {
            return convertirDesdeRegistroAntiguo(doc);
        }

        doc = reservasCollection.find(eq("documentoHuesped", documentoBuscado)).first();

        if (doc != null) {
            return convertirDesdeRegistroAntiguo(doc);
        }

        return null;
    }

    public List<Huesped> listar() {
        List<Huesped> huespedes = new ArrayList<>();

        for (Document doc : collection.find()) {
            huespedes.add(convertirDesdeHuespedes(doc));
        }

        return huespedes;
    }

    private Huesped convertirDesdeHuespedes(Document doc) {
        Huesped huesped = new Huesped();

        huesped.setId(doc.getObjectId("_id").toString());
        huesped.setNombre(doc.getString("nombre"));
        huesped.setDocumento(doc.getString("documento"));
        huesped.setTelefono(doc.getString("telefono"));
        huesped.setFechaRegistro(doc.getString("fechaRegistro"));

        return huesped;
    }

    private Huesped convertirDesdeRegistroAntiguo(Document doc) {
        Huesped huesped = new Huesped();

        huesped.setNombre(doc.getString("nombreHuesped"));
        huesped.setDocumento(doc.getString("documentoHuesped"));
        huesped.setTelefono(doc.getString("telefonoHuesped"));
        huesped.setFechaRegistro("Registro recuperado");

        return huesped;
    }
}