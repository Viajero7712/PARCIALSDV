/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controlador;
//Esto es un servlet

import Modelo.Clientes;
import Modelo.ClientesDAO;
import Modelo.Empleado;
import Modelo.EmpleadoDAO;
import Modelo.Producto;
import Modelo.ProductoDAO;
import Modelo.Venta;
import Modelo.VentaDAO;
import config.GenerarSerie;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Controlador extends HttpServlet {

    Empleado emp = new Empleado();
    EmpleadoDAO edao = new EmpleadoDAO();
    Clientes clien = new Clientes();
    ClientesDAO clienteDAO = new ClientesDAO();
    Producto produ = new Producto();
    ProductoDAO productoDAO = new ProductoDAO();
    int ide;
    int idc;
    int idp;

    Venta vn=new Venta();
    VentaDAO vdao=new VentaDAO();
    List<Venta>lista=new ArrayList<>();
    int item;
    int cod;
    String descripcion;
    double precio;
    int cantidad;
    double subtotal;
    double totalPagar;
    String numeroserie;
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String menu = request.getParameter("menu");
        String accion = request.getParameter("accion");//Tenemos una variable llamada accion, que recibe la accion del usuario, y se ejecuta la accion correspondiente

        if (menu.equals("Principal")) {
            request.getRequestDispatcher("Principal.jsp").forward(request, response);
        }

        if (menu.equals("Empleado")) {//Dentro de cada if se crean los switch

            switch (accion) {
                case "Listar":
                    List lista = edao.listar();
                    request.setAttribute("empleados", lista);

                    break;

                case "Agregar":
                    //Variables que almacenan datos ingresados para agregar
                    String dni = request.getParameter("txtDni");
                    String nom = request.getParameter("txtNombres");
                    String tel = request.getParameter("txtTel");
                    String est = request.getParameter("txtEstado");
                    String user = request.getParameter("txtUser");
                    emp.setDni(dni);//Se estan agregando los datos
                    emp.setNom(nom);
                    emp.setTel(tel);
                    emp.setEstado(est);
                    emp.setUser(user);
                    edao.agregar(emp);
                    request.getRequestDispatcher("Controlador?menu=Empleado&accion=Listar").forward(request, response);
                    break;

                case "Actualizar": //Actualiza los datos de esa misma fila, no agregar un nuevo dato
                    String dni1 = request.getParameter("txtDni");
                    String nom1 = request.getParameter("txtNombres");
                    String tel1 = request.getParameter("txtTel");
                    String est1 = request.getParameter("txtEstado");
                    String user1 = request.getParameter("txtUser");
                    emp.setDni(dni1);//Se estan agregando los datos
                    emp.setNom(nom1);
                    emp.setTel(tel1);
                    emp.setEstado(est1);
                    emp.setUser(user1);
                    emp.setId(ide);//Se envia el id que es capturado cuando el usuario presiona editar
                    edao.actualizar(emp);
                    request.getRequestDispatcher("Controlador?menu=Empleado&accion=Listar").forward(request, response);
                    break;
                case "Editar":
                    ide = Integer.parseInt(request.getParameter("id"));
                    Empleado e = edao.listarId(ide);//e almacena datos fila seleccionada
                    request.setAttribute("empleado", e);//Enviar datos al formulario
                    request.getRequestDispatcher("Controlador?menu=Empleado&accion=Listar").forward(request, response);
                    break;

                case "Delete":
                    ide = Integer.parseInt(request.getParameter("id"));
                    edao.delete(ide);
                    request.getRequestDispatcher("Controlador?menu=Empleado&accion=Listar").forward(request, response);
                    break;

                default:
                    throw new AssertionError();
            }
            request.getRequestDispatcher("Empleado.jsp").forward(request, response);
        }
        if (menu.equals("Clientes")) {
            switch (accion) {
                case "Listar":
                    List listaClientes = clienteDAO.listar();
                    request.setAttribute("clientes", listaClientes);
                    break;
                case "Agregar":
                    String dniCliente = request.getParameter("txtDniC");
                    String nombresCliente = request.getParameter("txtNombresC");
                    String direccionCliente = request.getParameter("txtDireccionC");
                    String estadoCliente = request.getParameter("txtEstadoC");
                    clien.setDni(dniCliente);
                    clien.setNombres(nombresCliente);
                    clien.setDireccion(direccionCliente);
                    clien.setEstado(estadoCliente);
                    clienteDAO.agregar(clien);
                    request.getRequestDispatcher("Controlador?menu=Clientes&accion=Listar").forward(request, response);
                    break;

                case "Actualizar":
                    String dniC = request.getParameter("txtDniC");
                    String nombresC = request.getParameter("txtNombresC");
                    String direccionC = request.getParameter("txtDireccionC");
                    String estadoC = request.getParameter("txtEstadoC");
                    clien.setDni(dniC);
                    clien.setNombres(nombresC);
                    clien.setDireccion(direccionC);
                    clien.setEstado(estadoC);
                    clien.setIdCliente(idc);
                    clienteDAO.actualizar(clien);
                    request.getRequestDispatcher("Controlador?menu=Clientes&accion=Listar").forward(request, response);
                    break;

                case "Editar":
                    idc = Integer.parseInt(request.getParameter("id"));
                    Clientes cl = clienteDAO.listarId(idc);
                    request.setAttribute("cliente", cl);
                    request.getRequestDispatcher("Controlador?menu=Clientes&accion=Listar").forward(request, response);
                    break;
                case "Delete":
                    idc = Integer.parseInt(request.getParameter("id"));
                    clienteDAO.delete(idc);
                    request.getRequestDispatcher("Controlador?menu=Clientes&accion=Listar").forward(request, response);
                    break;
                default:
                    throw new AssertionError();
            }
            request.getRequestDispatcher("Clientes.jsp").forward(request, response);
        }
        if (menu.equals("Producto")) {
            switch (accion) {
                case "Listar":
                    List listaProductos = productoDAO.listar();
                    request.setAttribute("productos", listaProductos);
                    break;

                case "Agregar":
                    String nombre = request.getParameter("txtNombres");
                    double precio = Double.parseDouble(request.getParameter("txtPrecio"));
                    int stock = Integer.parseInt(request.getParameter("txtStock"));
                    String estado = request.getParameter("txtEstado");
                    produ.setNombre(nombre);
                    produ.setPrecio(precio);
                    produ.setStock(stock);
                    produ.setEstado(estado);
                    productoDAO.agregar(produ);
                    request.getRequestDispatcher("Controlador?menu=Producto&accion=Listar").forward(request, response);
                    break;

                case "Actualizar":
                    String nombreActualizar = request.getParameter("txtNombres");
                    double precioActualizar = Double.parseDouble(request.getParameter("txtPrecio"));
                    int stockActualizar = Integer.parseInt(request.getParameter("txtStock"));
                    String estadoActualizar = request.getParameter("txtEstado");
                    produ.setNombre(nombreActualizar);
                    produ.setPrecio(precioActualizar);
                    produ.setStock(stockActualizar);
                    produ.setEstado(estadoActualizar);
                    produ.setId(idp); // Ensure idp is set correctly
                    productoDAO.actualizar(produ);
                    request.getRequestDispatcher("Controlador?menu=Producto&accion=Listar").forward(request, response);
                    break;

                case "Editar":
                    idp = Integer.parseInt(request.getParameter("id"));
                    Producto p = productoDAO.listarId(idp);
                    request.setAttribute("producto", p);
                    request.getRequestDispatcher("Controlador?menu=Producto&accion=Listar").forward(request, response);
                    break;

                case "Delete":
                    idp = Integer.parseInt(request.getParameter("id"));
                    productoDAO.delete(idp);
                    request.getRequestDispatcher("Controlador?menu=Producto&accion=Listar").forward(request, response);
                    break;

                default:
                    throw new AssertionError();
            }
            request.getRequestDispatcher("Producto.jsp").forward(request, response);
        }
        if (menu.equals("NuevaVenta")) {
            switch (accion) {
                case "BuscarCliente": {
                    String dni = request.getParameter("codigocliente");
                    clien.setDni(dni);
                    clien = clienteDAO.buscar(dni);
                    request.setAttribute("c", clien);
                    request.getRequestDispatcher("RegistrarVenta.jsp").forward(request, response);
                    break;
                }
               
               
            case "BuscarProducto":
                int id=Integer.parseInt(request.getParameter("codigoproducto"));
                produ=productoDAO.listarId(id);
                request.setAttribute("c", clien);
                request.setAttribute("producto", produ);
                request.setAttribute("lista", lista);
                request.setAttribute("totalpagar", totalPagar);
                break;
          
                
            case "Agregar":
                    request.setAttribute("c", clien);
                totalPagar=0.0;
                item=item+1;
                cod=produ.getIdProducto();
                descripcion=request.getParameter("nombreproducto");
                precio=Double.parseDouble(request.getParameter("precio"));
                cantidad=Integer.parseInt(request.getParameter("cant"));
                subtotal=precio*cantidad;
                vn=new Venta();
                vn.setItem(item);
                vn.setIdproducto(cod);
                vn.setDescripcion(descripcion);
                vn.setPrecio(precio);
                vn.setCantidad(cantidad);
                vn.setSubtotal(subtotal);
                lista.add(vn);
                for(int i = 0 ; i < lista.size() ; i++){
                    totalPagar=totalPagar +lista.get(i).getSubtotal();
                }
               request.setAttribute("totalpagar", totalPagar);
                request.setAttribute("lista", lista);
                break;
            case "GenerarVenta":
                for(int i = 0; i <lista.size();i++){
                    Producto pr=new Producto();
                    int cantidad=lista.get(i).getCantidad();
                    int idproducto=lista.get(i).getIdproducto();
                    ProductoDAO aO=new ProductoDAO();
                    pr=aO.buscar(idproducto);
                    int sac=pr.getStock()-cantidad;
                    aO.actualizarstock(idproducto, sac);
                }
                vn.setIdcliente(clien.getIdCliente());
                vn.setIdempleado(2);
                vn.setNumserie(numeroserie);
                vn.setFecha("2019-06-14");
                vn.setMonto(totalPagar);
                vn.setEstado("1");
                vdao.guardarVenta(vn);
                
                int idv=Integer.parseInt(vdao.IdVentas());
                for(int i = 0; i <lista.size();i++){
                    vn=new Venta();
                    vn.setId(idv);
                    vn.setIdproducto(lista.get(i).getIdproducto());
                    vn.setCantidad(lista.get(i).getCantidad());
                    vn.setPrecio(lista.get(i).getPrecio());
                    vdao.guardarDetalleventas(vn);
                }
                request.setAttribute("nserie", numeroserie);
                break;
            case "Eliminar":
                lista.remove(vn);
                request.getRequestDispatcher("Controlador?menu=NuevaVenta&accion=listar").forward(request, response);
                
                break;
            
            case "EliminarPro":
                    
                    break;
                  default: {
                      
                    vn=new Venta();
                    lista=new ArrayList<>();
                    item=0;
                    totalPagar=0.0;
                
                    numeroserie=vdao.GenerarSerie();
                    
                    if(numeroserie==null){
                    numeroserie="00000001";
                    request.setAttribute("nserie", numeroserie);
                    }else{
                        int incrementar=Integer.parseInt(numeroserie);
                        GenerarSerie gs=new GenerarSerie();
                        numeroserie=gs.NumeroSerie(incrementar);
                        request.setAttribute("nserie", numeroserie);
                    }
                    request.getRequestDispatcher("RegistrarVenta.jsp").forward(request, response);
                    }
            }
            request.getRequestDispatcher("RegistrarVenta.jsp").forward(request, response);
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
