/*
* Autor: Thiago Costa
* Programa para conversão de temperaturas via terminal utilizando Java.
* O programa fez as seguintes validações:
    * As entradas dos valores de temperatura devem ser valores numéricos;
    * Para as unidades de temperatura apenas são válidos: 'C', 'F' ou 'K';
    * A temperatura deve ser > 0 K (impossível temperatura menor);
    * A média das temperaturas foi calculada na unidade absoluta de Kelvin (K).
    *
    *
* */

import java.util.Scanner;

public class ConversorDeTemperatura {
    public static void main(String[] args) {
        try{
            Scanner entrada = new Scanner(System.in);

            System.out.print("-=-=-=-=-=-=-=-=-=-=-=-=-=\nCONVERSOR DE TEMPERATURAS\n-=-=-=-=-=-=-=-=-=-=-=-=-=");
            System.out.print("\nQuantas temperaturas você deseja CONVERTER: ");
            int quantidadeDeTemperaturas = entrada.nextInt();
            char unidadeDeTemperaturaDeOrigem, unidadeDeTemperaturaDeConvertida;
            double valorDeTemperaturaDeOrigem, valorDeTemperaturaDeConvertida;
            double mediaTemperaturasIniciais=0.0, mediaTemperaturasConvertidas=0.0;

            for(int iteracao=0; iteracao<quantidadeDeTemperaturas; iteracao++){
                System.out.print("Digite o formato de unidade da sua temperatura de origem e pressione Enter:");
                System.out.print("\n(Formatos válidos -> 'C': para celsius, 'K': para Kelvin, 'F' )\n");
                unidadeDeTemperaturaDeOrigem = leituraDeUnidadeDaTemperaturaException(entrada.next());
                
                System.out.print("Digite o valor de sua temperatura de origem:");
                valorDeTemperaturaDeOrigem = leituraDeValorDaTemperaturaException(entrada.next(), unidadeDeTemperaturaDeOrigem);
                
                System.out.print("Digite o formato de unidade da temperatura para qual deseja converter e pressione Enter:");
                System.out.print("\n(Formatos válidos -> 'C': para celsius, 'K': para Kelvin, 'F' )\n");
                unidadeDeTemperaturaDeConvertida = leituraDeUnidadeDaTemperaturaException(entrada.next());
                
                valorDeTemperaturaDeConvertida = conversaoDeTemperaturas(unidadeDeTemperaturaDeOrigem, unidadeDeTemperaturaDeConvertida, valorDeTemperaturaDeOrigem);
                imprimirTemperaturaNoConsole("Temperatura de origem fornecida: ", valorDeTemperaturaDeOrigem,unidadeDeTemperaturaDeOrigem);
                imprimirTemperaturaNoConsole("Temperatura convertida: ", valorDeTemperaturaDeConvertida,unidadeDeTemperaturaDeConvertida);

                // Utilizou-se Kelvin como referência para cálculo da média, como é a temperatura absoluta
                mediaTemperaturasIniciais = mediaTemperaturasIniciais + conversaoDeTemperaturas(unidadeDeTemperaturaDeOrigem, 'K', valorDeTemperaturaDeOrigem);
                mediaTemperaturasConvertidas = mediaTemperaturasConvertidas + conversaoDeTemperaturas(unidadeDeTemperaturaDeConvertida, 'K', valorDeTemperaturaDeConvertida);
            }
            mediaTemperaturasIniciais = mediaTemperaturasIniciais/quantidadeDeTemperaturas;
            mediaTemperaturasConvertidas = mediaTemperaturasConvertidas/quantidadeDeTemperaturas;

            // Calculou-se a média usando Celsius como referência
            System.out.println("A média das temperaturas inicias é de: "+mediaTemperaturasIniciais + "K");
            System.out.println("A média das temperaturas convertidas é de: " + mediaTemperaturasConvertidas + "K");
        }catch (Exception err){
            System.err.println(err.getMessage());
        }
    }

    private static void imprimirTemperaturaNoConsole(String message, double temperatura, char unidade){
        if(unidade=='K'){
            // Como é uma  unidade absoluta não utiliza graus centígrados
            System.out.println(message + temperatura + unidade);
        } else {
            // Unidades relativas utiliza graus centígrados '°'
            System.out.println(message + temperatura + "°" + unidade);
        }

    }
    
