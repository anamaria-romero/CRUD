import java.sql.*;
import java.util.Scanner;

public class Main {
    // Configuración de conexión
    private static final String URL = "jdbc:mysql://localhost:3306/project";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static void main(String[] args) {
        try (Connection conexion = DriverManager.getConnection(URL, USER, PASSWORD)) {
            System.out.println("Conexión exitosa!");
            Scanner scanner = new Scanner(System.in);
            int opcion;

            // Menú principal
            do {
                System.out.println("\nSeleccione una opción:");
                System.out.println("1. Insertar empleado");
                System.out.println("2. Consultar empleados");
                System.out.println("3. Actualizar empleado");
                System.out.println("4. Eliminar empleado");
                System.out.println("5. Salir");
                System.out.print("Opción: ");
                opcion = scanner.nextInt();

                switch (opcion) {
                    case 1 -> insertarEmpleadoMenu(conexion, scanner);
                    case 2 -> consultarEmpleados(conexion);
                    case 3 -> actualizarEmpleadoMenu(conexion, scanner);
                    case 4 -> eliminarEmpleadoMenu(conexion, scanner);
                    case 5 -> System.out.println("Salió.");
                    default -> System.out.println("Opción no válida.");
                }
            } while (opcion != 5);

            scanner.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error en la conexión.");
        }
    }

    // Método para insertar empleado (opción 1)
    private static void insertarEmpleadoMenu(Connection conexion, Scanner scanner) throws SQLException {
        scanner.nextLine(); // Limpiar el buffer

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Apellido Paterno: ");
        String apellidoPaterno = scanner.nextLine();
        System.out.print("Apellido Materno: ");
        String apellidoMaterno = scanner.nextLine();
        System.out.print("Cargo: ");
        String cargo = scanner.nextLine();
        System.out.print("Salario: ");
        double salario = scanner.nextDouble();

        String sql = "INSERT INTO employees (first_name, pa_surname, ma_surname, email, salary, cargo) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            pstmt.setString(2, apellidoPaterno);
            pstmt.setString(3, apellidoMaterno);
            pstmt.setString(4, nombre.toLowerCase() + "." + apellidoPaterno.toLowerCase() + "@gmail.com");
            pstmt.setDouble(5, salario);
            pstmt.setString(6, cargo);
            pstmt.executeUpdate();
            System.out.println("Empleado insertado correctamente!");
        }
    }

    // Método para consultar empleados (opción 2)
    private static void consultarEmpleados(Connection conexion) throws SQLException {
        String sql = "SELECT * FROM employees";
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.printf("ID: %d, Nombre: %s, Apellido Paterno: %s, Apellido Materno: %s, Cargo: %s, Salario: %.2f, Email: %s%n",
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("pa_surname"),
                        rs.getString("ma_surname"),
                        rs.getString("cargo"),
                        rs.getDouble("salary"),
                        rs.getString("email"));
            }
        }
    }

    // Método para actualizar empleado (opción 3)
    private static void actualizarEmpleadoMenu(Connection conexion, Scanner scanner) throws SQLException {
        System.out.print("Ingrese el ID del empleado a actualizar: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer

        System.out.print("Nuevo nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Nuevo apellido paterno: ");
        String apellidoPaterno = scanner.nextLine();
        System.out.print("Nuevo apellido materno: ");
        String apellidoMaterno = scanner.nextLine();
        System.out.print("Nuevo cargo: ");
        String cargo = scanner.nextLine();
        System.out.print("Nuevo salario: ");
        double salario = scanner.nextDouble();

        String sql = "UPDATE employees SET first_name = ?, pa_surname = ?, ma_surname = ?, cargo = ?, salary = ? WHERE id = ?";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            pstmt.setString(2, apellidoPaterno);
            pstmt.setString(3, apellidoMaterno);
            pstmt.setString(4, cargo);
            pstmt.setDouble(5, salario);
            pstmt.setInt(6, id);
            pstmt.executeUpdate();
            System.out.println("Empleado actualizado correctamente!");
        }
    }

    // Método para eliminar empleado (opción 4)
    private static void eliminarEmpleadoMenu(Connection conexion, Scanner scanner) throws SQLException {
        System.out.print("Ingrese el ID del empleado a eliminar: ");
        int id = scanner.nextInt();

        String sql = "DELETE FROM employees WHERE id = ?";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Empleado eliminado correctamente!");
        }
    }
}

















