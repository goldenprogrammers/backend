package com.actionsMicroservice.exceptions;

public class ActionCreationException extends RuntimeException {
    public ActionCreationException(String message) {
        super(message);
    }

    public static class RequiredField extends ActionCreationException {
        public RequiredField(String field) {
            super("O campo " + field + " é obrigatório.");
        }
    }

    public static class TitleException extends ActionCreationException {
        public TitleException() {
            super("O título pode ter no máximo 80 caracteres.");
        }
    }

    public static class DescriptionException extends ActionCreationException {
        public DescriptionException() {
            super("A descrição pode ter no máximo 4096 caracteres.");
        }
    }

    public static class ImageSizeException extends ActionCreationException {
        public ImageSizeException() { super("O tamanho da imagem deve ser de no máximo 2MB."); }
    }
}
