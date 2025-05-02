package shx.cotacaodolar.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import shx.cotacaodolar.model.Moeda;
import shx.cotacaodolar.service.MoedaService;

@RestController
@RequestMapping("/moeda")
public class MoedaController {

    @Autowired
    private MoedaService moedaService;
    
    @GetMapping
    public String getMoeda() {
        return "Dólar: 5.30";
    }

    // Ex: /moeda/periodo/01-01-2024/04-01-2024
    @GetMapping("/periodo/{data1}/{data2}")
    public List<Moeda> getCotacoesPeriodo(@PathVariable("data1") String startDate,
                                          @PathVariable("data2") String endDate)
            throws IOException, MalformedURLException, ParseException, URISyntaxException {
        return moedaService.getCotacoesPeriodo(startDate, endDate);
    }

    // Ex: /moeda/atual
    @GetMapping("/atual")
    public List<Moeda> getCotacaoAtual()
            throws IOException, MalformedURLException, ParseException, URISyntaxException {
        return moedaService.getCotacaoAtual();
    }

    // Ex: /moeda/menores/01-01-2024/04-01-2024
    @GetMapping("/menores/{data1}/{data2}")
    public List<Moeda> getCotacoesMenoresAtual(@PathVariable("data1") String startDate,
                                               @PathVariable("data2") String endDate)
            throws IOException, MalformedURLException, ParseException, URISyntaxException {
        return moedaService.getCotacoesMenoresAtual(startDate, endDate);
    }
}
