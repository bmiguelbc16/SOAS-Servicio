package com.servicios.contactos;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ContactosApplication {

    /** Rango de busqueda de puerto libre, estilo `php artisan serve` (Laravel escanea 8000-8009). */
    private static final int PUERTO_INICIAL = 8080;
    private static final int PUERTO_FINAL = 8090;

    public static void main(String[] args) {
        if (!puertoFijadoPorUsuario(args)) {
            int puerto = buscarPuertoLibre();
            System.setProperty("server.port", String.valueOf(puerto));
            System.out.println("[contactos-servicios] Puerto automatico: " + puerto
                    + "  (define SERVER_PORT si quieres fijarlo)");
        }
        SpringApplication.run(ContactosApplication.class, args);
    }

    /** true si el usuario fijo el puerto (SERVER_PORT, -Dserver.port o --server.port): se respeta. */
    private static boolean puertoFijadoPorUsuario(String[] args) {
        return System.getenv("SERVER_PORT") != null
                || System.getProperty("server.port") != null
                || Arrays.stream(args).anyMatch(arg -> arg.startsWith("--server.port"));
    }

    /** Busca el primer puerto libre del rango; si todos estan ocupados, devuelve 0 (el sistema asigna uno). */
    private static int buscarPuertoLibre() {
        for (int puerto = PUERTO_INICIAL; puerto <= PUERTO_FINAL; puerto++) {
            if (puertoDisponible(puerto)) {
                return puerto;
            }
        }
        return 0;
    }

    private static boolean puertoDisponible(int puerto) {
        try (ServerSocket socket = new ServerSocket(puerto)) {
            return true;
        } catch (IOException ocupado) {
            return false;
        }
    }
}
