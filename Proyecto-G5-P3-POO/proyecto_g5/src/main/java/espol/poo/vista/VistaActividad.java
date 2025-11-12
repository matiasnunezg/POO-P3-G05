package espol.poo.vista;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.InputMismatchException;

import espol.poo.modelo.Actividad;
import espol.poo.modelo.Actividad.TipoPrioridad;
import espol.poo.modelo.ActividadAcademica;
import espol.poo.modelo.ActividadAcademica.TipoActividadAcademica;
import espol.poo.modelo.ActividadPersonal;
import espol.poo.modelo.ActividadPersonal.TipoActividadPersonal;
public class VistaActividad {

    Scanner sc = new Scanner(System.in);
    public void mostrarListaActividades(ArrayList<Actividad> lista) {
    
    // 1. Encabezado
    System.out.println("\n--- LISTADO DE ACTIVIDADES PENDIENTES ---");
    
    // 2. Títulos de las Columnas (Usando printf para alinear)
    // %-3s:  ID, 3 caracteres, alineado a la izquierda
    // %-10s: TIPO, 10 caracteres, alineado a la izquierda
    // %-40s: NOMBRE, 40 caracteres, alineado a la izquierda
    // %-12s: VENCIMIENTO, 12 caracteres, alineado a la izquierda
    // %-10s: PRIORIDAD, 10 caracteres, alineado a la izquierda
    // %-8s:  AVANCE, 8 caracteres, alineado a la izquierda
    System.out.printf("%-3s | %-10s | %-40s | %-16s | %-10s | %-8s%n",
            "ID", "TIPO", "NOMBRE", "VENCIMIENTO", "PRIORIDAD", "AVANCE");
    
    // 3. Línea Separadora (coincidiendo con los anchos)
    System.out.println("----|------------|------------------------------------------|------------------|------------|-------------");

    // 4. Manejo de Lista Vacía
    if (lista.isEmpty()) {
        System.out.println("No hay actividades para mostrar en este filtro.");
    } 
    
    // 5. Bucle para imprimir cada fila
    else {
        for (Actividad a : lista) {
            String tipo = "N/A"; // Valor por defecto
            
            // Determina el tipo de actividad
            if (a instanceof ActividadAcademica) {
                // Obtiene el enum (TAREA, EXAMEN, etc.) y lo convierte a String
                tipo = ((ActividadAcademica) a).getActividadAcademica().toString();
            } else if (a instanceof ActividadPersonal) {
                // Obtiene el enum (CITA, EJERCICIO, etc.) y lo convierte a String
                tipo = ((ActividadPersonal) a).getActividadPersonal().toString();
            }
            
            // Imprime la fila con el mismo formato que el encabezado
            System.out.printf("%-3s | %-10s | %-40s | %-12s | %-10s | %-8s%n",
                    a.getId(),                 
                    tipo,                     
                    a.getNombre(),          
                    a.getFechaVencimiento(),   
                    a.getPrioridad(),        
                    a.getAvance() + "%");     
        }
    }
    
    // 6. Línea Separadora Final
    System.out.println("------------------------------------------------------------------------------------------------------");
}
public void mostrarDetalle(Actividad actividadmostrada) {
    System.out.println("==============================================================");
    System.out.println("==============================================================");
    
    if (actividadmostrada instanceof ActividadAcademica){
        ActividadAcademica academica = (ActividadAcademica) actividadmostrada;
        System.out.println("Detalles de "+academica.getActividadAcademica().toString());
        System.out.println("==============================================================");
        System.out.println("==============================================================");
        System.out.println("Nombre: "+actividadmostrada.getNombre());
        System.out.println("Tipo: "+academica.getActividadAcademica().toString());
        System.out.println("Asignatura: "+academica.getAsignatura());
        System.out.println("Prioridad: "+actividadmostrada.getPrioridad());
        if (actividadmostrada.getAvance() == 100){
            System.out.println("Estado: Terminada");
        } else {
            System.out.println("Estado: En curso");
        }
        System.out.println("Fecha Limite: "+actividadmostrada.getFechaVencimiento());
        
        // --- CORRECCIÓN DE COPIAR/PEGAR ---
        System.out.println("Tiempo Estimado Total: "+actividadmostrada.getTiempoEstimado() + " min.");
        
        System.out.println("Avance Actual: "+actividadmostrada.getAvance() + "%");
    } 
    else { // No es necesario 'if (actividadmostrada instanceof ActividadPersonal)'
        ActividadPersonal personal = (ActividadPersonal) actividadmostrada;
        System.out.println("Detalles de "+personal.getActividadPersonal());
        System.out.println("==============================================================");
        System.out.println("==============================================================");
        System.out.println("Nombre: "+actividadmostrada.getNombre());
        System.out.println("Tipo: "+personal.getActividadPersonal().toString());
        System.out.println("Lugar: "+personal.getLugar());
        System.out.println("Prioridad: "+actividadmostrada.getPrioridad());
        if (actividadmostrada.getAvance() == 100){
            System.out.println("Estado: Terminada");
        } else {
            System.out.println("Estado: En curso");
        }
        System.out.println("Fecha Limite: "+actividadmostrada.getFechaVencimiento());
        
        // --- CORRECCIÓN DE COPIAR/PEGAR ---
        System.out.println("Tiempo Estimado Total: "+actividadmostrada.getTiempoEstimado() + " min.");
        
        System.out.println("Avance Actual: "+actividadmostrada.getAvance() + "%");
    }
    
    // --- ¡¡SOLUCIÓN AL BUCLE INFINITO!! ---
    // Llama a tu método que espera a que el usuario presione [ENTER]
    mostrarMensaje("Presione [ENTER] para volver a la lista...");
}

public boolean verificarRango(int valoringresado,int valorminimo, int valormaximo){
        if ((valorminimo <= valoringresado) && (valormaximo >= valoringresado)){
            return true;
        }
        else{
            return false;
        }
    }
    public boolean pedirConfirmacion(String mensaje) {
    System.out.print(mensaje + " (S/N): ");
    String respuesta = sc.nextLine().trim().toUpperCase();
    return respuesta.equals("S");
}
    public boolean pedirConfirmacionEliminar(String nombreActividad) {
    System.out.println("Usted seleccionó la actividad: '" + nombreActividad + "'.");
    return pedirConfirmacion("¿Está seguro que desea ELIMINAR PERMANENTEMENTE esta actividad?");
}
    public void mostrarMensaje(String mensaje) {
    System.out.println(mensaje);
    System.out.println("Presione [ENTER] para continuar...");
    sc.nextLine();
}
    public int pedirOpcionGestion() {
        System.out.println("1. Visualizar actividades");
        System.out.println("2. Crear actividad");
        System.out.println("3. Registrar avance de actividad");
        System.out.println("4. Eliminar actividad");
        System.out.println("5. Volver al menú");
        int opcion = 0; 
        do {
            System.out.print("Ingrese la opción: ");
            try {
                opcion = sc.nextInt();
                
                if (verificarRango(opcion, 1,5) == false) {
                    System.out.println("Error: Ingrese una opción posible");
                    opcion = 0;
                }
            
            } catch (InputMismatchException e) {System.out.println("Error: Debe ingresar solo números.");
                opcion = 0; 
            } finally {

                sc.nextLine(); 
            }
        
        } while (opcion == 0);
        
        return opcion;
    }

