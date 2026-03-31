import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class CalculadoraIMC {

    // Constante que armazena a sequência de escape para limpar a tela do console
    public static String CLEAR_SCREEN = "\033[H\033[2J";

    public static void main(String[] args) {
        // O try-with-resources gerencia o BufferedReader automaticamente, otimizando a memória
        try (BufferedReader leitor = new BufferedReader(new InputStreamReader(System.in))) {
            
            // Limpa a tela no início da execução
            System.out.print(CLEAR_SCREEN);
            System.out.flush();

            System.out.println("=== SISTEMA DE AVALIAÇÃO DE IMC ===");
            
            System.out.print("Digite o seu nome: ");
            String nome = leitor.readLine();

            System.out.print("Gênero (M - Masculino, F - Feminino, N - Não informar): ");
            // Captura a linha, remove espaços extras, converte para maiúsculo e pega o 1º caractere
            String entradaGenero = leitor.readLine().trim().toUpperCase();
            char genero = entradaGenero.isEmpty() ? 'N' : entradaGenero.charAt(0);

            System.out.print("Altura em metros (ex: 1.75): ");
            double altura = Double.parseDouble(leitor.readLine().replace(",", "."));

            System.out.print("Peso em kg (ex: 70.5): ");
            double peso = Double.parseDouble(leitor.readLine().replace(",", "."));

            // Cálculo do IMC
            double imc = peso / (altura * altura);
            
            // Chama o método auxiliar para descobrir a classificação
            String classificacao = classificarIMC(imc, genero);

            // Limpa a tela novamente para dar destaque exclusivo ao resultado
            System.out.print(CLEAR_SCREEN);
            System.out.flush();

            // Text Block alinhado para uma exibição visualmente mais bonita e limpa
            String resultado = """
                              RESUMO DA AVALIAÇÃO

                    Nome..........: %s
                    Gênero........: %c
                    Altura........: %.2f m
                    Peso..........: %.2f kg
                    IMC Calculado.: %.2f
                    Classificação.: %s    
                    """.formatted(nome, genero, altura, peso, imc, classificacao);

            System.out.println(resultado);

        } catch (IOException e) {
            System.out.println("Erro de leitura de dados: " + e.getMessage());
        } catch (NumberFormatException e) {
            // Caso o usuário digite texto onde deveria ser número
            System.out.println("\n[ERRO] Formato de número inválido. Reinicie o programa e use apenas números para peso e altura.");
        }
    }

   public static String classificarIMC(double imc, char genero) {
        switch (genero) {
            case 'M':
                if (imc < 20) return "Abaixo do Normal";
                if (imc < 25) return "Normal";
                if (imc < 30) return "Obesidade Leve";
                if (imc < 40) return "Obesidade Moderada";
                return "Obesidade Mórbida";
                
            case 'F':
            case 'N':
            default:
                if (imc < 19) return "Abaixo do Normal";
                if (imc < 24) return "Normal";
                if (imc < 29) return "Obesidade Leve";
                if (imc < 39) return "Obesidade Moderada";
                return "Obesidade Mórbida";
        }
    }
}
