package com.gestion.vista;

import com.gestion.dao.ProductoDAO;
import com.gestion.modelo.Producto;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.List;

/**
 * Pantalla Principal de la Aplicacion "Gestion de Productos".
 * Desarrollada en Java Swing para la prueba tecnica de Desarrollador Java Jr.
 */
public class VentanaPrincipal extends JFrame {

    // Componentes del Formulario
    private JTextField txtId;
    private JTextField txtCodigo;
    private JTextField txtNombre;
    private JComboBox<String> cbCategoria;
    private JTextField txtPrecio;
    private JSpinner spStock;
    private JComboBox<String> cbEstado;

    // Botones del Formulario
    private JButton btnNuevo;
    private JButton btnGuardar;
    private JButton btnEliminar;

    // Componentes de Busqueda y Filtros
    private JTextField txtBuscar;
    private JButton btnBuscar;
    private JButton btnLimpiarBusqueda;
    private JButton btnBajoStock;

    // Tabla de Productos
    private JTable tblProductos;
    private DefaultTableModel modeloTabla;

    // Botones de Ajuste de Stock
    private JButton btnSumarStock;
    private JButton btnRestarStock;
    private JButton btnAjustarStockManual;

    // Etiqueta de Estado de la aplicacion
    private JLabel lblEstadoMensaje;

    // Objeto DAO para persistencia
    private final ProductoDAO productoDAO;

    // Formateador de moneda
    private final DecimalFormat df = new DecimalFormat("$#,##0.00");

    public VentanaPrincipal() {
        productoDAO = new ProductoDAO();
        initComponents();
        cargarDatosTabla();
    }

