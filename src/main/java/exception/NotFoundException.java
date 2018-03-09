package exception;

public class NotFoundException extends Exception {
	private static final long serialVersionUID = -3006486375386577451L;
	private static final String MSG = "La entidad buscada no existe";

	public NotFoundException(String message) {
		super(message);
	}

	public NotFoundException() {
		super(MSG);
	}
}
