import data.Alertas;
import data.Contactos;
import data.ContactosImp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private TableView<Contactos> tblContactos;
    @FXML
    private TextField txtBuscar, txtNombre, txtNumero, txtCorreo, txtDireccion;
    @FXML
    private GridPane gpBottom;

    private final ObservableList<Contactos> list = FXCollections.observableArrayList();
    private final FilteredList<Contactos> filtro = new FilteredList<>(list, p -> true);
    private final ContactosImp CI = new ContactosImp();
    private long edit = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gpBottom.setVisible(false);
        crearTable();
        buscar();
    }

    public void aSalir() {
        System.exit(0);
    }

    public void aAgregar() {
        gpBottom.setVisible(true);
        txtNombre.clear();
        txtNumero.clear();
        txtCorreo.clear();
        txtDireccion.clear();
    }

    public void aEditar() {
        try {
            edit = tblContactos.getSelectionModel().getSelectedItem().getNumeroTel();
            txtNombre.setText(tblContactos.getSelectionModel().getSelectedItem().getNombreApellido());
            txtNumero.setText(String.valueOf(tblContactos.getSelectionModel().getSelectedItem().getNumeroTel()));
            txtCorreo.setText(tblContactos.getSelectionModel().getSelectedItem().getCorreo());
            txtDireccion.setText(tblContactos.getSelectionModel().getSelectedItem().getDireccion());
            gpBottom.setVisible(true);
        } catch (NullPointerException e) {
            Alertas.info("Debe seleccionar el elemento de la tabla a borrar");
        }
    }

    public void aBorrar() {
        try {
            long contacto = tblContactos.getSelectionModel().getSelectedItem().getNumeroTel();
            if (Alertas.confirmar("Realmente desea borrar estos datos?")) {
                CI.borrarContacto(contacto);
                loadTable();
            }
        } catch (NullPointerException e) {
            Alertas.info("Debe seleccionar el elemento de la tabla a borrar");
        }
    }

    public void aAceptar() {
        try {
            String nombre = txtNombre.getText();
            long numero = Long.valueOf(txtNumero.getText());
            String correo = txtCorreo.getText();
            String direccion = txtDireccion.getText();
            if (nombre.isEmpty() || correo.isEmpty() || direccion.isEmpty())
                Alertas.info("Los datos no pueden estar en blanco");
            else if (CI.contactoExistente(numero) && edit == 0)
                Alertas.info("Ese numero de telefono ya se encuentra registrado");
            else if (!correo.contains("@"))
                Alertas.info("El correo ingresado no es valido");
            else {
                if (Alertas.confirmar("Guardar estos datos?")) {
                    if (edit != 0)
                        CI.borrarContacto(edit);
                    Contactos c = new Contactos(nombre, numero, correo, direccion);
                    CI.guardar(c);
                    loadTable();
                    gpBottom.setVisible(false);
                    edit = 0;
                }
            }
        } catch (Exception e) {
            Alertas.info("El numero de telefono no puede contener letras o estar en blanco");
        }

    }

    public void aCancelar() {
        gpBottom.setVisible(false);
    }

    private void buscar() {
        txtBuscar.textProperty().addListener((observable, oldValue, newValue) ->
                filtro.setPredicate(Contactos -> {
                    //Si el filtro está vacio, muestra todos los datos.
                    if (newValue == null || newValue.isEmpty())
                        return true;

                    String filtroMin = newValue.toLowerCase();
                    String numTel = String.valueOf(Contactos.getNumeroTel());

                    if (numTel.toLowerCase().contains(filtroMin))
                        return true; //filtra por telefono.
                    else if (String.valueOf(Contactos.getNombreApellido()).toLowerCase().contains(filtroMin))
                        return true; //filtra por nombre;
                    else if (Contactos.getCorreo().toLowerCase().contains(filtroMin))
                        return true; //filtra por correo.
                    else
                        return String.valueOf(Contactos.getDireccion()).toLowerCase().contains(filtroMin); //filtra por direccion.
                })
        );

        SortedList<Contactos> datos = new SortedList<>(filtro);
        datos.comparatorProperty().bind(tblContactos.comparatorProperty());
        tblContactos.setItems(datos);
    }

    private void crearTable() {
        TableColumn<Contactos, String> nombreApellido = new TableColumn<>("Nombre");
        nombreApellido.setPrefWidth(110);
        nombreApellido.setSortable(false);
        nombreApellido.setReorderable(false);
        nombreApellido.setCellValueFactory(new PropertyValueFactory<>("nombreApellido"));
        TableColumn<Contactos, Long> numeroTel = new TableColumn<>("Numero de telefono");
        numeroTel.setPrefWidth(130);
        numeroTel.setSortable(false);
        numeroTel.setReorderable(false);
        numeroTel.setCellValueFactory(new PropertyValueFactory<>("numeroTel"));
        TableColumn<Contactos, String> correo = new TableColumn<>("E-Mail");
        correo.setPrefWidth(130);
        correo.setSortable(false);
        correo.setReorderable(false);
        correo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        TableColumn<Contactos, Character> direccion = new TableColumn<>("Direccion");
        direccion.setPrefWidth(112);
        direccion.setSortable(false);
        direccion.setReorderable(false);
        direccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        tblContactos.setItems(list);
        tblContactos.getColumns().addAll(Arrays.asList(nombreApellido, numeroTel, correo, direccion));
        loadTable();
    }

    private void loadTable() {
        list.clear();
        list.addAll(CI.leer());
    }
}
