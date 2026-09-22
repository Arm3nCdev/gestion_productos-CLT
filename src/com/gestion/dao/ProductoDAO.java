package com.gestion.dao;

import com.gestion.config.Conexion;
import com.gestion.modelo.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase Data Access Object (DAO) para realizar operaciones CRUD
 * sobre la tabla 'productos' en la base de datos.
 */
public class ProductoDAO {

    /**
     * Guarda un nuevo producto en la base de datos.
     */
    public boolean guardar(Producto p) throws SQLException {
        String sql = "INSERT INTO productos (codigo, nombre, categoria, precio, stock, estado) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getCodigo());
            ps.setString(2, p.getNombre());
            ps.setString(3, p.getCategoria());
            ps.setDouble(4, p.getPrecio());
            ps.setInt(5, p.getStock());
            ps.setString(6, p.getEstado());

            return ps.executeUpdate() > 0;
        }
    }

    /**
     * Modifica los datos de un producto existente.
     */
    public boolean modificar(Producto p) throws SQLException {
        String sql = "UPDATE productos SET codigo = ?, nombre = ?, categoria = ?, precio = ?, stock = ?, estado = ? WHERE id = ?";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getCodigo());
            ps.setString(2, p.getNombre());
            ps.setString(3, p.getCategoria());
            ps.setDouble(4, p.getPrecio());
            ps.setInt(5, p.getStock());
            ps.setString(6, p.getEstado());
            ps.setInt(7, p.getId());

            return ps.executeUpdate() > 0;
        }
    }

    /**
     * Elimina un producto por su ID.
     */
    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM productos WHERE id = ?";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    /**
     * Obtiene la lista completa de productos.
     */
    public List<Producto> listarTodos() throws SQLException {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT id, codigo, nombre, categoria, precio, stock, estado FROM productos ORDER BY id DESC";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Producto p = new Producto(
                        rs.getInt("id"),
                        rs.getString("codigo"),
                        rs.getString("nombre"),
                        rs.getString("categoria"),
                        rs.getDouble("precio"),
                        rs.getInt("stock"),
                        rs.getString("estado")
                );
                lista.add(p);
            }
        }
        return lista;
    }

    /**
     * Busca productos por codigo o nombre (busqueda parcial).
     */
    public List<Producto> buscar(String criterio) throws SQLException {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT id, codigo, nombre, categoria, precio, stock, estado FROM productos WHERE codigo LIKE ? OR nombre LIKE ? ORDER BY id DESC";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            String filtro = "%" + criterio.trim() + "%";
            ps.setString(1, filtro);
            ps.setString(2, filtro);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Producto p = new Producto(
                            rs.getInt("id"),
                            rs.getString("codigo"),
                            rs.getString("nombre"),
                            rs.getString("categoria"),
                            rs.getDouble("precio"),
                            rs.getInt("stock"),
                            rs.getString("estado")
                    );
                    lista.add(p);
                }
            }
        }
        return lista;
    }

    /**
     * Obtiene los productos con stock inferior a un limite dado (ej. bajo stock).
     */
    public List<Producto> listarBajoStock(int limite) throws SQLException {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT id, codigo, nombre, categoria, precio, stock, estado FROM productos WHERE stock < ? ORDER BY stock ASC";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, limite);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Producto p = new Producto(
                            rs.getInt("id"),
                            rs.getString("codigo"),
                            rs.getString("nombre"),
                            rs.getString("categoria"),
                            rs.getDouble("precio"),
                            rs.getInt("stock"),
                            rs.getString("estado")
                    );
                    lista.add(p);
                }
            }
        }
        return lista;
    }

    /**
     * Actualiza unicamente el stock de un producto.
     */
    public boolean ajustarStock(int id, int nuevoStock) throws SQLException {
        String sql = "UPDATE productos SET stock = ? WHERE id = ?";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, nuevoStock);
            ps.setInt(2, id);

            return ps.executeUpdate() > 0;
        }
    }

    /**
     * Verifica si ya existe un producto registrado con el mismo codigo.
     */
    public boolean existeCodigo(String codigo) throws SQLException {
        String sql = "SELECT COUNT(*) FROM productos WHERE LOWER(codigo) = LOWER(?)";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, codigo.trim());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    /**
     * Verifica si existe el codigo en otro registro diferente al ID actual.
     */
    public boolean existeCodigoDiferenteId(String codigo, int idActual) throws SQLException {
        String sql = "SELECT COUNT(*) FROM productos WHERE LOWER(codigo) = LOWER(?) AND id <> ?";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, codigo.trim());
            ps.setInt(2, idActual);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }
}
