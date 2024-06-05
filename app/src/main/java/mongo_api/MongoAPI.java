package mongo_api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
public class MongoAPI {
    static String webService = "https://sa-east-1.aws.data.mongodb-api.com/app/data-glsin/endpoint/data/v1";
    static int codigoSucesso = 200;
    static Map<String, String> headers = new HashMap<>();

    public static String makeRequest(String actionDb, String body) throws Exception {
        String urlParaChamada = webService + actionDb;
        headers.put("Content-Type", "application/json");
        headers.put("api-key", "GkYXzrSsaEgankl5jLkJy2eA7eO5emqfGAb9xVwVvFQVzF1Szv1c6AkSDB39SOAQ");
        try {
            URL url = new URL(urlParaChamada);
            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();

            // Configurações da conexão
            conexao.setRequestMethod("POST");
            conexao.setRequestProperty("Content-Type", "application/json");
            conexao.setRequestProperty("Accept", "application/json");

            // Adiciona os headers à requisição
            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    conexao.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }

            // Envia o corpo da requisição, se houver
            if (body != null && !body.isEmpty()) {
                conexao.setDoOutput(true);
                OutputStream os = conexao.getOutputStream();
                os.write(body.getBytes());
                os.flush();
            }

            if (conexao.getResponseCode() != codigoSucesso){
                throw new RuntimeException("HTTP error code : " + conexao.getResponseCode());}

            BufferedReader resposta = new BufferedReader(new InputStreamReader((conexao.getInputStream())));
            StringBuilder respostaBuilder = new StringBuilder();
            String linha;
            while ((linha = resposta.readLine()) != null) {
                respostaBuilder.append(linha);
            }
            resposta.close();

            return respostaBuilder.toString();
        } catch (Exception e) {
            throw new Exception("ERRO: " + e);
        }
    }
}