    private static double conversaoDeTemperaturas(char unidadeTemperaturaOriginal, char unidadeTemperaturaParaConverter, double valorTemperaturaOriginal){
        // Como referência para os cálculos de conversão utilizou-se o link: https://www.infoescola.com/fisica/conversao-de-escalas-termometricas/
        if(unidadeTemperaturaOriginal == unidadeTemperaturaParaConverter){
            return valorTemperaturaOriginal;
        } else if (unidadeTemperaturaOriginal == 'C' && unidadeTemperaturaParaConverter == 'K') {
            return valorTemperaturaOriginal + 273;
        } else if(unidadeTemperaturaOriginal == 'K' && unidadeTemperaturaParaConverter == 'C'){
            return valorTemperaturaOriginal - 273;
        } else if(unidadeTemperaturaOriginal == 'F' && unidadeTemperaturaParaConverter == 'C'){
            return (valorTemperaturaOriginal - 32)/1.8;
        } else if(unidadeTemperaturaOriginal == 'C' && unidadeTemperaturaParaConverter == 'F'){
            return 1.8*valorTemperaturaOriginal + 32;
        } else if(unidadeTemperaturaOriginal == 'K' && unidadeTemperaturaParaConverter == 'F'){
            return (valorTemperaturaOriginal - 273) * 1.8 + 32;
        } else{ // única opção restante é conversão de F para K (uma vez que as unidades e valores foram validados)
            return (valorTemperaturaOriginal - 32)/1.8 + 273;
        }
    }
    private static boolean checarSeTemperaturaEhValida(char unidadeTemperatura, double temperatura) throws Exception{
        // Esse método checa se a temperatura é fisicamente possível. Temperaturas menores que 0 K são
        // fisicamente impossíveis de se alcançar, uma vez que Kelvin é uma medida absoluta que considera as
        // partículas completamente paradas (impossível conseguir temperatura menor que isso
        double temperaturaEmKelvin = conversaoDeTemperaturas(unidadeTemperatura, 'K', temperatura );

        if(temperaturaEmKelvin < 0){
            return false;
        }

        return true;
    }

    private static char leituraDeUnidadeDaTemperaturaException(String entrada) throws Exception{
        char unidadeVerificada = entrada.charAt(0);
        if(checarSeEhNumerico(entrada)){
            // Valida se é numérico
            throw new Exception("Unidade de temperatura inválida! Não pode ser número. (Unidades válidas: 'C', 'F' ou 'K' )");
        }
        else if(entrada.length()>1){
            // Valida temperatura -> deve ser um char (apenas 1 char) e ser 'C', 'F' ou 'K"
            throw new Exception("UNIDADE de temperatura INVÁLIDA! (Unidades válidas: 'C', 'F' ou 'K' )");
        } else if (unidadeVerificada == 'C') {
            return unidadeVerificada;
        } else if (unidadeVerificada == 'F') {
            return unidadeVerificada;
        } else if (unidadeVerificada == 'K') {
            return unidadeVerificada;
        }
        throw new Exception("UNIDADE de temperatura INVÁLIDA! (Unidades válidas: 'C', 'F' ou 'K' )");

    }

    private static double leituraDeValorDaTemperaturaException(String entrada, char unidadeTemperatura) throws Exception{
        if(checarSeEhNumerico(entrada)){
            // Valida se é numérico
            if(checarSeTemperaturaEhValida(unidadeTemperatura, Double.parseDouble(entrada))){
                // Valida se é temperatura possível fisicamente
                return Double.parseDouble(entrada);
            }
            else{
                // Valida se temperatura é menor que 0 K (fisicamente impossível)
                throw new Exception("Temperatura fisicamente impossível! As temperaturas devem ser maiores que 0 K, -273 ºC ou -459.40 °F.");
            }

        } else{
            throw new Exception("Valor da temperatura deve ser NUMÉRICO!");
        }

    }
    private static boolean checarSeEhNumerico(String entrada) {
        try {
            Double.parseDouble(entrada);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }


}