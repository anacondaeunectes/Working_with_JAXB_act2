package operaciones;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import clasesDatos.ObjectFactory;
import clasesDatos.VentasType;
import clasesDatos.Ventas.Venta;

/**
 * Clase SINGLETON que contiene la logica de las funcionalidades de la aplicacion.
 * @author Pablo Gutierrez
 *
 */
public class Operaciones {
	
	/**
	 * Instancia de la clase a ser utiliada de manera unica.
	 */
	private static Operaciones operaciones_Instance = null;
	
	/**
	 * Constructor vacio propio del patron singleton.
	 */
	private Operaciones() {}
	
	/**
	 * Metodo getter que permite recoger el objeto Operaciones unico. En caso de que todavia no se haya creado la instancia, se crea. 
	 * @return retorna la instancia unica de la clase operaciones.
	 */
	public static Operaciones getOperaciones(){
		if (operaciones_Instance == null) {
			operaciones_Instance = new Operaciones();
		}
		
		return operaciones_Instance;
	}
	
		/**
		 * Objeto JAXBElement estatico que contiene toda la informacion leida en el XML.
		 */
		public JAXBElement<VentasType> element;
		
		/**
		 * Objeto File que representa el XML con el que esta aplicacion trabaja.
		 */
		public File xmlFile = new File("ventasarticulos.xml");
		
		/**
		 * Metodo que lee la informacion contenida en el XML y la carga en un JAXBElement.
		 * @return <ul>
		 * 			<li>true: si se produce una lectura correcta.</li>
		 * 			<li>false: si se produce algun error en la lectura.</li>
		 * 			</ul>
		 */
		public boolean lecturaXML() {
			
			boolean flag = true;
			
			try {
				
				FileInputStream fis = new FileInputStream(xmlFile);
				
				JAXBContext context = JAXBContext.newInstance(ObjectFactory.class);
				
				Unmarshaller unmarshaller = context.createUnmarshaller();
				
				element = (JAXBElement) unmarshaller.unmarshal(fis);

			} catch (JAXBException e) {
				e.printStackTrace();
				flag = false;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				flag = false;
			}
			
			return flag;
		}
		
		/**
		 * Metodo que escribe la informacion del JAXBElement "element" en el XML.
		 * @return <ul>
		 * 			<li>true: si se produce una escritura correcta.</li>
		 * 			<li>false: si se produce algun error en la escritura. En cuyo caso, los cambios realizados seran deshechos.</li>
		 * 			</ul>
		 */
		public boolean escrituraXML() {
			
			boolean flag = true;
			
			try {
			
				JAXBContext context = JAXBContext.newInstance(ObjectFactory.class);
				
				Marshaller marshaller = context.createMarshaller();
				
				marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
				
				VentasType ventas = element.getValue();
				
				marshaller.marshal(new ObjectFactory().createVentasarticulos(ventas), xmlFile);
				
			} catch (JAXBException e) {
				e.printStackTrace();
				flag = false;
			}

			return flag;
		}
		
		/**
		 * Metodo que borra los datos de una venta en funcion de su numero de venta.
		 * @param numVenta numero de la venta a borrar.
		 * @return <ul>
		 * 			<li>true: si el borrado se realiza correctamente.</li>
		 * 			<li>false: si se produce algun error en el borrado.</li>
		 * 			</ul>
		 */
		public boolean borrarVenta(int numVenta) {
			
			boolean flag = false;		
			
			Optional<Venta> ventaBorrar = buscarVenta(numVenta);
			
			if (ventaBorrar.isPresent()) {
				element.getValue().getVentas().getVenta().remove(ventaBorrar.get());
				flag = true;
			}

			return flag;
		}
		
		/**
		 * Metodo que agrega un determinado numero de unidades a una de las ventas.
		 * @param numVenta numero de venta cuyas unidades quieren ser modificadas.
		 * @param adicion numero de unidades a agregar a las unidades actuales de a venta.
		 * @return <ul>
		 * 			<li>true: si la modificacion se efectua con exito.</li>
		 * 			<li>false: si se produce algun error en la modificacion.</li>
		 * 			</ul>
		 */
		public boolean agregarUnidades(int numVenta, int adicion) {
			
			boolean flag = false;
			
			Optional<Venta> ventaUnidades = buscarVenta(numVenta);
			
			if (ventaUnidades.isPresent()) {
				ventaUnidades.get().setUnidades(ventaUnidades.get().getUnidades() + adicion);
				flag = true;
			}
			
			return flag;
		}
		
		/**
		 * Metodo que modifica los datos de una venta, tanto las unidades vendidas como la fecha de la venta.
		 * @param numVenta numero de venta a modificar.
		 * @param unidades nuevas unidades de la venta.
		 * @param fecha nueva fecha de la venta.
		 * @return <ul>
		 * 			<li>true: si la modificacion se realiza con exito.</li>
		 * 			<li>false: si se produce algun error en la modificacion.</li>
		 * 			</ul>
		 */
		public boolean modificarVenta(int numVenta, int unidades, LocalDate fecha) {
			
			boolean flag = false;
			
			Optional<Venta> ventaModificar = buscarVenta(numVenta);
			
			if (ventaModificar.isPresent()) {
				
				ventaModificar.get().setUnidades(unidades);
				ventaModificar.get().setFecha(fecha.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
				
				flag = true;
			}
			
			return flag;
		}
		
		/**
		 * Metodo que busca una venta en el listado en funcion de su numero de venta.
		 * @param numVenta numero de venta a buscar
		 * @return devuelve un Optional<Venta> en funcion del numero de venta introducido por parametro. Este puede estar vacio si no ha encontrada esa numero de venta o puede contener el objeto Venta que coincide con el numero de venta buscado.
		 */
		private Optional<Venta> buscarVenta(int numVenta){
			
			List<Venta> listaVentas = element.getValue().getVentas().getVenta();

			return listaVentas.stream().
					filter(x -> x.getNumventa().intValue() == numVenta).
					findFirst();
		}

}
