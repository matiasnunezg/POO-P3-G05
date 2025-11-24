package espol.poo.modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// ==========================================================================
// CLASE PRINCIPAL: CONTENEDOR DE TODO EL MODELO
// Esta clase agrupa todas las funcionalidades para Sostenibilidad y Juego.
// ==========================================================================
public class Sostenibilidad {

    // ######################################################################
    //                          MÓDULO 1: SOSTENIBILIDAD
    //        (Clases para registrar acciones, días y calcular puntos)
    // ######################################################################

    /* * 1. CLASE ACCION SOSTENIBLE
     * Representa una única opción del menú (ej: "Usar Bici").
     * Define qué es la acción y cuántos puntos vale.
     */
    public static class AccionSostenible {
        private int id;
        private String descripcion;
        private int puntos;

        public AccionSostenible(int id, String descripcion, int puntos) {
            this.id = id;
            this.descripcion = descripcion;
            this.puntos = puntos;
        }

        public int getId() { return id; }
        public String getDescripcion() { return descripcion; }
        public int getPuntos() { return puntos; }

    } // --- FIN de la clase AccionSostenible ---


    /* * 2. CLASE REGISTRO DIARIO
     * Representa un día específico (ej: 14/10/2025).
     * Guarda la fecha y la lista de cosas que el usuario hizo ese día.
     */
    public static class RegistroDiarioSostenible {
        private LocalDate fecha;
        private List<AccionSostenible> accionesDelDia;

        public RegistroDiarioSostenible(LocalDate fecha, List<AccionSostenible> accionesDelDia) {
            this.fecha = fecha;
            this.accionesDelDia = accionesDelDia;
        }

        public LocalDate getFecha() { return fecha; }
        public List<AccionSostenible> getAccionesDelDia() { return accionesDelDia; }

        public int calcularPuntos() {
            int total = 0;
            for (AccionSostenible a : accionesDelDia) {
                total += a.getPuntos();
            }
            return total;
        }

    } // --- FIN de la clase RegistroDiarioSostenible ---


    /* * 3. CLASE REGISTRO SOSTENIBLE (GESTOR)
     * Es el historial completo. Guarda una lista de "Registros Diarios".
     * Aquí se calculan las estadísticas semanales y frecuencias.
     */
    public static class RegistroSostenible {
        private List<RegistroDiarioSostenible> registros;

        public RegistroSostenible() {
            this.registros = new ArrayList<>();
        }

        public void agregarRegistro(RegistroDiarioSostenible reg) {
            this.registros.add(reg);
        }

        public List<RegistroDiarioSostenible> getRegistros() {
            return registros;
        }

        // Calcula cuántas veces se repitió cada acción (ID -> Cantidad)
        public Map<Integer, Integer> calcularFrecuencias() {
            Map<Integer, Integer> freq = new HashMap<>();
            for (RegistroDiarioSostenible r : registros) {
                for (AccionSostenible a : r.getAccionesDelDia()) {
                    freq.put(a.getId(), freq.getOrDefault(a.getId(), 0) + 1);
                }
            }
            return freq;
        }

        public int contarDiasConAcciones() {
            int count = 0;
            for (RegistroDiarioSostenible r : registros) {
                if (!r.getAccionesDelDia().isEmpty()) {
                    count++;
                }
            }
            return count;
        }

        public int contarDiasPerfectos(int totalAcciones) {
            int count = 0;
            for (RegistroDiarioSostenible r : registros) {
                if (r.getAccionesDelDia().size() == totalAcciones) {
                    count++;
                }
            }
            return count;
        }

        public String generarTipEcologico() {
            return "Tip: Reduce el uso de envases descartables llevando siempre tu termo.";
        }

    } // --- FIN de la clase RegistroSostenible ---


    // ######################################################################
    //                          MÓDULO 2: JUEGO DE MEMORIA
    //            (Clases para las cartas y la lógica del tablero)
    // ######################################################################

    /* * 4. CLASE CARTA
     * Representa una ficha individual del juego.
     * Tiene un valor (texto/imagen) y sabe si está boca arriba o boca abajo.
     */
    public static class Carta {
        private String valor;
        private boolean descubierta;

        public Carta(String valor) {
            this.valor = valor;
            this.descubierta = false;
        }

        public String getValor() { return valor; }
        public boolean isDescubierta() { return descubierta; }
        public void setDescubierta(boolean descubierta) {
            this.descubierta = descubierta;
        }

    } // --- FIN de la clase Carta ---


    /* * 5. CLASE JUEGO MEMORIA (CONTROL DE REGLAS)
     * Crea el mazo, baraja las cartas y verifica si dos cartas son par.
     */
    public static class JuegoMemoria {
        private List<Carta> cartas;
        private int paresEncontrados;

        public JuegoMemoria(List<String> valores) {
            cartas = new ArrayList<>();
            // Por cada valor recibido, creamos 2 cartas para formar el par
            for (String v : valores) {
                cartas.add(new Carta(v));
                cartas.add(new Carta(v));
            }
            Collections.shuffle(cartas); // Barajar aleatoriamente
        }

        public List<Carta> getCartas() { return cartas; }
        public int getParesEncontrados() { return paresEncontrados; }

        // Lógica para verificar si dos posiciones elegidas son iguales
        public boolean seleccionarCartas(int i, int j) {
            // Validamos que los índices existan y no sean la misma carta
            if (i < 0 || j < 0 || i >= cartas.size() || j >= cartas.size() || i == j) {
                return false; 
            }

            Carta c1 = cartas.get(i);
            Carta c2 = cartas.get(j);

            if (c1.getValor().equals(c2.getValor())) {
                c1.setDescubierta(true);
                c2.setDescubierta(true);
                paresEncontrados++;
                return true;
            }
            return false;
        }

    } // --- FIN de la clase JuegoMemoria ---

} // ========================================================================
  // FIN DE LA CLASE PRINCIPAL ModeloSostenibilidad
  // ========================================================================

  //Hola soy Aarón 