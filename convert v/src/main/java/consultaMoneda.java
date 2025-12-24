import com.google.gson.Gson;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class consultaMoneda {
    public Monedas buscarMoneda() {

        String apiKey = "29c8e0228c6c497d88680f0ce902d99a";
        URI direccion = URI.create("https://api.currencyfreaks.com/v1/latest?apikey=" + apiKey);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(direccion)
                .build();

        try {
            // Enviamos la petición y recibimos la respuesta
            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());

            // Usamos GSON para convertir el JSON en nuestro record Monedas
            return new Gson().fromJson(response.body(), Monedas.class);
        } catch (Exception e) {
            throw new RuntimeException("Error: No se pudo conectar con el servicio de divisas.");
        }
    }
}