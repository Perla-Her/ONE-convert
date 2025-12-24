import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner lectura = new Scanner(System.in);
        consultaMoneda consulta = new consultaMoneda();
        int opcion = 0;

        while (opcion != 7) {
            System.out.println("**********************************************");
            System.out.println("Bienvenido al Conversor con CurrencyFreaks");
            System.out.println("1) Dólar (USD) =>> Peso argentino (ARS)\n" +
                    "2) Dólar (USD) =>> Real brasileño (BRL)\n" +
                    "3) Dólar (USD) =>> Peso colombiano (COP)\n" +
                    "4) Dólar (USD) =>> Euro (EUR)\n" +
                    "5) Dólar (USD) =>> Libra Esterlina (GBP)\n" +
                    "6) Dólar (USD) =>> Yen Japonés (JPY)\n" +
                    "7) Salir");
            System.out.print("Elija una opción válida: ");

            try {
                // Leemos la opción del menú
                String entradaOpcion = lectura.nextLine();
                opcion = Integer.parseInt(entradaOpcion);

                if (opcion == 7) {
                    System.out.println("Finalizando el programa. ¡Gracias!");
                    break;
                }

                // Determinamos la moneda de destino
                String monedaDestino = switch (opcion) {
                    case 1 -> "ARS";
                    case 2 -> "BRL";
                    case 3 -> "COP";
                    case 4 -> "EUR";
                    case 5 -> "GBP";
                    case 6 -> "JPY";
                    default -> null;
                };

                if (monedaDestino == null) {
                    System.out.println("Opción inválida, por favor intente de nuevo.");
                    continue;
                }

                // Pedimos la cantidad
                System.out.print("Ingrese la cantidad en Dólares (USD): ");
                double cantidad = Double.parseDouble(lectura.nextLine());

                // Llamamos a la API
                System.out.println("Consultando tasas de cambio...");
                Monedas datos = consulta.buscarMoneda();

                // VALIDACIÓN: Verificamos que la respuesta no sea nula
                if (datos != null && datos.rates() != null) {
                    String tasaEnTexto = datos.rates().get(monedaDestino);

                    if (tasaEnTexto != null) {
                        double tasa = Double.parseDouble(tasaEnTexto);
                        double resultado = cantidad * tasa;

                        // ESTA SECCIÓN ES LA QUE MUESTRA EL RESULTADO
                        System.out.println("\n==============================================");
                        System.out.println(cantidad + " USD equivalen a =>>> "
                                + String.format("%.2f", resultado) + " " + monedaDestino);
                        System.out.println("==============================================\n");
                    } else {
                        System.out.println("Error: No se encontró la tasa para " + monedaDestino);
                    }
                } else {
                    System.out.println("Error: La API no devolvió datos válidos.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Error: Por favor, ingrese un número válido.");
            } catch (RuntimeException e) {
                System.out.println("Error en la aplicación: " + e.getMessage());
            }
        }
    }
}