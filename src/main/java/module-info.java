module co.edu.uniquindio.poo.proyectofinal {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;

    // 🔓 EXPORTAR: Permite que las clases se vean entre paquetes
    exports co.edu.uniquindio.poo.proyectofinal;
    exports co.edu.uniquindio.poo.proyectofinal.Controller;
    exports co.edu.uniquindio.poo.proyectofinal.Model;
    exports co.edu.uniquindio.poo.proyectofinal.ViewController;

    // 🔓 ABRIR: Permite que JavaFX (y los componentes TableView) lean los datos
    opens co.edu.uniquindio.poo.proyectofinal.ViewController to javafx.fxml;
    opens co.edu.uniquindio.poo.proyectofinal.Controller to javafx.fxml;
    opens co.edu.uniquindio.poo.proyectofinal.Model to javafx.base, javafx.fxml;
}