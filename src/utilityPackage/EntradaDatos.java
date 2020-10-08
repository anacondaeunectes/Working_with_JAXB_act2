package utilityPackage;

import java.time.LocalDate;
import java.util.Scanner;

import operaciones.Operaciones;

/**
 * Clase SINGLETON que controla la entrada de datos al programa.
 * @author Pablo Gutierrez
 */
public class EntradaDatos {
	
	//Instancia de la clase a ser utiliada de manera unica.
	private static EntradaDatos entradaDatos_Instance = null;
	
	//Constructor vacio propio del patron singleton.
	private EntradaDatos() {}
	
	//Metodo getter que permite recoger el objeto EntradaDatos unico. En caso de que todavia no se haya creado la instancia, se crea. 
	public static EntradaDatos getEntradaDatos(){
		if (entradaDatos_Instance == null) {
			entradaDatos_Instance = new EntradaDatos();
		}
		
		return entradaDatos_Instance;
	}

	/**
	 * Scanner que crea el flujo de entrada.
	 */
	public Scanner entrada = new Scanner(System.in);

	/**
	 * Metodo que permite la entrada de un dato de tipo double por teclado
	 * @return double
	 */
	public double pedirDouble() {
		
		double num = 0;
		boolean ok=false;
		
		do {
			try {
				num = entrada.nextDouble();
				ok=true;
				
			} catch (Exception e) {
				System.out.println("Numero no valido. Por favor, vuelva a introducirlo: ");
				ok=false;
			}
			
			entrada.nextLine();
		} while (ok==false);
		
		return num;
	}
	
	/**
	 * Metodo que permite la entrada de un dato de tipo int por teclado
	 * @return int
	 */
	public int pedirInt(){
		
		int num = 0;
		boolean ok=true;
		
		do {
			try {
				
				ok=true;
				num = entrada.nextInt();

				
			} catch (Exception e) {
				System.out.println("Numero no valido. Por favor, vuelva a introducirlo: ");
				ok=false;
			}
			
			entrada.nextLine();
		}while(ok==false);
		
		
		
		return num;
	}
	
	/**
	 * Metodo que permite la entrada de un dato de tipo int por teclado pero que solo permite que este se encuentre en una determinada horquilla de numeros.
	 * @param min 
	 * @param max
	 * @return int
	 */
	public int pedirInt_Parametros(int min, int max) {
		
		String frase = "Numero no valido. Por favor, vuelva a introducirlo: ";
		int num = 0;
		boolean ok=true;
		
		do {
			
			do {
				try {
					num = entrada.nextInt();
					ok=true;
					
				} catch (Exception e) {
					System.out.println(frase);
					ok=false;
				}
				entrada.nextLine();
			}while(ok==false);
		
			if (num < min || num > max) {
				
				ok=false;
				System.out.println(frase);
			}
			
		} while(ok==false);
		
		
		
		return num;
	}
	
	/**
	 * Metodo que permite la entrada de un dato de tipo String por teclado
	 * @return String
	 */
	public String pedirString() {
		
		String cadena = entrada.nextLine();
		
		return cadena;
		
	}
	
	public LocalDate pedirFecha(int annoInicio, int annoLimite) {
		
		System.out.println("Introduzca un agno:");
		int year = pedirInt_Parametros(annoInicio, annoLimite);
		System.out.println("Introduzca un mes:");
		int month = pedirInt_Parametros(1, 12);
		System.out.println("Introduzca un dia:");
		int dayOfMonth = pedirInt_Parametros(1, 31);
		
		return LocalDate.of(year, month, dayOfMonth);
	}
	
	public int pedirYesNo() {
				
		char character; 
		
		int c = 5;
		
		while (c > 1) {
			System.out.println("Introduzca una opcion (y/n):");
			character = pedirString().charAt(0);
			
			if (character == 'y' || character == 'Y') {
				c = 1;
			}else if (character == 'n' || character == 'N') {
				c = 0;
			}
		}
		
		return c;
	}
	
}