    public int pedirtipoactividad(){
        int opcion = 0; 
        do {
            System.out.println("Seleccione la categoría de la actividad: ");
            System.out.println("1. Académica (Tarea, Examen, Proyecto)");
            System.out.println("2. Personal (Citas, Ejercicio, Hobbies)");
            try {
                opcion = sc.nextInt();
                
                if (verificarRango(opcion, 1, 2) == false){
                    System.out.println("Error: La opción debe ser un número entre 1 y 2.");
                    opcion = 0;
                }
            
            } catch (InputMismatchException e) {System.out.println("Error: Debe ingresar solo números.");
                opcion = 0; 
            } finally {

                sc.nextLine(); 
            }
        
        } while (opcion == 0);
        
        return opcion;
    }
    public int pedirIdActividad(int tamanoLista) {
        if (tamanoLista == 0) {
            System.out.println("No hay actividades para seleccionar.");
            return 0; 
        }

        int id = 0;

        do {
            System.out.print("Ingrese el ID de la actividad (1 - " + tamanoLista + "): ");
            
            try {
                id = sc.nextInt();
                if (verificarRango(id, 1, tamanoLista) == false) {
                    System.out.println("Error: El ID no es válido. Debe ser un número entre 1 y " + tamanoLista + ".");
                    id = 0;
                }
            
            } catch (InputMismatchException e) {
                System.out.println("Error: Debe ingresar solo números.");
                id = 0; 
            
            } finally {
                sc.nextLine(); 
            }

        } while (id == 0);
        
        return id;
    }
public int pedirNumeroRango(String mensaje, int min, int max) {
    int numero = 0;
    do {
        System.out.print(mensaje + " ");
        try {
            numero = sc.nextInt();
            if (numero < min || numero > max) {
                System.out.println("Error: El valor debe estar entre " + min + " y " + max + ".");
                numero = min - 1;
            }
        } catch (InputMismatchException e) {
            System.out.println("Error: Debe ingresar solo números.");
            numero = min - 1;
        } finally {
            sc.nextLine();
        }
    } while (numero < min || numero > max);
    return numero;
}
    public int pedirFiltroActividades() {
    System.out.println("\n--- Visualizar Actividades ---");
    System.out.println("¿Qué actividades desea ver?");
    System.out.println("1. Todas");
    System.out.println("2. Solo Académicas");
    System.out.println("3. Solo Personales");
    int opcion = pedirNumeroRango("Ingrese la opción: ",1,3);
    return opcion;
}
    public int pediravance() {
        int avance = 0; 
        do {
            System.out.print("Ingrese el porcentaje de avance (0% - 100%): ");
            try {
                avance = sc.nextInt();
                
                if (verificarRango(avance, 0, 100) == false) {
                    System.out.println("Error: El porcentaje de avance debe estar en el rango (0% - 100%)");
                    avance = 0;
                }
            
            } catch (InputMismatchException e) {System.out.println("Error: Debe ingresar solo números.");
                avance = 0; 
            } finally {

                sc.nextLine(); 
            }
        
        } while (avance == 0);
        
        return avance;
    }
    public void mostrarError(){
        System.out.println("Opción no válida, inténtelo de nuevo");
    }
    public String pedirTextoNoVacio(String mensaje) {
    String texto = ""; // Inicializa como vacío para que el bucle comience
    
    do {
        System.out.print(mensaje + " ");
        
        // 1. Lee la línea completa y usa .trim()
        // .trim() quita los espacios en blanco del inicio y del final.
        texto = sc.nextLine().trim(); 
        
        // 2. Comprueba si, después de quitar espacios, la cadena está vacía
        if (texto.isEmpty()) {
            System.out.println("Error: El campo no puede estar vacío. Intente de nuevo.");
        }
        
    } while (texto.isEmpty()); // 3. Repite si el texto sigue vacío
    
    return texto;
}
    public int pedirNumeroPositivo(String mensaje) {
    int numero = 0; 
    
    do {
        System.out.print(mensaje + " ");
        try {
            numero = sc.nextInt();
            
            // Valida que el número sea estrictamente positivo
            if (numero <= 0) {
                System.out.println("Error: El número debe ser positivo (mayor que 0).");
                numero = 0; 
            }
        
        } catch (InputMismatchException e) {
            System.out.println("Error: Debe ingresar solo números.");
            numero = 0; 
        
        } finally {
            sc.nextLine(); 
        }
    
    } while (numero == 0); 
    
    return numero;
}
    public TipoPrioridad pedirPrioridad() {
    System.out.println("Seleccione la Prioridad:");

    // 1. Imprime las opciones del enum dinámicamente
    int i = 1;
    for (TipoPrioridad p : TipoPrioridad.values()) {
        System.out.println(i + ". " + p.toString());
        i++;
    }
    
    // 2. Prepara la validación
    int maxOpcion = TipoPrioridad.values().length;
    int opcion = 0;
    
    do {
        System.out.print("Ingrese opción (1-" + maxOpcion + "): ");
        try {
            opcion = sc.nextInt();
            
            // 3. Valida el rango
            if (opcion < 1 || opcion > maxOpcion) {
                System.out.println("Error: Opción fuera de rango.");
                opcion = 0; // Marca como inválido para repetir
            }
        
        } catch (InputMismatchException e) {
            System.out.println("Error: Debe ingresar solo números.");
            opcion = 0; // Marca como inválido
        
        } finally {
            sc.nextLine(); // Limpia el buffer
        }
    
    } while (opcion == 0);
    
    // 4. Devuelve el valor del enum (opcion-1 para el índice)
    return TipoPrioridad.values()[opcion - 1];
}
    public TipoActividadAcademica pedirTipoActividadAcademica() {
        System.out.println("Ha seleccionado: ACADÉMICA.");
        System.out.println("Seleccione el tipo específico:");

        // 1. Imprime las opciones del enum dinámicamente
        int i = 1;
        for (TipoActividadAcademica tipo : TipoActividadAcademica.values()) {
            System.out.println(i + ". " + tipo.toString());
            i++;
        }
        
        // 2. Prepara la validación
        int maxOpcion = TipoActividadAcademica.values().length;
        int opcion = 0;
        
        do {
            System.out.print("Ingrese opción (1-" + maxOpcion + "): ");
            try {
                opcion = sc.nextInt();
                
                // 3. Valida el rango (1 hasta el número de opciones)
                if (opcion < 1 || opcion > maxOpcion) {
                    System.out.println("Error: Opción fuera de rango.");
                    opcion = 0; // Marca como inválido para repetir
                }
            
            } catch (InputMismatchException e) {
                System.out.println("Error: Debe ingresar solo números.");
                opcion = 0; // Marca como inválido
            
            } finally {
                sc.nextLine(); // Limpia el buffer
            }
        
        } while (opcion == 0);
        
        // 4. Devuelve el valor del enum (opcion-1 para el índice)
        return TipoActividadAcademica.values()[opcion - 1];
    }
    public TipoActividadPersonal pedirTipoActividadPersonal() {
        System.out.println("Ha seleccionado: PERSONAL.");
        System.out.println("Seleccione el tipo específico:");

        // 1. Imprime las opciones del enum dinámicamente
        int i = 1;
        for (TipoActividadPersonal tipo : TipoActividadPersonal.values()) {
            System.out.println(i + ". " + tipo.toString());
            i++;
        }
        
        // 2. Prepara la validación
        int maxOpcion = TipoActividadPersonal.values().length;
        int opcion = 0;
        
        do {
            System.out.print("Ingrese opción (1-" + maxOpcion + "): ");
            try {
                opcion = sc.nextInt();
                
                // 3. Valida el rango (1 hasta el número de opciones)
                if (opcion < 1 || opcion > maxOpcion) {
                    System.out.println("Error: Opción fuera de rango.");
                    opcion = 0; // Marca como inválido para repetir
                }
            
            } catch (InputMismatchException e) {
                System.out.println("Error: Debe ingresar solo números.");
                opcion = 0; // Marca como inválido
            
            } finally {
                sc.nextLine(); // Limpia el buffer
            }
        
        } while (opcion == 0);
        
        // 4. Devuelve el valor del enum (opcion-1 para el índice)
        return TipoActividadPersonal.values()[opcion - 1];
    }
    public int getMaxDiasEnMes(int mes, int anio) {
    switch (mes) {
        // Meses con 30 días
        case 4:  // Abril
        case 6:  // Junio
        case 9:  // Septiembre
        case 11: // Noviembre
            return 30;

        // Caso especial: Febrero
        case 2:
            // Esta es la regla del año bisiesto:
            // Divisible por 4, pero no por 100, A MENOS que también sea divisible por 400.
            if ((anio % 4 == 0 && anio % 100 != 0) || (anio % 400 == 0)) {
                return 29; // Es año bisiesto
            } else {
                return 28; // No es año bisiesto
            }

        // El resto de los meses (1, 3, 5, 7, 8, 10, 12)
        default:
            return 31;
    }
}

