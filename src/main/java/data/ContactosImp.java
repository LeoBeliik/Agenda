package data;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ContactosImp {
    private final String archivo = "src/main/resources/contactos.ct";
    private final List<Contactos> contactos = new ArrayList<>();

    @SuppressWarnings("InfiniteLoopStatement")
    //Funcion encargada de leer los contactos del archivo 'contactos.ct'
    public List<Contactos> leer() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(archivo))) {
            contactos.clear();
            while (true)
                contactos.add((Contactos) in.readObject());
        } catch (Exception ignored) {}
        return contactos;
    }
    //Funcion encargada de guardar los contactos en el archivo 'contactos.ct'
    public void guardar(Contactos contacto) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(archivo))) {
            contactos.add(contacto);
            for (Contactos c : contactos) {
                out.writeObject(c);
            }
        } catch (Exception ignored) {}
    }
    //borrar contacto de la lista y guardar
    public void borrarContacto(long numTel) {
        for (Contactos c : contactos) {
            if (c.getNumeroTel() == numTel) {
                contactos.remove(c);
                break;
            }
        }
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(archivo))) {
            for (Contactos c : contactos) {
                out.writeObject(c);
            }
        } catch (Exception ignored) {}
    }
    //buscar si el numero de telefono se encuentra en la lista de contactos
    public boolean contactoExistente(long numTel) {
        for (Contactos c : contactos) {
            if (c.getNumeroTel() == numTel) {
                contactos.remove(c);
                return true;
            }
        }
        return false;
    }
}
