/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.stepup.appfacturacionweb.rest01;

import ec.com.stepup.appfacturacion.entity_bean.Categoria;
import ec.com.stepup.appfacturacion.session_bean.CategoriaFacadeLocal;
import java.util.Arrays;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author admin
 */
@Path("categorias")
@RequestScoped
public class Categorias {

    @EJB
    private CategoriaFacadeLocal categoriaFacadeLocal;
    
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GenericResource
     */
    public Categorias() {
    }

    /**
     * Retrieves representation of an instance of
     * ec.com.stepup.appfacturacionweb.rest01.Categorias
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Categoria> getCategorias() {
        /*return Arrays.asList(
                new Categoria(1, "Alimentos", 'A'),
                new Categoria(2, "Ropa", 'I'),
                new Categoria(3, "Bebidas", 'A')
        );*/
        return categoriaFacadeLocal.findAll();
    }

    @GET
    @Path("activos")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Categoria> getCategoriasActivos() {
        return Arrays.asList(
                new Categoria(1, "Alimentos", 'A'),
                new Categoria(3, "Bebidas", 'A')
        );
    }

    @GET
    @Path("categoria1/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Categoria getCategoria1(@PathParam("id") Integer id) {
        return new Categoria(id, "Electrodomesticos", 'A');
    }

    @GET
    @Path("categoria2/{id}/{nombre}")
    @Produces(MediaType.APPLICATION_JSON)
    public Categoria getCategoria2(@PathParam("id") Integer id, @PathParam("nombre") String nombre) {
        return new Categoria(id, nombre, 'A');
    }

    @GET
    @Path("categoria3")
    @Produces(MediaType.APPLICATION_JSON)
    public Categoria getCategoria3(@QueryParam("id") Integer id) {
        return new Categoria(id, "Electrodomesticos", 'A');
    }

    @GET
    @Path("categoria4/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCategoria4(@PathParam("id") Integer id) {
        if (id < 10) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            //return Response.status(Response.Status.OK).entity(new Categoria(id, "NOMBRE", 'A')).build();
            return Response.ok(new Categoria(id, "NOMBRE", 'A')).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(Categoria categoria) {
        System.out.println("Categoria creada: " + categoria);
        return Response.ok().build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response edit(Categoria categoria) {
        System.out.println("Categoria editada: " + categoria);
        return Response.ok().build();
    }

    @DELETE
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response remove(@PathParam("id") Integer id) {
        System.out.println("Categoria eliminada: " + id);
        return Response.ok().build();
    }
}