    public String pedirfechaVencimiento() {
        int anio = 0; 
        do {
            System.out.print("Ingrese el año: ");
            try {
                anio = sc.nextInt();
                
                if (verificarRango(anio, 2025, 2100) == false) {
                    System.out.println("Error: El año debe estar entre (2025 - 2100)");
                    anio = 0;
                }
            
            } catch (InputMismatchException e) {System.out.println("Error: Debe ingresar solo números.");
                anio = 0; 
            } finally {

                sc.nextLine(); 
            }
        
        } while (anio == 0);

        int mes = 0; 
        do {
            System.out.print("Ingrese el mes: ");
            try {
                mes = sc.nextInt();
                
                if (verificarRango(mes, 1, 12) == false) {
                    System.out.println("Error: El mes debe estar entre (1 - 12)");
                    mes = 0;
                }
            
            } catch (InputMismatchException e) {System.out.println("Error: Debe ingresar solo números.");
                mes = 0; 
            } finally {

                sc.nextLine(); 
            }
        
        } while (mes == 0);

        int dia = 0; 
        do {
            System.out.print("Ingrese el dia: ");
            try {
                dia = sc.nextInt();
                
                if (verificarRango(dia, 1, getMaxDiasEnMes(mes, anio)) == false) {
                    System.out.println("Error: Dia no válido");
                    dia = 0;
                }
            
            } catch (InputMismatchException e) {System.out.println("Error: Debe ingresar solo números.");
                dia = 0; 
            } finally {

                sc.nextLine(); 
            }
        
        } while (dia == 0);
        
        return String.format("%02d/%02d/%d", dia, mes, anio);
    }
    
    public String pedirHoraVencimiento() {
    System.out.println("--- Ingrese la Hora de Vencimiento (Formato 24h) ---");
    int hora = pedirNumeroRango("Ingrese la Hora (0-23):", 0, 23);
    int minuto = pedirNumeroRango("Ingrese los Minutos (0-59):", 0, 59);
    return String.format("%02d:%02d", hora, minuto);
}
}
