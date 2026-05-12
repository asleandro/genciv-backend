package cliente.domain.exception;

public class InscricaoEstadualInvalidaException extends ClienteException {

   private static final String ERROR_CODE = "INSCRICAO_ESTADUAL_INVALIDA";

   public InscricaoEstadualInvalidaException(String valor){
       super(
               ERROR_CODE,
               String.format("Inscrição estadual inválida: %s", valor)
       );
   }
}
