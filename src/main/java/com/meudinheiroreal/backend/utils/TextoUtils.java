package com.meudinheiroreal.backend.utils;

public class TextoUtils {
    // Construtor privado previne que a classe seja instanciada com 'new TextoUtils()'
    private TextoUtils() {
        throw new UnsupportedOperationException("Classe utilitária não deve ser instanciada.");
    }

    //Remove espaços das pontas, transforma a primeira letra em maiúscul e o restante em minúscula. Exemplo: "  mERCADO  " -> "Mercado"

    public static String formatarTexto(String texto) {
        if (texto == null) {
            return null;
        }

        String textoLimpo = texto.trim();

        if (textoLimpo.isEmpty()) {
            return textoLimpo;
        }

        return textoLimpo.substring(0, 1).toUpperCase() + textoLimpo.substring(1).toLowerCase();
    }
}
