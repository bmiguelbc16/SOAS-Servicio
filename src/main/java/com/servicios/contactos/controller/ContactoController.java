package com.servicios.contactos.controller;

import com.servicios.contactos.dtos.ContactosDTO;
import com.servicios.contactos.model.Contacto;
import com.servicios.contactos.service.ContactoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tools.jackson.databind.json.JsonMapper;

import java.util.List;

@RestController
@RequestMapping("/api/contactos")
public class ContactoController {

    private final ContactoService contactoService;
    private final JsonMapper jsonMapper;

    public ContactoController(ContactoService contactoService, JsonMapper jsonMapper) {
        this.contactoService = contactoService;
        this.jsonMapper = jsonMapper;
    }

    @GetMapping
    public List<Contacto> listarContactos() {
        return contactoService.listarContactos();
    }

    @GetMapping("/buscar")
    public List<ContactosDTO> buscarContactos(@RequestParam String persona) {
        return contactoService.buscarPorPersona(persona);
    }

    // INSERTAR: acepta el JSON en el body (Postman) o en la URL (navegador)
    @RequestMapping(value = "/insertar/", method = {RequestMethod.GET, RequestMethod.POST})
    public ContactosDTO insertarContacto(
            @RequestBody(required = false) ContactosDTO desdeBody,
            @RequestParam(value = "contacto", required = false) String contactoJson) {
        ContactosDTO contacto = (desdeBody != null)
                ? desdeBody
                : jsonMapper.readValue(contactoJson, ContactosDTO.class);
        return contactoService.insertarContacto(contacto);
    }
}
