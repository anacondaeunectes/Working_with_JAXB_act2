package operaciones;

import java.time.LocalDate;

import utilityPackage.EntradaDatos;

/**
 * Clase que contiene el metodo main de la aplicacion y su punto de entrada. Este ejecuta un CLI que permite operar con los datos de un XML.
 * @author Pablo Gutierrez
 *
 */
public class Aplicacion {
	
	//Referencia a la instancia unica de la clase SINGLETON Operaciones. Con este objeto se accederan a todas las funcionalidades de la aplicaciones.
	private static Operaciones operaciones = Operaciones.getOperaciones();
	
	//Referencia a la instancia uica de la claase SINGLETON EntradaDatos. Con este objeto se accederan a todos los metodos de I/O entre el usuario y la aplica.
	private static EntradaDatos entradaDatos = EntradaDatos.getEntradaDatos();
	
	/**
	 * Metodo main donde se ejecuta el programa. Este ofrece las distintas funcionalidades de la aplicaion a traves de un menu por consola que interactua con el usuario.
	 * @param args Argumentos de entrada al programa, en este caso no van a ser utilizados.
	 */
	public static void main(String[] args) {
		
		int num = 1, numVenta, sumaUnidades;
		
		System.out.println("Leyendo archivo XML y caragando datos...");
		
		if (operaciones.lecturaXML()) {
			System.out.println("Lectura finalizada correctamente.");
		}else {
			System.out.println("Error al leer los datos. Por favor revise la existencia del fichero '" + operaciones.xmlFile + "'");
			num = 0;
		}
				
		//Bucle para el menu
		while (num != 0) {
			
			System.out.println("\nMENU GESTION VENTAS");
			System.out.println("=-=-=-=-=-=-=-=-=-=");
			System.out.println("1. Borrar venta");
			System.out.println("2. Agregar unidades");
			System.out.println("3. Modificar venta");
			System.out.println("4. Guardar cambios");
			System.out.println("0. Guardar y salir");
			System.out.println("\nIntroduzca la opcion deseada:");
		
			switch (num = entradaDatos.pedirInt()) {
			
			//Borrar venta
			case 1:

				System.out.println("Introduzca el numero de la venta:");
				numVenta = entradaDatos.pedirInt();
				if (operaciones.borrarVenta(numVenta)) {
					System.out.println("¡Venta borrada con exito!");
				}else {
					System.err.println("Error al borrar la venta. Por favor, revise el numero de venta introducido.");
				}
				
			break;
			
			//Agregar unidades
			case 2:
				
				System.out.println("Introduzca el numero de venta:");
				numVenta = entradaDatos.pedirInt();
				System.out.println("Introduzca el numero unidades a agregar:");
				sumaUnidades = entradaDatos.pedirInt();
				
				if (operaciones.agregarUnidades(numVenta, sumaUnidades) && sumaUnidades > 0) {
					System.out.println("¡Unidades agregadas con exito!");
				}else {
					System.err.println("Error al agregar las unidades. Posibles fallos:\n \t -Numero ve venta no existente\n \t -El numero de unidades a agregar sea negativo o 0.");
				}
				
				break;
			
			//Modificar datos venta
			case 3:
				System.out.println("Introduzca el numero de la venta:");
				numVenta = entradaDatos.pedirInt();
				System.out.println("Introduzca el nuevo numero de unidades de la venta:");
				sumaUnidades = entradaDatos.pedirInt();

				if (operaciones.modificarVenta(numVenta, sumaUnidades, entradaDatos.pedirFecha(1800, LocalDate.now().getYear()))) {
					System.out.println("¡Venta modificada con exito!");
				}else {
					System.err.println("Error al modificar la venta. Posibles fallos:\n \t -Numero ve venta no existente\n \t -El nuevo numero de unidades sea negativo o 0.\n \t -Fecha erronea o anterior a 1800.");
				}
				
				break;
			
			//Guardar cambios
			case 4:
				if (operaciones.escrituraXML()) {
					System.out.println("¡Guardado realizado con exito!");
				}else {
					System.err.println("Fallo en el guardado. Por favor intentelo de nuevo.");
				}
				
				break;
			
			//Guardar y salir
			case 0:
				if (operaciones.escrituraXML()) {
					System.out.println("¡Guardado realizado con exito!\n¡Adios!");
					num = 0;
				}else {
					System.err.println("Error al guardar los datos. ¿Quiere cancelar la operacion de salida y salir de todas formas?");
					if (entradaDatos.pedirYesNo() == 1) { //En caso afirmativo (y), se saldra sin guardar. En caso negativo (n), se cancelara la operacion de salida y se retornara al menu.
						num = 0;
					}
				}
			
				break;
			//Mensaje de error en caso de no seleccionar ninguna de las opciones descritas.
			default:
				System.err.println("Opcion no valida. Por favor, introduzca una opcion valida:");
				break;
			}
			
		}
		
	}	

}