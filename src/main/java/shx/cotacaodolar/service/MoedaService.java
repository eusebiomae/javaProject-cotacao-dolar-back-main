package shx.cotacaodolar.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import shx.cotacaodolar.model.Moeda;
import shx.cotacaodolar.model.Periodo;

@Service
public class MoedaService {

    public List<Moeda> getCotacoesPeriodo(String startDate, String endDate)
            throws IOException, MalformedURLException, ParseException, URISyntaxException {

        Periodo periodo = new Periodo(startDate, endDate);

        String urlString = "https://olinda.bcb.gov.br/olinda/servico/PTAX/versao/v1/odata/CotacaoDolarPeriodo(dataInicial=@dataInicial,dataFinalCotacao=@dataFinalCotacao)?%40dataInicial='"
                + periodo.getDataInicial() + "'&%40dataFinalCotacao='" + periodo.getDataFinal()
                + "'&%24format=json&%24skip=0&%24top=" + periodo.getDiasEntreAsDatasMaisUm();

        // URL(String) por URI + toURL()
        URI uri = new URI(urlString);
        URL url = uri.toURL();

        HttpURLConnection request = (HttpURLConnection) url.openConnection();
        request.connect();

        JsonElement response = JsonParser.parseReader(new InputStreamReader((InputStream) request.getContent()));
        JsonObject rootObj = response.getAsJsonObject();
        JsonArray cotacoesArray = rootObj.getAsJsonArray("value");

        List<Moeda> moedasLista = new ArrayList<>();

        for (JsonElement obj : cotacoesArray) {
            Moeda moedaRef = new Moeda();
            Date data = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    .parse(obj.getAsJsonObject().get("dataHoraCotacao").getAsString());

            moedaRef.preco = obj.getAsJsonObject().get("cotacaoCompra").getAsDouble();
            moedaRef.data = new SimpleDateFormat("dd/MM/yyyy").format(data);
            moedaRef.hora = new SimpleDateFormat("HH:mm:ss").format(data);

            moedasLista.add(moedaRef);
        }

        return moedasLista;
    }
    
    public List<Moeda> getCotacaoAtual() throws IOException, URISyntaxException, ParseException {
        List<Moeda> moedasLista = new ArrayList<>();

        // busca nos últimos 7 dias ate encontrar última data de cotação do BACEN
        int diasMaximosParaBuscar = 7;

        for (int i = 0; i < diasMaximosParaBuscar; i++) {
            // Ajusta a data: hoje menos i dias
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, -i);
            Date dataTentativa = calendar.getTime();
            String dataFormatada = new SimpleDateFormat("dd-MM-yyyy").format(dataTentativa);

            // Monta a URL com a data atual da tentativa
            String urlString = "https://olinda.bcb.gov.br/olinda/servico/PTAX/versao/v1/odata/CotacaoDolarDia(dataCotacao=@dataCotacao)?@dataCotacao='"
                    + dataFormatada + "'&$format=json";

            URI uri = new URI(urlString);
            URL url = uri.toURL();

            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.connect();

            JsonElement response = JsonParser.parseReader(new InputStreamReader((InputStream) request.getContent()));
            JsonObject rootObj = response.getAsJsonObject();
            JsonArray cotacoesArray = rootObj.getAsJsonArray("value");

            if (cotacoesArray != null && cotacoesArray.size() > 0) {
                // Achamos cotação válida: processa e retorna
                for (JsonElement obj : cotacoesArray) {
                    Moeda moedaRef = new Moeda();
                    Date data = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                            .parse(obj.getAsJsonObject().get("dataHoraCotacao").getAsString());

                    moedaRef.preco = obj.getAsJsonObject().get("cotacaoCompra").getAsDouble();
                    moedaRef.data = new SimpleDateFormat("dd/MM/yyyy").format(data);
                    moedaRef.hora = new SimpleDateFormat("HH:mm:ss").format(data);

                    moedasLista.add(moedaRef);
                }
                // Já encontramos, podemos parar o loop
                break;
            }
        }

        return moedasLista;
    }


    
    public List<Moeda> getCotacoesMenoresAtual(String startDate, String endDate)
            throws IOException, MalformedURLException, ParseException, URISyntaxException {

        // 1. Obter a cotação atual
        List<Moeda> cotacaoAtualLista = getCotacaoAtual();
        if (cotacaoAtualLista.isEmpty()) {
            return new ArrayList<>();
        }

        double precoAtual = cotacaoAtualLista.get(0).preco;

        // 2. Obter todas as cotações do período
        List<Moeda> cotacoesPeriodo = getCotacoesPeriodo(startDate, endDate);

        // 3. Filtrar apenas as que são menores que o preço atual
        List<Moeda> menores = new ArrayList<>();
        for (Moeda m : cotacoesPeriodo) {
            if (m.preco < precoAtual) {
                menores.add(m);
            }
        }

        return menores;
    }


}
