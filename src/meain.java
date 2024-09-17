import java.util.Random;
import java.util.Scanner;

public class meain {
    //Codigo de vendedor antes de rango de precios
/*public static void comerciante() {
        Scanner scanner = new Scanner(System.in);

        // Comerciante
        Random random = new Random();
        boolean continuarComprando = true;

        while (continuarComprando) {
            List<String> itemsDisponibles = new ArrayList<>();
            for (String objeto : Objetos) {
                if (objeto != null && !objeto.equals("Mapa")) {
                    itemsDisponibles.add(objeto);
                }
            }
            for (String equipo : Equipamiento) {
                if (equipo != null && !equipo.equals("Jugador")) {
                    itemsDisponibles.add(equipo);
                }
            }
            for (int i = 1; i < NombreVarita.length; i++) {
                if (!VaritaActiva[i]) {
                    itemsDisponibles.add(NombreVarita[i]);
                }
            }

            Collections.shuffle(itemsDisponibles);
            System.out.println("");
            System.out.println(CYAN + "  Tus gemas: " + Gemas);
            imprimirPocoAPoco(AMARILLO + "  El comerciante tiene los siguientes objetos a la venta:" + RESET, 20);
            int[] precios = new int[4];

            for (int i = 0; i < 4 && i < itemsDisponibles.size(); i++) {
                String item = itemsDisponibles.get(i);
                if (item != null) {  // Verifica que el item no sea null
                    int precio = generarPrecioAleatorio(item);
                    precios[i] = precio;
                    imprimirPocoAPoco(VERDE  + "  "+ (i + 1) + ". " + item + " - Precio: " + precio + " gemas" + RESET, 20);
                }
            }

            imprimirPocoAPoco(AZUL + "  Elige un objeto para comprar (0 para salir): " + RESET, 20);
            int eleccion = scanner.nextInt();
            System.out.println("");
            if (eleccion > 0 && eleccion <= 4 && eleccion <= itemsDisponibles.size()) {
                String itemComprado = itemsDisponibles.get(eleccion - 1);
                int precio = precios[eleccion - 1];
                if (Gemas >= precio) {
                    Gemas -= precio;

                    imprimirPocoAPoco(MAGENTA + "  Has comprado " + itemComprado + " por " + precio + " gemas. Te quedan " + Gemas + " gemas." + RESET, 20);
                    procesarCompra(itemComprado, VaritaActiva, Salud, Cantidades);
                } else {
                    imprimirPocoAPoco(ROJO + "  No tienes suficientes gemas para comprar " + itemComprado + "." + RESET, 30);
                }
            } else {
                continuarComprando = false;
            }
        }
    }
    public static int generarPrecioAleatorio(String item) {
        Random random = new Random();
        int basePrice;

        Map<String, Integer> precioBaseMap = new HashMap<>();
        precioBaseMap.put("Poción de curación", 5);
        precioBaseMap.put("Poción de fuerza", 5);
        precioBaseMap.put("Poción de agilidad", 5);
        precioBaseMap.put("Antorcha", 2);
        precioBaseMap.put("Cristal de mana", 10);
        precioBaseMap.put("Armadura", 10);
        precioBaseMap.put("Escudo", 10);
        precioBaseMap.put("Espada", 10);
        precioBaseMap.put("Bayas", 2);

        basePrice = precioBaseMap.getOrDefault(item, 50);

        int precioAleatorio = basePrice + random.nextInt(31);
        return precioAleatorio;
    }
    public static void procesarCompra(String item, boolean[] VaritaActiva, int[] Salud, int[] Cantidades) {
        Random random = new Random();

        if (item.equals("Armadura")) {
            Salud[1] = 20 + random.nextInt(50);
            imprimirPocoAPoco("  Salud de armadura: " + Salud[1],20);
            System.out.println("");
        } else if (item.equals("Escudo")) {
            Salud[2] = 10 + random.nextInt(40);
            imprimirPocoAPoco("  Salud de escudo: " + Salud[2], 20);
            System.out.println("");
        } else if (item.equals("Espada")) {
            Salud[3] = 5 + random.nextInt(15);
            Daño[1] = 3 + random.nextInt(12);
            imprimirPocoAPoco("  Daño de espada: " + Daño[1], 20);
            imprimirPocoAPoco("  Salud de espada: " + Salud[3], 20);
            System.out.println("");
        } else if (item.equals("Poción de curación")) {
            Cantidades[0]++;
            imprimirPocoAPoco("  Poción de curación obtenida: + 1", 20);
            System.out.println("");
        } else if (item.equals("Poción de fuerza")) {
            Cantidades[1]++;
            imprimirPocoAPoco("  Poción de fuerza obtenida: + 1", 20);
            System.out.println("");
        } else if (item.equals("Poción de agilidad")) {
            Cantidades[2]++;
            imprimirPocoAPoco("  Poción de agilidad obtenida: + 1", 20);
            System.out.println("");
        } else if (item.equals("Antorcha")) {
            Cantidades[4]++;
            imprimirPocoAPoco("  Antorcha obtenida: + 1",20);
            System.out.println("");
        } else if (item.equals("Cristal de mana")) {
            Cantidades[5]++;
            imprimirPocoAPoco("  Cristal de mana obtenida: + 1", 20);
            System.out.println("");
        } else {
            Map<String, Integer> varitaMap = new HashMap<>();
            varitaMap.put("Varita de fuego", 1);
            varitaMap.put("Varita de hielo", 2);
            varitaMap.put("Varita de debilidad", 3);
            varitaMap.put("Varita de curación", 4);
            varitaMap.put("Varita de protección", 5);

            if (varitaMap.containsKey(item)) {
                VaritaActiva[varitaMap.get(item)] = true;
            }
        }
    }
*/




}