    private void initComponents() {
        setTitle("Gestión de Productos - Control de Inventario");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1050, 680);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(900, 550));

        // Panel Principal con margen
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(12, 12, 12, 12));
        setContentPane(mainPanel);

        // Header Panel (Titulo superior)
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(33, 43, 54));
        headerPanel.setBorder(new EmptyBorder(12, 15, 12, 15));

        JLabel lblTitulo = new JLabel("GESTIÓN DE PRODUCTOS");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitulo.setForeground(Color.WHITE);
        headerPanel.add(lblTitulo, BorderLayout.WEST);

        JLabel lblSubtitulo = new JLabel("Prueba Técnica Java Jr");
        lblSubtitulo.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        lblSubtitulo.setForeground(new Color(200, 210, 225));
        headerPanel.add(lblSubtitulo, BorderLayout.EAST);

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Split Pane central (Formulario a la izquierda, Tabla a la derecha)
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, crearPanelFormulario(), crearPanelTabla());
        splitPane.setDividerLocation(360);
        splitPane.setResizeWeight(0.35);
        mainPanel.add(splitPane, BorderLayout.CENTER);

        // Bar de Estado inferior
        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setBorder(new EmptyBorder(4, 8, 4, 8));

        lblEstadoMensaje = new JLabel("Listo.");
        lblEstadoMensaje.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblEstadoMensaje.setForeground(new Color(70, 80, 95));
        footerPanel.add(lblEstadoMensaje, BorderLayout.WEST);

        mainPanel.add(footerPanel, BorderLayout.SOUTH);
    }

    /**
     * Panel izquierdo: Formulario de entrada de datos y botones principales.
     */
    private JPanel crearPanelFormulario() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), " Datos del Producto ",
                TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
                new Font("Segoe UI", Font.BOLD, 14), new Color(33, 43, 54)
        ));

        JPanel formFields = new JPanel(new GridBagLayout());
        formFields.setBorder(new EmptyBorder(8, 8, 8, 8));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // ID (oculto/deshabilitado)
        txtId = new JTextField();
        txtId.setEditable(false);
        txtId.setVisible(false);

        // Campos
        txtCodigo = new JTextField(15);
        txtNombre = new JTextField(15);

        String[] categorias = {"Electrónica", "Ropa", "Alimentos", "Hogar", "Calzado", "Herramientas", "Otros"};
        cbCategoria = new JComboBox<>(categorias);

        txtPrecio = new JTextField(15);
        spStock = new JSpinner(new SpinnerNumberModel(0, 0, 99999, 1));

        String[] estados = {"Activo", "Inactivo"};
        cbEstado = new JComboBox<>(estados);

        // Fila 0: Codigo
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.3;
        formFields.add(new JLabel("Código: *"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.7;
        formFields.add(txtCodigo, gbc);

        // Fila 1: Nombre
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.3;
        formFields.add(new JLabel("Nombre: *"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.7;
        formFields.add(txtNombre, gbc);

        // Fila 2: Categoria
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0.3;
        formFields.add(new JLabel("Categoría:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.7;
        formFields.add(cbCategoria, gbc);

        // Fila 3: Precio
        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0.3;
        formFields.add(new JLabel("Precio ($): *"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.7;
        formFields.add(txtPrecio, gbc);

        // Fila 4: Stock
        gbc.gridx = 0; gbc.gridy = 4; gbc.weightx = 0.3;
        formFields.add(new JLabel("Stock Inicial:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.7;
        formFields.add(spStock, gbc);

        // Fila 5: Estado
        gbc.gridx = 0; gbc.gridy = 5; gbc.weightx = 0.3;
        formFields.add(new JLabel("Estado:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.7;
        formFields.add(cbEstado, gbc);

        panel.add(formFields, BorderLayout.CENTER);

        // Panel de Botones inferiores del formulario
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 10));

        btnNuevo = new JButton("Nuevo");
        btnNuevo.setToolTipText("Limpiar formulario para registrar un nuevo producto");
        btnNuevo.addActionListener(e -> limpiarFormulario());

        btnGuardar = new JButton("Guardar");
        btnGuardar.setToolTipText("Guardar producto nuevo o actualizar seleccionado");
        btnGuardar.setBackground(new Color(40, 167, 69));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnGuardar.addActionListener(e -> guardarProducto());

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setToolTipText("Eliminar el producto seleccionado");
        btnEliminar.setBackground(new Color(220, 53, 69));
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnEliminar.addActionListener(e -> eliminarProducto());

        panelBotones.add(btnNuevo);
        panelBotones.add(btnGuardar);
        panelBotones.add(btnEliminar);

        panel.add(panelBotones, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Panel derecho: Busqueda, Tabla de listado de productos y Ajustes de Stock.
     */
    private JPanel crearPanelTabla() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), " Listado de Productos ",
                TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
                new Font("Segoe UI", Font.BOLD, 14), new Color(33, 43, 54)
        ));

        // Panel Superior de Busqueda y Filtros
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 6));

        searchPanel.add(new JLabel("Buscar:"));
        txtBuscar = new JTextField(15);
        txtBuscar.setToolTipText("Buscar por código o nombre");
        txtBuscar.addActionListener(e -> buscarProductos());

        btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(e -> buscarProductos());

        btnLimpiarBusqueda = new JButton("Ver Todos");
        btnLimpiarBusqueda.addActionListener(e -> {
            txtBuscar.setText("");
            cargarDatosTabla();
        });

        btnBajoStock = new JButton("Bajo Stock (< 5)");
        btnBajoStock.setToolTipText("Mostrar únicamente productos con stock menor a 5 unidades");
        btnBajoStock.setBackground(new Color(255, 193, 7));
        btnBajoStock.setFont(new Font("Segoe UI", Font.BOLD, 11));
        btnBajoStock.addActionListener(e -> mostrarBajoStock());

        searchPanel.add(txtBuscar);
        searchPanel.add(btnBuscar);
        searchPanel.add(btnLimpiarBusqueda);
        searchPanel.add(btnBajoStock);

        panel.add(searchPanel, BorderLayout.NORTH);

        // Tabla de Productos
        String[] columnas = {"ID", "Código", "Nombre", "Categoría", "Precio", "Stock", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer tabla no editable directamente
            }
        };

        tblProductos = new JTable(modeloTabla);
        tblProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblProductos.setRowHeight(24);
        tblProductos.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tblProductos.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tblProductos.getTableHeader().setBackground(new Color(230, 235, 245));

        // Alineacion y formato de celdas
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
        tblProductos.getColumnModel().getColumn(4).setCellRenderer(rightRenderer); // Precio

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tblProductos.getColumnModel().getColumn(0).setCellRenderer(centerRenderer); // ID
        tblProductos.getColumnModel().getColumn(1).setCellRenderer(centerRenderer); // Codigo
        tblProductos.getColumnModel().getColumn(5).setCellRenderer(centerRenderer); // Stock
        tblProductos.getColumnModel().getColumn(6).setCellRenderer(centerRenderer); // Estado

        // Listener para cargar fila seleccionada en el formulario
        tblProductos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                cargarProductoSeleccionado();
            }
        });

        JScrollPane scrollPane = new JScrollPane(tblProductos);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Panel Inferior: Controles rapidos de Ajuste de Stock
        JPanel stockControlsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 6));
        stockControlsPanel.add(new JLabel("Ajuste rápido de stock:"));

        btnSumarStock = new JButton("+1 Unid.");
        btnSumarStock.setToolTipText("Sumar 1 unidad al producto seleccionado");
        btnSumarStock.addActionListener(e -> modificarStockRapido(1));

        btnRestarStock = new JButton("-1 Unid.");
        btnRestarStock.setToolTipText("Restar 1 unidad al producto seleccionado");
        btnRestarStock.addActionListener(e -> modificarStockRapido(-1));

        btnAjustarStockManual = new JButton("Ajustar Cantidad...");
        btnAjustarStockManual.setToolTipText("Establecer un valor de stock específico");
        btnAjustarStockManual.addActionListener(e -> abrirDialogoAjustarStock());

        stockControlsPanel.add(btnSumarStock);
        stockControlsPanel.add(btnRestarStock);
        stockControlsPanel.add(btnAjustarStockManual);

        panel.add(stockControlsPanel, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Carga todos los productos desde la base de datos a la JTable.
     */
    private void cargarDatosTabla() {
        try {
            List<Producto> productos = productoDAO.listarTodos();
            actualizarTabla(productos);
            mostrarMensajeEstado("Se cargaron " + productos.size() + " productos.");
        } catch (SQLException e) {
            mostrarError("Error al cargar productos desde la base de datos", e);
        }
    }

    /**
     * Actualiza las filas de la JTable con la lista provista.
     */
    private void actualizarTabla(List<Producto> lista) {
        modeloTabla.setRowCount(0);
        for (Producto p : lista) {
            Object[] fila = {
                    p.getId(),
                    p.getCodigo(),
                    p.getNombre(),
                    p.getCategoria(),
                    df.format(p.getPrecio()),
                    p.getStock(),
                    p.getEstado()
            };
            modeloTabla.addRow(fila);
        }
    }

    /**
     * Carga los datos de la fila seleccionada de la tabla al formulario.
     */
    private void cargarProductoSeleccionado() {
        int fila = tblProductos.getSelectedRow();
        if (fila >= 0) {
            txtId.setText(modeloTabla.getValueAt(fila, 0).toString());
            txtCodigo.setText(modeloTabla.getValueAt(fila, 1).toString());
            txtNombre.setText(modeloTabla.getValueAt(fila, 2).toString());
            cbCategoria.setSelectedItem(modeloTabla.getValueAt(fila, 3).toString());

            // Formato precio
            String precioStr = modeloTabla.getValueAt(fila, 4).toString()
                    .replace("$", "").replace(".", "").replace(",", ".").trim();
            txtPrecio.setText(precioStr);

            spStock.setValue(Integer.parseInt(modeloTabla.getValueAt(fila, 5).toString()));
            cbEstado.setSelectedItem(modeloTabla.getValueAt(fila, 6).toString());

            btnGuardar.setText("Modificar");
            mostrarMensajeEstado("Producto seleccionado: " + txtCodigo.getText() + " - " + txtNombre.getText());
        }
    }

    /**
     * Guarda o Modifica un producto evaluando validaciones de negocio.
     */
    private void guardarProducto() {
        String codigo = txtCodigo.getText().trim();
        String nombre = txtNombre.getText().trim();
        String categoria = (String) cbCategoria.getSelectedItem();
        String precioText = txtPrecio.getText().trim();
        int stock = (Integer) spStock.getValue();
        String estado = (String) cbEstado.getSelectedItem();

        // Validacion 1: Campos obligatorios (codigo y nombre)
        if (codigo.isEmpty() || nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "El código y el nombre del producto son campos obligatorios.",
                    "Dato Faltante", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Validacion 2: Precio debe ser valido y mayor a 0
        double precio;
        try {
            precio = Double.parseDouble(precioText.replace(",", "."));
            if (precio <= 0) {
                JOptionPane.showMessageDialog(this,
                        "El precio del producto debe ser mayor a 0.",
                        "Precio Inválido", JOptionPane.WARNING_MESSAGE);
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Por favor ingrese un valor numérico válido para el precio.",
                    "Precio Inválido", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Validacion 3: Stock >= 0
        if (stock < 0) {
            JOptionPane.showMessageDialog(this,
                    "El stock no puede ser un número negativo.",
                    "Stock Inválido", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            boolean esModificacion = !txtId.getText().isEmpty();

            if (esModificacion) {
                int id = Integer.parseInt(txtId.getText());

                // Verificar que no duplique el codigo de otro producto
                if (productoDAO.existeCodigoDiferenteId(codigo, id)) {
                    JOptionPane.showMessageDialog(this,
                            "Ya existe otro producto registrado con el código '" + codigo + "'.",
                            "Código Duplicado", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                Producto p = new Producto(id, codigo, nombre, categoria, precio, stock, estado);
                if (productoDAO.modificar(p)) {
                    JOptionPane.showMessageDialog(this,
                            "Producto actualizado correctamente.",
                            "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    limpiarFormulario();
                    cargarDatosTabla();
                }
            } else {
                // Registro de Nuevo Producto: verificar codigo duplicado
                if (productoDAO.existeCodigo(codigo)) {
                    JOptionPane.showMessageDialog(this,
                            "Ya existe un producto registrado con el código '" + codigo + "'.",
                            "Código Duplicado", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                Producto p = new Producto(codigo, nombre, categoria, precio, stock, estado);
                if (productoDAO.guardar(p)) {
                    JOptionPane.showMessageDialog(this,
                            "Producto registrado con éxito.",
                            "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    limpiarFormulario();
                    cargarDatosTabla();
                }
            }

        } catch (SQLException e) {
            mostrarError("Error al guardar/modificar el producto", e);
        }
    }

    /**
     * Elimina el producto seleccionado previa confirmacion del usuario.
     */
    private void eliminarProducto() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Seleccione primero un producto de la tabla para eliminar.",
                    "Selección requerida", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = Integer.parseInt(txtId.getText());
        String nombre = txtNombre.getText();

        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de que desea eliminar el producto '" + nombre + "'?",
                "Confirmar Eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                if (productoDAO.eliminar(id)) {
                    JOptionPane.showMessageDialog(this,
                            "Producto eliminado correctamente.",
                            "Eliminado", JOptionPane.INFORMATION_MESSAGE);
                    limpiarFormulario();
                    cargarDatosTabla();
                }
            } catch (SQLException e) {
                mostrarError("Error al eliminar el producto", e);
            }
        }
    }

    /**
     * Busca productos filtrando por codigo o nombre.
     */
    private void buscarProductos() {
        String criterio = txtBuscar.getText().trim();
        if (criterio.isEmpty()) {
            cargarDatosTabla();
            return;
        }

        try {
            List<Producto> resultados = productoDAO.buscar(criterio);
            actualizarTabla(resultados);
            mostrarMensajeEstado("Búsqueda: " + resultados.size() + " productos encontrados para '" + criterio + "'.");
        } catch (SQLException e) {
            mostrarError("Error en la búsqueda de productos", e);
        }
    }

    /**
     * Muestra productos con bajo stock (< 5 unidades).
     */
    private void mostrarBajoStock() {
        try {
            int limite = 5;
            List<Producto> bajoStock = productoDAO.listarBajoStock(limite);
            actualizarTabla(bajoStock);
            mostrarMensajeEstado("Mostrando productos con stock menor a " + limite + " unidades (" + bajoStock.size() + " encontrados).");
        } catch (SQLException e) {
            mostrarError("Error al listar productos con bajo stock", e);
        }
    }

    /**
     * Incrementa o decrementa el stock en 1 unidad para el producto seleccionado.
     */
    private void modificarStockRapido(int delta) {
        int fila = tblProductos.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this,
                    "Seleccione primero un producto de la tabla para ajustar su stock.",
                    "Selección requerida", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = Integer.parseInt(modeloTabla.getValueAt(fila, 0).toString());
        int stockActual = Integer.parseInt(modeloTabla.getValueAt(fila, 5).toString());
        int nuevoStock = stockActual + delta;

        if (nuevoStock < 0) {
            JOptionPane.showMessageDialog(this,
                    "El stock no puede quedar en valores negativos.",
                    "Acción no permitida", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            if (productoDAO.ajustarStock(id, nuevoStock)) {
                cargarDatosTabla();
                seleccionarFilaPorId(id);
                mostrarMensajeEstado("Stock de producto ID " + id + " actualizado a " + nuevoStock + " unidades.");
            }
        } catch (SQLException e) {
            mostrarError("Error al ajustar el stock", e);
        }
    }

    /**
     * Abre un dialogo emergente para establecer manualmente el stock.
     */
    private void abrirDialogoAjustarStock() {
        int fila = tblProductos.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this,
                    "Seleccione primero un producto de la tabla para ajustar su stock.",
                    "Selección requerida", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = Integer.parseInt(modeloTabla.getValueAt(fila, 0).toString());
        String nombre = modeloTabla.getValueAt(fila, 2).toString();
        int stockActual = Integer.parseInt(modeloTabla.getValueAt(fila, 5).toString());

        String input = JOptionPane.showInputDialog(this,
                "Ingrese la nueva cantidad de stock para '" + nombre + "':",
                String.valueOf(stockActual));

        if (input != null && !input.trim().isEmpty()) {
            try {
                int nuevoStock = Integer.parseInt(input.trim());
                if (nuevoStock < 0) {
                    JOptionPane.showMessageDialog(this,
                            "El stock no puede ser negativo.",
                            "Stock Inválido", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (productoDAO.ajustarStock(id, nuevoStock)) {
                    JOptionPane.showMessageDialog(this,
                            "Stock actualizado correctamente a " + nuevoStock + " unidades.",
                            "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    cargarDatosTabla();
                    seleccionarFilaPorId(id);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Por favor ingrese un número entero válido.",
                        "Valor Inválido", JOptionPane.WARNING_MESSAGE);
            } catch (SQLException e) {
                mostrarError("Error al actualizar el stock", e);
            }
        }
    }

    /**
     * Limpia los campos del formulario y desselecciona la tabla.
     */
    private void limpiarFormulario() {
        txtId.setText("");
        txtCodigo.setText("");
        txtNombre.setText("");
        cbCategoria.setSelectedIndex(0);
        txtPrecio.setText("");
        spStock.setValue(0);
        cbEstado.setSelectedIndex(0);

        btnGuardar.setText("Guardar");
        tblProductos.clearSelection();
        txtCodigo.requestFocus();
        mostrarMensajeEstado("Formulario listo para nuevo registro.");
    }

    /**
     * Selecciona una fila en la JTable dado su ID de producto.
     */
    private void seleccionarFilaPorId(int id) {
        for (int i = 0; i < modeloTabla.getRowCount(); i++) {
            int idFila = Integer.parseInt(modeloTabla.getValueAt(i, 0).toString());
            if (idFila == id) {
                tblProductos.setRowSelectionInterval(i, i);
                break;
            }
        }
    }

    private void mostrarMensajeEstado(String msj) {
        lblEstadoMensaje.setText(msj);
    }

    private void mostrarError(String titulo, Exception e) {
        lblEstadoMensaje.setText("ERROR: " + e.getMessage());
        JOptionPane.showMessageDialog(this,
                titulo + ":\n" + e.getMessage(),
                "Error en la aplicación", JOptionPane.ERROR_MESSAGE);
    }
}
