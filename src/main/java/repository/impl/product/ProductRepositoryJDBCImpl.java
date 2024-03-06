package repository.impl.product;

import config.DatabaseConnection;
import model.Category;
import model.Product;
import repository.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ProductRepositoryJDBCImpl implements Repository<Product> {
    private Connection getConnection() throws SQLException {
        return DatabaseConnection.getInstance();
    }

    private Product createProduct(ResultSet resultSet) throws SQLException {
        Product producto = new Product();
        producto.setId(resultSet.getInt("id"));
        producto.setName(resultSet.getString("nombre"));
        producto.setPrice(resultSet.getDouble("precio"));
        java.sql.Date dbSqlDate = resultSet.getDate("fecha_registro");
        if (dbSqlDate != null) {
            LocalDate fechaRegistro = dbSqlDate.toLocalDate();
            producto.setRegistrationDate(fechaRegistro.atStartOfDay()); // Convierte LocalDate a LocalDateTime al inicio del día
        } else {
            producto.setRegistrationDate(null);
        }
        producto.setCategory(new Category(
                resultSet.getInt("category_id"),
                resultSet.getString("category_name")

        ));
        return producto;
    }

   /* @Override
    public List<Product> list() {
        List<Product> productoList = new ArrayList<>();
        try (Statement statement = getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * from productos")) {
            while (resultSet.next()) {
                Product product = createProduct(resultSet);
                productoList.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productoList;
    }*/

    @Override
    public List<Product> list() {
        List<Product>productosList=new ArrayList<>();
        try(Statement statement=getConnection().createStatement();
            ResultSet resultSet=statement.executeQuery(
                    """
                        SELECT p.*, c.name as category_name, c.id as category_id
                        FROM productos AS p
                        INNER JOIN categories AS c ON p.categoria_id = c.id;
                        """
            ))
        {
            while (resultSet.next()){
                Product producto=createProduct(resultSet);
                productosList.add(producto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productosList;
    }

/*    @Override
    public Product byId(int id) {
        Product product = null;
        try (PreparedStatement preparedStatement = getConnection()
                .prepareStatement("SELECT * FROM productos WHERE id =?")) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                product = createProduct(resultSet);
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }*/

    @Override
    public Product byId(int id) {
        Product producto=null;
        try (PreparedStatement preparedStatement=getConnection()
                .prepareStatement(""" 
                                    SELECT p.*, c.name as category_name, c.id as category_id
                                    FROM productos AS p
                                    INNER JOIN categories AS c ON p.categoria_id = c.id
                                    WHERE p.id=?
                                    """)
        ) {
            preparedStatement.setLong(1,id);
            ResultSet resultSet= preparedStatement.executeQuery();
            if (resultSet.next()){
                producto=createProduct(resultSet);
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return producto;
    }

    @Override
    public void save(Product producto) {
        try(PreparedStatement preparedStatement = getConnection()
                .prepareStatement("""
                                       INSERT INTO Productos(nombre,precio,fecha_registro,categoria_id) values (?,?,?,?)
                                       """)
        ){
            preparedStatement.setString(1, producto.getName());
            preparedStatement.setDouble(2, producto.getPrice());
            LocalDateTime fechaRegistro = producto.getRegistrationDate();
            preparedStatement.setDate(3, Date.valueOf(fechaRegistro.toLocalDate()));
            preparedStatement.setInt(4,producto.getCategory().getId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Product producto) {
        try(PreparedStatement preparedStatement = getConnection()
                .prepareStatement("""
                                    UPDATE Productos SET nombre = ?, precio = ?, fecha_registro = ? , categoria_id=? WHERE id = ?;
                                      """
                )
        ){
            preparedStatement.setString(1, producto.getName());
            preparedStatement.setDouble(2, producto.getPrice());
            LocalDateTime fechaRegistro = producto.getRegistrationDate();
            preparedStatement.setDate(3, Date.valueOf(fechaRegistro.toLocalDate()));
            preparedStatement.setInt(4,producto.getCategory().getId());
            preparedStatement.setInt(5,producto.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    @Override
    public void delete(int id) {
        try(PreparedStatement preparedStatement = getConnection()
                .prepareStatement("""
                                      DELETE FROM Productos where id=?
                                      """)
        ){
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
